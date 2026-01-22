/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.School;
import business.Students;
import business.Teachers;
import business.User;
import java.security.Timestamp;
//import com.mysql.cj.xdevapi.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import javax.naming.NamingException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author kembe
 */
public class KelasiDB {

    //retrieve all users to send to the allusers.jsp
    public static LinkedHashMap<String, User> selectUser() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT username, password, role, email, phone, created_at, schoolID, isActive, id FROM users";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<String, User> user = new LinkedHashMap<>();
        while (rs.next()) {

            String userName = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            int schoolID = rs.getInt("schoolID");
            String isActive = rs.getString("isActive");
            int userID = rs.getInt("id");

            User users = new User(userName, password, role, email, phone, createdAt, schoolID, isActive, userID);
            user.put(users.getUserName(), users);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return user;

    }

    public static LinkedHashMap<Integer, School> selectSchool() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT schoolID, schoolName, shortName, registrationNumber, schoolType, schoolLevel, "
                + "coutry, city, schoolAddress, schoolEmail, website, schoolLogo, isActive, createdAt FROM school";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<Integer, School> school = new LinkedHashMap<>();
        while (rs.next()) {

            int schoolID = rs.getInt("schoolID");
            String schoolName = rs.getString("schoolName");
            String shortName = rs.getString("shortName");
            String registrationNumber = rs.getString("registrationNumber");
            String schoolType = rs.getString("schoolType");
            String schoolLevel = rs.getString("schoolLevel");
            String country = rs.getString("country");
            String schoolCity = rs.getString("city");
            String schoolAddress = rs.getString("schoolAddress");
            String schoolEmail = rs.getString("schoolEmail");
            String website = rs.getString("website");
            String schoolLogo = rs.getString("schoolLogo");
            boolean isActive = rs.getBoolean("isActive");
            LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();

            School schoolss = new School(schoolID, schoolName, shortName, registrationNumber,
                    schoolType, schoolLevel, country, schoolCity, schoolAddress, schoolEmail, website, schoolLogo, isActive, createdAt);
            school.put(schoolss.getSchoolID(), schoolss);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return school;

    }

