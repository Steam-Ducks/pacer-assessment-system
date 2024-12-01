package steamducks.SistemaRecap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_recap";
    //admin
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    //professor
    private static final String PROF_USER = "prof";
    private static final String PROF_PASSWORD = "profrecap";

    //aluno
    private static final String ALUNO_USER = "aluno";
    private static final String ALUNO_PASSWORD = "alunorecap";

    private static String funcaoAtual = "admin"; // padrão

    public static void setFuncaoAtual(String role) {
        funcaoAtual = role;
    }

    protected Connection getConnection() throws SQLException {
        String username;
        String password;

        switch (funcaoAtual) {
            case "professor":
                username = PROF_USER;
                password = PROF_PASSWORD;
                break;
            case "aluno":
                username = ALUNO_USER;
                password = ALUNO_PASSWORD;
                break;
            default:
                username = ADMIN_USER;
                password = ADMIN_PASSWORD;
                break;
        }

        return DriverManager.getConnection(URL, username, password);
    }
}