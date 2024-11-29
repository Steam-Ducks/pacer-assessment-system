package steamducks.SistemaRecap.dao;

import steamducks.SistemaRecap.models.Pontuacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PontuacaoDAO extends ConexaoDAO {

    public Pontuacao cadastrarPontuacao(Pontuacao pontuacao) {
        Connection con = null;
        int idPontuacao;
        try {
            con = getConnection();
            con.setAutoCommit(false);

            String insertPontuacaoSql = "INSERT INTO pontuacao (pontos, id_sprint, id_equipe) VALUES (?,?,?)";
            PreparedStatement pstPontuacao = con.prepareStatement(insertPontuacaoSql, Statement.RETURN_GENERATED_KEYS);
            pstPontuacao.setDouble(1, pontuacao.getPontos());
            pstPontuacao.setInt(2, pontuacao.getIdSprint());
            pstPontuacao.setInt(3, pontuacao.getIdEquipe());
            pstPontuacao.executeUpdate();

            ResultSet rs = pstPontuacao.getGeneratedKeys();
            if (rs.next()) {
                idPontuacao = rs.getInt(1);
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao criar pontuacao! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return pontuacao;
    }

    public List<Pontuacao> buscarPontuacao() {
        List<Pontuacao> pontuacoes = new ArrayList<>();
        String selectSQL = "SELECT * FROM pontuacao";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Pontuacao pontuacao = new Pontuacao();
                pontuacao.setId(resultSet.getInt("id_pontuacao"));
                pontuacao.setPontos(resultSet.getDouble("pontos"));
                pontuacao.setIdSprint(resultSet.getInt("id_sprint"));
                pontuacao.setIdEquipe(resultSet.getInt("id_equipe"));
                pontuacoes.add(pontuacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar pontuações! " + e.getMessage(), e);
        }

        return pontuacoes;
    }

    public void editarPontuacao(Pontuacao pontuacao) {
        String updatePontuacaoSql = "UPDATE pontuacao SET pontos = ? WHERE id_pontuacao = ?";

        try (Connection con = getConnection();
             PreparedStatement pstPontuacao = con.prepareStatement(updatePontuacaoSql)) {

            pstPontuacao.setDouble(1, pontuacao.getPontos());
            pstPontuacao.setInt(2, pontuacao.getId());

            int linhasAfetadas = pstPontuacao.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhuma pontuação Editada!");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar a pontuação: " + e.getMessage(), e);
        }
    }

    public boolean pontuacaoJaExiste(int idEquipe, int idSprint) {
        String sql = "SELECT COUNT(*) FROM pontuacao WHERE id_equipe = ? AND id_sprint = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idEquipe);
            pst.setInt(2, idSprint);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar pontuação existente: " + e.getMessage(), e);
        }
        return false;
    }

    public boolean existePontuacaoParaSprint(int idSprint) {
        String sql = "SELECT COUNT(*) AS count FROM pontuacao WHERE id_sprint = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSprint);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}