    public static String getPasswordForEmail(String email) throws NamingException, SQLException {

        if (email == null || email.isEmpty()) {

            return null;
        }

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String password = null;

        connection = pool.getConnection();

        String query = "SELECT username, password, role, email, phone, created_at FROM users WHERE email = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, email);

        rs = ps.executeQuery();

        if (rs.next()) {

            password = rs.getString("password");

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return password;

    }

    public static User selectUserByEmail(String email) throws NamingException, SQLException {

        if (email == null || email.isEmpty()) {

            return null;
        }

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;

        connection = pool.getConnection();

        String query = "SELECT id, username, password, role, email, phone, created_at, schoolID, isActive FROM users WHERE email = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, email);

        rs = ps.executeQuery();

        if (rs.next()) {

            String userName = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");
            String emails = rs.getString("email");
            String phone = rs.getString("phone");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            int schoolID = rs.getInt("schoolID");
            String isActive = rs.getString("isActive");
            int userID = rs.getInt("id");
            user = new User(userName, password, role, emails, phone, schoolID, isActive, userID);

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return user;

    }

    public static boolean usernameExists(String username) throws NamingException, SQLException {

        boolean exists = false;

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = pool.getConnection();

        String query = "SELECT username FROM users WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            exists = true;

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return exists;

    }

    // if email exist for school
    public static boolean schoolEmailExists(String email) throws NamingException, SQLException {
        boolean exists = false;

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = pool.getConnection();

        String query = "SELECT email FROM users WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            exists = true;

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return exists;
    }

    // if email exist for user admin
    public static boolean adminEmailExists(String email) throws NamingException, SQLException {
        boolean exists = false;

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = pool.getConnection();

        String query = "SELECT email FROM users WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            exists = true;

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return exists;
    }

    //Insert the admin user
    public static int insertSchool(Connection conn, School school)
            throws SQLException, NamingException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO school (schoolName, shortName, registrationNumber, schoolType, schoolLevel, "
                + "country, city, schoolAddress, schoolEmail, website, schoolLogo, isActive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, school.getSchoolName());
        ps.setString(2, school.getShortName());
        ps.setString(3, school.getRegistrationNumber());
        ps.setString(4, school.getSchoolType());
        ps.setString(5, school.getSchoolLevel());
        ps.setString(6, school.getCountry());
        ps.setString(7, school.getSchoolCity());
        ps.setString(8, school.getSchoolAddress());
        ps.setString(9, school.getSchoolEmail());
        ps.setString(10, school.getWebsite());
        ps.setString(11, school.getSchoolLogo());
        ps.setBoolean(12, school.isActive());

        int rows = ps.executeUpdate();

        int schoolID = 0;
        if (rows > 0) {
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                schoolID = rs.getInt(1);
            }
        }

        if (rs != null) {
            rs.close();
        }
        ps.close();

        return schoolID;
    }

    //insert teacher 
    public static int insertTeacher(Connection conn, Teachers teacher)
            throws SQLException, NamingException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO teachers (user_id, first_name, last_name, subject, qualification, phoneNumber, officeLocation, "
                + "isActive, hireDate, schoolID ) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, teacher.getUserID());
        ps.setString(2, teacher.getFirstName());
        ps.setString(3, teacher.getLastName());
        ps.setString(4, teacher.getSubject());
        ps.setString(5, teacher.getQualification());
        ps.setString(6, teacher.getPhoneNumber());
        ps.setString(7, teacher.getOfficeLocation());
        ps.setString(8, teacher.getIsActive());
        ps.setDate(9, Date.valueOf(teacher.getHireDate()));
        ps.setInt(10, teacher.getSchoolID());

        int rows = ps.executeUpdate();

        int teacherID = 0;
        if (rows > 0) {
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                teacherID = rs.getInt(1);
            }
        }

        if (rs != null) {
            rs.close();
        }
        ps.close();

        return teacherID;
    }

