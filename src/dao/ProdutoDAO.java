package dao;

import model.Produto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    
    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, descricao, preco_unitario, categoria, " +
                     "unidade_medida, quantidade_estoque) VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPrecoUnitario());
            stmt.setString(4, produto.getCategoria());
            stmt.setString(5, produto.getUnidadeMedida());
            stmt.setDouble(6, produto.getQuantidadeEstoque());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    produto.setId(rs.getInt(1));
                }
                rs.close();
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                Conexao.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    public boolean atualizar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco_unitario = ?, " +
                     "categoria = ?, unidade_medida = ?, quantidade_estoque = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPrecoUnitario());
            stmt.setString(4, produto.getCategoria());
            stmt.setString(5, produto.getUnidadeMedida());
            stmt.setDouble(6, produto.getQuantidadeEstoque());
            stmt.setInt(7, produto.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                Conexao.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    public boolean excluir(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                Conexao.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto produto = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setUnidadeMedida(rs.getString("unidade_medida"));
                produto.setQuantidadeEstoque(rs.getDouble("quantidade_estoque"));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    produto.setDataCadastro(timestamp.toLocalDateTime());
                }
            }
            
            return produto;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto por ID: " + e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexao.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    public List<Produto> listarTodos() {
        String sql = "SELECT * FROM produtos ORDER BY nome";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setUnidadeMedida(rs.getString("unidade_medida"));
                produto.setQuantidadeEstoque(rs.getDouble("quantidade_estoque"));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    produto.setDataCadastro(timestamp.toLocalDateTime());
                }
                
                produtos.add(produto);
            }
            
            return produtos;
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexao.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    public List<Produto> listarPorCategoria(String categoria) {
        String sql = "SELECT * FROM produtos WHERE categoria = ? ORDER BY nome";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, categoria);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setUnidadeMedida(rs.getString("unidade_medida"));
                produto.setQuantidadeEstoque(rs.getDouble("quantidade_estoque"));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    produto.setDataCadastro(timestamp.toLocalDateTime());
                }
                
                produtos.add(produto);
            }
            
            return produtos;
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos por categoria: " + e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexao.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    public boolean atualizarEstoque(int produtoId, double quantidade) {
        String sql = "UPDATE produtos SET quantidade_estoque = quantidade_estoque + ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setDouble(1, quantidade);
            stmt.setInt(2, produtoId);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar estoque: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                Conexao.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
}