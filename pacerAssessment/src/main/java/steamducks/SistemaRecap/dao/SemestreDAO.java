package steamducks.SistemaRecap.dao;

import steamducks.SistemaRecap.models.Criterio;
import steamducks.SistemaRecap.models.Semestre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SemestreDAO extends ConexaoDAO {

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

    public List<String> buscarNomeSemestres() {
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

    public int buscarIdSemestrePorNome(String nomeSemestre) {
        int idSemestre = -1; // Inicializa com valor inválido para verificar se o semestre foi encontrado
        String selectSQL = "SELECT id FROM semestre WHERE nome = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setString(1, nomeSemestre); // Define o nome do semestre na consulta
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                idSemestre = rs.getInt("id"); // Obtém o ID do semestre
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratar o erro conforme necessário (exibir mensagem, log etc.)
        }
        return idSemestre;
    }

    public int contarCriteriosPorIdSemestre(int idSemestre) {
        int count = 0;
        Connection con = null;

        try {
            con = getConnection();
            String query = "SELECT COUNT(*) AS total FROM criterio c " +
                    "JOIN semestre_criterio sc ON c.id_criterio = sc.id_criterio " +
                    "WHERE sc.id_semestre = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idSemestre);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao contar critérios para o semestre " + idSemestre + "! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return count;
    }

    // New method to fetch criteria by semester ID
    public List<Integer> buscarCriteriosPorIdSemestre(int idSemestre) {
        List<Integer> criterios = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String query = "SELECT id_criterio FROM semestre_criterio WHERE id_semestre = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idSemestre);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                criterios.add(rs.getInt("id_criterio"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar critérios para o semestre " + idSemestre + "! " + e.getMessage(), e);
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
}