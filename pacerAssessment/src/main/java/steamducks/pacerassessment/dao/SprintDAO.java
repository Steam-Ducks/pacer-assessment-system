package steamducks.pacerassessment.dao;

import steamducks.pacerassessment.models.Sprint;
import steamducks.pacerassessment.models.NotaSprint;

import steamducks.pacerassessment.models.Criterio;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SprintDAO {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_recap", "admin", "1234");
    }

    public Sprint criarSprint(Sprint sprint) {
        Connection con = null;
        int idSprint = 0;

        try {
            con = getConnection();
            con.setAutoCommit(false);

            String insertSprintSql = "INSERT INTO sprint (nome, data_inicio, data_fim) VALUES (?,?,?)";
            PreparedStatement pstSprint = con.prepareStatement(insertSprintSql, Statement.RETURN_GENERATED_KEYS);
            pstSprint.setString(1, sprint.getNome());
            pstSprint.setString(2, sprint.getDataInicio().toString());
            pstSprint.setString(3, sprint.getDataFim().toString());
            pstSprint.executeUpdate();

            ResultSet rs = pstSprint.getGeneratedKeys();
            if (rs.next()) {
                idSprint = rs.getInt(1);
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

        return sprint;
    }

    public List<Sprint> buscarSprint() {
        List<Sprint> sprints = new ArrayList<>(); //lista que vamos salvar os criterios que retornar do banco
        String selectSQL = "SELECT * FROM sprint"; //select que vamos fazer no banco

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) // aqui salva o resultado do select na variavel resultSet
        {

            while (resultSet.next()) {
                Sprint sprint = new Sprint(); //cria um novo objeto criterio e usa os dados do resultado pra preencher os valores
                sprint.setNome(resultSet.getString("nome"));
                sprint.setIdSprint(resultSet.getInt("id_sprint"));
                sprint.setDataInicio(resultSet.getObject("data_inicio", LocalDate.class));
                sprint.setDataFim(resultSet.getObject("nome", LocalDate.class));
                sprint.setIdSemestre(resultSet.getInt("id_semestre"));
                sprints.add(sprint);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar sprints! " + e.getMessage(), e);
        }

        return sprints;
    }

    public void editarSprint(Sprint sprint) {
        String updateSprintSql = "UPDATE sprint SET nome = ?, data_inicio = ?, data_fim = ? WHERE id_criterio = ?";

        try (Connection con = getConnection();
             PreparedStatement pstSprint = con.prepareStatement(updateSprintSql)) {

            pstSprint.setString(1, sprint.getNome());
            pstSprint.setString(2, sprint.getDataInicio().toString());
            pstSprint.setString(3, sprint.getDataFim().toString());

            int linhasAfetadas = pstSprint.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhuma sprint Editado!");
            }

        } catch (SQLException e) {
            throw new RuntimeException("nenhuma sprint encontrado");
        }

    }

    public void removerSprint(int id_sprint) { //passando o id do criterio que queremos bigodar
        String deleteSprintSql = "DELETE FROM sprint WHERE id_criterio = ?"; //query que deleta

        try (Connection con = getConnection();
             PreparedStatement pstSprint = con.prepareStatement(deleteSprintSql)) {

            pstSprint.setInt(1, id_sprint); // passa o id

            int linhasAfetadas = pstSprint.executeUpdate(); // ve se apagou algo ou nao

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhum sprint encontrado com o ID: " + id_sprint);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover sprint " + e.getMessage(), e);
        }
    }

}
