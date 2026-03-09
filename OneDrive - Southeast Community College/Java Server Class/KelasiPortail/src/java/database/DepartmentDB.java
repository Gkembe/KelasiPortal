/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.Department;
import business.Levels;
import java.sql.Connection;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

/**
 *
 * @author kembe
 */
public class DepartmentDB {
    
    public static int insertDepartment(Department d) throws SQLException, NamingException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        int rows = 0;
        String query
                = "INSERT INTO departments (schoolID, departmentName, status) "
                + "VALUES (?, ?, ?)";

        ps = conn.prepareStatement(query);

        ps.setInt(1, d.getSchoolID());
        
       
        
        ps.setString(2, d.getDepartmentName());
       
        ps.setString(3, d.getStatus());

        rows = ps.executeUpdate();

        if (ps != null) {
            ps.close();
            pool.freeConnection(conn);

        }

        return rows;
    }
    
    public static boolean departmentExists(String departmentName) throws NamingException, SQLException {

        boolean exists = false;

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = pool.getConnection();

        String query = "SELECT departmentName FROM departments WHERE departmentName = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, departmentName);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            exists = true;

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return exists;

    }
    
     //SELECT ALL DEPARTMENT'S FIELD
    public static LinkedHashMap<String, Department> selectAllDepartmentByID(int schoolID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        

        String query = "SELECT * FROM departments WHERE schoolID = ? ORDER BY departmentName";

        ps = connection.prepareStatement(query);
        

        ps.setInt(1, schoolID);
        rs = ps.executeQuery();

        LinkedHashMap<String, Department> department = new LinkedHashMap<>();
        while (rs.next()) {
            Department d = new Department();
            d.setDepartmentID(rs.getInt("departmentID"));
           
            
            
            d.setSchoolID(rs.getInt("schoolID"));
            d.setDepartmentName(rs.getString("departmentName"));
          
            
            d.setCreatedAT(rs.getTimestamp("createdAT").toLocalDateTime());
            d.setStatus(rs.getString("status"));
            

            department.put("" + d.getDepartmentName(), d);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return department;

    }
    
    public static LinkedHashMap<String, Department> selectAllDepartmentByIDAndStatus(int schoolID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        

        String query = "SELECT * FROM departments WHERE schoolID = ? AND status = 'ACTIVE' ORDER BY departmentName";

        ps = connection.prepareStatement(query);
        

        ps.setInt(1, schoolID);
        rs = ps.executeQuery();

        LinkedHashMap<String, Department> department = new LinkedHashMap<>();
        while (rs.next()) {
            Department d = new Department();
            d.setDepartmentID(rs.getInt("departmentID"));
           
            
            
            d.setSchoolID(rs.getInt("schoolID"));
            d.setDepartmentName(rs.getString("departmentName"));
          
            
            d.setCreatedAT(rs.getTimestamp("createdAT").toLocalDateTime());
            d.setStatus(rs.getString("status"));
            

            department.put("" + d.getDepartmentName(), d);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return department;

    }
    
}
