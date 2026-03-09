/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import business.Levels;
import business.School;
import business.Students;
import business.Teachers;
import business.User;
import java.security.Timestamp;
//import com.mysql.cj.xdevapi.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import javax.naming.NamingException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author kembe
 */
public class UserDB {

    //retrieve all users to send to the allusers.jsp
    public static LinkedHashMap<String, User> selectUser() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT username, password, role, email, phone, created_at, schoolID, isActive, id FROM users";

        ps = connection.prepareStatement(query);

        rs = ps.executeQuery();

        LinkedHashMap<String, User> user = new LinkedHashMap<>();
        while (rs.next()) {

            String userName = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            int schoolID = rs.getInt("schoolID");
            String isActive = rs.getString("isActive");
            int userID = rs.getInt("id");

            User users = new User(userName, password, role, email, phone, createdAt, schoolID, isActive, userID);
            user.put(users.getUserName(), users);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return user;

    }

    
    public static String getPasswordForEmail(String email) throws NamingException, SQLException {

        if (email == null || email.isEmpty()) {

            return null;
        }

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String password = null;

        connection = pool.getConnection();

        String query = "SELECT username, password, role, email, phone, created_at FROM users WHERE email = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, email);

        rs = ps.executeQuery();

        if (rs.next()) {

            password = rs.getString("password");

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return password;

    }

    public static User selectUserByEmail(String email) throws NamingException, SQLException {

        if (email == null || email.isEmpty()) {

            return null;
        }

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;

        connection = pool.getConnection();

        String query = "SELECT id, username, password, role, email, phone, created_at, schoolID, isActive FROM users WHERE email = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, email);

        rs = ps.executeQuery();

        if (rs.next()) {

            String userName = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");
            String emails = rs.getString("email");
            String phone = rs.getString("phone");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            int schoolID = rs.getInt("schoolID");
            String isActive = rs.getString("isActive");
            int userID = rs.getInt("id");
            user = new User(userName, password, role, emails, phone, schoolID, isActive, userID);

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return user;

    }

    public static boolean usernameExists(String username) throws NamingException, SQLException {

        boolean exists = false;

        ConnectionPool pool = ConnectionPool.getInstance();

        Connection connection = pool.getConnection();

        String query = "SELECT username FROM users WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            exists = true;

        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return exists;

    }

    // if email exist for user admin
    public static boolean adminEmailExists(String email) throws NamingException, SQLException {
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

    public static int insertUser(Connection conn, User user)
            throws SQLException, NamingException {

        PreparedStatement ps = null;

        String query
                = "INSERT INTO users (username, password, role, email, phone, schoolID, isActive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, user.getUserName());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getRole());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPhoneNumber());
        ps.setInt(6, user.getSchoolID());
        ps.setString(7, user.getIsActive());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        int userID = 0;

        if (rs.next()) {
            userID = rs.getInt(1);
        }

        rs.close();
        ps.close();

        return userID;
    }

    public static int countUsersBySchool(int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT COUNT(*) AS total FROM users WHERE schoolID = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        ResultSet rs = ps.executeQuery();

        int total = 0;
        if (rs.next()) {
            total = rs.getInt("total");
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return total;
    }

    public static int countUsersByRoleAndSchool(String role, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query = "SELECT COUNT(*) AS total FROM users WHERE role = ? AND schoolID = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, role);
        ps.setInt(2, schoolID);

        ResultSet rs = ps.executeQuery();

        int total = 0;
        if (rs.next()) {
            total = rs.getInt("total");
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return total;
    }

    public static LinkedHashMap<String, User> selectAllUsersByID(int schoolID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT id, username, password, role, email, phone, created_at, schoolID, isActive FROM users WHERE schoolID = ? AND role = 'ADMIN' ORDER BY created_at";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);

        rs = ps.executeQuery();

        LinkedHashMap<String, User> user = new LinkedHashMap<>();
        while (rs.next()) {
            User users = new User();
            users.setUserName(rs.getString("username"));

            users.setPassword(rs.getString("password"));
            users.setRole(rs.getString("role"));
            users.setEmail(rs.getString("email"));
            users.setPhoneNumber(rs.getString("phone"));
            users.setSchoolID(rs.getInt("schoolID"));
            users.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            users.setIsActive(rs.getString("isActive"));
            users.setUserID(rs.getInt("id"));

            user.put("" + users.getUserName(), users);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return user;

    }

    //INSERT THE ADMIN 
    public static int insertAdmin(User user)
            throws SQLException, NamingException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO users (username, password, role, email, phone, schoolID, isActive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, user.getUserName());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getRole());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPhoneNumber());
        ps.setInt(6, user.getSchoolID());
        ps.setString(7, user.getIsActive());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        int userID = 0;

        if (rs.next()) {
            userID = rs.getInt(1);
        }
        rs.close();
        ps.close();

        return userID;
    }

    
    //UDPDATE USER TO DEACTIVE HIM
    public static void deactiveUser(int userID, int schoolID, int loggedAdminID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE users SET isActive='INACTIVE' WHERE id =? AND schoolID =? AND role= 'ADMIN' AND id <> ?";
        ps = connection.prepareStatement(q1);
        ps.setInt(1, userID);
        ps.setInt(2, schoolID);
        ps.setInt(3, loggedAdminID);
        ps.executeUpdate();
        ps.close();

        pool.freeConnection(connection);
    }

    public static void activeUser(int userID, int schoolID) throws NamingException, SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String q1 = "UPDATE users SET isActive='ACTIVE' WHERE id=? AND schoolID=? AND role= 'ADMIN'";
        ps = connection.prepareStatement(q1);
        ps.setInt(1, userID);
        ps.setInt(2, schoolID);
        ps.executeUpdate();
        ps.close();

        pool.freeConnection(connection);
    }

    
    //SEARCH FOR USER 
    public static LinkedHashMap<String, User> searchUsersByUserName(int schoolID, String userName) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT id, username, password, role, email, phone, created_at, schoolID, isActive FROM users WHERE schoolID = ? AND role = 'ADMIN' AND (id=? OR username LIKE ?) ORDER BY created_at";

        ps = connection.prepareStatement(query);
        ps.setInt(1, schoolID);
        ps.setString(2, userName);
        ps.setString(3, "%" + userName);

        rs = ps.executeQuery();

        LinkedHashMap<String, User> user = new LinkedHashMap<>();
        while (rs.next()) {
            User users = new User();
            users.setUserName(rs.getString("username"));

            users.setPassword(rs.getString("password"));
            users.setRole(rs.getString("role"));
            users.setEmail(rs.getString("email"));
            users.setPhoneNumber(rs.getString("phone"));
            users.setSchoolID(rs.getInt("schoolID"));
            users.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            users.setIsActive(rs.getString("isActive"));
            users.setUserID(rs.getInt("id"));

            user.put("" + users.getUserName(), users);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return user;

    }

    

}
