package steamducks.SistemaRecap.dao;

import javafx.collections.ObservableList;
import steamducks.SistemaRecap.models.Equipe;
import steamducks.SistemaRecap.models.Semestre;
import steamducks.SistemaRecap.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAO extends ConexaoDAO {

    // CREATE
    public int criarEquipe(String nomeEquipe, String github, int idSemestre) {
        int idEquipe = -1;
        Connection con = null;

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

    // READ
    public List<Equipe> getEquipes() {
        List<Equipe> equipes = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String selectSql = "SELECT * FROM equipe";
            PreparedStatement pst = con.prepareStatement(selectSql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_equipe");
                String nome = rs.getString("nome");
                String github = rs.getString("github");
                int idSemestre = rs.getInt("id_semestre");

                Equipe equipe = new Equipe(id, nome, github, idSemestre);
                equipes.add(equipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar equipes: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return equipes;
    }

    // UPDATE
    public void atualizarEquipe(int idEquipe, String nome, String github, int idSemestre) {
        Connection con = null;

        try {
            con = getConnection();
            String updateSql = "UPDATE equipe SET nome = ?, github = ?, id_semestre = ? WHERE id_equipe = ?";
            PreparedStatement pst = con.prepareStatement(updateSql);
            pst.setString(1, nome);
            pst.setString(2, github);
            pst.setInt(3, idSemestre);
            pst.setInt(4, idEquipe);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhuma equipe encontrada com o ID fornecido: " + idEquipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar equipe: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }
    }

    // DELETE
    public boolean excluirEquipe(int idEquipe) {
        Connection con = null;
        String deleteEquipeSql = "DELETE FROM equipe WHERE id_equipe = ?";

        try {
            con = getConnection();
            con.setAutoCommit(false);

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean usuariosExcluidos = usuarioDAO.excluirUsuariosPorEquipe(idEquipe);

            if (!usuariosExcluidos) {
                con.rollback();
                return false;
            }

            PreparedStatement pstEquipe = con.prepareStatement(deleteEquipeSql);
            pstEquipe.setInt(1, idEquipe);
            int linhasAfetadas = pstEquipe.executeUpdate();

            con.commit();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }
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
                grupo.setIdSemestre(rs.getInt("id_semestre"));
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

    public Equipe buscarEquipePorId(int idEquipe) {
        Equipe equipe = null;
        Connection con = null;

        try {
            con = getConnection();
            String select_sql = "SELECT * FROM equipe WHERE id_equipe = ?";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setInt(1, idEquipe);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                equipe = new Equipe();
                equipe.setIdEquipe(rs.getInt("id_equipe"));
                equipe.setNome(rs.getString("nome"));
                equipe.setGithub(rs.getString("github"));
                equipe.setIdSemestre(rs.getInt("id_semestre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar equipe com ID " + idEquipe + ": " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return equipe;
    }

 public List<Equipe> getEquipesPorIdSemestre(int idSemestre) {
        List<Equipe> equipes = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String select_sql = "SELECT * FROM equipe WHERE id_semestre = ?";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setInt(1, idSemestre);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id_equipe = rs.getInt("id_equipe");
                String nome = rs.getString("nome");
                String github = rs.getString("github");
                int id_semestre = rs.getInt("id_semestre");



                Equipe equipe = new Equipe(id_equipe, nome, github, id_semestre);
                equipes.add(equipe);
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


    public boolean adicionarUsuarioAEquipe(int idEquipe, List<Usuario> usuarios) {
        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println("Lista de usuários vazia ou nula");
            return false; // Retorna false se a lista estiver vazia ou nula
        }

        String sql = "UPDATE usuario SET id_equipe = ? WHERE email = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Adiciona os parâmetros em batch para cada usuário
            for (Usuario usuario : usuarios) {
                System.out.println(usuario.getNome());
                System.out.println("Usuário: " + usuario.getEmail());
                System.out.println(idEquipe);
                stmt.setInt(1, idEquipe); // Define o ID da equipe
                stmt.setString(2, usuario.getEmail()); // Define o ID do usuário
                stmt.addBatch(); // Adiciona o comando ao batch
            }

            // Executa todos os comandos em batch
            int[] linhasAfetadas = stmt.executeBatch();

            // Verifica se todos os updates foram bem-sucedidos
            for (int resultado : linhasAfetadas) {
                if (resultado == 0) {
                    return false; // Retorna false se algum update não afetou nenhuma linha
                }
            }

            return true; // Retorna true se todos os updates foram bem-sucedidos

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false em caso de falha
        }
    }

    public List<String> buscarEmailsAlunosPorIdEquipe(int idEquipe) {
        List<String> emails = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String select_sql = "SELECT email FROM usuario WHERE id_equipe = ?";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setInt(1, idEquipe);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                emails.add(rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar emails dos alunos da equipe com ID " + idEquipe + ": " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return emails;
    }
}