package steamducks.SistemaRecap.dao;

import steamducks.SistemaRecap.models.Usuario;
import steamducks.SistemaRecap.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends ConexaoDAO {

    // CREATE
    public boolean adicionarAlunos(long idEquipe, List<Usuario> usuarios) {
        Connection con = null;

        try {
            con = getConnection();
            String insertUsuarioSql = "INSERT INTO usuario (nome, email, senha, is_professor, id_equipe) VALUES (?, ?, ?, ?, ?)";
            con.setAutoCommit(false);
            PreparedStatement pstUsuario = con.prepareStatement(insertUsuarioSql);

            for (Usuario usuario : usuarios) {
                pstUsuario.setString(1, usuario.getNome());
                pstUsuario.setString(2, usuario.getEmail());
                pstUsuario.setString(3, Utils.hashPassword(usuario.getSenha()));
                pstUsuario.setBoolean(4, usuario.isProfessor());
                pstUsuario.setLong(5, idEquipe);
                pstUsuario.executeUpdate();
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
            throw new RuntimeException("Erro ao adicionar usuários!");
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

    // READ
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String selectSql = "SELECT * FROM usuario";
            PreparedStatement pst = con.prepareStatement(selectSql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setIsProfessor(rs.getBoolean("is_professor"));
                usuario.setIdEquipe(rs.getInt("id_equipe"));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuários: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return usuarios;
    }

    // UPDATE
    public boolean atualizarUsuario(Usuario usuario) {
        Connection con = null;

        try {
            con = getConnection();
            String updateSql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, is_professor = ?, id_equipe = ? WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(updateSql);
            pst.setString(1, usuario.getNome());
            pst.setString(2, usuario.getEmail());
            pst.setString(3, Utils.hashPassword(usuario.getSenha()));
            pst.setBoolean(4, usuario.isProfessor());
            pst.setInt(5, usuario.getIdEquipe());
            pst.setString(6, usuario.getEmail());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }
    }

    // UPDATE PASSWORD
    public boolean atualizarSenha(String email, String novaSenha) {
        Connection con = null;

        try {
            con = getConnection();
            String updateSql = "UPDATE usuario SET senha = ? WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(updateSql);
            pst.setString(1, Utils.hashPassword(novaSenha));
            pst.setString(2, email);

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar senha: " + e.getMessage(), e);
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
    public boolean excluirUsuario(String email) {
        Connection con = null;
        String sql = "DELETE FROM usuario WHERE email = ?";

        try {
            con = getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
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

    public boolean excluirUsuariosPorEquipe(int idEquipe) {
        Connection con = null;
        String deleteUsuariosSql = "DELETE FROM usuario WHERE id_equipe = ?";

        try {
            con = getConnection();
            PreparedStatement pst = con.prepareStatement(deleteUsuariosSql);
            pst.setInt(1, idEquipe);
            int linhasAfetadas = pst.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
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

    public List<Usuario> getUsuariosPorEquipe(int idEquipe) {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;

        try {
            con = getConnection();
            String sql = "SELECT * FROM usuario WHERE id_equipe = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, idEquipe);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setIsProfessor(rs.getBoolean("is_professor"));
                usuario.setIdEquipe(rs.getInt("id_equipe"));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuários por equipe: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }

        return usuarios;
    }

    public boolean removerDaEquipe(Usuario usuario) {
        Connection con = null;

        try {
            con = getConnection();
            String updateSql = "UPDATE usuario SET id_equipe = NULL WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(updateSql);

            pst.setString(1, usuario.getEmail());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover da equipe: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fechar conexão: " + e.getMessage(), e);
            }
        }
    }

    public List<Usuario> buscarUsuariosSemEquipe() {
        List<Usuario> usuariosSemEquipe = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE id_equipe IS NULL AND is_professor = 0";
        ; // Ajuste conforme sua estrutura de banco

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(rs.getString("email"));
                usuario.setIdEquipe(rs.getInt("id_equipe"));
                usuario.setNome(rs.getString("nome"));
                // Preencha outros atributos, se necessário
                usuariosSemEquipe.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuariosSemEquipe;
}
}