/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.School;
import business.User;
import database.KelasiDB;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
//import java.lang.System.Logger;

import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Pattern;
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
@WebServlet(name = "Public", urlPatterns = {"/Public"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024, // 5MB
        maxRequestSize = 10 * 1024 * 1024 // 10MB
)

public class Public extends HttpServlet {

    static final Logger LOG = Logger.getLogger(Public.class.getName());

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
        response.setContentType("text/html;charset=UTF-8");

        ArrayList<String> errors = new ArrayList<>();
        String url = "/login.jsp";

        String action = request.getParameter("action");

        if (action == null) {

            action = "default";

        }

        try {

            switch (action) {

                case "gotoLogin":
                    url = "/login.jsp";

                    break;
                case "gotoRegister":
                    url = "/signup.jsp";
                    break;

                case "logout":
                    // Invalidate session if it exists
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.invalidate();
                    }
                    request.setAttribute("message", "You have been logged out.");
                    url = "/index.jsp";
                    break;

                case "login": {
                    //I've given you the start of a login system here

                    String email = request.getParameter("email");
                    String password = request.getParameter("password");

                    if (email == null || email.isEmpty() || password == null || password.isEmpty()) {

                        request.setAttribute("message", "Please enter the email and password!");

                        url = "/login.jsp";
                        break;

                    }

                    User loggedInUser = KelasiDB.selectUserByEmail(email);

                    if (loggedInUser == null) {

                        request.setAttribute("message", "User not found");
                        url = "/login.jsp";
                        break;

                    }
                    String storedHash = loggedInUser.getPassword();
                    String status = loggedInUser.getIsActive();

                    if (!status.equals("ACTIVE")) {

                        request.getSession().removeAttribute("loggedInUser");
                        request.setAttribute("message", "Account is inactive");
                        url = "/login.jsp";
                        break;
                    }

                    boolean isMatch = false;

                    try {

                        SecretKeyCredentialHandler ch = new SecretKeyCredentialHandler();
                        ch.setAlgorithm("PBKDF2WithHmacSHA256");
                        ch.setKeyLength(256);
                        ch.setSaltLength(16);
                        ch.setIterations(4096);

                        isMatch = ch.matches(password, storedHash);

                    } catch (Exception ex) {
                        LOG.log(Level.SEVERE, null, ex);
                        request.setAttribute("message", "Error with hashing algorithm.");
                        url = "/login.jsp";
                        break;
                    }

                    if (!status.equals("ACTIVE")) {

                        request.getSession().removeAttribute("loggedInUser");
                        request.setAttribute("message", "Account is inactive");
                        url = "/login.jsp";
                        break;
                    }

                    if (isMatch) {
                        request.getSession().setAttribute("loggedInUser", loggedInUser);
                        response.sendRedirect("Private?action=gotoProfile");
                        //url = "/Private?action=gotoProfile";
                        return;
                    } else {
                        request.setAttribute("message", "Invalid credentials");
                        url = "/login.jsp";
                    }

                    break;

                }

                case "signup": {

                    //String errors = "";
                    boolean isValid = true;

                    // READ SCHOOL
                    String schoolName = request.getParameter("schoolname");
                    String shortName = request.getParameter("shortname");
                    String registrationNumber = request.getParameter("registrationNumber");
                    String schoolType = request.getParameter("schooltype");
                    String schoolLevel = request.getParameter("schoollevel");
                    String country = request.getParameter("country");
                    String schoolCity = request.getParameter("schoolcity");
                    String schoolAddress = request.getParameter("schooladdress");
                    String website = request.getParameter("website");
                    String schoolEmail = request.getParameter("schoolemail");
                    Part logoPart = request.getPart("schoollogo");
                    

                    // READ USER ADMIN
                    String username = request.getParameter("username");
                    String adminEmail = request.getParameter("adminemail");
                    String adminPhone = request.getParameter("adminphone");
                    String role = "ADMIN";
                    String password = request.getParameter("password");
                    String confirmPassword = request.getParameter("confirmpassword");
                    String isAc = "ACTIVE";

                    //VALIDATION SCHOOL
                    if (schoolName == null || schoolName.trim().isEmpty() || schoolName.trim().length() < 3 || schoolName.trim().length() > 100) {
                        errors.add("School Name must be between 3 and 100 characters.");
                        isValid = false;
                    } else {
                        schoolName = schoolName.trim();
                    }

                    if (shortName == null || shortName.trim().length() < 2 || shortName.trim().length() > 10) {
                        errors.add("Short Name must be 2-10 characters.");
                        isValid = false;
                    } else {
                        shortName = shortName.trim();
                        if (!shortName.matches("[A-Z0-9]+")) {
                            errors.add("Short Name must contain only uppercase letters and numbers.");
                            isValid = false;
                        }
                    }

                    if (website != null) {
                        website = website.trim();
                    }
                    if (website != null && !website.isEmpty()) {
                        if (!(website.startsWith("http://") || website.startsWith("https://"))) {
                            errors.add("Website must start with http:// or https://\n");
                            isValid = false;
                        }
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

                    // Part logoPart = request.getPart("schoollogo");
                    String logoFileName = null;

                    if (logoPart != null && logoPart.getSize() > 0) {

                        // taille max 2MB
                        if (logoPart.getSize() > 2 * 1024 * 1024) {
                            errors.add("Logo file is too large (max 2MB).");
                            isValid = false;
                        }

                        // type MIME
                        String contentType = logoPart.getContentType();
                        if (!contentType.equals("image/png")
                                && !contentType.equals("image/jpeg")) {

                            errors.add("Logo must be PNG or JPG image.");
                            isValid = false;
                        }

                        // nom du fichier
                        String originalName = Paths.get(logoPart.getSubmittedFileName())
                                .getFileName()
                                .toString();

                        // extension
                        String ext = originalName.substring(originalName.lastIndexOf('.') + 1);

                        // nom sécurisé (unique)
                        logoFileName = "school_" + System.currentTimeMillis() + "." + ext;
                    }

                    // STOP HERE if invalid 
                    if (!isValid) {
                        request.setAttribute("errors", errors);
                        url = "/signup.jsp";
                        break;
                    }

                    //BUILD OBJECTS
                    School school = new School();

                    school.setSchoolName(schoolName);
                    school.setShortName(shortName);
                    school.setRegistrationNumber(registrationNumber);
                    school.setSchoolType(schoolType);
                    school.setSchoolLevel(schoolLevel);
                    school.setCountry(country);
                    school.setSchoolCity(schoolCity);
                    school.setSchoolAddress(schoolAddress);
                    school.setWebsite(website);
                    school.setSchoolEmail(schoolEmail);
                    school.setIsActive(true);

                    //logo
                    if (logoPart != null && logoPart.getSize() > 0) {

                        String uploadPath = getServletContext().getRealPath("/uploads/logos");

                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }

                        logoPart.write(uploadPath + File.separator + logoFileName);
                    }

                    school.setSchoolLogo(logoFileName);

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
                        errors.add("Password encryption failed.");
                        request.setAttribute("errors", errors);
                        url = "/signup.jsp";
                        break;
                    }

                    User user = new User(username, hash, role, adminEmail, adminPhone, 0, isAc);

                    //DB (transaction)
                    try {
                        boolean ok = KelasiDB.registerSchoolAndAdmin(school, user);

                        if (ok) {
                            request.setAttribute("message", "Registration successful. Please log in.");
                            url = "/index.jsp";
                        } else {

                            errors.add("Registration failed.");
                            request.setAttribute("errors", errors);
                            url = "/signup.jsp";
                        }

                    } catch (SQLException | NamingException ex) {
                        ex.printStackTrace();
                    }

                    break;
                }

            }
        } catch (SQLException | NamingException e) {

            e.printStackTrace();
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
