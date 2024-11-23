package steamducks.SistemaRecap.dao;

import steamducks.SistemaRecap.utils.Utils;
import steamducks.SistemaRecap.models.Usuario;

import java.sql.*;

public class LoginDAO {

    private static final String ADMIN_EMAIL = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    public LoginDAO() {
        criarAdmin();
    }

    private void criarAdmin() {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM usuario WHERE email = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, ADMIN_EMAIL);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    String insertQuery = "INSERT INTO usuario (nome, email, senha, id_equipe, is_professor) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, "Admin");
                        insertStmt.setString(2, ADMIN_EMAIL);
                        insertStmt.setString(3, Utils.hashPassword(ADMIN_PASSWORD));
                        insertStmt.setNull(4, Types.INTEGER);
                        insertStmt.setBoolean(5, true);

                        insertStmt.executeUpdate();
                        System.out.println("Admin criado.");
                    }
                } else {
                    System.out.println("Admin já existe.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro criando user admin: " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_recap", "admin", "1234");
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = null;

        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, Utils.hashPassword(senha));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getInt("id_equipe"),
                            rs.getBoolean("is_professor")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }

        return usuario;
    }
}