    public static int insertUser(Connection conn, User user)
            throws SQLException, NamingException {

        PreparedStatement ps = null;

        String query
                = "INSERT INTO users (username, password, role, email, phone, schoolID, isActive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, user.getUserName());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getRole());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPhoneNumber());
        ps.setInt(6, user.getSchoolID());
        ps.setString(7, user.getIsActive());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        int userID = 0;

        if (rs.next()) {
            userID = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return userID;
    }

    public static boolean registerTeacher(User user, Teachers teacher)
            throws SQLException, NamingException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();

        try {
            conn.setAutoCommit(false);

          
            int userID = insertUser(conn, user);

           
            teacher.setUserID(userID);

           
            insertTeacher(conn, teacher);

            conn.commit();
            return true;

        } catch (SQLException ex) {
            conn.rollback();
            throw ex;

        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public static boolean registerSchoolAndAdmin(School school, User user)
            throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();

        conn.setAutoCommit(false);

        //Insert school
        int schoolID = insertSchool(conn, school);
        if (schoolID <= 0) {
            conn.rollback();
            pool.freeConnection(conn);
            return false;
        }

        // set foreign key
        user.setSchoolID(schoolID);

        //insert user
        int rows = insertUser(conn, user);
        if (rows <= 0) {
            conn.rollback();
            pool.freeConnection(conn);
            return false;
        }

        // if everything is ok
        conn.commit();
        conn.setAutoCommit(true);
        pool.freeConnection(conn);

        return true;
    }

    public static School getSchoolByID(int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT schoolID, schoolName, shortName, registrationNumber, schoolType, schoolLevel, "
                + "country, city, schoolAddress, schoolEmail, website, schoolLogo, isActive, createdAt "
                + "FROM school WHERE schoolID = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        ResultSet rs = ps.executeQuery();

        School school = null;
        if (rs.next()) {
            school = new School();

            school.setSchoolID(rs.getInt("schoolID"));
            school.setSchoolName(rs.getString("schoolName"));
            school.setShortName(rs.getString("shortName"));
            school.setSchoolEmail(rs.getString("schoolEmail"));
            school.setRegistrationNumber(rs.getString("registrationNumber"));
            school.setSchoolType(rs.getString("schoolType"));
            school.setSchoolLevel(rs.getString("schoolLevel"));
            school.setCountry(rs.getString("country"));
            school.setSchoolCity(rs.getString("city"));
            school.setSchoolAddress(rs.getString("schoolAddress"));
            school.setWebsite(rs.getString("website"));
            school.setSchoolLogo(rs.getString("schoolLogo"));
            school.setIsActive(rs.getBoolean("isActive"));

            if (rs.getTimestamp("createdAt") != null) {
                school.setCreatedAT(rs.getTimestamp("createdAt").toLocalDateTime());
            }
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return school;
    }

    public static int countUsersBySchool(int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT COUNT(*) AS total FROM users WHERE schoolID = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        ResultSet rs = ps.executeQuery();

        int total = 0;
        if (rs.next()) {
            total = rs.getInt("total");
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return total;
    }

    public static int countUsersByRoleAndSchool(String role, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT COUNT(*) AS total FROM users WHERE role = ? AND schoolID = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, role);
        ps.setInt(2, schoolID);

        ResultSet rs = ps.executeQuery();

        int total = 0;
        if (rs.next()) {
            total = rs.getInt("total");
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return total;
    }

    public static LinkedHashMap<String, User> selectAllUsersByID(int schoolID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT id, username, password, role, email, phone, created_at, schoolID, isActive FROM users WHERE schoolID = ? AND role = 'ADMIN' ORDER BY created_at";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        rs = ps.executeQuery();

        LinkedHashMap<String, User> user = new LinkedHashMap<>();
        while (rs.next()) {
            User users = new User();
            users.setUserName(rs.getString("username"));

            users.setPassword(rs.getString("password"));
            users.setRole(rs.getString("role"));
            users.setEmail(rs.getString("email"));
            users.setPhoneNumber(rs.getString("phone"));
            users.setSchoolID(rs.getInt("schoolID"));
            users.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            users.setIsActive(rs.getString("isActive"));
            users.setUserID(rs.getInt("id"));

            user.put("" + users.getUserName(), users);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return user;

    }

    //select teachers
    public static LinkedHashMap<String, Teachers> selectAllTeachersByID(int schoolID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT user_id, first_name, last_name, subject, qualification, phoneNumber, officeLocation, isActive, hireDate, createdAT, schoolID, teacherID FROM teachers WHERE schoolID = ? ORDER BY first_name";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        rs = ps.executeQuery();

        LinkedHashMap<String, Teachers> teacher = new LinkedHashMap<>();
        while (rs.next()) {
            Teachers t = new Teachers();
            t.setUserID(rs.getInt("user_id"));
            t.setFirstName(rs.getString("first_name"));
            t.setLastName(rs.getString("last_name"));
            t.setSubject(rs.getString("subject"));
            t.setQualification(rs.getString("qualification"));
            t.setPhoneNumber(rs.getString("phoneNumber"));
            t.setOfficeLocation(rs.getString("officeLocation"));
            t.setIsActive(rs.getString("isActive"));
            t.setHireDate(rs.getDate("hireDate").toLocalDate());
            t.setCreatedAT(rs.getTimestamp("createdAT").toLocalDateTime());
            t.setSchoolID(rs.getInt("schoolID"));
            t.setTeacherID(rs.getInt("teacherID"));

            teacher.put("" + "" + t.getFirstName(), t);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return teacher;

    }

    //Students
    public static boolean reistrationNumberForStudentExists(String registrationNumber) throws NamingException, SQLException {

        boolean exists = false;

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = pool.getConnection();

        String query = "SELECT registrationNumber FROM students WHERE registrationNumber = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, registrationNumber);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            exists = true;

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return exists;

    }

    public static int insertStudent(Connection connection, Students student)
            throws SQLException, NamingException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO students (schoolID, userID, registrationNumber, firstName, lastName,"
                + " gender, dateOfBirth, gradeLevel, department, enrollmentDate, academicYear, phone, address, isActive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, student.getSchoolID());
        ps.setInt(2, student.getUserID());

        ps.setString(3, student.getRegistrationNumber());
        ps.setString(4, student.getFirstName());
        ps.setString(5, student.getLastName());
        ps.setString(6, student.getGender());
        ps.setDate(7, Date.valueOf(student.getDateOfBirth()));
        ps.setString(8, student.getGradeLevel());
        ps.setString(9, student.getDepartment());
        ps.setDate(10, Date.valueOf(student.getEnrollmentDate()));
        ps.setString(11, student.getAcademicYear());
        ps.setString(12, student.getPhoneNumber());
        ps.setString(13, student.getAddress());
        ps.setString(14, student.getIsActive());

        int rows = ps.executeUpdate();

        int studentID = 0;
        if (rows > 0) {
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                studentID = rs.getInt(1);
            }
        }

        if (rs != null) {
            rs.close();
        }
        ps.close();

        return studentID;
    }

    public static boolean registerStudent(User user, Students student)
            throws SQLException, NamingException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try {
            connection.setAutoCommit(false);

            // 1. insert user
            int userID = insertUser(connection, user);

            // 2. lier student
            student.setUserID(userID);

            // 3. insert teacher
            insertStudent(connection, student);

            connection.commit();
            return true;

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;

        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    //select all students 
    public static LinkedHashMap<String, Students> selectAllStudentsByID(int schoolID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT schoolID, userID, registrationNumber, firstName, lastName,"
                + " gender, dateOfBirth, gradeLevel, department, enrollmentDate, academicYear,"
                + " phone, address, isActive, createdAt, updatedAt FROM students WHERE schoolID = ? ORDER BY firstName";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        rs = ps.executeQuery();

        LinkedHashMap<String, Students> student = new LinkedHashMap<>();
        while (rs.next()) {
            Students s = new Students();
            s.setUserID(rs.getInt("userID"));
            s.setFirstName(rs.getString("firstName"));
            s.setLastName(rs.getString("lastName"));
            s.setSchoolID(rs.getInt("schoolID"));
            s.setRegistrationNumber(rs.getString("registrationNumber"));
            s.setGender(rs.getString("gender"));
            s.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
            s.setGradeLevel(rs.getString("gradeLevel"));
            s.setDepartment(rs.getString("department"));
            s.setEnrollmentDate(rs.getDate("enrollmentDate").toLocalDate());
            s.setAcademicYear(rs.getString("academicYear"));
            s.setPhoneNumber(rs.getString("phone"));
            s.setAddress(rs.getString("address"));
            s.setIsActive(rs.getString("isActive"));
            s.setCreatedAT(rs.getTimestamp("createdAt").toLocalDateTime());
            s.setUpdatedAT(rs.getTimestamp("updatedAt").toLocalDateTime());

            student.put(s.getRegistrationNumber(), s);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return student;
    }

    //UDPDATE STUDENT TO DEACTIVE HIM
    public static void deactiveStudent(String registrationNumber, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE students SET isActive='INACTIVE' WHERE registrationNumber=? AND schoolID=?";
        ps = connection.prepareStatement(q1);
        ps.setString(1, registrationNumber);
        ps.setInt(2, schoolID);
        ps.executeUpdate();
        ps.close();

        String q2 = "SELECT userID FROM students WHERE registrationNumber=? AND schoolID=?";
        ps = connection.prepareStatement(q2);
        ps.setString(1, registrationNumber);
        ps.setInt(2, schoolID);

        ResultSet rs = ps.executeQuery();

        int userID = 0;
        if (rs.next()) {
            userID = rs.getInt("userID");
        }
        rs.close();
        ps.close();

        if (userID != 0) {
            String q3 = "UPDATE users SET isActive='INACTIVE' WHERE id=? AND schoolID=?";
            ps = connection.prepareStatement(q3);
            ps.setInt(1, userID);
            ps.setInt(2, schoolID);
            ps.executeUpdate();
            ps.close();
        }

        ps.close();
        pool.freeConnection(connection);
    }

    //UDPDATE STUDENT TO ACTIVE HIM
    public static void activeStudent(String registrationNumber, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE students SET isActive='ACTIVE' WHERE registrationNumber=? AND schoolID=?";
        ps = connection.prepareStatement(q1);
        ps.setString(1, registrationNumber);
        ps.setInt(2, schoolID);
        ps.executeUpdate();
        ps.close();

        String q2 = "SELECT userID FROM students WHERE registrationNumber=? AND schoolID=?";
        ps = connection.prepareStatement(q2);
        ps.setString(1, registrationNumber);
        ps.setInt(2, schoolID);

        ResultSet rs = ps.executeQuery();

        int userID = 0;
        if (rs.next()) {
            userID = rs.getInt("userID");
        }
        rs.close();
        ps.close();

        if (userID != 0) {
            String q3 = "UPDATE users SET isActive='ACTIVE' WHERE id=? AND schoolID=?";
            ps = connection.prepareStatement(q3);
            ps.setInt(1, userID);
            ps.setInt(2, schoolID);
            ps.executeUpdate();
            ps.close();
        }

        ps.close();
        pool.freeConnection(connection);
    }

    //INSERT THE ADMIN 
    public static int insertAdmin(User user)
            throws SQLException, NamingException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO users (username, password, role, email, phone, schoolID, isActive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, user.getUserName());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getRole());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPhoneNumber());
        ps.setInt(6, user.getSchoolID());
        ps.setString(7, user.getIsActive());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        int userID = 0;

        if (rs.next()) {
            userID = rs.getInt(1);
        }
        rs.close();
        ps.close();

        return userID;
    }

    //UDPDATE TEACHER TO DEACTIVE HIM
    public static void deactiveTeacher(int teacherID, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE teachers SET isActive='INACTIVE' WHERE teacherID=? AND schoolID=?";
        ps = connection.prepareStatement(q1);
        ps.setInt(1, teacherID);
        ps.setInt(2, schoolID);
        ps.executeUpdate();
        ps.close();

        String q2 = "SELECT user_id FROM teachers WHERE teacherID=? AND schoolID=?";
        ps = connection.prepareStatement(q2);
        ps.setInt(1, teacherID);
        ps.setInt(2, schoolID);

        ResultSet rs = ps.executeQuery();

        int userID = 0;
        if (rs.next()) {
            userID = rs.getInt("user_id");
        }
        rs.close();
        ps.close();

        if (userID != 0) {
            String q3 = "UPDATE users SET isActive='INACTIVE' WHERE id=? AND schoolID=?";
            ps = connection.prepareStatement(q3);
            ps.setInt(1, userID);
            ps.setInt(2, schoolID);
            ps.executeUpdate();
            ps.close();
        }

        pool.freeConnection(connection);
    }

    public static void activeTeacher(int teacherID, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE teachers SET isActive='ACTIVE' WHERE teacherID=? AND schoolID=?";
        ps = connection.prepareStatement(q1);
        ps.setInt(1, teacherID);
        ps.setInt(2, schoolID);
        ps.executeUpdate();
        ps.close();

        String q2 = "SELECT user_id FROM teachers WHERE teacherID=? AND schoolID=?";
        ps = connection.prepareStatement(q2);
        ps.setInt(1, teacherID);
        ps.setInt(2, schoolID);

        ResultSet rs = ps.executeQuery();

        int userID = 0;
        if (rs.next()) {
            userID = rs.getInt("user_id");

        }
        rs.close();
        ps.close();

        if (userID != 0) {
            String q3 = "UPDATE users SET isActive='ACTIVE' WHERE id=? AND schoolID=?";
            ps = connection.prepareStatement(q3);
            ps.setInt(1, userID);
            ps.setInt(2, schoolID);
            ps.executeUpdate();
            ps.close();
        }

        pool.freeConnection(connection);
    }

    //UDPDATE USER TO DEACTIVE HIM
    public static void deactiveUser(int userID, int schoolID, int loggedAdminID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE users SET isActive='INACTIVE' WHERE id =? AND schoolID =? AND role= 'ADMIN' AND id <> ?";
        ps = connection.prepareStatement(q1);
        ps.setInt(1, userID);
        ps.setInt(2, schoolID);
        ps.setInt(3, loggedAdminID);
        ps.executeUpdate();
        ps.close();

        pool.freeConnection(connection);
    }

    public static void activeUser(int userID, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE users SET isActive='ACTIVE' WHERE id=? AND schoolID=? AND role= 'ADMIN'";
        ps = connection.prepareStatement(q1);
        ps.setInt(1, userID);
        ps.setInt(2, schoolID);
        ps.executeUpdate();
        ps.close();

        pool.freeConnection(connection);
    }

    public static void updateSchoolLogo(int schoolID, String logoFileName) throws SQLException, NamingException {

        String sql = "UPDATE school SET schoolLogo=? WHERE schoolID=?";

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, logoFileName);
        ps.setInt(2, schoolID);

        ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);
    }

    //EDIT SCHOOL INFO
    
    
    public static int updateSchool(School school) throws SQLException, NamingException {

    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = pool.getConnection();
    PreparedStatement ps = null;

    String sql = "UPDATE school "
            + "SET schoolName = ?, shortName = ?, schoolType = ?, schoolLevel = ?, "
            + "country = ?, city = ?, schoolAddress = ?, website = ?, schoolEmail = ?, isActive = ? "
            + "WHERE schoolID = ?";

    try {
        ps = connection.prepareStatement(sql);

        ps.setString(1,  school.getSchoolName());
        ps.setString(2,  school.getShortName());
        ps.setString(3,  school.getSchoolType());
        ps.setString(4,  school.getSchoolLevel());
        ps.setString(5,  school.getCountry());
        ps.setString(6,  school.getSchoolCity());     
        ps.setString(7,  school.getSchoolAddress());
        ps.setString(8,  school.getWebsite());
        ps.setString(9,  school.getSchoolEmail());
        ps.setBoolean(10, school.isActive());
        ps.setInt(11, school.getSchoolID());

        return ps.executeUpdate();

    } finally {
        if (ps != null) ps.close();
        pool.freeConnection(connection);
    }
}

    
    //search for student by studentID
    
    
    
    public static LinkedHashMap<String, Students> selectStudentsByID(int schoolID, String studentID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT schoolID, userID, registrationNumber, firstName, lastName,"
                + " gender, dateOfBirth, gradeLevel, department, enrollmentDate, academicYear,"
                + " phone, address, isActive, createdAt, updatedAt FROM students WHERE schoolID = ? AND registrationNumber =? ORDER BY firstName";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);
        ps.setString(2, studentID);

        rs = ps.executeQuery();

        LinkedHashMap<String, Students> student = new LinkedHashMap<>();
        while (rs.next()) {
            Students s = new Students();
            s.setUserID(rs.getInt("userID"));
            s.setFirstName(rs.getString("firstName"));
            s.setLastName(rs.getString("lastName"));
            s.setSchoolID(rs.getInt("schoolID"));
            s.setRegistrationNumber(rs.getString("registrationNumber"));
            s.setGender(rs.getString("gender"));
            s.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
            s.setGradeLevel(rs.getString("gradeLevel"));
            s.setDepartment(rs.getString("department"));
            s.setEnrollmentDate(rs.getDate("enrollmentDate").toLocalDate());
            s.setAcademicYear(rs.getString("academicYear"));
            s.setPhoneNumber(rs.getString("phone"));
            s.setAddress(rs.getString("address"));
            s.setIsActive(rs.getString("isActive"));
            s.setCreatedAT(rs.getTimestamp("createdAt").toLocalDateTime());
            s.setUpdatedAT(rs.getTimestamp("updatedAt").toLocalDateTime());

            student.put(s.getRegistrationNumber(), s);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return student;
    }

}
