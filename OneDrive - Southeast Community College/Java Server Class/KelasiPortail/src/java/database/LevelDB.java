/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.Levels;
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
public class LevelDB {
    
    //LEVELS PROCESS INSERT
    public static int insertLevels(Levels level) throws SQLException, NamingException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        int rows = 0;
        String query
                = "INSERT INTO levels (schoolID, departmentID, levelName, levelCode, status) "
                + "VALUES (?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(query);

        ps.setInt(1, level.getSchoolID());
        
        if(level.getDepartmentID() == null){
            
            ps.setNull(2, java.sql.Types.INTEGER);
        }else{
            
           ps.setInt(2, level.getDepartmentID());
        }
        
        ps.setString(3, level.getLevelName());
        ps.setString(4, level.getLevelCode());
        ps.setString(5, level.getStatus());

        rows = ps.executeUpdate();

        if (ps != null) {
            ps.close();
            pool.freeConnection(conn);

        }

        return rows;
    }
    
     // if level already exist
    public static boolean levelNameExists(String level, int departmentID) throws NamingException, SQLException {
        boolean exists = false;

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = pool.getConnection();

        String query = "SELECT levelName, departmentID FROM levels WHERE levelName = ? AND departmentID = ? LIMIT 1";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, level);
        ps.setInt(2, departmentID);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            exists = true;

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return exists;
    }
    
    //SELECT ALL LEVEL'S FIELD
    public static LinkedHashMap<String, Levels> selectAllLevelByID(int schoolID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        

        String query = "SELECT * FROM levels WHERE schoolID = ? ORDER BY levelName";

        ps = connection.prepareStatement(query);
        

        ps.setInt(1, schoolID);
        rs = ps.executeQuery();

        LinkedHashMap<String, Levels> level = new LinkedHashMap<>();
        while (rs.next()) {
            Levels l = new Levels();
            l.setLevelID(rs.getInt("levelID"));
            l.setDepartmentID(rs.getInt("departmentID"));
            
            
            l.setSchoolID(rs.getInt("schoolID"));
            l.setLevelName(rs.getString("levelName"));
            l.setLevelCode(rs.getString("levelCode"));
            
            l.setCreatedAT(rs.getTimestamp("createdAT").toLocalDateTime());
            l.setStatus(rs.getString("status"));
            

            level.put("" + l.getLevelName(), l);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return level;

    }
    
    public static LinkedHashMap<String, Levels> selectLevelBySchoolAndDepartment(int schoolID, int departmentID)
        throws NamingException, SQLException {

    LinkedHashMap<String, Levels> levels = new LinkedHashMap<>();

    ConnectionPool pool = ConnectionPool.getInstance();

    String query = "SELECT * FROM levels "
                 + "WHERE schoolID = ? AND departmentID = ? AND status = 'ACTIVE' "
                 + "ORDER BY levelName";

    try (
        Connection connection = pool.getConnection();
        PreparedStatement ps = connection.prepareStatement(query)
    ) {
        ps.setInt(1, schoolID);
        ps.setInt(2, departmentID);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Levels l = new Levels();

                l.setLevelID(rs.getInt("levelID"));
                l.setSchoolID(rs.getInt("schoolID"));
                l.setDepartmentID(rs.getInt("departmentID"));
                l.setLevelName(rs.getString("levelName"));
                l.setLevelCode(rs.getString("levelCode"));

                if (rs.getTimestamp("createdAT") != null) {
                    l.setCreatedAT(rs.getTimestamp("createdAT").toLocalDateTime());
                }

                l.setStatus(rs.getString("status"));

               
                levels.put(String.valueOf(l.getLevelID()), l);
            }
        }
    }

    return levels;
}
    
}
