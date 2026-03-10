/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.Department;
import business.Levels;
import business.School;
import business.SchoolCycle;

import business.Students;
import business.Teachers;
import business.User;
import database.DepartmentDB;
import database.UserDB;
import database.LevelDB;
import database.SchoolDB;
import database.StudentDB;
import database.TeacherDB;
import database.SchoolCycleDB;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.catalina.realm.SecretKeyCredentialHandler;

/**
 *
 * @author kembe
 */
@WebServlet(name = "Private", urlPatterns = {"/Private"})
@MultipartConfig

public class Private extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedInUser = null;
        String ViewUser = null;

        ArrayList<String> errors = new ArrayList<>();

        ArrayList<String> wrong = new ArrayList<>();
        ArrayList<String> badMessage = new ArrayList<>();
        ArrayList<String> ERRORS = new ArrayList<>();
        ArrayList<String> departmentError = new ArrayList<>();

        int sectionDep = 0;
        if (session != null) {

            loggedInUser = (User) session.getAttribute("loggedInUser");
            ViewUser = (String) session.getAttribute("ViewUser");
        }

        //if the user isn't logged in, direct them to the Public controller
        if (loggedInUser == null) {
            response.sendRedirect("Public?action=login");

            return;
        }

        String action = request.getParameter("action");
        String url = "";

        if (action == null) {

            action = "default";
        }

        try {
            switch (action) {
                case "gotoProfile":

                    int schoolID = loggedInUser.getSchoolID();

                    School school = SchoolDB.getSchoolByID(schoolID);

                    int totalUsers = UserDB.countUsersBySchool(schoolID);
                    int totalAdmin = UserDB.countUsersByRoleAndSchool("Admin", schoolID);
                    ;
                    int totalTeachers = UserDB.countUsersByRoleAndSchool("TEACHER", schoolID);
                    int totalStudents = UserDB.countUsersByRoleAndSchool("STUDENT", schoolID);

                    request.setAttribute("totalUsers", totalUsers);
                    request.setAttribute("totalAdmin", totalAdmin);
                    request.setAttribute("totalTeachers", totalTeachers);
                    request.setAttribute("totalStudents", totalStudents);

                    request.setAttribute("loggedInUser", loggedInUser);
                    request.setAttribute("school", school);

                    url = "/Admin/dashboard.jsp";
                    break;
                case "listUsers":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    LinkedHashMap<String, User> users = UserDB.selectAllUsersByID(schoolID);

                    request.setAttribute("users", users);
                    request.setAttribute("school", school);

                    url = "/Admin/users.jsp";
                    break;
                case "gotoAddTeacher":
                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    request.setAttribute("school", school);
                    url = "/Admin/teachers.jsp";

                    break;

                case "addTeacher":

                    boolean isValid = true;
                    // TEACHER 
                    String firstName = request.getParameter("firstName");
                    String lastName = request.getParameter("lastName");
                    String phoneNumber = request.getParameter("phoneNumber");
                    String officeLocation = request.getParameter("officeLocation");
                    String specification = request.getParameter("specification");
                    String hireDates = request.getParameter("hireDate");
                    String isActive = request.getParameter("status");
                    String qualification = request.getParameter("qualification");

                    LocalDate hireDate = null;

                    // READ USER ADMIN
                    String username = request.getParameter("username");
                    String adminEmail = request.getParameter("adminemail");
                    String adminPhone = request.getParameter("adminphone");
                    String role = "TEACHER";
                    String password = request.getParameter("password");
                    String confirmPassword = request.getParameter("confirmpassword");
                    String isActives = "ACTIVE";

                    //VALIDATION SCHOOL
                    if (firstName == null || firstName.isEmpty() || firstName.trim().length() <= 0 || firstName.trim().length() > 50) {
                        errors.add("First Name must be between 1 and 50 characters.");
                        isValid = false;
                    } else {
                        firstName = firstName.trim();
                    }

                    if (lastName == null || lastName.trim().length() <= 0 || lastName.trim().length() > 50 || lastName.trim().isEmpty()) {
                        errors.add("Short Name must be 1-50 characters.");
                        isValid = false;
                    } else {
                        lastName = lastName.trim();

                    }

                    if (officeLocation == null || officeLocation.isEmpty()) {

                        errors.add("office number cannot be empty.");

                        isValid = false;
                    } else {

                        officeLocation = officeLocation.trim();
                    }

                    if (specification == null || specification.isEmpty()) {

                        errors.add("Specification cannot be empty.");

                        isValid = false;
                    } else {

                        specification = specification.trim();
                    }

                    if (hireDates == null || hireDates.isEmpty()) {

                        errors.add("Hire Date cannot be empty.");

                        isValid = false;
                    } else {

                        hireDate = LocalDate.parse(hireDates);
                    }
                    if (isActive == null || isActive.isEmpty()) {

                        errors.add("Status cannot be empty.");

                        isValid = false;
                    } else {

                        isActive = isActive.trim();
                    }

                    if (phoneNumber.length() > 15) {

                        errors.add("Phone Number cannot be more then 15 characters.");
                        isValid = false;

                    } else {

                        phoneNumber = phoneNumber.trim();
                    }

                    //VALIDATION USER ADMIN
                    if (username == null || username.trim().isEmpty()) {
                        errors.add("Username is required.");
                        isValid = false;
                    } else {
                        username = username.trim();
                        if (UserDB.usernameExists(username)) {
                            errors.add("Username already exists.");
                            isValid = false;
                        }
                    }

                    if (adminEmail == null || adminEmail.trim().isEmpty()) {
                        errors.add("Admin email is required.");
                        isValid = false;
                    } else {
                        adminEmail = adminEmail.trim();
                        if (UserDB.adminEmailExists(adminEmail)) {
                            errors.add("Admin email already exists.");
                            isValid = false;
                        }
                    }

                    if (adminPhone.length() > 15) {

                        errors.add("Phone Number cannot be more then 15 characters.");
                        isValid = false;

                    } else {

                        adminPhone = adminPhone.trim();
                    }

                    // Password validation 
                    if (password == null || password.isBlank()) {
                        errors.add("Password cannot be empty.");
                        isValid = false;
                    } else {
                        if (password.length() < 10) {
                            errors.add("Password must be at least 10 characters.");
                            isValid = false;
                        }
                        if (!password.matches(".*[A-Z].*")) {
                            errors.add("Password must contain at least one uppercase letter.");
                            isValid = false;
                        }
                        if (!password.matches(".*[a-z].*")) {
                            errors.add("Password must contain at least one lowercase letter.");
                            isValid = false;
                        }
                        if (!password.matches(".*[0-9].*")) {
                            errors.add("Password must contain at least one number.");
                            isValid = false;
                        }
                        if (!password.matches(".*[^a-zA-Z0-9].*")) {
                            errors.add("Password must contain at least one special character.");
                            isValid = false;
                        }
                    }

                    if (confirmPassword == null || !password.equals(confirmPassword)) {
                        errors.add("Password must match confirmation password.");
                        isValid = false;
                    }

                    // STOP HERE if invalid 
                    if (!isValid) {
                        schoolID = loggedInUser.getSchoolID();
                        school = SchoolDB.getSchoolByID(schoolID);
                        request.setAttribute("school", school);
                        request.setAttribute("errors", errors);
                        url = "/Admin/teachers.jsp";
                        break;
                    }

                    //BUILD OBJECTS
                    schoolID = loggedInUser.getSchoolID();

                    Teachers teacher = new Teachers();
                    teacher.setFirstName(firstName);
                    teacher.setLastName(lastName);
                    teacher.setSubject(specification);
                    teacher.setQualification(qualification);
                    teacher.setPhoneNumber(phoneNumber);
                    teacher.setOfficeLocation(officeLocation);
                    teacher.setHireDate(hireDate);
                    teacher.setIsActive(isActive);
                    teacher.setSchoolID(schoolID);

                    // Hash password
                    String hash = null;

                    try {
                        SecretKeyCredentialHandler ch = new SecretKeyCredentialHandler();
                        ch.setAlgorithm("PBKDF2WithHmacSHA256");
                        ch.setKeyLength(256);
                        ch.setSaltLength(16);
                        ch.setIterations(4096);

                        hash = ch.mutate(password);

                    } catch (Exception e) {

                        request.setAttribute("errors", "Password encryption failed.");
                        url = "/Admin/teachers.jsp";
                        break;
                    }

                    User user = new User(username, hash, role, adminEmail, adminPhone, schoolID, isActives);

                    //DB (transaction)
                    try {
                        boolean ok = TeacherDB.registerTeacher(user, teacher);

                        if (ok) {
                            request.setAttribute("message", "Registration successful.");
                            url = "/Admin/teachers.jsp";
                        } else {
                            request.setAttribute("errors", "Registration failed.");
                            url = "/Admin/teachers.jsp";
                        }

                    } catch (SQLException | NamingException ex) {
                        ex.printStackTrace();

                    }
                    break;
                case "listTeachers":

                    schoolID = loggedInUser.getSchoolID();

                    school = SchoolDB.getSchoolByID(schoolID);

                    LinkedHashMap<String, Teachers> teachers = TeacherDB.selectAllTeachersByID(schoolID);

                    request.setAttribute("teachers", teachers);
                    request.setAttribute("school", school);

                    url = "/Admin/allTeachers.jsp";

                    break;

                case "gotoChooseSection":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    LinkedHashMap<String, Department> departments
                            = DepartmentDB.selectAllDepartmentByIDAndStatus(schoolID);

                    request.setAttribute("departments", departments.values());
                    request.setAttribute("school", school);
                    url = "/Admin/chooseSection.jsp";
                    break;
                case "gotoAddStudent":

                    boolean isDepOk = true;
                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    String deptIdStr = request.getParameter("departmentID");

                    if (deptIdStr == null || deptIdStr.trim().isEmpty()) {

                        departmentError.add("Please select Section.");
                        isDepOk = false;
                    } else {

                        sectionDep = Integer.parseInt(deptIdStr);

                    }

                    if (!isDepOk) {
                        schoolID = loggedInUser.getSchoolID();
                        school = SchoolDB.getSchoolByID(schoolID);
                        String deptarIdStr = request.getParameter("departmentID");

                        sectionDep = Integer.parseInt(deptarIdStr);
                        LinkedHashMap<String, Department> dep
                                = DepartmentDB.selectAllDepartmentByIDAndStatus(schoolID);

                        request.setAttribute("departments", dep.values());
                        request.setAttribute("departmentID", sectionDep);
                        request.setAttribute("school", school);
                        request.setAttribute("errors", departmentError);
                        url = "/Admin/chooseSection.jsp";
                        break;
                    }

                    LinkedHashMap<String, Levels> ll
                            = LevelDB.selectLevelBySchoolAndDepartment(schoolID, sectionDep);
                    request.setAttribute("levels", ll);
                    request.setAttribute("departmentID", sectionDep);
                    request.setAttribute("school", school);
                    url = "/Admin/addStudents.jsp";
                    break;
                case "addStudent":

                    //GET PARAMETERES FOR STUDENT 
                    boolean isOk = true;

                    String depetIdStr = request.getParameter("departmentID");

                    sectionDep = Integer.parseInt(depetIdStr);
                    String registrationNumber = request.getParameter("registrationNumber");
                    String SFirstName = request.getParameter("firstName");
                    String SMiddleName = request.getParameter("middleName");
                    String SLastName = request.getParameter("lastName");
                    String gender = request.getParameter("gender");
                    String birthDates = request.getParameter("birthDate");
                    String gradeLevel = request.getParameter("gradeLevel");

                    String enrollmentDates = request.getParameter("enrollmentDate");
                    String academicYear = request.getParameter("academicYear");
                    String SphoneNumber = request.getParameter("phoneNumber");
                    String address = request.getParameter("address");
                    String SisActive = request.getParameter("isActive");
                    String theLevel = request.getParameter("levelID");
                    LocalDate birthDate = null;
                    LocalDate enrollmentDate = null;

                    int theLevels = 0;

                    //GET PARAMETERS FOR ADMIN STUDENT 
                    String Susername = request.getParameter("username");
                    String SadminEmail = request.getParameter("adminemail");
                    String SadminPhone = request.getParameter("adminphone");
                    String Srole = "STUDENT";
                    String Spassword = request.getParameter("password");
                    String SconfirmPassword = request.getParameter("confirmpassword");
                    String isAct = "ACTIVE";

                    //VALIDATION FOR STUDENT INFORMATION
                    if (registrationNumber == null || registrationNumber.isEmpty()) {

                        badMessage.add("Student ID cannot be empty.");
                    } else if (registrationNumber.length() < 6 || registrationNumber.length() > 35) {
                        badMessage.add("Student ID must be between 6 and 35 charaters.");
                    } else {

                        registrationNumber = registrationNumber.trim();
                        if (StudentDB.reistrationNumberForStudentExists(registrationNumber)) {
                            badMessage.add("This student ID already exist for another student. It must be unique.");
                            isOk = false;

                        }
                        String regex = "^[A-Z]{2}[0-9]+$";
                        if (!registrationNumber.matches(regex)) {

                            badMessage.add("Student ID must start with 2 uppercase letters and followed by numbers.");
                            isOk = false;
                        }
                    }

                    if (SFirstName == null || SFirstName.isEmpty() || SFirstName.trim().length() <= 0 || SFirstName.trim().length() > 50) {
                        errors.add("First Name must be between 1 and 50 characters.");
                        isOk = false;
                    } else {
                        SFirstName = SFirstName.trim();
                    }
                    if (SMiddleName.trim().length() > 50) {

                        errors.add("Middle Name must be between 1 and 50 characters.");
                        isOk = false;
                    } else {

                        SMiddleName = SMiddleName.trim();
                    }

                    if (SLastName == null || SLastName.trim().length() <= 0 || SLastName.trim().length() > 50 || SLastName.trim().isEmpty()) {
                        errors.add("Short Name must be 1-50 characters.");
                        isOk = false;
                    } else {
                        SLastName = SLastName.trim();

                    }
                    if (theLevel == null || theLevel.trim().isEmpty()) {

                        errors.add("You must select Student Level");
                        isOk = false;

                    } else {

                        theLevels = Integer.parseInt(theLevel);

                    }

                    if (gender == null || gender.isEmpty()) {

                        badMessage.add("The Gender cannot be empty.");
                        isOk = false;
                    } else if (!gender.equals("MALE") && !gender.equals("FEMELE") && !gender.equals("OTHER")) {

                        badMessage.add("ERROR, You must select Male, female or other.");
                        isOk = false;

                    } else {

                        gender = gender.trim();
                    }
                    if (birthDates == null || birthDates.isEmpty()) {

                        badMessage.add("Select or put your Birth Date");
                        isOk = false;
                    } else {

                        birthDate = LocalDate.parse(birthDates);
                    }

                    if (enrollmentDates == null || enrollmentDates.isEmpty()) {

                        badMessage.add("Enrollment Date cannot be empty.");
                        isOk = false;
                    } else {

                        enrollmentDate = LocalDate.parse(enrollmentDates);
                    }

                    if (academicYear == null || academicYear.isEmpty()) {

                        badMessage.add("academic year cannot be empty.");
                        isOk = false;

                    } else if (academicYear.length() < 9 || academicYear.length() > 9) {

                        badMessage.add("Wrong data for Academic year. e.g 2020-2021.");
                        isOk = false;
                    } else {

                        academicYear = academicYear.trim();
                    }
                    if (SphoneNumber == null || SphoneNumber.isEmpty()) {

                        badMessage.add("Phone number cannot be empty.");
                        isOk = false;
                    } else {

                        SphoneNumber = SphoneNumber.trim();
                        String regex = "^\\+1[0-9]{10}";

                        if (!SphoneNumber.matches(regex)) {

                            badMessage.add("Put the correct phone number e.g +14004205656.");
                            isOk = false;
                        }
                    }
                    if (address == null || address.isEmpty()) {

                        badMessage.add("Address cannot be empty.");
                        isOk = false;
                    } else if (address.length() > 255) {

                        badMessage.add("Address cannot be more then 255 charaxters.");
                        isOk = false;
                    } else {

                        address = address.trim();
                    }
                    if (SisActive == null || SisActive.isEmpty()) {

                        badMessage.add("Status cannot be empty.");
                        isOk = false;
                    } else if (!SisActive.equals("ACTIVE") && !SisActive.equals("INACTIVE")) {

                        badMessage.add("Wrong data on Status.");
                        isOk = false;
                    } else {
                        SisActive = SisActive.trim();
                    }

                    //VALIDATION FOR ADMIN STUDENT
                    if (Susername == null || Susername.trim().isEmpty()) {
                        badMessage.add("Username is required.");
                        isOk = false;
                    } else {
                        username = Susername.trim();
                        if (UserDB.usernameExists(Susername)) {
                            badMessage.add("Username already exists.");
                            isOk = false;
                        }
                    }

                    if (SadminEmail == null || SadminEmail.trim().isEmpty()) {
                        badMessage.add("Admin email is required.");
                        isOk = false;
                    } else {
                        SadminEmail = SadminEmail.trim();
                        if (UserDB.adminEmailExists(SadminEmail)) {
                            badMessage.add("Admin email already exists.");
                            isOk = false;
                        }
                    }

                    if (SadminPhone.length() > 15) {

                        badMessage.add("Phone Number cannot be more then 15 characters.");
                        isOk = false;

                    } else {

                        SadminPhone = SadminPhone.trim();
                    }

                    // Password validation 
                    if (Spassword == null || Spassword.isBlank()) {
                        badMessage.add("Password cannot be empty.");
                        isOk = false;
                    } else {
                        if (Spassword.length() < 10) {
                            badMessage.add("Password must be at least 10 characters.");
                            isOk = false;
                        }
                        if (!Spassword.matches(".*[A-Z].*")) {
                            badMessage.add("Password must contain at least one uppercase letter.");
                            isOk = false;
                        }
                        if (!Spassword.matches(".*[a-z].*")) {
                            badMessage.add("Password must contain at least one lowercase letter.");
                            isOk = false;
                        }
                        if (!Spassword.matches(".*[0-9].*")) {
                            badMessage.add("Password must contain at least one number.");
                            isOk = false;
                        }
                        if (!Spassword.matches(".*[^a-zA-Z0-9].*")) {
                            badMessage.add("Password must contain at least one special character.");
                            isOk = false;
                        }
                    }

                    if (SconfirmPassword == null || !Spassword.equals(SconfirmPassword)) {
                        badMessage.add("Password must match confirmation password.");
                        isOk = false;
                    }
                    // STOP HERE if invalid 
                    if (!isOk) {
                        schoolID = loggedInUser.getSchoolID();
                        school = SchoolDB.getSchoolByID(schoolID);
                        request.setAttribute("school", school);
                        String depetaIdStr = request.getParameter("departmentID");
                        sectionDep = Integer.parseInt(depetIdStr);
                        LinkedHashMap<String, Levels> l
                                = LevelDB.selectLevelBySchoolAndDepartment(schoolID, sectionDep);

                        request.setAttribute("levels", l);
                        request.setAttribute("departmentID", sectionDep);
                        request.setAttribute("badMessage", badMessage);
                        url = "/Admin/addStudents.jsp";
                        break;
                    }

                    //SET AND INSERT DATA, build object
                    schoolID = loggedInUser.getSchoolID();

                    Students s = new Students();
                    s.setSchoolID(schoolID);
                    s.setRegistrationNumber(registrationNumber);
                    s.setFirstName(SFirstName);
                    s.setLastName(SLastName);
                    s.setGender(gender);
                    s.setDateOfBirth(birthDate);
                    s.setMiddleName(SMiddleName);
                    s.setEnrollmentDate(enrollmentDate);
                    s.setAcademicYear(academicYear);
                    s.setPhoneNumber(SphoneNumber);
                    s.setAddress(address);
                    s.setIsActive(SisActive);
                    s.setLevelID(theLevels);

                    // Hash password
                    String hashed = null;

                    try {
                        SecretKeyCredentialHandler ch = new SecretKeyCredentialHandler();
                        ch.setAlgorithm("PBKDF2WithHmacSHA256");
                        ch.setKeyLength(256);
                        ch.setSaltLength(16);
                        ch.setIterations(4096);

                        hashed = ch.mutate(Spassword);

                    } catch (Exception e) {

                        badMessage.add("Password encryption failed.");
                        request.setAttribute("badMessage", badMessage);
                        url = "/Admin/addStudents.jsp";
                        break;
                    }

                    User u = new User(Susername, hashed, Srole, SadminEmail, SadminPhone, schoolID, isAct);

                    //DB (transaction)
                    try {
                        boolean good = StudentDB.registerStudent(u, s);

                        if (good) {

                            request.setAttribute("message", "Registration successful.");
                             request.setAttribute("departmentID", sectionDep);
                            url = "/Admin/addStudents.jsp";
                        } else {
                            badMessage.add("Registration failed.");
                            request.setAttribute("badMessage", badMessage);
                            url = "/Admin/addStudents.jsp";
                        }

                    } catch (SQLException | NamingException ex) {
                        ex.printStackTrace();

                        request.setAttribute("message", "ERROR, " + ex.getClass());

                        url = "/errorPage.jsp";

                    }

                    break;

                case "listStudents":

                    schoolID = loggedInUser.getSchoolID();

                    school = SchoolDB.getSchoolByID(schoolID);

                    LinkedHashMap<String, Students> student = StudentDB.selectAllStudentsByID(schoolID);

                    request.setAttribute("student", student);
                    request.setAttribute("school", school);

                    url = "/Admin/students.jsp";
                    break;

                case "inactiveStudent":
                    String registrationNumb = request.getParameter("registrationNumber");

                    schoolID = loggedInUser.getSchoolID();
                    //school = KelasiDB.getSchoolByID(schoolID);

                    StudentDB.deactiveStudent(registrationNumb, schoolID);

                    response.sendRedirect("Private?action=listStudents");
                    return;
                //break;

                case "activeStudent":

                    String registrationNum = request.getParameter("registrationNumber");

                    schoolID = loggedInUser.getSchoolID();
                    //school = KelasiDB.getSchoolByID(schoolID);

                    StudentDB.activeStudent(registrationNum, schoolID);

                    response.sendRedirect("Private?action=listStudents");

                    return;

                case "gotoAddUser":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    request.setAttribute("school", school);
                    url = "/Admin/addUsers.jsp";

                    break;

                case "addUsers":

                    //GET PARAMETERS FOR ADMIN STUDENT 
                    String aUsername = request.getParameter("username");
                    String aAdminEmail = request.getParameter("adminemail");
                    String aAdminPhone = request.getParameter("adminphone");
                    String aRole = "ADMIN";
                    String aPassword = request.getParameter("password");
                    String aConfirmPassword = request.getParameter("confirmpassword");
                    String isActiv = "ACTIVE";

                    //VALIDATION FOR ADMIN STUDENT
                    boolean isGood = true;
                    if (aUsername == null || aUsername.trim().isEmpty()) {
                        badMessage.add("Username is required.");
                        isOk = false;
                    } else {
                        aUsername = aUsername.trim();
                        if (UserDB.usernameExists(aUsername)) {
                            badMessage.add("Username already exists.");
                            isGood = false;
                        }
                    }

                    if (aAdminEmail == null || aAdminEmail.trim().isEmpty()) {
                        badMessage.add("Admin email is required.");
                        isGood = false;
                    } else {
                        SadminEmail = aAdminEmail.trim();
                        if (UserDB.adminEmailExists(SadminEmail)) {
                            badMessage.add("Admin email already exists.");
                            isGood = false;
                        }
                    }

                    if (aAdminPhone.length() > 15) {

                        badMessage.add("Phone Number cannot be more then 15 characters.");
                        isGood = false;

                    } else {

                        aAdminPhone = aAdminPhone.trim();
                    }

                    // Password validation 
                    if (aPassword == null || aPassword.isBlank()) {
                        badMessage.add("Password cannot be empty.");
                        isGood = false;
                    } else {
                        if (aPassword.length() < 10) {
                            badMessage.add("Password must be at least 10 characters.");
                            isGood = false;
                        }
                        if (!aPassword.matches(".*[A-Z].*")) {
                            badMessage.add("Password must contain at least one uppercase letter.");
                            isGood = false;
                        }
                        if (!aPassword.matches(".*[a-z].*")) {
                            badMessage.add("Password must contain at least one lowercase letter.");
                            isGood = false;
                        }
                        if (!aPassword.matches(".*[0-9].*")) {
                            badMessage.add("Password must contain at least one number.");
                            isGood = false;
                        }
                        if (!aPassword.matches(".*[^a-zA-Z0-9].*")) {
                            badMessage.add("Password must contain at least one special character.");
                            isGood = false;
                        }
                    }

                    if (aConfirmPassword == null || !aPassword.equals(aConfirmPassword)) {
                        badMessage.add("Password must match confirmation password.");
                        isOk = false;
                    }
                    // STOP HERE if invalid 
                    if (!isGood) {
                        request.setAttribute("badMessage", badMessage);
                        url = "/Admin/addUsers.jsp";
                        break;
                    }

                    // Hash password
                    String hashedPass = null;

                    schoolID = loggedInUser.getSchoolID();

                    try {
                        SecretKeyCredentialHandler ch = new SecretKeyCredentialHandler();
                        ch.setAlgorithm("PBKDF2WithHmacSHA256");
                        ch.setKeyLength(256);
                        ch.setSaltLength(16);
                        ch.setIterations(4096);

                        hashedPass = ch.mutate(aPassword);

                    } catch (Exception e) {

                        badMessage.add("Password encryption failed.");
                        request.setAttribute("badMessage", badMessage);
                        url = "/Admin/addUsers.jsp";
                        break;
                    }

                    User us = new User(aUsername, hashedPass, aRole, aAdminEmail, aAdminPhone, schoolID, isActiv);

                    try {
                        UserDB.insertAdmin(us);

                        if (isGood) {

                            request.setAttribute("message", "Registration successful.");
                            url = "/Admin/addUsers.jsp";
                        } else {
                            badMessage.add("Registration failed.");
                            request.setAttribute("badMessage", badMessage);
                            url = "/Admin/addUsers.jsp";
                        }

                    } catch (SQLException | NamingException ex) {
                        ex.printStackTrace();

                        request.setAttribute("message", "ERROR, " + ex.getClass());

                        url = "/errorPage.jsp";
                    }

                    break;

                case "inactiveTeacher":
                    String teacherID = request.getParameter("teacherID");

                    schoolID = loggedInUser.getSchoolID();
                    //school = KelasiDB.getSchoolByID(schoolID);

                    int teacherid = Integer.parseInt(teacherID);

                    TeacherDB.deactiveTeacher(teacherid, schoolID);

                    response.sendRedirect("Private?action=listTeachers");

                    return;
                //break;

                case "activeTeacher":

                    String teacherId = request.getParameter("teacherID");

                    schoolID = loggedInUser.getSchoolID();
                    //school = KelasiDB.getSchoolByID(schoolID);

                    int teacherIDS = Integer.parseInt(teacherId);
                    TeacherDB.activeTeacher(teacherIDS, schoolID);

                    response.sendRedirect("Private?action=listTeachers");

                    return;

                case "inactiveUser":
                    String useID = request.getParameter("userID");

                    schoolID = loggedInUser.getSchoolID();
                    int userIDS = loggedInUser.getUserID();
                    //school = KelasiDB.getSchoolByID(schoolID);

                    int userId = Integer.parseInt(useID);

                    HttpSession se = request.getSession();
                    if (userId == userIDS) {

                        request.getSession().setAttribute("me",
                                "You cannot deactivate your own administrator account.");
                        response.sendRedirect("Private?action=listUsers");
                        return;
                    } else {

                        se.removeAttribute("me");

                    }

                    UserDB.deactiveUser(userId, schoolID, userIDS);

                    response.sendRedirect("Private?action=listUsers");

                    return;
                //break;                //break;

                case "activeUser":

                    String userid = request.getParameter("userID");

                    schoolID = loggedInUser.getSchoolID();
                    //school = KelasiDB.getSchoolByID(schoolID);

                    int userID = Integer.parseInt(userid);
                    UserDB.activeUser(userID, schoolID);

                    response.sendRedirect("Private?action=listUsers");

                    return;

                case "gotoSchoolProfile":

                    schoolID = loggedInUser.getSchoolID();

                    School schools = SchoolDB.getSchoolByID(schoolID);

                    //SchoolCycle cycles = SchoolCycleDB.getCycleByID(schoolID);
                    LinkedHashMap<String, SchoolCycle> cycles = SchoolCycleDB.selectAllCycleByID(schoolID);

                    request.setAttribute("loggedInUser", loggedInUser);
                    request.setAttribute("school", schools);
                    request.setAttribute("cycles", cycles);

                    url = "/Admin/schoolProfile.jsp";
                    break;

                case "changeLogo":

                    boolean isVali = true;
                    List<String> error = new ArrayList<>();

                    User loggedInUser2 = (User) session.getAttribute("loggedInUser");
                    if (loggedInUser2 == null) {
                        response.sendRedirect("Public?action=login");
                        return;
                    }

                    int schoolId = loggedInUser2.getSchoolID();
                    School sc = SchoolDB.getSchoolByID(schoolId);

                    Part logoPart = request.getPart("schoollogo");
                    String logoFileName = null;

                    if (logoPart != null && logoPart.getSize() > 0) {

                        if (logoPart.getSize() > 2 * 1024 * 1024) {
                            error.add("Logo file is too large (max 2MB).");
                            isVali = false;
                        }

                        String contentType = logoPart.getContentType();
                        if (!contentType.equals("image/png") && !contentType.equals("image/jpeg")) {
                            error.add("Logo must be PNG or JPG image.");
                            isVali = false;
                        }

                        String originalName = Paths.get(logoPart.getSubmittedFileName())
                                .getFileName().toString();

                        String ext = originalName.substring(originalName.lastIndexOf('.') + 1);

                        logoFileName = "school_" + sc.getSchoolID()
                                + "_" + System.currentTimeMillis()
                                + "." + ext;
                    }

                    if (isVali && logoPart != null && logoPart.getSize() > 0) {

                        String uploadPath = getServletContext().getRealPath("/uploads/logos");
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }

                        logoPart.write(uploadPath + File.separator + logoFileName);

                        sc.setSchoolLogo(logoFileName);

                        SchoolDB.updateSchoolLogo(sc.getSchoolID(), sc.getSchoolLogo());

                        session.setAttribute("school", sc);

                        session.setAttribute("loggedInUser", loggedInUser2);

                        request.setAttribute("successMessage", "Logo updated successfully.");
                        response.sendRedirect(request.getContextPath() + "/Private?action=gotoSchoolProfile");
                        return;
                    }

                    if (!isVali) {
                        request.setAttribute("errors", error);
                        request.getRequestDispatcher("/Admin/schoolProfile.jsp").forward(request, response);
                        return;
                    }

                    break;

                case "gotoEditSchoolProfile":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    request.setAttribute("school", school);

                    url = "/Admin/editSchool.jsp";

                    break;

                case "editSchool":

                    boolean isFine = true;

                    // READ SCHOOL
                    String schoolName = request.getParameter("schoolname");
                    String shortName = request.getParameter("shortname");
                    //String registrationN = request.getParameter("registrationNumber");
                    String schoolType = request.getParameter("schooltype");
                    String schoolLevel = request.getParameter("schoollevel");
                    String country = request.getParameter("country");
                    String schoolCity = request.getParameter("schoolcity");
                    String schoolAddress = request.getParameter("schooladdress");
                    String website = request.getParameter("website");
                    String schoolEmail = request.getParameter("schoolemail");

                    //VALIDATION SCHOOL
                    if (schoolName == null || schoolName.trim().isEmpty() || schoolName.trim().length() < 3 || schoolName.trim().length() > 100) {
                        wrong.add("School Name must be between 3 and 100 characters.");
                        isFine = false;
                    } else {
                        schoolName = schoolName.trim();
                    }

                    if (shortName == null || shortName.trim().length() < 2 || shortName.trim().length() > 10) {
                        wrong.add("Short Name must be 2-10 characters.");
                        isFine = false;
                    } else {
                        shortName = shortName.trim();
                        if (!shortName.matches("[A-Z0-9]+")) {
                            wrong.add("Short Name must contain only uppercase letters and numbers.");
                            isFine = false;
                        }
                    }

                    if (website != null) {
                        website = website.trim();
                    }
                    if (website != null && !website.isEmpty()) {
                        if (!(website.startsWith("http://") || website.startsWith("https://"))) {
                            wrong.add("Website must start with http:// or https://\n");
                            isFine = false;
                        }
                    }
                    if (schoolType == null || schoolType.isEmpty()) {

                        wrong.add("Schoool Type cannot be empty.");
                        isFine = false;
                    } else if (!"PUBLIC".equals(schoolType) && !"PRIVATE".equals(schoolType)
                            && !"CCHARTER".equals(schoolType) && !"RELIGION".equals(schoolType) && !"OTHER".equals(schoolType)) {

                        wrong.add("Schoool Type cannot be different than the option.");
                        isFine = false;
                    } else if (schoolType != null) {

                        schoolType = schoolType.trim();
                    }
                    if (schoolLevel == null || schoolLevel.isEmpty()) {
                        wrong.add("Schoool Level cannot be empty.");
                        isFine = false;

                    } else if (!"PRIMARY".equals(schoolLevel) && !"MIDDLE".equals(schoolLevel)
                            && !"HIGH".equals(schoolLevel) && !"COLLEGE".equals(schoolLevel) && !"UNIVERSITY".equals(schoolLevel)) {

                        wrong.add("Schoool Level cannot be different than the option.");
                        isFine = false;
                    } else if (schoolLevel != null) {

                        schoolLevel = schoolLevel.trim();
                    }
                    if (country == null || country.isEmpty()) {

                        wrong.add("Country cannot be empty.");
                        isFine = false;

                    } else if (country != null) {

                        country = country.trim();
                    }

                    if (schoolCity == null || schoolCity.isEmpty()) {

                        wrong.add("City cannot be empty.");
                        isFine = false;
                    } else if (schoolCity.trim().length() > 60) {

                        wrong.add("City cannot contain more than 60 characters.");
                        isFine = false;
                    } else {

                        schoolCity = schoolCity.trim();

                    }

                    if (schoolAddress == null || schoolAddress.isEmpty()) {

                        wrong.add("School Address cannot be empty.");
                        isFine = false;
                    } else if (schoolAddress.trim().length() > 120) {

                        wrong.add("School Address cannot contain more than 120 characters.");
                        isFine = false;
                    } else {

                        schoolAddress = schoolAddress.trim();

                    }

                    if (schoolEmail != null) {
                        schoolEmail = schoolEmail.trim();
                    }

                    if (schoolEmail == null || schoolEmail.isEmpty()) {
                        wrong.add("School email cannot be empty.");
                        isFine = false;
                    } else if (!schoolEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$")) {
                        wrong.add("School email format is invalid (example: name@domain.com).");
                        isFine = false;
                    }
                    if (schoolEmail.length() > 100) {
                        wrong.add("School email is too long.");
                        isFine = false;
                    }

                    if (!isFine) {
                        request.setAttribute("wrong", wrong);
                        url = "/Admin/editSchool.jsp";
                        break;
                    }

                    School sch = new School();

                    schoolID = loggedInUser.getSchoolID();

                    sch.setSchoolID(schoolID);
                    sch.setSchoolName(schoolName);
                    sch.setShortName(shortName);
                    sch.setSchoolType(schoolType);

                    sch.setSchoolAddress(schoolAddress);
                    sch.setSchoolCity(schoolCity);
                    sch.setWebsite(website);
                    sch.setSchoolEmail(schoolEmail);
                    sch.setCountry(country);

                    sch.setIsActive(true);

                    int rows = SchoolDB.updateSchool(sch);

                    if (rows > 0) {

                        request.setAttribute("success", "School updated successfully.");

                        request.setAttribute("school", sch);

                        //url = "/Admin/schoolProfile.jsp";
                        response.sendRedirect(request.getContextPath() + "/Private?action=gotoSchoolProfile");
                        return;
                    } else {

                        wrong.add("Update failed. Please try again.");
                        request.setAttribute("wrong", wrong);
                        url = "/Admin/editSchool.jsp";
                    }
                    break;

                case "searchStudent":

                    String studentID = request.getParameter("studentID");

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    if (studentID != null) {
                        LinkedHashMap<String, Students> getStudent = StudentDB.selectStudentsByID(schoolID, studentID);

                        request.setAttribute("getStudent", getStudent);

                    }

                    request.setAttribute("school", school);
                    url = "/Admin/searchStudent.jsp";

                    break;

                case "studentProfile":

                    String registrationNumbe = request.getParameter("registrationNumber");

                    //int registrationNumbe = Integer.parseInt(r);
                    schoolID = loggedInUser.getSchoolID();

                    Students st = StudentDB.getStudentForProfileByID(registrationNumbe, schoolID);

                    school = SchoolDB.getSchoolByID(schoolID);

                    request.setAttribute("school", school);
                    request.setAttribute("student", st);
                    url = "/Admin/studentProfile.jsp";

                    //response.sendRedirect("Private?action=listStudents");
                    break;

                //Inable or able student after search
                case "inactiveStudentSearch":
                    String registrationNu = request.getParameter("registrationNumber");

                    schoolID = loggedInUser.getSchoolID();

                    StudentDB.deactiveStudent(registrationNu, schoolID);

                    response.sendRedirect("Private?action=searchStudent&studentID=" + registrationNu);
                    return;
                //break;

                case "activeStudentSearch":

                    String registrationN = request.getParameter("registrationNumber");

                    schoolID = loggedInUser.getSchoolID();

                    StudentDB.activeStudent(registrationN, schoolID);

                    response.sendRedirect("Private?action=searchStudent&studentID=" + registrationN);

                    return;

                //SEARCH TEACHER BY NAME
                case "searchTeacher":

                    String teacherName = request.getParameter("teacherName");

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    if (teacherName != null) {
                        LinkedHashMap<String, Teachers> getTeacher = TeacherDB.searchTeachersByName(schoolID, teacherName);

                        request.setAttribute("getTeacher", getTeacher);

                    }

                    request.setAttribute("school", school);
                    url = "/Admin/searchTeacher.jsp";

                    break;

                //Inable or able teacher after search
                case "inactiveTeacherSearch":
                    String teachID = request.getParameter("teacherID");

                    schoolID = loggedInUser.getSchoolID();
                    int teachersID = Integer.parseInt(teachID);
                    TeacherDB.deactiveTeacher(teachersID, schoolID);

                    response.sendRedirect("Private?action=searchTeacher&teacherName=" + teachID);
                    return;

                case "activeTeacherSearch":
                    String teachIDs = request.getParameter("teacherID");

                    schoolID = loggedInUser.getSchoolID();
                    int teachersIDs = Integer.parseInt(teachIDs);
                    TeacherDB.activeTeacher(teachersIDs, schoolID);

                    response.sendRedirect("Private?action=searchTeacher&teacherName=" + teachIDs);
                    return;

                case "gotoLevels":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    request.setAttribute("school", school);

                    url = "/Admin/levels.jsp";

                    break;

                case "gotoAddLevels":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    LinkedHashMap<String, Department> de = DepartmentDB.selectAllDepartmentByIDAndStatus(schoolID);

                    request.setAttribute("department", de);

                    request.setAttribute("school", school);

                    url = "/Admin/addLevels.jsp";

                    break;

                case "addLevels":
                    schoolID = loggedInUser.getSchoolID();
                    boolean isNice = true;
                    String levelName = request.getParameter("levelName");
                    String levelCode = request.getParameter("levelCode");
                    String departmentId = request.getParameter("departmentID");

                    int departmentid = Integer.parseInt(request.getParameter("departmentID"));
                    int departmentID = 0;
                    String status = "ACTIVE";

                    if (levelName == null || levelName.trim().isEmpty()) {

                        ERRORS.add("You must Enter LevelName.");
                        isNice = false;

                    } else if (levelName.length() >= 100) {

                        ERRORS.add("Level Name must be less than 100 characters.");
                        isNice = false;
                    } else {

                        levelName = levelName.trim();

                    }

                    if (levelCode.length() > 10) {

                        ERRORS.add("Level Name must cannot be more than 10 characters.");
                        isNice = false;
                    } else {
                        levelCode = levelCode.trim();

                    }
                    if (LevelDB.levelNameExists(levelName, departmentid)) {

                        ERRORS.add("Level Name already exists.");
                        isNice = false;
                    }
                    if (departmentId == null || departmentId.trim().isEmpty()) {

                        ERRORS.add("Section cannot be empty.");
                        isNice = false;

                    } else {

                        departmentID = Integer.parseInt(departmentId);

                    }

                    if (!isNice) {

                        schoolID = loggedInUser.getSchoolID();
                        school = SchoolDB.getSchoolByID(schoolID);
                        LinkedHashMap<String, Department> depart = DepartmentDB.selectAllDepartmentByIDAndStatus(schoolID);

                        request.setAttribute("department", depart);
                        request.setAttribute("ERRORS", ERRORS);
                        request.setAttribute("school", school);
                        url = "/Admin/addLevels.jsp";
                        break;
                    }

                    Levels level = new Levels();

                    level.setLevelName(levelName);
                    level.setLevelCode(levelCode);
                    level.setStatus(status);
                    level.setSchoolID(schoolID);
                    level.setDepartmentID(departmentID);
                    LevelDB.insertLevels(level);

                    school = SchoolDB.getSchoolByID(schoolID);
                    request.setAttribute("success", "Level added successfull!");
                    request.setAttribute("school", school);

                    url = "/Admin/addLevels.jsp";

                    break;

                case "gotoLevelsList":

                    schoolID = loggedInUser.getSchoolID();

                    school = SchoolDB.getSchoolByID(schoolID);

                    LinkedHashMap<String, Levels> levels = LevelDB.selectAllLevelByID(schoolID);

                    request.setAttribute("levels", levels);
                    request.setAttribute("school", school);

                    url = "/Admin/levels.jsp";
                    break;

                case "gotoAddDepartment":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    request.setAttribute("school", school);

                    url = "/Admin/addDepartment.jsp";

                    break;

                case "addDepartment":

                    ArrayList<String> theResult = new ArrayList<>();
                    schoolID = loggedInUser.getSchoolID();
                    boolean isTrue = true;

                    String departmentName = request.getParameter("departmentName");
                    String theStatus = "ACTIVE";

                    if (departmentName == null || departmentName.trim().isEmpty()) {

                        theResult.add("Section cannot be empty. ");
                        isTrue = false;
                    } else if (departmentName.length() > 100) {

                        theResult.add("Section cannot be more than 100 characters.");
                        isTrue = false;
                    } else if (DepartmentDB.departmentExists(departmentName)) {

                        theResult.add("This Section already exist.");
                        isTrue = false;
                    } else {

                        departmentName = departmentName.trim();
                    }

                    if (!isTrue) {
                        schoolID = loggedInUser.getSchoolID();
                        school = SchoolDB.getSchoolByID(schoolID);

                        request.setAttribute("school", school);
                        request.setAttribute("ERRORS", theResult);
                        url = "/Admin/addDepartment.jsp";
                        break;
                    }

                    Department d = new Department();

                    d.setSchoolID(schoolID);

                    d.setDepartmentName(departmentName);

                    d.setStatus(theStatus);

                    DepartmentDB.insertDepartment(d);

                    school = SchoolDB.getSchoolByID(schoolID);
                    request.setAttribute("success", "Section added successfull!");
                    request.setAttribute("school", school);

                    url = "/Admin/addDepartment.jsp";
                    break;

                case "gotoDepartmentList":

                    schoolID = loggedInUser.getSchoolID();

                    school = SchoolDB.getSchoolByID(schoolID);

                    LinkedHashMap<String, Department> dep = DepartmentDB.selectAllDepartmentByID(schoolID);

                    request.setAttribute("department", dep);
                    request.setAttribute("school", school);

                    url = "/Admin/department.jsp";
                    break;

                default:
                    url = "/index.jsp";
                    break;
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error retrieving users");
            url = "/errorPage.jsp";
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
