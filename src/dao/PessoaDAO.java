package dao;

import model.Pessoa;
import model.Pessoa.TipoPessoa;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    
    public boolean inserir(Pessoa pessoa) {
        String sql = "INSERT INTO pessoas (nome, cpf, telefone, email, endereco, tipo) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setString(4, pessoa.getEmail());
            stmt.setString(5, pessoa.getEndereco());
            stmt.setString(6, pessoa.getTipo().toString());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    pessoa.setId(rs.getInt(1));
                }
                rs.close();
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir pessoa: " + e.getMessage());
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
    
    public boolean atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoas SET nome = ?, cpf = ?, telefone = ?, " +
                     "email = ?, endereco = ?, tipo = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setString(4, pessoa.getEmail());
            stmt.setString(5, pessoa.getEndereco());
            stmt.setString(6, pessoa.getTipo().toString());
            stmt.setInt(7, pessoa.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pessoa: " + e.getMessage());
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
        String sql = "DELETE FROM pessoas WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir pessoa: " + e.getMessage());
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
    
    public Pessoa buscarPorId(int id) {
        String sql = "SELECT * FROM pessoas WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pessoa pessoa = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setEndereco(rs.getString("endereco"));
                pessoa.setTipo(TipoPessoa.valueOf(rs.getString("tipo")));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    pessoa.setDataCadastro(timestamp.toLocalDateTime());
                }
            }
            
            return pessoa;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pessoa por ID: " + e.getMessage());
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
    
    public Pessoa buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM pessoas WHERE cpf = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pessoa pessoa = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, cpf);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setEndereco(rs.getString("endereco"));
                pessoa.setTipo(TipoPessoa.valueOf(rs.getString("tipo")));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    pessoa.setDataCadastro(timestamp.toLocalDateTime());
                }
            }
            
            return pessoa;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pessoa por CPF: " + e.getMessage());
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
    
    public List<Pessoa> listarTodos() {
        String sql = "SELECT * FROM pessoas ORDER BY nome";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pessoa> pessoas = new ArrayList<>();
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setEndereco(rs.getString("endereco"));
                pessoa.setTipo(TipoPessoa.valueOf(rs.getString("tipo")));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    pessoa.setDataCadastro(timestamp.toLocalDateTime());
                }
                
                pessoas.add(pessoa);
            }
            
            return pessoas;
        } catch (SQLException e) {
            System.err.println("Erro ao listar pessoas: " + e.getMessage());
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
    
    public List<Pessoa> listarPorTipo(TipoPessoa tipo) {
        String sql = "SELECT * FROM pessoas WHERE tipo = ? ORDER BY nome";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Pessoa> pessoas = new ArrayList<>();
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, tipo.toString());
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setEndereco(rs.getString("endereco"));
                pessoa.setTipo(TipoPessoa.valueOf(rs.getString("tipo")));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    pessoa.setDataCadastro(timestamp.toLocalDateTime());
                }
                
                pessoas.add(pessoa);
            }
            
            return pessoas;
        } catch (SQLException e) {
            System.err.println("Erro ao listar pessoas por tipo: " + e.getMessage());
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
}