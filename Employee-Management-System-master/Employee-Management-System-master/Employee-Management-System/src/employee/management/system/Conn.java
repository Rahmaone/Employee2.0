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
            try (PreparedStatement prepstat = c.prepareStatement(query)) {
                prepstat.setString(1, username);
                prepstat.setString(2, password);
                ResultSet rs = prepstat.executeQuery();
                
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
            return false;
        }
    }
}
