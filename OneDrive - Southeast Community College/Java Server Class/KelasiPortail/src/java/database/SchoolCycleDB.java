/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.School;
import business.SchoolCycle;
import business.Teachers;
import business.User;
import static database.SchoolDB.insertSchool;
import static database.TeacherDB.insertTeacher;
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
public class SchoolCycleDB {

    public static int insertSchoolCycle(Connection conn, SchoolCycle cycle)
            throws SQLException, NamingException {
        
         
        PreparedStatement ps;
        ResultSet rs;

        String sql = "INSERT INTO schoolCycle (schoolID, cycleName, status) VALUES (?, ?, ?)";

        ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, cycle.getSchoolID());
        ps.setString(2, cycle.getCycleName());
        ps.setString(3, cycle.getStatus());

        ps.executeUpdate();

        rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    
    public static boolean registerSchoolAndAdmin(School school, SchoolCycle cycle)
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
        cycle.setSchoolID(schoolID);

        //insert schoolCycle
        int rows = insertSchoolCycle(conn, cycle);
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
    
    public static LinkedHashMap<String, SchoolCycle> selectSchoolCycle() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT schoolCycleID, schoolID, cycleName, status FROM schoolCycle";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<String, SchoolCycle> cycle = new LinkedHashMap<>();
        while (rs.next()) {

            int schoolCycleID = rs.getInt("schoolCycleID");
            int schoolID = rs.getInt("schoolID");
            String cycleName = rs.getString("cycleName");
            
            
            String status = rs.getString("status");
            

            SchoolCycle cycles = new SchoolCycle(schoolCycleID, schoolID, cycleName, status);
            cycle.put(cycles.getCycleName(), cycles);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return cycle;

    }
    
    public static LinkedHashMap<String, SchoolCycle> selectAllCycleByID(int schoolID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM schoolCycle WHERE schoolID = ? ORDER BY cycleName";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        rs = ps.executeQuery();

        LinkedHashMap<String, SchoolCycle> cycle = new LinkedHashMap<>();
        while (rs.next()) {
            SchoolCycle c = new SchoolCycle();
            
            c.setSchoolCycle(rs.getInt("schoolCycleID"));
            c.setSchoolID(rs.getInt("schoolID"));
            c.setCycleName(rs.getString("cycleName"));
            c.setStatus(rs.getString("status"));
            

            cycle.put("" + "" + c.getCycleName(), c);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return cycle;

    }
    
    public static SchoolCycle getCycleByID(int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT * FROM schoolCycle WHERE schoolID = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        ResultSet rs = ps.executeQuery();

        SchoolCycle cycle = null;
        if (rs.next()) {
            cycle = new SchoolCycle();
            
            cycle.setSchoolCycle(rs.getInt("schoolCycleID"));
            cycle.setSchoolID(rs.getInt("schoolID"));
            cycle.setCycleName(rs.getString("cycleName"));
            cycle.setStatus(rs.getString("status"));
            

           
        }


        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return cycle;
    }

}
