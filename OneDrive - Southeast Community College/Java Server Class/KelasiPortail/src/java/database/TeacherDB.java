/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.Teachers;
import business.User;
import static database.UserDB.insertUser;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author kembe
 */
public class TeacherDB {
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
    
    
    //select teachers
    public static LinkedHashMap<String, Teachers> selectAllTeachersByID(int schoolID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT user_id, first_name, last_name, subject, qualification, phoneNumber, officeLocation, "
                + "isActive, hireDate, createdAT, schoolID, teacherID FROM teachers WHERE schoolID = ? ORDER BY first_name";

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

    //SEARCH FOR TEACHER
    public static LinkedHashMap<String, Teachers> searchTeachersByName(int schoolID, String teacherName) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT user_id, first_name, last_name, subject, qualification, phoneNumber, officeLocation, "
                + "isActive, hireDate, createdAT, schoolID, teacherID FROM teachers WHERE schoolID = ? AND (teacherID=? OR first_name LIKE ? OR last_name LIKE ?) ORDER BY first_name";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);
        ps.setString(2, teacherName);
        ps.setString(3, "%" + teacherName + "%");
        ps.setString(4, "%" + teacherName + "%");

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


}
