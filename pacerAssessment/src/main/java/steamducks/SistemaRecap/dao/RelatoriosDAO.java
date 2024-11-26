package steamducks.SistemaRecap.dao;

import steamducks.SistemaRecap.models.Semestre;
import steamducks.SistemaRecap.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatoriosDAO {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_recap", "admin", "1234");
    }



    public List<Semestre> buscarTodosSemestres() {
        List<Semestre> semestres = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();

            String select_sql = "SELECT * FROM semestre";  // Consulta para pegar todos os semestres
            PreparedStatement pst = con.prepareStatement(select_sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Semestre semestre = new Semestre();
                semestre.setId(rs.getInt("id_semestre"));
                semestre.setNome(rs.getString("nome"));
                // Preencha outros campos de Semestre, se necessário
                semestres.add(semestre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar todos os semestres! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar a conexão: " + e.getMessage(), e);
            }
        }

        return semestres;
    }

    public List<steamducks.SistemaRecap.models.Equipe> buscarEquipesPorSemestre(int idSemestre) {
        List<steamducks.SistemaRecap.models.Equipe> equipes = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();

            String select_sql = "SELECT * FROM equipe WHERE id_semestre = ?";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setInt(1, idSemestre);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                steamducks.SistemaRecap.models.Equipe equipe = new steamducks.SistemaRecap.models.Equipe();
                equipe.setIdEquipe(rs.getInt("id_equipe"));
                equipe.setNome(rs.getString("nome"));
                equipe.setGithub(rs.getString("github"));
                equipe.setIdSemestre(rs.getInt("id_semestre"));
                equipes.add(equipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar equipes por semestre: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar a conexão: " + e.getMessage(), e);
            }
        }

        return equipes;
    }

    public List<String> buscarSprintsPorSemestre(int idSemestre) {
        List<String> sprints = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();

            String select_sql = "SELECT nome FROM sprint WHERE id_semestre = ?";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setInt(1, idSemestre);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                sprints.add(rs.getString("nome"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar sprints por semestre: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar a conexão: " + e.getMessage(), e);
            }
        }

        return sprints;
    }

    public List<Usuario> buscarAlunosPorSemestre(int idSemestre) {
        List<Usuario> alunos = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();

            String select_sql = """
                SELECT u.nome, u.email, u.senha, u.id_equipe, u.is_professor
                FROM usuario u
                INNER JOIN equipe e ON u.id_equipe = e.id_equipe
                WHERE e.id_semestre = ? AND u.is_professor = false
                """;
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setInt(1, idSemestre);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Usuario aluno = new Usuario();
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setSenha(rs.getString("senha"));
                aluno.setIdEquipe(rs.getInt("id_equipe"));
                aluno.setIsProfessor(rs.getBoolean("is_professor"));
                alunos.add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar alunos por semestre: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar a conexão: " + e.getMessage(), e);
            }
        }

        return alunos;
    }

    public List<Usuario> buscarAlunosPorSemestreEEquipe(int idSemestre, int idEquipe) {
        List<Usuario> alunos = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();

            String select_sql = """
                SELECT u.nome, u.email, u.senha, u.id_equipe, u.is_professor
                FROM usuario u
                INNER JOIN equipe e ON u.id_equipe = e.id_equipe
                WHERE e.id_semestre = ? AND u.id_equipe = ? AND u.is_professor = false
                """;
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setInt(1, idSemestre);
            pst.setInt(2, idEquipe);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Usuario aluno = new Usuario();
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setSenha(rs.getString("senha"));
                aluno.setIdEquipe(rs.getInt("id_equipe"));
                aluno.setIsProfessor(rs.getBoolean("is_professor"));
                alunos.add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar alunos por semestre e equipe: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar a conexão: " + e.getMessage(), e);
            }
        }

        return alunos;
    }




}


