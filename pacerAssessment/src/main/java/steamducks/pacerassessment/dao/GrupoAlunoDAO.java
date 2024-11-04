package steamducks.pacerassessment.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import steamducks.pacerassessment.models.Equipe;
import steamducks.pacerassessment.models.Usuario;

public class GrupoAlunoDAO {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_recap", "admin", "1234");
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

    public List<String> buscarEquipesPorIdSemestre(int idSemestre) {
        List<String> equipes = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String select_sql = "SELECT nome FROM equipe WHERE id_semestre = ?";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setInt(1, idSemestre);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                equipes.add(rs.getString("nome"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar equipes para o semestre " + idSemestre + "! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return equipes;
    }

    public Equipe buscarEquipePorNomeEIdSemestre(String nomeEquipe, int idSemestre) {
        Equipe grupo = null;
        Connection con = null;

        try {
            con = getConnection();
            String select_sql = "SELECT * FROM equipe WHERE nome = ? AND id_semestre = ?";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setString(1, nomeEquipe);
            pst.setInt(2, idSemestre);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                grupo = new Equipe();
                grupo.setIdEquipe(rs.getInt("id_equipe")); // Defina o ID da equipe
                grupo.setSemestre(rs.getInt("id_semestre"));
                grupo.setNome(rs.getString("nome"));
                // Preencha outros campos de 'Equipe', se necessário
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar equipe " + nomeEquipe + " para o semestre " + idSemestre + "! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return grupo;
    }

    public int getNumeroDeMembros(String nomeEquipe, int idSemestre) {
        int numeroDeMembros = 0;
        Connection con = null;

        try {
            con = getConnection();
            String select_sql = "SELECT COUNT(*) AS total FROM usuario WHERE id_equipe = (SELECT id_equipe FROM equipe WHERE nome = ? AND id_semestre = ?)";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setString(1, nomeEquipe);
            pst.setInt(2, idSemestre);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                numeroDeMembros = rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter o número de membros da equipe " + nomeEquipe + " para o semestre " + idSemestre + "! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return numeroDeMembros;
    }

}
