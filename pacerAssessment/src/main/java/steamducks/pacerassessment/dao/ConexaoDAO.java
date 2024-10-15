package steamducks.pacerassessment.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/recapSystem";
    private final String jdbcUsername = "sistema";
    private final String jdbcPassword = "123";
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
}