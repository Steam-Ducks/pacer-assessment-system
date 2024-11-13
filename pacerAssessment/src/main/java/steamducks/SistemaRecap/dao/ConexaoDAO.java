package steamducks.SistemaRecap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/sistema_recap";
    private final String jdbcUsername = "admin";
    private final String jdbcPassword = "1234";
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
}