/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.School;
import business.User;
import static database.UserDB.insertUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author kembe
 */
public class SchoolDB {
    
    
    public static LinkedHashMap<Integer, School> selectSchool() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT schoolID, schoolName, shortName, registrationNumber, schoolType, "
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
                    schoolType, country, schoolCity, schoolAddress, schoolEmail, website, schoolLogo, isActive, createdAt);
            school.put(schoolss.getSchoolID(), schoolss);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return school;

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
    
    //Insert the admin user
    public static int insertSchool(Connection conn, School school)
            throws SQLException, NamingException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO school (schoolName, shortName, registrationNumber, schoolType,"
                + "country, city, schoolAddress, schoolEmail, website, schoolLogo, isActive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, school.getSchoolName());
        ps.setString(2, school.getShortName());
        ps.setString(3, school.getRegistrationNumber());
        ps.setString(4, school.getSchoolType());
        
        ps.setString(5, school.getCountry());
        ps.setString(6, school.getSchoolCity());
        ps.setString(7, school.getSchoolAddress());
        ps.setString(8, school.getSchoolEmail());
        ps.setString(9, school.getWebsite());
        ps.setString(10, school.getSchoolLogo());
        ps.setBoolean(11, school.isActive());

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

        String query = "SELECT schoolID, schoolName, shortName, registrationNumber, schoolType, "
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
                + "SET schoolName = ?, shortName = ?, schoolType = ?, "
                + "country = ?, city = ?, schoolAddress = ?, website = ?, schoolEmail = ?, isActive = ? "
                + "WHERE schoolID = ?";

        try {
            ps = connection.prepareStatement(sql);

            ps.setString(1, school.getSchoolName());
            ps.setString(2, school.getShortName());
            ps.setString(3, school.getSchoolType());
           
            ps.setString(4, school.getCountry());
            ps.setString(5, school.getSchoolCity());
            ps.setString(6, school.getSchoolAddress());
            ps.setString(7, school.getWebsite());
            ps.setString(8, school.getSchoolEmail());
            ps.setBoolean(9, school.isActive());
            ps.setInt(10, school.getSchoolID());

            return ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }
            pool.freeConnection(connection);
        }
    }

}
