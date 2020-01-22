package Palindromo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Connector {
    private final String username;
    private final String password;
    private final String connectionURL = "jdbc:mysql://localhost:3306/palindrome?serverTimezone=UTC&useSSL=false";

    Connector(String username, String password) {
        this.username = username;
        this.password = password;
    }

    Connection connect() {
        try {
            return DriverManager.getConnection(connectionURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
