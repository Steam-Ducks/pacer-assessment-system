package steamducks.pacerassessment.dao;

import steamducks.pacerassessment.models.Equipe;
import steamducks.pacerassessment.models.Usuario;

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

                Equipe equipe = new Equipe(id, nome, github, String.valueOf(idSemestre));
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