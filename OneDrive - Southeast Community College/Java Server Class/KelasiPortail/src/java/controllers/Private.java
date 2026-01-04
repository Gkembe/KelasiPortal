/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.School;
import business.Students;
import business.Teachers;
import business.User;
import database.KelasiDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.realm.SecretKeyCredentialHandler;

/**
 *
 * @author kembe
 */
@WebServlet(name = "Private", urlPatterns = {"/Private"})
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

        ArrayList<String> badMessage = new ArrayList<>();

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

                    School school = KelasiDB.getSchoolByID(schoolID);

                    int totalUsers = KelasiDB.countUsersBySchool(schoolID);
                    int totalTeachers = KelasiDB.countUsersByRoleAndSchool("TEACHER", schoolID);
                    int totalStudents = KelasiDB.countUsersByRoleAndSchool("STUDENT", schoolID);

                    request.setAttribute("totalUsers", totalUsers);
                    request.setAttribute("totalTeachers", totalTeachers);
                    request.setAttribute("totalStudents", totalStudents);

                    request.setAttribute("loggedInUser", loggedInUser);
                    request.setAttribute("school", school);

                    url = "/Admin/dashboard.jsp";
                    break;
                case "listUsers":

                    
                    schoolID = loggedInUser.getSchoolID();
                    school = KelasiDB.getSchoolByID(schoolID);

                    LinkedHashMap<String, User> users = KelasiDB.selectAllUsersByID(schoolID);

                    request.setAttribute("users", users);
                    request.setAttribute("school", school);

                    url = "/Admin/users.jsp";
                    break;
                case "gotoAddTeacher":
                    schoolID = loggedInUser.getSchoolID();
                    school = KelasiDB.getSchoolByID(schoolID);
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
                        if (KelasiDB.usernameExists(username)) {
                            errors.add("Username already exists.");
                            isValid = false;
                        }
                    }

                    if (adminEmail == null || adminEmail.trim().isEmpty()) {
                        errors.add("Admin email is required.");
                        isValid = false;
                    } else {
                        adminEmail = adminEmail.trim();
                        if (KelasiDB.adminEmailExists(adminEmail)) {
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
                        boolean ok = KelasiDB.registerTeacher(user, teacher);

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

                    school = KelasiDB.getSchoolByID(schoolID);

                    LinkedHashMap<String, Teachers> teachers = KelasiDB.selectAllTeachersByID(schoolID);

                    request.setAttribute("teachers", teachers);
                    request.setAttribute("school", school);

                    url = "/Admin/allTeachers.jsp";

                    break;

                case "gotoAddStudent":
                    schoolID = loggedInUser.getSchoolID();
                    school = KelasiDB.getSchoolByID(schoolID);
                    request.setAttribute("school", school);
                    url = "/Admin/addStudents.jsp";
                    break;

                case "addStudent":

                    //GET PARAMETERES FOR STUDENT 
                    boolean isOk = true;

                    String registrationNumber = request.getParameter("registrationNumber");
                    String SFirstName = request.getParameter("firstName");
                    String SLastName = request.getParameter("lastName");
                    String gender = request.getParameter("gender");
                    String birthDates = request.getParameter("birthDate");
                    String gradeLevel = request.getParameter("gradeLevel");
                    String department = request.getParameter("department");
                    String enrollmentDates = request.getParameter("enrollmentDate");
                    String academicYear = request.getParameter("academicYear");
                    String SphoneNumber = request.getParameter("phoneNumber");
                    String address = request.getParameter("address");
                    String SisActive = request.getParameter("isActive");
                    LocalDate birthDate = null;
                    LocalDate enrollmentDate = null;

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
                        if (KelasiDB.reistrationNumberForStudentExists(registrationNumber)) {
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

                    if (SLastName == null || SLastName.trim().length() <= 0 || SLastName.trim().length() > 50 || SLastName.trim().isEmpty()) {
                        errors.add("Short Name must be 1-50 characters.");
                        isOk = false;
                    } else {
                        SLastName = SLastName.trim();

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
                    if (gradeLevel == null || gradeLevel.isEmpty()) {

                        badMessage.add("Grade Level cannot be empty.");
                        isOk = false;

                    } else if (!gradeLevel.equals("Freshman") && !gradeLevel.equals("Sophomore")
                            && !gradeLevel.equals("Junior") && !gradeLevel.equals("Senior")) {

                        badMessage.add("Wrong data on Grade Level");
                        isOk = false;
                    } else {

                        gradeLevel = gradeLevel.trim();
                    }

                    if (department == null || department.isEmpty()) {

                        badMessage.add("Department cannot be empty.");
                        isOk = false;
                    } else if (department.length() > 100) {

                        badMessage.add("Department cannot be more than 100 characters.");
                        isOk = false;
                    } else {

                        department = department.trim();
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
                        if (KelasiDB.usernameExists(Susername)) {
                            badMessage.add("Username already exists.");
                            isOk = false;
                        }
                    }

                    if (SadminEmail == null || SadminEmail.trim().isEmpty()) {
                        badMessage.add("Admin email is required.");
                        isOk = false;
                    } else {
                        SadminEmail = SadminEmail.trim();
                        if (KelasiDB.adminEmailExists(SadminEmail)) {
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
                    s.setGradeLevel(gradeLevel);
                    s.setDepartment(department);
                    s.setEnrollmentDate(enrollmentDate);
                    s.setAcademicYear(academicYear);
                    s.setPhoneNumber(SphoneNumber);
                    s.setAddress(address);
                    s.setIsActive(SisActive);

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
                        boolean good = KelasiDB.registerStudent(u, s);

                        if (good) {

                            request.setAttribute("message", "Registration successful.");
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

                    school = KelasiDB.getSchoolByID(schoolID);

                    LinkedHashMap<String, Students> student = KelasiDB.selectAllStudentsByID(schoolID);

                    request.setAttribute("student", student);
                    request.setAttribute("school", school);

                    url = "/Admin/students.jsp";
                    break;

                case "inactiveStudent":
                    String registrationNumb = request.getParameter("registrationNumber");

                    schoolID = loggedInUser.getSchoolID();
                    //school = KelasiDB.getSchoolByID(schoolID);

                    KelasiDB.deactiveStudent(registrationNumb, schoolID);

                    response.sendRedirect("Private?action=listStudents");
                    return;
                //break;

                case "activeStudent":

                    String registrationNum = request.getParameter("registrationNumber");

                    schoolID = loggedInUser.getSchoolID();
                    //school = KelasiDB.getSchoolByID(schoolID);

                    KelasiDB.activeStudent(registrationNum, schoolID);

                    response.sendRedirect("Private?action=listStudents");

                    return;

                case "gotoAddUser":

                    schoolID = loggedInUser.getSchoolID();
                    school = KelasiDB.getSchoolByID(schoolID);
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
                        if (KelasiDB.usernameExists(aUsername)) {
                            badMessage.add("Username already exists.");
                            isGood = false;
                        }
                    }

                    if (aAdminEmail == null || aAdminEmail.trim().isEmpty()) {
                        badMessage.add("Admin email is required.");
                        isGood = false;
                    } else {
                        SadminEmail = aAdminEmail.trim();
                        if (KelasiDB.adminEmailExists(SadminEmail)) {
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
                        KelasiDB.insertAdmin(us);

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

                    KelasiDB.deactiveTeacher(teacherid, schoolID);

                    response.sendRedirect("Private?action=listTeachers");

                    return;
                //break;

                case "activeTeacher":

                    String teacherId = request.getParameter("teacherID");

                    schoolID = loggedInUser.getSchoolID();
                    //school = KelasiDB.getSchoolByID(schoolID);

                    int teacherIDS = Integer.parseInt(teacherId);
                    KelasiDB.activeTeacher(teacherIDS, schoolID);

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
                    }
                    else{
                        
                        se.removeAttribute("me");
                     
                    }
                    
                    

                    KelasiDB.deactiveUser(userId, schoolID, userIDS);

                    response.sendRedirect("Private?action=listUsers");

                    
                    return;
                //break;

                case "activeUser":

                    String userid = request.getParameter("userID");

                    schoolID = loggedInUser.getSchoolID();
                    //school = KelasiDB.getSchoolByID(schoolID);

                    int userID = Integer.parseInt(userid);
                    KelasiDB.activeUser(userID, schoolID);

                    response.sendRedirect("Private?action=listUsers");

                    return;
                case "gotoEditProfile":

                    url = "/edit.jsp";

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
