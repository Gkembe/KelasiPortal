/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.Levels;
import business.StudentGuardians;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.naming.NamingException;

/**
 *
 * @author kembe
 */
public class StudentGuardianDB {

    public static int insertGuardain(StudentGuardians guardian) throws SQLException, NamingException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        int rows = 0;
        String query
                = "INSERT INTO studentGuardians (studentID, fullName, phone, email, address, occupation, relationshipType, isPrimary) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(query);

        ps.setInt(1, guardian.getStudentID());

        ps.setString(2, guardian.getFullName());
        ps.setString(3, guardian.getPhone());
        ps.setString(4, guardian.getEmail());
        ps.setString(5, guardian.getAddress());
        ps.setString(6, guardian.getOccupation());
        ps.setString(7, guardian.getRelationship());
        ps.setString(8, guardian.getIsPrimary());

        rows = ps.executeUpdate();

        if (ps != null) {
            ps.close();
            pool.freeConnection(conn);

        }

        return rows;
    }

    public static LinkedHashMap<String, StudentGuardians> selectGuardianByStudentID(int studentID)
            throws NamingException, SQLException {

        LinkedHashMap<String, StudentGuardians> guardian = new LinkedHashMap<>();

        ConnectionPool pool = ConnectionPool.getInstance();

        String query = "SELECT * FROM studentGuardians "
                + "WHERE studentID = ? AND isActive = 'ACTIVATE'";
                

       
            Connection connection = pool.getConnection(); 
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, studentID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentGuardians g = new StudentGuardians();
                    g.setGuardianID(rs.getInt("guardianID"));
                    g.setStudentID(rs.getInt("studentID"));
                    g.setFullName(rs.getString("fullName"));
                    g.setPhone(rs.getString("phone"));
                    g.setEmail(rs.getString("email"));
                    g.setAddress(rs.getString("address"));
                    g.setOccupation(rs.getString("occupation"));
                    g.setRelationship(rs.getString("relationshipType"));
                    g.setIsPrimary(rs.getString("isPrimary"));
                    g.setCreatedAT(rs.getTimestamp("createdAT").toLocalDateTime());
                    g.setUpdatedAT(rs.getTimestamp("updatedAT").toLocalDateTime());
                    guardian.put(String.valueOf(g.getGuardianID()), g);
                }
            }
        

        return guardian;
    }
    
    public static StudentGuardians selectStudentGuardian(int studentID, int guardianID) throws NamingException, SQLException {

        
        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StudentGuardians g = null;

        connection = pool.getConnection();

        String query = "SELECT * FROM studentGuardians "
                + "WHERE studentID = ? AND guardianID =?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, studentID);
        ps.setInt(2, guardianID);

        rs = ps.executeQuery();

        
        if (rs.next()) {

            g = new StudentGuardians();
            
            g.setEmail(rs.getString("email"));
            g.setFullName(rs.getString("fullName"));
            g.setAddress(rs.getString("address"));
            g.setRelationship(rs.getString("relationshipType"));
            g.setOccupation(rs.getString("occupation"));
            g.setIsPrimary(rs.getString("isPrimary"));
            g.setStudentID(rs.getInt("studentID"));
            g.setGuardianID(rs.getInt("guardianID"));
            
            g.setPhone(rs.getString("phone"));
            
            
            
            
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return g;

    }
    
    public static int updateGuardianInfo(StudentGuardians g) throws SQLException, NamingException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String sql = "UPDATE studentGuardians "
                + "SET fullName = ?, phone = ?, email = ?, address = ?, occupation =?, relationshipType =?, isPrimary =?, updatedAT = NOW()"
                
                + "WHERE studentID = ? AND guardianID =?";

        try {
            ps = connection.prepareStatement(sql);

            ps.setString(1, g.getFullName());
            ps.setString(2, g.getPhone());
            ps.setString(3, g.getEmail());
            ps.setString(4, g.getAddress());
            ps.setString(5, g.getOccupation());
            ps.setString(6, g.getRelationship());
            
            ps.setString(7, g.getIsPrimary());
            
            ps.setInt(8, g.getStudentID());
            ps.setInt(9, g.getGuardianID());
            
           
            
            int rows  = ps.executeUpdate();

           
            return rows;

            
        } finally {
            if (ps != null) {
                ps.close();
            }
            pool.freeConnection(connection);
        }
    }
    
    
    public static void deactiveGuardian(int guardianID, int studentID) throws SQLException, NamingException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE studentGuardians SET isActive='INACTIVATE' WHERE guardianID =? AND studentID =?";
        ps = connection.prepareStatement(q1);
        ps.setInt(1, guardianID);
        ps.setInt(2, studentID);
        
        ps.executeUpdate();
        ps.close();

        pool.freeConnection(connection);
    }
}
