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
        String query = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (PreparedStatement prepstat = c.prepareStatement(query)) {
            prepstat.setString(1, username);
            prepstat.setString(2, password);
            ResultSet rs = prepstat.executeQuery();
            return rs.next(); // Returns true if a matching record is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
