package steamducks.pacerassessment.dao;

import steamducks.pacerassessment.models.Criterio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CriteriosDAO {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_recap", "admin", "1234");
    }

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
        List<Criterio> criterios = new ArrayList<>(); //lista que vamos salvar os criterios que retornar do banco
        String selectSQL = "SELECT * FROM criterio"; //select que vamos fazer no banco

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) // aqui salva o resultado do select na variavel resultSet
        {

            //try catch com esse connection = getConnection() usa pra ele fazer(tentar) a conexao com o banco

            //faz um loop por todos os resultados
            while (resultSet.next()) {
                Criterio criterio = new Criterio(); //cria um novo objeto criterio e usa os dados do resultado pra preencher os valores
                criterio.setId(resultSet.getInt("id_criterio"));
                criterio.setDescricao(resultSet.getString("descricao"));
                criterio.setNome(resultSet.getString("nome"));
                criterios.add(criterio);
            }

        } catch (SQLException e) { //se der algo errado ele passa pra ca e faz o que tiver dentro do bloco
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar critérios! " + e.getMessage(), e);
        }

        return criterios; // volta a lista de criterios
    }

    public void removerCriterio(int idCriterio) { //passando o id do criterio que queremos bigodar
        String deleteCriterioSql = "DELETE FROM criterio WHERE id_criterio = ?"; //query que deleta

        try (Connection con = getConnection();
             PreparedStatement pstCriterio = con.prepareStatement(deleteCriterioSql)) {

            pstCriterio.setInt(1, idCriterio); // passa o id

            int linhasAfetadas = pstCriterio.executeUpdate(); // ve se apagou algo ou nao

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
                throw new RuntimeException("Nenhum criterio Editado!");
            }

            } catch (SQLException e) {
            throw new RuntimeException("nenhum criterio encontrado");
        }

    }

    public List<String> buscarCriteriosPorIdSemestre(int idSemestre) {
        List<String> criterios = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String select_sql = "SELECT c.nome FROM criterio c " +
                    "JOIN semestre_criterio sc ON c.id_criterio = sc.id_criterio " +
                    "WHERE sc.id_semestre = ?";
            PreparedStatement pst = con.prepareStatement(select_sql);
            pst.setInt(1, idSemestre);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                criterios.add(rs.getString("nome"));
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

