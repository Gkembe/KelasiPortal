/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.Courses;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author kembe
 */
public class LevelCourseDB {

    public static int insertLevelCourse(int levelID, int courseID) throws SQLException, NamingException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO levelCourse (levelID, courseID) VALUES (?, ?)";

        ps = conn.prepareStatement(query);
        ps.setInt(1, levelID);
        ps.setInt(2, courseID);

        int rows = ps.executeUpdate();

        if (ps != null) {
            ps.close();
        }

        pool.freeConnection(conn);

        return rows;
    }

    public static LinkedHashMap<String, Courses> selectCoursesByLevel(int levelID) throws SQLException, NamingException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        LinkedHashMap<String, Courses> courses = new LinkedHashMap<>();

        String query = "SELECT c.courseID, c.schoolID, c.courseName, c.courseCode, c.status, c.createdAT "
                + "FROM levelCourse lc "
                + "JOIN courses c ON lc.courseID = c.courseID "
                + "WHERE lc.levelID = ? "
                + "AND c.status = 'ACTIVE'";

        ps = conn.prepareStatement(query);
        ps.setInt(1, levelID);

        rs = ps.executeQuery();

        while (rs.next()) {
            Courses c = new Courses();

            c.setCourseID(rs.getInt("courseID"));
            c.setSchoolID(rs.getInt("schoolID"));
            c.setCourseName(rs.getString("courseName"));
            c.setCourseCode(rs.getString("courseCode"));
            c.setStatus(rs.getString("status"));
            c.setCreatedAT(rs.getTimestamp("createdAT").toLocalDateTime());

            courses.put(String.valueOf(c.getCourseID()), c);
        }

        if (rs != null) {
            rs.close();
        }

        if (ps != null) {
            ps.close();
        }

        pool.freeConnection(conn);

        return courses;
    }
}
