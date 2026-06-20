/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.Courses;
import business.Department;
import business.Levels;
import business.School;
import business.SchoolCycle;
import business.StudentGuardians;

import business.Students;
import business.Teachers;
import business.User;
import database.CourseDB;
import database.DepartmentDB;
import database.LevelCourseDB;
import database.UserDB;
import database.LevelDB;
import database.SchoolDB;
import database.StudentDB;
import database.TeacherDB;
import database.SchoolCycleDB;
import database.StudentGuardianDB;
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
        ArrayList<String> guardianError = new ArrayList<>();
        ArrayList<String> EditError = new ArrayList<>();
        ArrayList<String> resetError = new ArrayList<>();

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
                    String middleName = request.getParameter("middleName");
                    String lastName = request.getParameter("lastName");
                    String theGender = request.getParameter("gender");
                    String theBirthDate = request.getParameter("birthDate");
                    String phoneNumber = request.getParameter("phoneNumber");
                    String officeLocation = request.getParameter("officeLocation");
                    String theHomeAddress = request.getParameter("address");
                    //String teacherEmail = request.getParameter("email");
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

                    if (middleName.length() > 50) {

                        errors.add("Middle Name must be between 1 and 50 characters.");
                        isValid = false;
                    } else {

                        middleName = middleName.trim();
                    }

                    if (lastName == null || lastName.trim().length() <= 0 || lastName.trim().length() > 50 || lastName.trim().isEmpty()) {
                        errors.add("Short Name must be 1-50 characters.");
                        isValid = false;
                    } else {
                        lastName = lastName.trim();

                    }

                    if (officeLocation == null || officeLocation.isEmpty() || officeLocation.length() > 20) {

                        errors.add("office number cannot be empty and more then 20 characters.");

                        isValid = false;
                    } else {

                        officeLocation = officeLocation.trim();
                    }

                    if (theHomeAddress == null || theHomeAddress.isEmpty() || theHomeAddress.length() > 100) {

                        errors.add("Home address cannot be empty and more than 100 characters.");

                        isValid = false;
                    } else {

                        theHomeAddress = theHomeAddress.trim();
                    }

                    if (theGender == null || theGender.isEmpty()) {

                        errors.add("The Gender cannot be empty.");
                        isValid = false;
                    } else if (!theGender.equals("MALE") && !theGender.equals("FEMELE") && !theGender.equals("OTHER")) {

                        errors.add("ERROR, You must select Male, female or other.");
                        isValid = false;

                    } else {

                        theGender = theGender.trim();
                    }

                    if (theBirthDate == null || theBirthDate.isEmpty()) {

                        errors.add("ERROR, birth Date cannot be empty.");
                        isValid = false;
                    } else {

                        theBirthDate = theBirthDate.trim();
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
                        } else if (!adminEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

                            errors.add("Admin email must be in standard form. ex: gedeon@gmail.com");
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
                    teacher.setMiddleName(middleName);
                    teacher.setLastName(lastName);
                    teacher.setGender(theGender);
                    teacher.setSubject(specification);
                    teacher.setQualification(qualification);
                    teacher.setPhoneNumber(phoneNumber);
                    teacher.setOfficeLocation(officeLocation);
                    teacher.setAddress(theHomeAddress);
                    teacher.setEmail(adminEmail);
                    teacher.setHireDate(hireDate);
                    teacher.setIsActive(isActive);
                    teacher.setSchoolID(schoolID);

                    LocalDate birth = LocalDate.parse(theBirthDate);
                    teacher.setDateOfBirth(birth);

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

                case "teacherProfile":

                    int teacherIds = Integer.parseInt(request.getParameter("teacherID"));

                    schoolID = loggedInUser.getSchoolID();

                    school = SchoolDB.getSchoolByID(schoolID);

                    Teachers t = TeacherDB.getTeacherForProfileByID(teacherIds, schoolID);

                    request.setAttribute("teacher", t);
                    request.setAttribute("school", school);

                    url = "/Admin/teacherProfile.jsp";
                    break;

                case "gotoEditTeacherProfile":

                    int teacherIDSs = Integer.parseInt(request.getParameter("teacherID"));
                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    Teachers teach = TeacherDB.getTeacherForProfileByID(teacherIDSs, schoolID);

                    request.setAttribute("teacher", teach);
                    request.setAttribute("school", school);

                    url = "/Admin/editTeacher.jsp";

                    break;

                case "editTeacher":

                    boolean isTeacher = true;
                    ArrayList<String> tError = new ArrayList<>();
                    // TEACHER 
                    int theTeacherID = Integer.parseInt(request.getParameter("teacherID"));
                    String tFirstName = request.getParameter("firstName");
                    String tMiddleName = request.getParameter("middleName");
                    String tLastName = request.getParameter("lastName");
                    String tGender = request.getParameter("gender");
                    String tBirthDate = request.getParameter("birthDate");
                    String tPhoneNumber = request.getParameter("phoneNumber");
                    String tOfficeLocation = request.getParameter("officeLocation");
                    String tHomeAddress = request.getParameter("address");
                    String tEmail = request.getParameter("adminemail");
                    String tSpecification = request.getParameter("specification");
                    String tHireDates = request.getParameter("hireDate");
                    String tIsActive = request.getParameter("status");
                    String tQualification = request.getParameter("qualification");

                    LocalDate thireDate = null;

                    //VALIDATION Teacher
                    if (tFirstName == null || tFirstName.isEmpty() || tFirstName.trim().length() <= 0 || tFirstName.trim().length() > 50) {
                        tError.add("First Name must be between 1 and 50 characters.");
                        isTeacher = false;
                    } else {
                        tFirstName = tFirstName.trim();
                    }

                    if (tMiddleName.length() > 50) {

                        tError.add("Middle Name must be between 1 and 50 characters.");
                        isTeacher = false;
                    } else {

                        tMiddleName = tMiddleName.trim();
                    }

                    if (tLastName == null || tLastName.trim().length() <= 0 || tLastName.trim().length() > 50 || tLastName.trim().isEmpty()) {
                        tError.add("Short Name must be 1-50 characters.");
                        isTeacher = false;
                    } else {
                        tLastName = tLastName.trim();

                    }

                    if (tOfficeLocation == null || tOfficeLocation.isEmpty() || tOfficeLocation.length() > 20) {

                        tError.add("office number cannot be empty and more then 20 characters.");

                        isTeacher = false;
                    } else {

                        tOfficeLocation = tOfficeLocation.trim();
                    }

                    if (tHomeAddress == null || tHomeAddress.isEmpty() || tHomeAddress.length() > 100) {

                        tError.add("Home address cannot be empty and more than 100 characters.");

                        isTeacher = false;
                    } else {

                        tHomeAddress = tHomeAddress.trim();
                    }

                    if (tGender == null || tGender.isEmpty()) {

                        tError.add("The Gender cannot be empty.");
                        isTeacher = false;
                    } else if (!tGender.equals("MALE") && !tGender.equals("FEMELE") && !tGender.equals("OTHER")) {

                        tError.add("ERROR, You must select Male, female or other.");
                        isTeacher = false;

                    } else {

                        tGender = tGender.trim();
                    }

                    if (tBirthDate == null || tBirthDate.isEmpty()) {

                        tError.add("ERROR, birth Date cannot be empty.");
                        isTeacher = false;
                    } else {

                        tBirthDate = tBirthDate.trim();
                    }

                    if (tSpecification == null || tSpecification.isEmpty()) {

                        tError.add("Specification cannot be empty.");

                        isTeacher = false;
                    } else {

                        tSpecification = tSpecification.trim();
                    }

                    if (tHireDates == null || tHireDates.isEmpty()) {

                        tError.add("Hire Date cannot be empty.");

                        isTeacher = false;
                    } else {

                        thireDate = LocalDate.parse(tHireDates);
                    }

                    if (tPhoneNumber == null || tPhoneNumber.isEmpty() || tPhoneNumber.length() > 15) {

                        tError.add("Phone Number cannot be empty or more than 15 characters.");
                        isTeacher = false;

                    } else {

                        tPhoneNumber = tPhoneNumber.trim();
                    }

                    if (tEmail == null || tEmail.trim().isEmpty()) {
                        tError.add("Admin email is required.");
                        isTeacher = false;
                    } else {
                        tEmail = tEmail.trim();

                    }
                    if (!tEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

                        tError.add("Admin email must be in standard form. ex: gedeon@gmail.com");
                        isTeacher = false;
                    }

                    // STOP HERE if invalid 
                    if (!isTeacher) {

                        int teacherIDSss = Integer.parseInt(request.getParameter("teacherID"));

                        schoolID = loggedInUser.getSchoolID();
                        school = SchoolDB.getSchoolByID(schoolID);
                        Teachers teache = TeacherDB.getTeacherForProfileByID(teacherIDSss, schoolID);

                        request.setAttribute("teacher", teache);
                        request.setAttribute("school", school);
                        request.setAttribute("errors", tError);
                        url = "/Admin/editTeacher.jsp";
                        break;
                    }

                    //BUILD OBJECTS
                    schoolID = loggedInUser.getSchoolID();

                    Teachers te = new Teachers();
                    te.setFirstName(tFirstName);
                    te.setMiddleName(tMiddleName);
                    te.setLastName(tLastName);
                    te.setGender(tGender);
                    te.setSubject(tSpecification);
                    te.setQualification(tQualification);
                    te.setPhoneNumber(tPhoneNumber);
                    te.setOfficeLocation(tOfficeLocation);
                    te.setAddress(tHomeAddress);
                    te.setEmail(tEmail);
                    te.setHireDate(thireDate);
                    te.setIsActive(tIsActive);
                    te.setSchoolID(schoolID);
                    te.setTeacherID(theTeacherID);

                    LocalDate births = LocalDate.parse(tBirthDate);
                    te.setDateOfBirth(births);

                    TeacherDB.updateTeacher(te);
                    response.sendRedirect("Private?action=teacherProfile&teacherID=" + theTeacherID);
                    return;

                case "gotoResetTeacherLoginInfo":

                    int teacherIDReset = Integer.parseInt(request.getParameter("teacherID"));
                    int teacherUserIDs = Integer.parseInt(request.getParameter("userID"));

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    User resetUse = UserDB.resetUser(teacherUserIDs, schoolID);

                    Teachers resetTeache = TeacherDB.getTeacherForProfileByID(teacherIDReset, schoolID);

                    request.setAttribute("teacher", resetTeache);

                    request.setAttribute("user", resetUse);
                    request.setAttribute("school", school);
                    url = "/Admin/resetTeacherLogin.jsp";

                    break;

                case "resetTeacherLogin":

                    boolean isReset = true;

                    int teacherIDResetsss = Integer.parseInt(request.getParameter("teacherID"));
                    int teacherUserID = Integer.parseInt(request.getParameter("userID"));

                    String tenewEmail = request.getParameter("email");
                    String tePhoneNumber = request.getParameter("phoneNumber");
                    String teNewPassword = request.getParameter("password");
                    String teconfirmNewPassword = request.getParameter("confirmPassword");

                    if (tenewEmail == null || tenewEmail.trim().isEmpty()) {
                        resetError.add("Email is required.");
                        isReset = false;
                    } else {
                        tenewEmail = tenewEmail.trim();

                        String regexEmailUser = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

                        if (!tenewEmail.matches(regexEmailUser)) {

                            resetError.add("Put the valid email address ex. gedeon@gmail.com");
                            isReset = false;

                        }
                    }

                    if (tePhoneNumber.length() > 15) {

                        badMessage.add("Phone Number cannot be more then 15 characters.");
                        isReset = false;

                    } else {

                        tePhoneNumber = tePhoneNumber.trim();
                        String regexPhones = "^\\+1[0-9]{10}";
                        if (!tePhoneNumber.matches(regexPhones)) {

                            resetError.add("Put the correct phone number e.g +14004205656.");
                            isReset = false;
                        }

                    }

                    // Password validation 
                    if (teNewPassword == null || teNewPassword.isBlank()) {
                        resetError.add("Password cannot be empty.");
                        isReset = false;
                    } else {
                        if (teNewPassword.length() < 10) {
                            resetError.add("Password must be at least 10 characters.");
                            isReset = false;
                        }
                        if (!teNewPassword.matches(".*[A-Z].*")) {
                            resetError.add("Password must contain at least one uppercase letter.");
                            isReset = false;
                        }
                        if (!teNewPassword.matches(".*[a-z].*")) {
                            resetError.add("Password must contain at least one lowercase letter.");
                            isReset = false;
                        }
                        if (!teNewPassword.matches(".*[0-9].*")) {
                            resetError.add("Password must contain at least one number.");
                            isReset = false;
                        }
                        if (!teNewPassword.matches(".*[^a-zA-Z0-9].*")) {
                            resetError.add("Password must contain at least one special character.");
                            isReset = false;
                        }
                    }

                    if (teconfirmNewPassword == null || !teNewPassword.equals(teconfirmNewPassword)) {
                        resetError.add("Password must match confirmation password.");
                        isReset = false;
                    }
                    // STOP HERE if invalid 
                    if (!isReset) {

                        int teacherIDResetss = Integer.parseInt(request.getParameter("teacherID"));
                        int teacherUserIDss = Integer.parseInt(request.getParameter("userID"));

                        schoolID = loggedInUser.getSchoolID();
                        school = SchoolDB.getSchoolByID(schoolID);
                        User teUser = UserDB.resetUser(teacherUserIDss, schoolID);

                        Teachers teachs = TeacherDB.getTeacherForProfileByID(teacherIDResetss, schoolID);
                        
                        request.setAttribute("school", school);
                        request.setAttribute("teacher", teachs);
                        request.setAttribute("user", teUser);
                        
                        request.setAttribute("ERRORS", resetError);
                        url = "/Admin/resetTeacherLogin.jsp";
                        break;
                    }

                    // Hash password
                    String thashePassword = null;

                    schoolID = loggedInUser.getSchoolID();

                    try {
                        SecretKeyCredentialHandler ch = new SecretKeyCredentialHandler();
                        ch.setAlgorithm("PBKDF2WithHmacSHA256");
                        ch.setKeyLength(256);
                        ch.setSaltLength(16);
                        ch.setIterations(4096);

                        thashePassword = ch.mutate(teNewPassword);

                    } catch (Exception e) {

                        resetError.add("Password encryption failed.");
                        request.setAttribute("ERRORS", resetError);
                        url = "/Admin/resetTeacherLogin.jsp";
                        break;
                    }

                    User resUser = new User();

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    User theUser = UserDB.resetUser(teacherUserID, schoolID);

                    resUser.setEmail(tenewEmail);
                    resUser.setPhoneNumber(tePhoneNumber);
                    resUser.setPassword(thashePassword);
                    resUser.setUserID(teacherUserID);
                    resUser.setSchoolID(schoolID);

                    UserDB.updateUserInfo(resUser);

                    Teachers teachss = TeacherDB.getTeacherForProfileByID(teacherIDResetsss, schoolID);
                    
                    
                    request.setAttribute("teacher", teachss);
                    
                    request.setAttribute("success", "Updated successful!");
                    request.setAttribute("user", theUser);
                    request.setAttribute("school", school);
                    url = "/Admin/resetTeacherLogin.jsp";
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

                case "gotoAddStudentGuardian":

                    String regiNumber = request.getParameter("registrationNumber");
                    String theStudentID = request.getParameter("studentID");

                    int convStudentID = Integer.parseInt(theStudentID);
                    //int registrationNumbe = Integer.parseInt(r);
                    schoolID = loggedInUser.getSchoolID();

                    Students stu = StudentDB.getStudentForProfileByID(regiNumber, schoolID);

                    school = SchoolDB.getSchoolByID(schoolID);

                    request.setAttribute("registrationNumber", regiNumber);
                    request.setAttribute("studentID", convStudentID);
                    request.setAttribute("student", stu);

                    request.setAttribute("school", school);
                    url = "/Admin/addStudentGuardian.jsp";

                    break;

                case "gotoEditStudentProfile":

                    String registNumber = request.getParameter("registrationNumber");
                    String theStudentEditID = request.getParameter("studentID");

                    int convStudentEditID = Integer.parseInt(theStudentEditID);

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    Students studentEdit = StudentDB.getStudentForProfileByID(registNumber, schoolID);
                    request.setAttribute("registrationNumber", registNumber);
                    request.setAttribute("studentID", convStudentEditID);
                    request.setAttribute("student", studentEdit);
                    request.setAttribute("school", school);
                    url = "/Admin/editStudent.jsp";

                    break;

                case "editStudent":

                    boolean isEditValid = true;

                    String registreNumber = request.getParameter("registrationNumber");
                    String studentIDEdit = request.getParameter("studentID");
                    int convertStudentIDEdit = Integer.parseInt(studentIDEdit);

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    String EditFirstName = request.getParameter("firstName");
                    String EditMiddleName = request.getParameter("middleName");
                    String EditLastName = request.getParameter("lastName");
                    String Editgender = request.getParameter("gender");
                    String EditbirthDates = request.getParameter("birthDate");

                    String EditacademicYear = request.getParameter("academicYear");
                    String EditphoneNumber = request.getParameter("phoneNumber");
                    String Editaddress = request.getParameter("address");

                    LocalDate EditbirthDate = null;

                    if (EditFirstName == null || EditFirstName.isEmpty() || EditFirstName.trim().length() <= 0 || EditFirstName.trim().length() > 50) {
                        EditError.add("First Name must be between 1 and 50 characters.");
                        isEditValid = false;
                    } else {
                        EditFirstName = EditFirstName.trim();
                    }
                    if (EditMiddleName.trim().length() > 50) {

                        EditError.add("Middle Name must be between 1 and 50 characters.");
                        isEditValid = false;
                    } else {

                        EditMiddleName = EditMiddleName.trim();
                    }

                    if (EditLastName == null || EditLastName.trim().length() <= 0 || EditLastName.trim().length() > 50 || EditLastName.trim().isEmpty()) {
                        EditError.add("Short Name must be 1-50 characters.");
                        isEditValid = false;
                    } else {
                        EditLastName = EditLastName.trim();

                    }

                    if (Editgender == null || Editgender.isEmpty()) {

                        EditError.add("The Gender cannot be empty.");
                        isEditValid = false;
                    } else if (!Editgender.equals("MALE") && !Editgender.equals("FEMALE") && !Editgender.equals("OTHER")) {

                        EditError.add("ERROR, You must select Male, female or other.");
                        isEditValid = false;

                    } else {

                        Editgender = Editgender.trim();
                    }
                    if (EditbirthDates == null || EditbirthDates.isEmpty()) {

                        EditError.add("Select or put your Birth Date");
                        isEditValid = false;
                    } else {

                        EditbirthDate = LocalDate.parse(EditbirthDates);
                    }

                    if (EditacademicYear == null || EditacademicYear.isEmpty()) {

                        EditError.add("academic year cannot be empty.");
                        isEditValid = false;

                    } else if (EditacademicYear.length() < 9 || EditacademicYear.length() > 9) {

                        EditError.add("Wrong data for Academic year. e.g 2020-2021.");
                        isEditValid = false;
                    } else {

                        EditacademicYear = EditacademicYear.trim();
                    }
                    if (EditphoneNumber == null || EditphoneNumber.isEmpty()) {

                        EditError.add("Phone number cannot be empty.");
                        isEditValid = false;
                    } else {

                        EditphoneNumber = EditphoneNumber.trim();
                        String regexe = "^\\+1[0-9]{10}";

                        if (!EditphoneNumber.matches(regexe)) {

                            EditError.add("Put the correct phone number e.g +14004205656.");
                            isEditValid = false;
                        }
                    }
                    if (Editaddress == null || Editaddress.isEmpty()) {

                        EditError.add("Address cannot be empty.");
                        isEditValid = false;
                    } else if (Editaddress.length() > 255) {

                        EditError.add("Address cannot be more then 255 charaxters.");
                        isEditValid = false;
                    } else {

                        Editaddress = Editaddress.trim();
                    }

                    if (!isEditValid) {

                        String registrNumber = request.getParameter("registrationNumber");
                        String theStudentEditedID = request.getParameter("studentID");

                        int convertStudentEditID = Integer.parseInt(theStudentEditedID);

                        schoolID = loggedInUser.getSchoolID();
                        school = SchoolDB.getSchoolByID(schoolID);

                        Students studentEdited = StudentDB.getStudentForProfileByID(registrNumber, schoolID);
                        request.setAttribute("registrationNumber", registrNumber);
                        request.setAttribute("studentID", convertStudentEditID);
                        request.setAttribute("student", studentEdited);
                        request.setAttribute("school", school);
                        request.setAttribute("ERRORS", EditError);
                        url = "/Admin/editStudent.jsp";

                        break;
                    }

                    Students st = new Students();

                    st.setFirstName(EditFirstName);
                    st.setMiddleName(EditMiddleName);
                    st.setLastName(EditLastName);
                    st.setAcademicYear(EditacademicYear);
                    st.setAddress(Editaddress);
                    st.setPhoneNumber(EditphoneNumber);
                    st.setGender(Editgender);
                    st.setDateOfBirth(EditbirthDate);
                    st.setSchoolID(schoolID);
                    st.setStudentID(convertStudentIDEdit);

                    int r = StudentDB.updateStudentInfo(st);

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    Students studentEdited = StudentDB.getStudentForProfileByID(registreNumber, schoolID);
                    request.setAttribute("registrationNumber", registreNumber);
                    request.setAttribute("studentID", convertStudentIDEdit);
                    request.setAttribute("student", studentEdited);
                    request.setAttribute("school", school);
                    request.setAttribute("success", "Udated successful!");
                    url = "/Admin/editStudent.jsp";

                    break;

                case "gotoResetStudentLoginInfo":

                    String registraNumber = request.getParameter("registrationNumber");
                    String IDStudent = request.getParameter("studentID");
                    String userIDs = request.getParameter("userID");

                    int IDStudentParse = Integer.parseInt(IDStudent);
                    int IDUser = Integer.parseInt(userIDs);

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    User use = UserDB.resetUser(IDUser, schoolID);

                    Students studentReset = StudentDB.getStudentForProfileByID(registraNumber, schoolID);
                    request.setAttribute("registrationNumber", registraNumber);
                    request.setAttribute("studentID", IDStudentParse);
                    request.setAttribute("student", studentReset);
                    request.setAttribute("userID", IDUser);
                    request.setAttribute("user", use);
                    request.setAttribute("school", school);
                    url = "/Admin/resetStudentInfo.jsp";

                    break;

                case "resetStudentInfo":

                    boolean isResets = true;
                    String registratNumber = request.getParameter("registrationNumber");
                    String IdStudent = request.getParameter("studentID");
                    String userIds = request.getParameter("userID");

                    String newEmail = request.getParameter("email");
                    String sPhoneNumber = request.getParameter("phoneNumber");
                    String NewPassword = request.getParameter("password");
                    String confirmNewPassword = request.getParameter("confirmPassword");

                    int IdStudentParse = Integer.parseInt(IdStudent);
                    int IdUser = Integer.parseInt(userIds);

                    if (newEmail == null || newEmail.trim().isEmpty()) {
                        resetError.add("Email is required.");
                        isResets = false;
                    } else {
                        newEmail = newEmail.trim();

                        String regexEmailUser = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

                        if (!newEmail.matches(regexEmailUser)) {

                            resetError.add("Put the valid email address ex. gedeon@gmail.com");
                            isResets = false;

                        }
                    }

                    if (sPhoneNumber.length() > 15) {

                        badMessage.add("Phone Number cannot be more then 15 characters.");
                        isResets = false;

                    } else {

                        sPhoneNumber = sPhoneNumber.trim();
                        String regexPhones = "^\\+1[0-9]{10}";
                        if (!sPhoneNumber.matches(regexPhones)) {

                            resetError.add("Put the correct phone number e.g +14004205656.");
                            isResets = false;
                        }

                    }

                    // Password validation 
                    if (NewPassword == null || NewPassword.isBlank()) {
                        resetError.add("Password cannot be empty.");
                        isResets = false;
                    } else {
                        if (NewPassword.length() < 10) {
                            resetError.add("Password must be at least 10 characters.");
                            isResets = false;
                        }
                        if (!NewPassword.matches(".*[A-Z].*")) {
                            resetError.add("Password must contain at least one uppercase letter.");
                            isResets = false;
                        }
                        if (!NewPassword.matches(".*[a-z].*")) {
                            resetError.add("Password must contain at least one lowercase letter.");
                            isResets = false;
                        }
                        if (!NewPassword.matches(".*[0-9].*")) {
                            resetError.add("Password must contain at least one number.");
                            isResets = false;
                        }
                        if (!NewPassword.matches(".*[^a-zA-Z0-9].*")) {
                            resetError.add("Password must contain at least one special character.");
                            isResets = false;
                        }
                    }

                    if (confirmNewPassword == null || !NewPassword.equals(confirmNewPassword)) {
                        resetError.add("Password must match confirmation password.");
                        isResets = false;
                    }
                    // STOP HERE if invalid 
                    if (!isResets) {

                        String registratiNumber = request.getParameter("registrationNumber");
                        String IdStudents = request.getParameter("studentID");
                        String userIdss = request.getParameter("userID");

                        int IDStudentParses = Integer.parseInt(IdStudents);
                        int IDUsers = Integer.parseInt(userIdss);

                        schoolID = loggedInUser.getSchoolID();
                        school = SchoolDB.getSchoolByID(schoolID);
                        User theUsers = UserDB.resetUser(IdUser, schoolID);

                        Students studentResets = StudentDB.getStudentForProfileByID(registratNumber, schoolID);
                        request.setAttribute("registrationNumber", registratiNumber);
                        request.setAttribute("studentID", IDStudentParses);
                        request.setAttribute("school", school);
                        request.setAttribute("student", studentResets);
                        request.setAttribute("user", theUsers);
                        request.setAttribute("userID", IDUsers);
                        request.setAttribute("ERRORS", resetError);
                        url = "/Admin/resetStudentInfo.jsp";
                        break;
                    }

                    // Hash password
                    String hashePassword = null;

                    schoolID = loggedInUser.getSchoolID();

                    try {
                        SecretKeyCredentialHandler ch = new SecretKeyCredentialHandler();
                        ch.setAlgorithm("PBKDF2WithHmacSHA256");
                        ch.setKeyLength(256);
                        ch.setSaltLength(16);
                        ch.setIterations(4096);

                        hashePassword = ch.mutate(NewPassword);

                    } catch (Exception e) {

                        resetError.add("Password encryption failed.");
                        request.setAttribute("ERRORS", resetError);
                        url = "/Admin/resetStudentInfo.jsp";
                        break;
                    }

                    User userss = new User();

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    User theUsers = UserDB.resetUser(IdUser, schoolID);

                    userss.setEmail(newEmail);
                    userss.setPhoneNumber(sPhoneNumber);
                    userss.setPassword(hashePassword);
                    userss.setUserID(IdUser);
                    userss.setSchoolID(schoolID);

                    UserDB.updateUserInfo(userss);

                    Students studentResets = StudentDB.getStudentForProfileByID(registratNumber, schoolID);
                    request.setAttribute("registrationNumber", registratNumber);
                    request.setAttribute("studentID", IdStudentParse);
                    request.setAttribute("student", studentResets);
                    request.setAttribute("userID", IdUser);
                    request.setAttribute("success", "Updated successful!");
                    request.setAttribute("user", theUsers);
                    request.setAttribute("school", school);
                    url = "/Admin/resetStudentInfo.jsp";
                    break;

                case "addStudentGuardian":

                    boolean isGuardian = true;

                    String studentIDforGuardian = request.getParameter("studentID");
                    int studentIDCovert = Integer.parseInt(studentIDforGuardian);
                    String regisNumber = request.getParameter("registrationNumber");
                    String fullName = request.getParameter("fullName");
                    String phone = request.getParameter("phone");
                    String email = request.getParameter("email");
                    String homeAddress = request.getParameter("address");
                    String occupation = request.getParameter("occupation");
                    String relationship = request.getParameter("relationship");
                    String isPrimary = request.getParameter("isPrimary");

                    if (regisNumber == null || regisNumber.isEmpty()) {

                        guardianError.add("registration Number is empty.");
                        isGuardian = false;

                    }

                    if (fullName == null || fullName.trim().isEmpty()) {

                        guardianError.add("Full name cannot be empty.");
                        isGuardian = false;
                    } else if (fullName.length() > 50) {

                        guardianError.add("full name cannot have more than 50 characteres");
                        isGuardian = false;
                    } else {

                        fullName = fullName.trim();

                    }

                    if (phone == null || phone.trim().isEmpty()) {

                        guardianError.add("Phone number cannot be empty.");
                        isGuardian = false;
                    } else {

                        phone = phone.trim();

                        String regexPhone = "^\\+1[0-9]{10}";

                        if (!phone.matches(regexPhone)) {

                            guardianError.add("Put the correct phone number e.g +14004205656.");
                            isGuardian = false;
                        }
                    }
                    if (email == null || email.trim().isEmpty()) {

                        guardianError.add("Email cannot be empty.");
                        isGuardian = false;
                    } else if (email.length() > 100) {

                        guardianError.add("Email cannot contain more than 100 chararcters");
                        isGuardian = false;
                    } else {

                        email = email.trim();

                        String regexEmailGuardian = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

                        if (!email.matches(regexEmailGuardian)) {

                            guardianError.add("Put the valid email address ex. gedeon@gmail.com");
                            isGuardian = false;

                        }

                    }

                    if (homeAddress == null || homeAddress.trim().isEmpty()) {

                        guardianError.add("Home Address cannot be empty.");
                        isGuardian = false;
                    } else if (homeAddress.length() > 250) {

                        guardianError.add("Home address cannot conatain more than 250 characters.");
                        isGuardian = false;
                    } else {

                        homeAddress = homeAddress.trim();
                    }

                    if (occupation == null || occupation.trim().isEmpty()) {

                        guardianError.add("Please choose an occupation.");
                        isGuardian = false;
                    } else if (occupation.length() > 100) {

                        guardianError.add("Occupation cannot contain more than 100 characters.");
                        isGuardian = false;
                    } else {

                        occupation = occupation.trim();

                    }

                    if (relationship == null || relationship.trim().isEmpty()) {

                        guardianError.add("Relationship cannot be empty.");
                        isGuardian = false;
                    } else if (relationship.length() > 50) {

                        guardianError.add("Relationship cannot be more than 50 characters.");
                        isGuardian = false;
                    } else {

                        relationship = relationship.trim();
                    }

                    if (isPrimary == null || isPrimary.trim().isEmpty()) {

                        guardianError.add("first responsable cannot be empty.");
                        isGuardian = false;
                    } else if (!isPrimary.equals("Yes") && !isPrimary.equals("No")) {

                        guardianError.add("You must choose yes or no.");
                        isGuardian = false;
                    } else {

                        isPrimary = isPrimary.trim();
                    }

                    if (!isGuardian) {

                        String regisNumb = request.getParameter("registrationNumber");
                        String studentIDGuardian = request.getParameter("studentID");

                        int convertStudentID = Integer.parseInt(studentIDGuardian);
                        schoolID = loggedInUser.getSchoolID();

                        Students studentNum = StudentDB.getStudentForProfileByID(regisNumb, schoolID);

                        school = SchoolDB.getSchoolByID(schoolID);

                        request.setAttribute("registrationNumber", regisNumber);
                        request.setAttribute("studentID", convertStudentID);

                        request.setAttribute("student", studentNum);
                        request.setAttribute("ERROR", guardianError);
                        request.setAttribute("school", school);
                        url = "/Admin/addStudentGuardian.jsp";

                        break;
                    }

                    StudentGuardians g = new StudentGuardians();

                    g.setFullName(fullName);
                    g.setPhone(phone);
                    g.setEmail(email);
                    g.setAddress(homeAddress);
                    g.setStudentID(studentIDCovert);
                    g.setOccupation(occupation);
                    g.setRelationship(relationship);
                    g.setIsPrimary(isPrimary);

                    schoolID = loggedInUser.getSchoolID();

                    Students theStudent = StudentDB.getStudentForProfileByID(regisNumber, schoolID);

                    school = SchoolDB.getSchoolByID(schoolID);

                    StudentGuardianDB.insertGuardain(g);
                    request.setAttribute("registrationNumber", regisNumber);
                    request.setAttribute("studentID", studentIDCovert);

                    request.setAttribute("student", theStudent);
                    request.setAttribute("ERROR", guardianError);
                    request.setAttribute("success", "Guardian added successfull");
                    request.setAttribute("school", school);
                    url = "/Admin/addStudentGuardian.jsp";
                    break;

                case "gotoEditGuardian":

                    String registNumbers = request.getParameter("registrationNumber");
                    String theStudentEditIDs = request.getParameter("studentID");
                    String guardianIDss = request.getParameter("guardianID");
                    int convStudentEditIDs = Integer.parseInt(theStudentEditIDs);

                    int guardiansIDs = Integer.parseInt(guardianIDss);

                    StudentGuardians guar = StudentGuardianDB.selectStudentGuardian(convStudentEditIDs, guardiansIDs);
                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    Students studentEdits = StudentDB.getStudentForProfileByID(registNumbers, schoolID);
                    request.setAttribute("registrationNumber", registNumbers);
                    request.setAttribute("studentID", convStudentEditIDs);
                    request.setAttribute("student", studentEdits);
                    request.setAttribute("school", school);
                    request.setAttribute("guardian", guar);

                    url = "/Admin/editStudentGuardian.jsp";
                    break;

                case "editGuardian":

                    boolean isGuardians = true;

                    ArrayList<String> theError = new ArrayList<>();
                    String studentIDforGuardians = request.getParameter("studentID");
                    int studentIDCovertss = Integer.parseInt(studentIDforGuardians);
                    String regisNumbers = request.getParameter("registrationNumber");
                    String fullNames = request.getParameter("fullName");
                    String phones = request.getParameter("phone");
                    String emails = request.getParameter("email");
                    String homeAddresss = request.getParameter("address");
                    String occupations = request.getParameter("occupation");
                    String relationships = request.getParameter("relationship");
                    String isPrimarys = request.getParameter("isPrimary");
                    String guardianIDsss = request.getParameter("guardianID");

                    int guardiansIDss = Integer.parseInt(guardianIDsss);

                    //StudentGuardians guard = StudentGuardianDB.selectStudentGuardian(studentIDCovertss, guardiansIDss);
                    if (regisNumbers == null || regisNumbers.isEmpty()) {

                        theError.add("registration Number is empty.");
                        isGuardians = false;

                    }

                    if (fullNames == null || fullNames.trim().isEmpty()) {

                        theError.add("Full name cannot be empty.");
                        isGuardians = false;
                    } else if (fullNames.length() > 50) {

                        theError.add("full name cannot have more than 50 characteres");
                        isGuardians = false;
                    } else {

                        fullNames = fullNames.trim();

                    }

                    if (phones == null || phones.trim().isEmpty()) {

                        theError.add("Phone number cannot be empty.");
                        isGuardians = false;
                    } else {

                        phones = phones.trim();

                        String regexPhones = "^\\+1[0-9]{10}";

                        if (!phones.matches(regexPhones)) {

                            theError.add("Put the correct phone number e.g +14004205656.");
                            isGuardians = false;
                        }
                    }
                    if (emails == null || emails.trim().isEmpty()) {

                        theError.add("Email cannot be empty.");
                        isGuardians = false;
                    } else if (emails.length() > 100) {

                        theError.add("Email cannot contain more than 100 chararcters");
                        isGuardians = false;
                    } else {

                        emails = emails.trim();

                        String regexEmailGuardians = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

                        if (!emails.matches(regexEmailGuardians)) {

                            theError.add("Put the valid email address ex. gedeon@gmail.com");
                            isGuardians = false;

                        }

                    }

                    if (homeAddresss == null || homeAddresss.trim().isEmpty()) {

                        theError.add("Home Address cannot be empty.");
                        isGuardians = false;
                    } else if (homeAddresss.length() > 250) {

                        theError.add("Home address cannot conatain more than 250 characters.");
                        isGuardians = false;
                    } else {

                        homeAddresss = homeAddresss.trim();
                    }

                    if (occupations == null || occupations.trim().isEmpty()) {

                        theError.add("Please choose an occupation.");
                        isGuardians = false;
                    } else if (occupations.length() > 100) {

                        theError.add("Occupation cannot contain more than 100 characters.");
                        isGuardians = false;
                    } else {

                        occupations = occupations.trim();

                    }

                    if (relationships == null || relationships.trim().isEmpty()) {

                        theError.add("Relationship cannot be empty.");
                        isGuardians = false;
                    } else if (relationships.length() > 50) {

                        theError.add("Relationship cannot be more than 50 characters.");
                        isGuardians = false;
                    } else {

                        relationships = relationships.trim();
                    }

                    if (isPrimarys == null || isPrimarys.trim().isEmpty()) {

                        theError.add("first responsable cannot be empty.");
                        isGuardians = false;
                    } else if (!isPrimarys.equals("Yes") && !isPrimarys.equals("No")) {

                        theError.add("You must choose yes or no.");
                        isGuardians = false;
                    } else {

                        isPrimarys = isPrimarys.trim();
                    }

                    if (!isGuardians) {

                        String regisNumbss = request.getParameter("registrationNumber");
                        String studentIDGuardianss = request.getParameter("studentID");
                        String guardianIDssss = request.getParameter("guardianID");

                        int guardiansIDsss = Integer.parseInt(guardianIDssss);

                        StudentGuardians guard = StudentGuardianDB.selectStudentGuardian(studentIDCovertss, guardiansIDss);
                        int convertStudentID = Integer.parseInt(studentIDGuardianss);
                        schoolID = loggedInUser.getSchoolID();

                        Students studentNums = StudentDB.getStudentForProfileByID(regisNumbss, schoolID);

                        school = SchoolDB.getSchoolByID(schoolID);

                        request.setAttribute("registrationNumber", regisNumbers);
                        request.setAttribute("studentID", convertStudentID);

                        request.setAttribute("guardian", guard);
                        request.setAttribute("student", studentNums);
                        request.setAttribute("ERROR", theError);
                        request.setAttribute("school", school);
                        url = "/Admin/editStudentGuardian.jsp";

                        break;
                    }

                    StudentGuardians gd = new StudentGuardians();

                    gd.setGuardianID(guardiansIDss);
                    gd.setFullName(fullNames);
                    gd.setPhone(phones);
                    gd.setEmail(emails);
                    gd.setAddress(homeAddresss);
                    gd.setStudentID(studentIDCovertss);
                    gd.setOccupation(occupations);
                    gd.setRelationship(relationships);
                    gd.setIsPrimary(isPrimarys);

                    schoolID = loggedInUser.getSchoolID();

                    StudentGuardians guardi = StudentGuardianDB.selectStudentGuardian(studentIDCovertss, guardiansIDss);

                    Students theStudentss = StudentDB.getStudentForProfileByID(regisNumbers, schoolID);

                    school = SchoolDB.getSchoolByID(schoolID);

                    StudentGuardianDB.updateGuardianInfo(gd);
                    request.setAttribute("registrationNumber", regisNumbers);
                    request.setAttribute("studentID", studentIDCovertss);
                    request.setAttribute("guardianID", guardiansIDss);
                    request.setAttribute("student", theStudentss);
                    request.setAttribute("ERROR", theError);
                    request.setAttribute("guardian", guardi);
                    request.setAttribute("success", "Guardian added successfull");
                    request.setAttribute("school", school);
                    url = "/Admin/editStudentGuardian.jsp";

                    break;

                case "deactivateGuardian":

                    String regisNumbssss = request.getParameter("registrationNumber");
                    String studentIDGuardianssss = request.getParameter("studentID");
                    String guardianIDsssss = request.getParameter("guardianID");

                    int convertStudentIDssss = Integer.parseInt(studentIDGuardianssss);

                    int guardiansIDssss = Integer.parseInt(guardianIDsssss);

                    StudentGuardianDB.deactiveGuardian(guardiansIDssss, convertStudentIDssss);

                    request.setAttribute("registrationNumber", regisNumbssss);

                    response.sendRedirect("Private?action=studentProfile&registrationNumber=" + regisNumbssss + "&studentID=" + convertStudentIDssss + "&guardianID= " + guardianIDsssss);
                    return;
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

                    String studentIDsss = request.getParameter("studentID");
                    int ssID = Integer.parseInt(studentIDsss);
                    schoolID = loggedInUser.getSchoolID();

                    Students stud = StudentDB.getStudentForProfileByID(registrationNumbe, schoolID);

                    school = SchoolDB.getSchoolByID(schoolID);
                    LinkedHashMap<String, StudentGuardians> guardian = StudentGuardianDB.selectGuardianByStudentID(ssID);

                    request.setAttribute("registrationNumber", registrationNumbe);
                    request.setAttribute("guardian", guardian);
                    request.setAttribute("studentID", studentIDsss);
                    request.setAttribute("school", school);
                    request.setAttribute("student", stud);
                    request.setAttribute("studentID", ssID);
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
                    LinkedHashMap<String, Department> depart = DepartmentDB.selectAllDepartmentByIDAndStatus(schoolID);

                    request.setAttribute("department", depart);
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

                case "gotoCourses":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    LinkedHashMap<String, Courses> c = CourseDB.selectCourses(schoolID);

                    request.setAttribute("courses", c);

                    request.setAttribute("school", school);

                    url = "/Admin/courses.jsp";

                    break;

                case "gotoAddCourses":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    request.setAttribute("school", school);

                    url = "/Admin/addCourses.jsp";

                    break;

                case "addCourses":

                    ArrayList<String> theCourse = new ArrayList<>();
                    schoolID = loggedInUser.getSchoolID();
                    boolean isCourse = true;

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    String courseName = request.getParameter("courseName");
                    String courseCode = request.getParameter("courseCode");
                    String statusCourse = "ACTIVE";

                    if (courseName == null || courseName.trim().isEmpty()) {

                        theCourse.add("Course Name field cannot be empty.");
                        isCourse = false;

                    } else if (courseName.length() > 100) {

                        theCourse.add("Course Name cannot contain more than 100 characters.");
                        isCourse = false;
                    } else {

                        courseName = courseName.trim();
                    }

                    if (courseCode == null || courseCode.trim().isEmpty()) {

                        theCourse.add("Course Code field cannot be empty.");
                        isCourse = false;
                    } else if (courseCode.length() > 10) {

                        theCourse.add("Course code cannot contain more than 10 characters.");
                        isCourse = false;
                    } else {

                        courseCode = courseCode.trim();
                    }

                    if (!isCourse) {

                        schoolID = loggedInUser.getSchoolID();
                        school = SchoolDB.getSchoolByID(schoolID);

                        request.setAttribute("school", school);
                        request.setAttribute("ERRORS", theCourse);
                        url = "/Admin/addCourses.jsp";
                        break;
                    }

                    Courses co = new Courses();

                    co.setSchoolID(schoolID);
                    co.setCourseName(courseName);
                    co.setCourseCode(courseCode);
                    co.setStatus(statusCourse);

                    CourseDB.insertCourses(co);
                    request.setAttribute("school", school);
                    request.setAttribute("success", "Course added successful!");
                    url = "/Admin/addCourses.jsp";

                    break;

                case "activateCourse":

                    String courseIDs = request.getParameter("courseID");

                    int courseIds = Integer.parseInt(courseIDs);

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    CourseDB.activeCourse(courseIds, schoolID);
                    request.setAttribute("school", school);

                    response.sendRedirect("Private?action=gotoCourses");
                    return;
                case "deactivateCourse":

                    String courseID = request.getParameter("courseID");

                    int courseId = Integer.parseInt(courseID);

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    CourseDB.deactiveCourse(courseId, schoolID);
                    request.setAttribute("school", school);

                    response.sendRedirect("Private?action=gotoCourses");
                    return;

                case "gotoAddLevelCourses":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    String levelId = request.getParameter("levelID");

                    int levelIds = Integer.parseInt(levelId);

                    LinkedHashMap<String, Courses> cou = CourseDB.selectCourses(schoolID);

                    request.setAttribute("courses", cou);
                    request.setAttribute("school", school);
                    request.setAttribute("levelID", levelIds);

                    url = "/Admin/addLevelCourse.jsp";

                    break;
                case "addLevelCourses":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);

                    int levelIDs = Integer.parseInt(request.getParameter("levelID"));
                    String[] courseIDss = request.getParameterValues("courseID");

                    if (courseIDss != null) {
                        for (int i = 0; i < courseIDss.length; i++) {
                            int courseIDsss = Integer.parseInt(courseIDss[i]);
                            LevelCourseDB.insertLevelCourse(levelIDs, courseIDsss);
                        }
                    }

                    request.setAttribute("success", "Course added to the Level successful!");
                    response.sendRedirect(request.getContextPath() + "/Private?action=gotoAddLevelCourses&levelID=" + levelIDs);
                    return;

                case "viewLevelCourses":

                    schoolID = loggedInUser.getSchoolID();
                    school = SchoolDB.getSchoolByID(schoolID);
                    String levelIdss = request.getParameter("levelID");

                    if (levelIdss == null || levelIdss.equals("")) {
                        response.sendRedirect(request.getContextPath() + "/Private?action=gotoLevels");
                        return;
                    }

                    int levelID = Integer.parseInt(levelIdss);

                    LinkedHashMap<String, Courses> courses = LevelCourseDB.selectCoursesByLevel(levelID);

                    request.setAttribute("school", school);
                    request.setAttribute("courses", courses);
                    request.setAttribute("levelID", levelID);

                    url = "/Admin/levelCourses.jsp";

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

        getServletContext()
                .getRequestDispatcher(url).forward(request, response);
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
