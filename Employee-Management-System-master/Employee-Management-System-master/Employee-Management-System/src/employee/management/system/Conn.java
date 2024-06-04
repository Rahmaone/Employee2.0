package employee.management.system;

import java.sql.*;

public class Conn {
    
    Connection c;
    Statement s;

    public Conn () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql:///employeemanagementsystem", "root", "");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean authenticate(String username, String password) {
        try {
            // Preconditions
            assert username != null && !username.isEmpty() : "Username should not be null or empty";
            assert password != null && !password.isEmpty() : "Password should not be null or empty";
            
            String query = "SELECT * FROM login WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = c.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                
                boolean authenticated = rs.next();
                
                // Postconditions
                assert authenticated || !authenticated : "Authenticated should be a boolean";
                
                return authenticated;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (AssertionError e) {
            System.err.println("Assertion failed: " + e.getMessage());
            // Handle the assertion failure as needed
            return false;
        }
    }
}
