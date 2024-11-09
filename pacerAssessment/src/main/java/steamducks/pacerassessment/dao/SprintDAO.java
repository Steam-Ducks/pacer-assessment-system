package steamducks.pacerassessment.dao;

import steamducks.pacerassessment.models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SprintDAO {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_recap", "admin", "1234");
    }

    public Sprint criarSprint(Sprint sprint) {
        Connection con = null;
        int idSprint = 0;

        try {
            con = getConnection();
            con.setAutoCommit(false);

            String insertSprintSql = "INSERT INTO sprint (nome, data_inicio, data_fim, id_semestre) VALUES (?,?,?,?)";
            PreparedStatement pstSprint = con.prepareStatement(insertSprintSql, Statement.RETURN_GENERATED_KEYS);
            pstSprint.setString(1, sprint.getNome());
            pstSprint.setString(2, sprint.getDataInicio().toString());
            pstSprint.setString(3, sprint.getDataFim().toString());
            pstSprint.setInt(4, sprint.getIdSemestre());
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
            throw new RuntimeException("Erro ao criar sprint! " + e.getMessage(), e);
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
    public static List<Sprint> buscarSprint() {
        List<Sprint> sprints = new ArrayList<>(); // Lista que vamos salvar os critérios que retornar do banco
        String selectSQL = "SELECT * FROM sprint"; // Select que vamos fazer no banco

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) // Aqui salva o resultado do select na variável resultSet
        {

            while (resultSet.next()) {
                Sprint sprint = new Sprint(); // Cria um novo objeto Sprint e usa os dados do resultado para preencher os valores
                sprint.setNome(resultSet.getString("nome"));
                sprint.setIdSprint(resultSet.getInt("id_sprint"));
                sprint.setDataInicio(resultSet.getObject("data_inicio", LocalDate.class));
                sprint.setDataFim(resultSet.getObject("data_fim", LocalDate.class)); // Corrigido para buscar a coluna data_fim
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
        String deleteSprintSql = "DELETE FROM sprint WHERE id_sprint = ?"; //query que deleta

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

    //Busca Sprint por ID do semestre, mosterando assim apenas as sprints cadastradas para aquele semestre

    public static List<Sprint> buscarSprintPorID(int idSemestre) {
        List<Sprint> sprints = new ArrayList<>();
        String selectSQL = "SELECT * FROM sprint WHERE id_semestre = ?"; // Filtro por id_semestre

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            // Definindo o parâmetro id_semestre na consulta
            preparedStatement.setInt(1, idSemestre);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Sprint sprint = new Sprint();
                    sprint.setNome(resultSet.getString("nome"));
                    sprint.setIdSprint(resultSet.getInt("id_sprint"));
                    sprint.setDataInicio(resultSet.getObject("data_inicio", LocalDate.class));
                    sprint.setDataFim(resultSet.getObject("data_fim", LocalDate.class));
                    sprint.setIdSemestre(resultSet.getInt("id_semestre"));
                    sprints.add(sprint);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar sprints! " + e.getMessage(), e);
        }

        return sprints;
    }

    public Sprint buscarSprintPorNomeEIdSemestre(String nomeSprint, int idSemestre) {
        Sprint sprint = null;
        Connection con = null;

        try {
            con = getConnection();
            String select_sql = "SELECT * FROM sprint WHERE nome = ? AND id_semestre = ?";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setString(1, nomeSprint);
            pst.setInt(2, idSemestre);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                sprint = new Sprint();
                sprint.setIdSprint(rs.getInt("id_sprint"));
                sprint.setNome(rs.getString("nome"));
                sprint.setIdSemestre(rs.getInt("id_semestre"));
                // Preencha outros campos da Sprint, se necessário
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar sprint " + nomeSprint + " para o semestre " + idSemestre + "! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return sprint;
    }

    public List<Semestre> buscarTodosSemestres() {
        List<Semestre> semestres = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();

            String select_sql = "SELECT * FROM semestre";  // Consulta para pegar todos os semestres
            PreparedStatement pst = con.prepareStatement(select_sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Semestre semestre = new Semestre();
                semestre.setId(rs.getInt("id_semestre"));
                semestre.setNome(rs.getString("nome"));
                // Preencha outros campos de Semestre, se necessário
                semestres.add(semestre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar todos os semestres! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar a conexão: " + e.getMessage(), e);
            }
        }

        return semestres;
    }

}