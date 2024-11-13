package steamducks.SistemaRecap.dao;

import steamducks.SistemaRecap.models.Avaliacao;
import steamducks.SistemaRecap.models.Criterio;
import steamducks.SistemaRecap.models.Sprint;
import steamducks.SistemaRecap.models.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO extends ConexaoDAO {

    public List<Usuario> getAlunosEquipe(int idEquipe) {
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




    public List<Criterio> getNotasPorCriterio(String emailAvaliador, String emailAvaliado, int idSprint) {
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


    public int getTotalDePontosDaEquipeNaSprint(int idSprint, int idEquipe) {
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


    public int getPontosTotaisExcluindoAluno(String emailAvaliador, String emailAvaliado, int idSprint) {
        int somaPontos = 0;
        String sql = """
        SELECT SUM(nota) AS soma_pontos
        FROM avaliacao
        WHERE email_avaliador = ? AND id_sprint = ? AND email_avaliado != ?
    """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emailAvaliador);
            stmt.setInt(2, idSprint);
            stmt.setString(3, emailAvaliado);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    somaPontos = rs.getInt("soma_pontos");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return somaPontos;
    }

    public Sprint getSprintAtivaPorDataEEquipe(LocalDate data, int idEquipe) {
        Sprint sprintAtiva = null;
        String sql = """
        SELECT s.id_sprint, s.nome, s.data_inicio, s.data_fim, s.id_semestre
        FROM sprint s
        INNER JOIN equipe e ON s.id_semestre = e.id_semestre
        WHERE e.id_equipe = ? AND ? BETWEEN s.data_inicio AND DATE_ADD(s.data_fim, INTERVAL 1 WEEK)
           OR ? BETWEEN s.data_inicio AND DATE_ADD(s.data_inicio, INTERVAL 8 DAY)
    """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEquipe);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setDate(3, Date.valueOf(data));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    sprintAtiva = new Sprint();
                    sprintAtiva.setIdSprint(rs.getInt("id_sprint"));
                    sprintAtiva.setNome(rs.getString("nome"));
                    sprintAtiva.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                    sprintAtiva.setDataFim(rs.getDate("data_fim").toLocalDate());
                    sprintAtiva.setIdSemestre(rs.getInt("id_semestre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sprintAtiva;
    }
    public void cadastrarOuAtualizarAvaliacao(Avaliacao avaliacao) {
        String checkSql = """
        SELECT COUNT(*) AS count
        FROM avaliacao
        WHERE email_avaliador = ? AND email_avaliado = ? AND id_sprint = ? AND id_criterio = ?
    """;

        String insertSql = """
        INSERT INTO avaliacao (nota, email_avaliador, email_avaliado, id_sprint, id_criterio)
        VALUES (?, ?, ?, ?, ?)
    """;

        String updateSql = """
        UPDATE avaliacao
        SET nota = ?
        WHERE email_avaliador = ? AND email_avaliado = ? AND id_sprint = ? AND id_criterio = ?
    """;

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, avaliacao.getEmailAvaliador());
            checkStmt.setString(2, avaliacao.getEmailAvaliado());
            checkStmt.setInt(3, avaliacao.getIdSprint());
            checkStmt.setInt(4, avaliacao.getIdCriterio());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt("count") > 0) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, avaliacao.getNota());
                        updateStmt.setString(2, avaliacao.getEmailAvaliador());
                        updateStmt.setString(3, avaliacao.getEmailAvaliado());
                        updateStmt.setInt(4, avaliacao.getIdSprint());
                        updateStmt.setInt(5, avaliacao.getIdCriterio());
                        updateStmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, avaliacao.getNota());
                        insertStmt.setString(2, avaliacao.getEmailAvaliador());
                        insertStmt.setString(3, avaliacao.getEmailAvaliado());
                        insertStmt.setInt(4, avaliacao.getIdSprint());
                        insertStmt.setInt(5, avaliacao.getIdCriterio());
                        insertStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving evaluation", e);
        }
    }



    public int getMediaAlunoPorCriterio(int idSprint, String emailAluno, int idCriterio) {
        String sql = """
            SELECT AVG(nota) AS media
            FROM avaliacao
            WHERE id_sprint = ? AND email_avaliado = ? AND id_criterio = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSprint);
            stmt.setString(2, emailAluno);
            stmt.setInt(3, idCriterio);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double media = rs.getInt("media");
                    return (int) ((media >= 2.5) ? Math.ceil(media) : Math.floor(media));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao calcular a média do aluno por critério", e);
        }

        return 0;
    }


}
