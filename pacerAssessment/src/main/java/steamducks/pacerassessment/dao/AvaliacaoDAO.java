package steamducks.pacerassessment.dao;

import steamducks.pacerassessment.models.Criterio;
import steamducks.pacerassessment.models.Sprint;
import steamducks.pacerassessment.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO extends ConexaoDAO {

    public List<Usuario> obterColegasEquipe(int idEquipe) {
        List<Usuario> colegas = new ArrayList<>();
        String sql = """
            SELECT email, nome, is_professor, id_equipe 
            FROM usuario 
            WHERE id_equipe = ? AND is_professor = FALSE
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEquipe);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setEmail(rs.getString("email"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setIsProfessor(rs.getBoolean("is_professor"));
                    usuario.setIdEquipe(rs.getInt("id_equipe"));
                    colegas.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return colegas;
    }

    public List<Sprint> obterSprintsPorEquipe(int idEquipe) {
        List<Sprint> sprints = new ArrayList<>();
        String sql = """
            SELECT s.id_sprint, s.nome, s.data_inicio, s.data_fim, s.id_semestre
            FROM sprint s
            INNER JOIN equipe e ON s.id_semestre = e.id_semestre
            WHERE e.id_equipe = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEquipe);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Sprint sprint = new Sprint();
                    sprint.setIdSprint(rs.getInt("id_sprint"));
                    sprint.setNome(rs.getString("nome"));
                    sprint.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                    sprint.setDataFim(rs.getDate("data_fim").toLocalDate());
                    sprint.setIdSemestre(rs.getInt("id_semestre"));
                    sprints.add(sprint);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sprints;
    }

    public List<Criterio> obterCriteriosPorEquipe(int idEquipe) {
        List<Criterio> criterios = new ArrayList<>();
        String sql = """
            SELECT c.id_criterio, c.nome, c.descricao
            FROM criterio c
            INNER JOIN semestre_criterio sc ON c.id_criterio = sc.id_criterio
            INNER JOIN equipe e ON sc.id_semestre = e.id_semestre
            WHERE e.id_equipe = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEquipe);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Criterio criterio = new Criterio();
                    criterio.setId(rs.getInt("id_criterio"));
                    criterio.setNome(rs.getString("nome"));
                    criterio.setDescricao(rs.getString("descricao"));
                    criterios.add(criterio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return criterios;
    }

    public List<Criterio> obterNotasPorCriterio(String emailAvaliador, String emailAvaliado, int idSprint) {
        List<Criterio> criteriosComNota = new ArrayList<>();
        String sql = """
        SELECT c.id_criterio, c.nome, c.descricao, COALESCE(a.nota, 0) AS nota
        FROM criterio c
        LEFT JOIN avaliacao a ON c.id_criterio = a.id_criterio
            AND a.email_avaliador = ? 
            AND a.email_avaliado = ?
            AND a.id_sprint = ?
    """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emailAvaliador);
            stmt.setString(2, emailAvaliado);
            stmt.setInt(3, idSprint);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Criterio criterio = new Criterio();
                    criterio.setId(rs.getInt("id_criterio"));
                    criterio.setNome(rs.getString("nome"));
                    criterio.setDescricao(rs.getString("descricao"));
                    criterio.setNota(rs.getInt("nota"));
                    criteriosComNota.add(criterio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return criteriosComNota;
    }


    public int obterTotalPontosPorSprintEEquipe(int idSprint, int idEquipe) {
        int totalPontos = 0;
        String sql = """
        SELECT SUM(pontos) AS total_pontos
        FROM pontuacao
        WHERE id_sprint = ? AND id_equipe = ?
    """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSprint);
            stmt.setInt(2, idEquipe);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalPontos = rs.getInt("total_pontos");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalPontos;
    }


}
