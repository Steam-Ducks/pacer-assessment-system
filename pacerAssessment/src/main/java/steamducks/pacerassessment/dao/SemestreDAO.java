package steamducks.pacerassessment.dao;

import steamducks.pacerassessment.models.Criterio;
import steamducks.pacerassessment.models.Semestre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SemestreDAO {
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_recap", "admin", "1234");
    }
    // PUT
    public int criarSemestre(String nome) {
        Connection con = null;
        int idSemestre = 0;

        try {
            con = getConnection();
            con.setAutoCommit(false);

            String insertSemestreSql = "INSERT INTO semestre (nome) VALUES (?)";
            PreparedStatement pstSemestre = con.prepareStatement(insertSemestreSql, Statement.RETURN_GENERATED_KEYS);
            pstSemestre.setString(1, nome);
            pstSemestre.executeUpdate();

            ResultSet rs = pstSemestre.getGeneratedKeys();
            if (rs.next()) {
                idSemestre = rs.getInt(1);
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao criar semestre! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return idSemestre;
    }

    // GET ALL
    public List<Semestre> getSemestres() {
        List<Semestre> semestres = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String selectSql = "SELECT * FROM semestre";
            PreparedStatement pst = con.prepareStatement(selectSql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_semestre");
                String nome = rs.getString("nome");

                Semestre semestre = new Semestre(id, nome);
                semestres.add(semestre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar semestres: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return semestres;
    }

    // UPDATE
    public void atualizarNomeSemestre(int idSemestre, String novoNome) {
        Connection con = null;

        try {
            con = getConnection();
            String updateSql = "UPDATE semestre SET nome = ? WHERE id_semestre = ?";
            PreparedStatement pst = con.prepareStatement(updateSql);
            pst.setString(1, novoNome);
            pst.setInt(2, idSemestre);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum semestre encontrado com o ID fornecido: " + idSemestre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar o nome do semestre: " + e.getMessage(), e);
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
    public boolean excluirSemestre(int id) {
        Connection con = null;
        String sql = "DELETE FROM semestre WHERE id_semestre = ?";

        try {
            con = getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

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

    public List<Criterio> buscarCriterios() {
        List<Criterio> criterios = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String selectSql = "SELECT * FROM criterio";
            PreparedStatement pst = con.prepareStatement(selectSql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Criterio c = new Criterio(rs.getInt("id_criterio"), rs.getString("nome"), rs.getString("descricao"));
                criterios.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar critérios! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return criterios;
    }

    public void vincularCriterios(int idSemestre, List<Criterio> criterios) {
        Connection con = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);

            String insertCriteriosSql = "INSERT INTO semestre_criterio (id_semestre, id_criterio) VALUES (?, ?)";
            PreparedStatement pstCriterios = con.prepareStatement(insertCriteriosSql);

            for (Criterio criterio : criterios) {
                pstCriterios.setInt(1, idSemestre);
                pstCriterios.setInt(2, criterio.getId());
                pstCriterios.executeUpdate();
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao vincular critérios! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }
    }
}
