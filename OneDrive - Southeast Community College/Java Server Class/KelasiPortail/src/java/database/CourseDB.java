/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.Courses;
import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author kembe
 */
public class CourseDB {
    
    public static LinkedHashMap<String, Courses> selectCourses(int schoolId) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM courses WHERE schoolID = ?";

        
        ps = connection.prepareStatement(query);

        ps.setInt(1, schoolId);
        rs = ps.executeQuery();

        LinkedHashMap<String, Courses> course = new LinkedHashMap<>();
        while (rs.next()) {

            int courseID = rs.getInt("courseID");
            int schoolID = rs.getInt("schoolID");
            String courseName = rs.getString("courseName");
            String courseCode = rs.getString("courseCode");
            LocalDateTime createdAt = rs.getTimestamp("createdAT").toLocalDateTime();
            String status = rs.getString("status");
            
            

            Courses cours  = new Courses(courseID, schoolID, courseName, courseCode, createdAt, status);
            
            course.put(cours.getCourseName(), cours);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return course;

    }
    
    
    public static int insertCourses(Courses c) throws SQLException, NamingException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        int rows = 0;
        String query
                = "INSERT INTO courses (schoolID, courseName, courseCode, status) "
                + "VALUES (?, ?, ?, ?)";

        ps = conn.prepareStatement(query);

        ps.setInt(1, c.getSchoolID());
        
       
        
        ps.setString(2, c.getCourseName());
       
        ps.setString(3, c.getCourseCode());
        
        ps.setString(4, c.getStatus());

        rows = ps.executeUpdate();

        if (ps != null) {
            ps.close();
            pool.freeConnection(conn);

        }

        return rows;
    }

    public static void deactiveCourse(int courseID, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE courses SET status='INACTIVE' WHERE courseID =? AND schoolID =?";
        ps = connection.prepareStatement(q1);
        ps.setInt(1, courseID);
        ps.setInt(2, schoolID);
        
        ps.executeUpdate();
        ps.close();

        pool.freeConnection(connection);
    }
    
    public static void activeCourse(int courseID, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE courses SET status='ACTIVE' WHERE courseID =? AND schoolID =?";
        ps = connection.prepareStatement(q1);
        ps.setInt(1, courseID);
        ps.setInt(2, schoolID);
        
        ps.executeUpdate();
        ps.close();

        pool.freeConnection(connection);
    }
    
}
