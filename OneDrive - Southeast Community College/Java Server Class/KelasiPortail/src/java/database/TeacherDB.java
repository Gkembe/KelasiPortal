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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
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
                = "INSERT INTO teachers (user_id, first_name, middleName, last_name, gender, "
                + "subject, qualification, phoneNumber, officeLocation, address, email, "
                + "isActive, hireDate, schoolID, dateOfBirth ) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, teacher.getUserID());
        ps.setString(2, teacher.getFirstName());
        ps.setString(3, teacher.getMiddleName());
        ps.setString(4, teacher.getLastName());
        ps.setString(5, teacher.getGender());
        ps.setString(6, teacher.getSubject());
        ps.setString(7, teacher.getQualification());
        ps.setString(8, teacher.getPhoneNumber());
        ps.setString(9, teacher.getOfficeLocation());
        ps.setString(10, teacher.getAddress());
        ps.setString(11, teacher.getEmail());
        ps.setString(12, teacher.getIsActive());
        ps.setDate(13, Date.valueOf(teacher.getHireDate()));
        ps.setInt(14, teacher.getSchoolID());

        ps.setDate(15, Date.valueOf(teacher.getDateOfBirth()));
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

        String query = "SELECT *  FROM teachers WHERE schoolID = ? ORDER BY first_name";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        rs = ps.executeQuery();

        LinkedHashMap<String, Teachers> teacher = new LinkedHashMap<>();
        while (rs.next()) {
            Teachers t = new Teachers();
            t.setUserID(rs.getInt("user_id"));
            t.setFirstName(rs.getString("first_name"));
            t.setMiddleName(rs.getString("middleName"));
            t.setGender(rs.getString("gender"));
            t.setLastName(rs.getString("last_name"));
            t.setSubject(rs.getString("subject"));
            t.setQualification(rs.getString("qualification"));
            t.setPhoneNumber(rs.getString("phoneNumber"));
            t.setOfficeLocation(rs.getString("officeLocation"));
            t.setAddress(rs.getString("address"));
            t.setEmail(rs.getString("email"));
            t.setIsActive(rs.getString("isActive"));
//            t.setHireDate(rs.getDate("hireDate").toLocalDate());
//            t.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());

            java.sql.Date hireDate = rs.getDate("hireDate");

            if (hireDate != null) {
                t.setHireDate(hireDate.toLocalDate());
            } else {
                t.setHireDate(null);
            }

            java.sql.Date dob = rs.getDate("dateOfBirth");

            if (dob != null) {
                t.setDateOfBirth(dob.toLocalDate());
            } else {
                t.setDateOfBirth(null);
            }
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
            t.setMiddleName(rs.getString("middleName"));
            t.setLastName(rs.getString("last_name"));
            t.setSubject(rs.getString("subject"));
            t.setQualification(rs.getString("qualification"));
            t.setPhoneNumber(rs.getString("phoneNumber"));
            t.setOfficeLocation(rs.getString("officeLocation"));
            t.setIsActive(rs.getString("isActive"));
            t.setHireDate(rs.getDate("hireDate").toLocalDate());
            t.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
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

    public static Teachers getTeacherForProfileByID(int teacherID, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT * FROM teachers "
                + "WHERE teacherID = ? AND schoolID =  ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, teacherID);
        ps.setInt(2, schoolID);

        ResultSet rs = ps.executeQuery();

        Teachers t = null;
        if (rs.next()) {
            t = new Teachers();

            t.setUserID(rs.getInt("user_Id"));
            t.setTeacherID(teacherID);
            t.setLastName(rs.getString("last_name"));
            t.setSubject(rs.getString("subject"));
            t.setQualification(rs.getString("qualification"));
            t.setOfficeLocation(rs.getString("officeLocation"));
            t.setSchoolID(rs.getInt("schoolID"));
            t.setAddress(rs.getString("address"));
            t.setEmail(rs.getString("email"));
            t.setMiddleName(rs.getString("middleName"));
            t.setGender(rs.getString("gender"));
            t.setFirstName(rs.getString("first_name"));
            t.setMiddleName(rs.getString("middleName"));
            t.setLastName(rs.getString("last_name"));

            java.sql.Date hireDate = rs.getDate("hireDate");

            if (hireDate != null) {
                t.setHireDate(hireDate.toLocalDate());
            } else {
                t.setHireDate(null);
            }

            java.sql.Date dob = rs.getDate("dateOfBirth");

            if (dob != null) {
                t.setDateOfBirth(dob.toLocalDate());
            } else {
                t.setDateOfBirth(null);
            }
            t.setPhoneNumber(rs.getString("phoneNumber"));

            t.setIsActive(rs.getString("isActive"));
            t.setCreatedAT(rs.getTimestamp("createdAt").toLocalDateTime());

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return t;
    }

    public static int updateTeacher(Teachers t) throws SQLException, NamingException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String sql = "UPDATE Teachers "
                + "SET first_name = ?, middleName =?, last_name =?, gender =?, "
                + "subject = ?, qualification =?, phoneNumber =?, officeLocation =?, address =?, email=?, "
                + "hireDate = ?, dateOfBirth =?"
                + "WHERE schoolID = ? and teacherID = ?";

        try {
            ps = connection.prepareStatement(sql);

            ps.setString(1, t.getFirstName());
            ps.setString(2, t.getMiddleName());
            ps.setString(3, t.getLastName());
            ps.setString(4, t.getGender());
            ps.setString(5, t.getSubject());
            ps.setString(6, t.getQualification());
            ps.setString(7, t.getPhoneNumber());
            ps.setString(8, t.getOfficeLocation());
            ps.setString(9, t.getAddress());
            ps.setString(10, t.getEmail());
            ps.setDate(11, Date.valueOf(t.getHireDate()));
            ps.setDate(12, Date.valueOf(t.getDateOfBirth()));

            ps.setInt(13, t.getSchoolID());
            ps.setInt(14, t.getTeacherID());

            return ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }
            pool.freeConnection(connection);
        }
    }

}
