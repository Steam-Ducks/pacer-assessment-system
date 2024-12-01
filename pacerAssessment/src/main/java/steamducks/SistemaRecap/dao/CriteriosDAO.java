package steamducks.SistemaRecap.dao;

import steamducks.SistemaRecap.models.Criterio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CriteriosDAO extends ConexaoDAO {

    public int adicionarCriterio(Criterio criterio) {
        String insertCriterioSql = "INSERT INTO criterio (nome, descricao) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pstCriterio = con.prepareStatement(insertCriterioSql, Statement.RETURN_GENERATED_KEYS)) {

            pstCriterio.setString(1, criterio.getNome());
            pstCriterio.setString(2, criterio.getDescricao());

            int linhasAfetadas = pstCriterio.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhuma linha foi afetada ao adicionar o critério.");
            }

            try (ResultSet generatedKeys = pstCriterio.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new RuntimeException("Erro ao obter o ID do critério inserido.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar critério! " + e.getMessage(), e);
        }
    }

    public List<Criterio> buscarCriterios() {
        List<Criterio> criterios = new ArrayList<>();
        String selectSQL = "SELECT * FROM criterio";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Criterio criterio = new Criterio();
                criterio.setId(resultSet.getInt("id_criterio"));
                criterio.setDescricao(resultSet.getString("descricao"));
                criterio.setNome(resultSet.getString("nome"));
                criterios.add(criterio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar critérios! " + e.getMessage(), e);
        }

        return criterios;
    }
    public List<Criterio> getCriteriosPorSemestre(int idSemestre) {
        String sql = "SELECT * FROM criterio WHERE id_semestre = ?";
        List<Criterio> criterios = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idSemestre);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Criterio criterio = new Criterio();
                criterio.setId(rs.getInt("id"));
                criterio.setNome(rs.getString("nome"));
                criterio.setId(rs.getInt("id_semestre"));
                criterios.add(criterio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar critérios por semestre: " + e.getMessage(), e);
        }
        return criterios;
    }

    public void removerCriterio(int idCriterio) {
        String deleteCriterioSql = "DELETE FROM criterio WHERE id_criterio = ?";

        try (Connection con = getConnection();
             PreparedStatement pstCriterio = con.prepareStatement(deleteCriterioSql)) {

            pstCriterio.setInt(1, idCriterio);

            int linhasAfetadas = pstCriterio.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhum critério encontrado com o ID: " + idCriterio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover critério " + e.getMessage(), e);
        }
    }

    public void editarCriterio(Criterio criterio) {
        String updateCriterioSql = "UPDATE criterio SET nome = ?, descricao = ? WHERE id_criterio = ?";

        try (Connection con = getConnection();
             PreparedStatement pstCriterio = con.prepareStatement(updateCriterioSql)) {

            pstCriterio.setString(1, criterio.getNome());
            pstCriterio.setString(2, criterio.getDescricao());
            pstCriterio.setInt(3, criterio.getId());

            int linhasAfetadas = pstCriterio.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhum critério editado!");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar critério! " + e.getMessage(), e);
        }
    }

    public boolean existeCriterioComNome(String nome) {
        String sql = "SELECT COUNT(*) FROM criterio WHERE nome = ?";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar existência do critério! " + e.getMessage(), e);
        }
        return false;
    }
}