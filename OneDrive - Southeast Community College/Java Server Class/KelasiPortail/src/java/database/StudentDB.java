/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.Students;
import business.User;
import static database.UserDB.insertUser;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.naming.NamingException;

/**
 *
 * @author kembe
 */
public class StudentDB {
    
    
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
                = "INSERT INTO students (schoolID, userID, registrationNumber, firstName, middleName, lastName,"
                + " gender, dateOfBirth, enrollmentDate, academicYear, phone, address, isActive, levelID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, student.getSchoolID());
        ps.setInt(2, student.getUserID());

        ps.setString(3, student.getRegistrationNumber());
        ps.setString(4, student.getFirstName());
        ps.setString(5, student.getMiddleName());
        ps.setString(6, student.getLastName());
        ps.setString(7, student.getGender());
        ps.setDate(8, Date.valueOf(student.getDateOfBirth()));
        
        ps.setDate(9, Date.valueOf(student.getEnrollmentDate()));
        ps.setString(10, student.getAcademicYear());
        ps.setString(11, student.getPhoneNumber());
        ps.setString(12, student.getAddress());
        ps.setString(13, student.getIsActive());
        ps.setInt(14, student.getLevelID());

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

        String query = "SELECT schoolID, userID, registrationNumber, firstName, middleName, lastName,"
                + " gender, dateOfBirth, enrollmentDate, academicYear,"
                + " phone, address, isActive, createdAt, updatedAt, levelID FROM students WHERE schoolID = ? ORDER BY firstName";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        rs = ps.executeQuery();

        LinkedHashMap<String, Students> student = new LinkedHashMap<>();
        while (rs.next()) {
            Students s = new Students();
            s.setUserID(rs.getInt("userID"));
            s.setFirstName(rs.getString("firstName"));
            s.setMiddleName(rs.getString("middleName"));
            s.setLastName(rs.getString("lastName"));
            s.setSchoolID(rs.getInt("schoolID"));
            s.setRegistrationNumber(rs.getString("registrationNumber"));
            s.setGender(rs.getString("gender"));
            s.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
            
            s.setEnrollmentDate(rs.getDate("enrollmentDate").toLocalDate());
            s.setAcademicYear(rs.getString("academicYear"));
            s.setPhoneNumber(rs.getString("phone"));
            s.setAddress(rs.getString("address"));
            s.setIsActive(rs.getString("isActive"));
            s.setCreatedAT(rs.getTimestamp("createdAt").toLocalDateTime());
            s.setUpdatedAT(rs.getTimestamp("updatedAt").toLocalDateTime());
            s.setLevelID(rs.getInt("levelID"));

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

    //search for student by studentID, firstName, and lastName
    public static LinkedHashMap<String, Students> selectStudentsByID(int schoolID, String studentID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT schoolID, userID, registrationNumber, firstName, middleName, lastName,"
                + " gender, dateOfBirth, enrollmentDate, academicYear,"
                + " phone, address, isActive, createdAt, updatedAt FROM students "
                + "WHERE schoolID = ? AND (registrationNumber =? OR FirstName LIKE ? OR lastName LIKE ? ) ORDER BY firstName";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);
        ps.setString(2, studentID);
        ps.setString(3, "%" + studentID + "%");
        ps.setString(4, "%" + studentID + "%");

        rs = ps.executeQuery();

        LinkedHashMap<String, Students> student = new LinkedHashMap<>();
        while (rs.next()) {
            Students s = new Students();
            s.setUserID(rs.getInt("userID"));
            s.setFirstName(rs.getString("firstName"));
            s.setMiddleName(rs.getString("middleName"));
            s.setLastName(rs.getString("lastName"));
            s.setSchoolID(rs.getInt("schoolID"));
            s.setRegistrationNumber(rs.getString("registrationNumber"));
            s.setGender(rs.getString("gender"));
            s.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
            
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

    public static Students getStudentForProfileByID(String studentID, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT s.*, l.levelName, l.levelCode, d.departmentName "
                + "FROM students AS s LEFT JOIN levels AS l ON s.levelID = l.levelID "
                + "LEFT JOIN departments AS d ON l.departmentID = d.departmentID "
                + "WHERE s.registrationNumber = ? AND s.schoolID =  ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, studentID);
        ps.setInt(2, schoolID);

        ResultSet rs = ps.executeQuery();

        Students s = null;
        if (rs.next()) {
            s = new Students();

            s.setUserID(rs.getInt("userID"));
            s.setStudentID(rs.getInt("studentID"));
            s.setSchoolID(rs.getInt("schoolID"));
            s.setRegistrationNumber(rs.getString("registrationNumber"));
            s.setLevelName(rs.getString("levelName"));
            s.setLevelCode(rs.getString("levelCode"));
            s.setDepartmentName(rs.getString("departmentName"));
            
            s.setSchoolID(rs.getInt("schoolID"));
            s.setFirstName(rs.getString("firstName"));
            s.setMiddleName(rs.getString("middleName"));
            s.setLastName(rs.getString("lastName"));
            s.setGender(rs.getString("gender"));
            s.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
            s.setEnrollmentDate(rs.getDate("enrollmentDate").toLocalDate());
            s.setAcademicYear(rs.getString("academicYear"));
            s.setPhoneNumber(rs.getString("phone"));
            s.setAddress(rs.getString("address"));
            s.setIsActive(rs.getString("isActive"));
            s.setCreatedAT(rs.getTimestamp("createdAt").toLocalDateTime());
            s.setUpdatedAT(rs.getTimestamp("updatedAt").toLocalDateTime());
            s.setLevelID(rs.getInt("levelID"));
            
            
           
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return s;
    }
    
    public static int updateStudentInfo(Students s) throws SQLException, NamingException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String sql = "UPDATE students "
                + "SET firstName = ?, middleName = ?, lastName = ?, "
                + "gender = ?, dateOfBirth = ?, address = ?, academicYear = ?, phone = ? "
                + "WHERE schoolID = ? and studentID = ?";

        try {
            ps = connection.prepareStatement(sql);

            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getMiddleName());
            ps.setString(3, s.getLastName());
            ps.setString(4, s.getGender());
            ps.setDate(5, Date.valueOf(s.getDateOfBirth()));
            ps.setString(6, s.getAddress());
            ps.setString(7, s.getAcademicYear());
            ps.setString(8, s.getPhoneNumber());
            ps.setInt(9, s.getSchoolID());
            ps.setInt(10, s.getStudentID());
            

            return ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }
            pool.freeConnection(connection);
        }
    }
    
    public static User selectStudentUser(int userID, int schoolID) throws NamingException, SQLException {

        
        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;

        connection = pool.getConnection();

        String query = "SELECT username, email, phone FROM users WHERE id = ? AND schoolID = ? AND role = 'STUDENT'";

        ps = connection.prepareStatement(query);
        ps.setInt(1, userID);
        ps.setInt(2, schoolID);

        rs = ps.executeQuery();

        
        if (rs.next()) {

            user = new User();
            
            user.setEmail(rs.getString("email"));
            user.setUserName(rs.getString("username"));
            user.setPhoneNumber(rs.getString("phone"));
            
            
            
            
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return user;

    }

    public static int updateStudentUserInfo(User u) throws SQLException, NamingException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String sql = "UPDATE users "
                + "SET phone = ?, email = ?, password = ?, "
                
                + "WHERE id = ? AND schoolID = ? AND role = 'STUDENT'";

        try {
            ps = connection.prepareStatement(sql);

            ps.setString(1, u.getPhoneNumber());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
           
            

            return ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }
            pool.freeConnection(connection);
        }
    }
    
}
