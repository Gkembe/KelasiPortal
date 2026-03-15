/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.Levels;
import business.StudentGuardians;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    
}
