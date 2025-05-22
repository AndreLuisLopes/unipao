package dao;

import model.Funcionario;
import model.Pessoa.TipoPessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    
    public boolean inserir(Funcionario funcionario) {
        String sql = "INSERT INTO pessoas (nome, cpf, telefone, email, endereco, tipo, cargo, salario, data_contratacao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getEndereco());
            stmt.setString(6, funcionario.getTipo().toString());
            stmt.setString(7, funcionario.getCargo());
            stmt.setDouble(8, funcionario.getSalario());
            stmt.setString(9, funcionario.getDataContratacao());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    funcionario.setId(rs.getInt(1));
                }
                rs.close();
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir funcionário: " + e.getMessage());
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
    
    public boolean atualizar(Funcionario funcionario) {
        String sql = "UPDATE pessoas SET nome = ?, cpf = ?, telefone = ?, " +
                     "email = ?, endereco = ?, cargo = ?, salario = ?, data_contratacao = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getEndereco());
            stmt.setString(6, funcionario.getCargo());
            stmt.setDouble(7, funcionario.getSalario());
            stmt.setString(8, funcionario.getDataContratacao());
            stmt.setInt(9, funcionario.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
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
        String sql = "DELETE FROM pessoas WHERE id = ? AND tipo = 'FUNCIONARIO'";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir funcionário: " + e.getMessage());
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
    
    public Funcionario buscarPorId(int id) {
        String sql = "SELECT * FROM pessoas WHERE id = ? AND tipo = 'FUNCIONARIO'";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Funcionario funcionario = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setEndereco(rs.getString("endereco"));
                funcionario.setTipo(TipoPessoa.valueOf(rs.getString("tipo")));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setSalario(rs.getDouble("salario"));
                funcionario.setDataContratacao(rs.getString("data_contratacao"));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    funcionario.setDataCadastro(timestamp.toLocalDateTime());
                }
            }
            
            return funcionario;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por ID: " + e.getMessage());
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
    
    public Funcionario buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM pessoas WHERE cpf = ? AND tipo = 'FUNCIONARIO'";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Funcionario funcionario = null;
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, cpf);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setEndereco(rs.getString("endereco"));
                funcionario.setTipo(TipoPessoa.valueOf(rs.getString("tipo")));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setSalario(rs.getDouble("salario"));
                funcionario.setDataContratacao(rs.getString("data_contratacao"));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    funcionario.setDataCadastro(timestamp.toLocalDateTime());
                }
            }
            
            return funcionario;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por CPF: " + e.getMessage());
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
    
    public List<Funcionario> listarTodos() {
        String sql = "SELECT * FROM pessoas WHERE tipo = 'FUNCIONARIO' ORDER BY nome";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setEndereco(rs.getString("endereco"));
                funcionario.setTipo(TipoPessoa.valueOf(rs.getString("tipo")));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setSalario(rs.getDouble("salario"));
                funcionario.setDataContratacao(rs.getString("data_contratacao"));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    funcionario.setDataCadastro(timestamp.toLocalDateTime());
                }
                
                funcionarios.add(funcionario);
            }
            
            return funcionarios;
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
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
    
    public List<Funcionario> listarPorCargo(String cargo) {
        String sql = "SELECT * FROM pessoas WHERE tipo = 'FUNCIONARIO' AND cargo = ? ORDER BY nome";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Funcionario> funcionarios = new ArrayList<>();
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, cargo);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setEndereco(rs.getString("endereco"));
                funcionario.setTipo(TipoPessoa.valueOf(rs.getString("tipo")));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setSalario(rs.getDouble("salario"));
                funcionario.setDataContratacao(rs.getString("data_contratacao"));
                
                Timestamp timestamp = rs.getTimestamp("data_cadastro");
                if (timestamp != null) {
                    funcionario.setDataCadastro(timestamp.toLocalDateTime());
                }
                
                funcionarios.add(funcionario);
            }
            
            return funcionarios;
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários por cargo: " + e.getMessage());
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