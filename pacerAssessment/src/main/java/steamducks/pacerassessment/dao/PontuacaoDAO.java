package steamducks.pacerassessment.dao;

import steamducks.pacerassessment.models.Sprint;
import steamducks.pacerassessment.models.Pontuacao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PontuacaoDAO {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_recap", "admin", "1234");
    }

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

    public static List<Pontuacao> buscarPoutuacao() {
        List<Pontuacao> pontuacoes = new ArrayList<>(); // Lista que vamos salvar os critérios que retornar do banco
        String selectSQL = "SELECT * FROM pontuacao"; // Select que vamos fazer no banco

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) // Aqui salva o resultado do select na variável resultSet
        {

            while (resultSet.next()) {
                Pontuacao pontuacao = new Pontuacao(); // Cria um novo objeto Sprint e usa os dados do resultado para preencher os valores
                pontuacao.setId(resultSet.getInt("id_pontuacao"));
                pontuacao.setPontos(Double.parseDouble(resultSet.getString("pontos")));
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
            pstPontuacao.setInt(2, pontuacao.getId()); // Definindo o ID da pontuação para atualizar

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
                return rs.getInt(1) > 0; // Retorna true se já existe uma pontuação
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar pontuação existente: " + e.getMessage(), e);
        }
        return false;
    }


}
