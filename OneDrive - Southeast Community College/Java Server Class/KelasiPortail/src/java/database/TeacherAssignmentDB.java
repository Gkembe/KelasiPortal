/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.Levels;

import business.TeacherAssignments;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author kembe
 */
public class TeacherAssignmentDB {

    public static LinkedHashMap<String, Levels> getLevelsNameAndHisDepartment(int schoolID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT l.levelID, l.levelName, l.levelCode, d.departmentName FROM levels AS l INNER JOIN departments AS d "
                + "ON l.departmentID = d.departmentID WHERE l.schoolID = ? AND l.status = 'ACTIVE' ORDER BY d.departmentName, l.levelName";

        ps = connection.prepareStatement(query);

        ps.setInt(1, schoolID);
        rs = ps.executeQuery();

        LinkedHashMap<String, Levels> level = new LinkedHashMap<>();
        while (rs.next()) {
            Levels l = new Levels();
            l.setLevelID(rs.getInt("levelID"));
            l.setLevelName(rs.getString("levelName"));
            l.setDepartmentName(rs.getString("departmentName"));
            l.setLevelCode(rs.getString("levelCode"));

            String displayName = l.getLevelName() + " _ " + l.getDepartmentName() + " _ " + l.getLevelCode();
            level.put(displayName, l);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return level;

    }

    public static int assignCourse(int schoolID, int teacherID, int levelID, int courseID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query
                = "INSERT INTO teacherAssignments "
                + "(schoolID, teacherID, levelID, courseID, status, createdAt) "
                + "VALUES (?, ?, ?, ?, 'ACTIVE', NOW())";

        PreparedStatement ps
                = connection.prepareStatement(query);

        ps.setInt(1, schoolID);
        ps.setInt(2, teacherID);
        ps.setInt(3, levelID);
        ps.setInt(4, courseID);

        int rowCount = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rowCount;
    }

    public static boolean courseAlreadyAssigned(int schoolID, int levelID, int courseID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query
                = "SELECT assignmentID "
                + "FROM teacherAssignments "
                + "WHERE schoolID = ? "
                + "AND levelID = ? "
                + "AND courseID = ? "
                + "AND status = 'ACTIVE'";

        PreparedStatement ps
                = connection.prepareStatement(query);

        ps.setInt(1, schoolID);
        ps.setInt(2, levelID);
        ps.setInt(3, courseID);

        ResultSet rs = ps.executeQuery();

        boolean assigned = rs.next();

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return assigned;
    }

    public static ArrayList<TeacherAssignments> getCoursesByTeacherID(int teacherID)throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query
                = "SELECT ta.assignmentID, ta.teacherID, ta.levelID, ta.courseID, c.courseName, l.levelName, d.departmentName "
                + "FROM teacherAssignments ta "
                + "INNER JOIN courses c ON ta.courseID = c.courseID INNER JOIN levels l ON ta.levelID = l.levelID "
                + "INNER JOIN departments d ON l.departmentID = d.departmentID WHERE ta.teacherID = ? AND ta.status = 'ACTIVE' "
                + "ORDER BY d.departmentName, l.levelName, c.courseName";

        PreparedStatement ps= connection.prepareStatement(query);

        ps.setInt(1, teacherID);

        ResultSet rs = ps.executeQuery();

        ArrayList<TeacherAssignments> assignments = new ArrayList<TeacherAssignments>();

        while (rs.next()) {

            TeacherAssignments assignment = new TeacherAssignments();

            assignment.setAssignmentID(rs.getInt("assignmentID"));

            assignment.setTeacherID(rs.getInt("teacherID"));

            assignment.setLevelID(rs.getInt("levelID"));

            assignment.setCourseID(rs.getInt("courseID"));

            assignment.setCourseName(rs.getString("courseName"));

            assignment.setLevelName(rs.getString("levelName"));

            assignment.setDepartmentName(rs.getString("departmentName"));

            assignments.add(assignment);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return assignments;
    }

    public static int removeTeacherCourse(int assignmentID)throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "UPDATE teacherAssignments SET status = 'INACTIVE' WHERE assignmentID = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, assignmentID);

        int rowCount = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);

        return rowCount;
    }
    
    
    // Check if course already assigned to a professor in the past and reactivate it instead if insert it again
    
    public static String getAssignmentStatus(
        int schoolID,
        int levelID,
        int courseID)
        throws NamingException, SQLException {

    ConnectionPool pool =
            ConnectionPool.getInstance();

    Connection connection =
            pool.getConnection();

    String query =
            "SELECT status "
          + "FROM teacherAssignments "
          + "WHERE schoolID = ? "
          + "AND levelID = ? "
          + "AND courseID = ?";

    PreparedStatement ps =
            connection.prepareStatement(query);

    ps.setInt(1, schoolID);
    ps.setInt(2, levelID);
    ps.setInt(3, courseID);

    ResultSet rs = ps.executeQuery();

    String status = null;

    if (rs.next()) {
        status = rs.getString("status");
    }

    rs.close();
    ps.close();
    pool.freeConnection(connection);

    return status;
}
    
    //IF COURSE FIND, REACTIVATE IT
    
    
   public static int reactivateCourse(
        int schoolID,
        int teacherID,
        int levelID,
        int courseID)
        throws NamingException, SQLException {

    ConnectionPool pool =
            ConnectionPool.getInstance();

    Connection connection =
            pool.getConnection();

    String query =
            "UPDATE teacherAssignments "
          + "SET teacherID = ?, "
          + "status = 'ACTIVE' "
          + "WHERE schoolID = ? "
          + "AND levelID = ? "
          + "AND courseID = ?";

    PreparedStatement ps =
            connection.prepareStatement(query);

    ps.setInt(1, teacherID);
    ps.setInt(2, schoolID);
    ps.setInt(3, levelID);
    ps.setInt(4, courseID);

    int rowCount = ps.executeUpdate();

    ps.close();
    pool.freeConnection(connection);

    return rowCount;
}
}
