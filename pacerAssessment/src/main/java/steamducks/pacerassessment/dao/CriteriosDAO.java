package steamducks.pacerassessment.dao;

import steamducks.pacerassessment.Criterios;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CriteriosDAO {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_recap", "admin", "1234");
    }

    public int criarCriterio(String nomeCriterio, String descricaoCriterio) {
        Connection con = null;
        int idCriterio = 0;

        try {
            con = getConnection();
            String insert_sql = "INSERT INTO criterio (nome, descricao) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(insert_sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nomeCriterio);
            pst.setString(2, descricaoCriterio);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                idCriterio = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar novo critério! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return idCriterio;
    }

    public boolean adicionarCriterio(long idCriterio, List<Criterios> criterios) {
        Connection con = null;

        try {
            con = getConnection();
            String insertCriterioSql = "INSERT INTO criterios (nome, descricao) VALUES (?, ?, ?)";
            con.setAutoCommit(false);
            PreparedStatement pstCriterio = con.prepareStatement(insertCriterioSql);

            for (Criterios criterio : criterios) {
                pstCriterio.setString(1, criterio.getNome());
                pstCriterio.setString(2, criterio.getDescricao());
                pstCriterio.setLong(3, idCriterio);
                pstCriterio.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao adicionar usuários! " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }
    }

    public List<Criterios> buscarCriterios() {
        List<Criterios> criterios = new ArrayList<>(); //lista que vamos salvar os criterios que retornar do banco
        String selectSQL = "SELECT * FROM criterio"; //select que vamos fazer no banco

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) // aqui salva o resultado do select na variavel resultSet
        {

            //try catch com esse connection = getConnection() usa pra ele fazer(tentar) a conexao com o banco

            //faz um loop por todos os resultados
            while (resultSet.next()) {
                Criterios criterio = new Criterios(); //cria um novo objeto criterio e usa os dados do resultado pra preencher os valores
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

}
