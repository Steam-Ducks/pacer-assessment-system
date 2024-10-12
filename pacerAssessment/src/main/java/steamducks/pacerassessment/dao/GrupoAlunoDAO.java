package steamducks.pacerassessment.dao;

import steamducks.pacerassessment.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoAlunoDAO {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/recapSystem", "sistema", "123");
    }

    public List<String> buscarSemestres() {
        List<String> semestres = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String select_sql = "SELECT nome FROM semestre";
            PreparedStatement pst = con.prepareStatement(select_sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                semestres.add(rs.getString("nome"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar semestres! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return semestres;
    }

    public int criarEquipe(String nomeEquipe, String github, int idSemestre) {
        Connection con = null;
        int idEquipe = 0;

        try {
            con = getConnection();
            String insert_sql = "INSERT INTO equipe (nome, github, id_semestre) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insert_sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nomeEquipe);
            pst.setString(2, github);
            pst.setInt(3, idSemestre);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                idEquipe = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar nova equipe! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return idEquipe;
    }

    public boolean adicionarAlunos(long idEquipe, List<Usuario> usuarios) {
        Connection con = null;

        try {
            con = getConnection();
            String insertUsuarioSql = "INSERT INTO usuario (nome, email, senha, is_professor, id_equipe) VALUES (?, ?, ?, ?, ?)";
            con.setAutoCommit(false);
            PreparedStatement pstUsuario = con.prepareStatement(insertUsuarioSql);

            for (Usuario usuario : usuarios) {
                pstUsuario.setString(1, usuario.getNome());
                pstUsuario.setString(2, usuario.getEmail());
                pstUsuario.setString(3, usuario.getSenha());
                pstUsuario.setBoolean(4, usuario.isProfessor());
                pstUsuario.setLong(5, idEquipe);
                pstUsuario.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao adicionar usuários! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }
    }

    public int obterIdSemestre(String nomeSemestre) {
        int idSemestre = -1;
        Connection con = null;

        try {
            con = getConnection();
            String select_sql = "SELECT id_semestre FROM semestre WHERE nome = ?";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setString(1, nomeSemestre);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idSemestre = rs.getInt("id_semestre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar ID do semestre! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return idSemestre;
    }
}
