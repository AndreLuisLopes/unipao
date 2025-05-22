package dao;

import model.Fornecedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {
    private Connection connection;

    public FornecedorDAO() {
        try {
            this.connection = Conexao.getConnection();
        } catch (SQLException e) {
            System.err.println("Erro ao obter conexão com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean inserir(Fornecedor fornecedor) {
        String sql = "INSERT INTO fornecedor (nome_empresa, cnpj, telefone, tipo_produto, email, endereco, contato, observacoes) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fornecedor.getNomeEmpresa());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getTipoProduto());
            stmt.setString(5, fornecedor.getEmail());
            stmt.setString(6, fornecedor.getEndereco());
            stmt.setString(7, fornecedor.getContato());
            stmt.setString(8, fornecedor.getObservacoes());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        fornecedor.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir fornecedor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Fornecedor fornecedor) {
        String sql = "UPDATE fornecedor SET nome_empresa = ?, cnpj = ?, telefone = ?, tipo_produto = ?, " +
                     "email = ?, endereco = ?, contato = ?, observacoes = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fornecedor.getNomeEmpresa());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getTipoProduto());
            stmt.setString(5, fornecedor.getEmail());
            stmt.setString(6, fornecedor.getEndereco());
            stmt.setString(7, fornecedor.getContato());
            stmt.setString(8, fornecedor.getObservacoes());
            stmt.setInt(9, fornecedor.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar fornecedor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM fornecedor WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir fornecedor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Fornecedor buscarPorId(int id) {
        String sql = "SELECT * FROM fornecedor WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarFornecedor(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fornecedor por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    public Fornecedor buscarPorCnpj(String cnpj) {
        String sql = "SELECT * FROM fornecedor WHERE cnpj = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarFornecedor(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fornecedor por CNPJ: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    public List<Fornecedor> listarTodos() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor ORDER BY nome_empresa";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                fornecedores.add(criarFornecedor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar fornecedores: " + e.getMessage());
            e.printStackTrace();
        }
        
        return fornecedores;
    }

    public List<Fornecedor> buscarPorTipoProduto(String tipoProduto) {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor WHERE tipo_produto LIKE ? ORDER BY nome_empresa";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + tipoProduto + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    fornecedores.add(criarFornecedor(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fornecedores por tipo de produto: " + e.getMessage());
            e.printStackTrace();
        }
        
        return fornecedores;
    }

    public List<Fornecedor> buscarPorNome(String nome) {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor WHERE nome_empresa LIKE ? ORDER BY nome_empresa";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    fornecedores.add(criarFornecedor(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fornecedores por nome: " + e.getMessage());
            e.printStackTrace();
        }
        
        return fornecedores;
    }

    private Fornecedor criarFornecedor(ResultSet rs) throws SQLException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(rs.getInt("id"));
        fornecedor.setNomeEmpresa(rs.getString("nome_empresa"));
        fornecedor.setCnpj(rs.getString("cnpj"));
        fornecedor.setTelefone(rs.getString("telefone"));
        fornecedor.setTipoProduto(rs.getString("tipo_produto"));
        fornecedor.setEmail(rs.getString("email"));
        fornecedor.setEndereco(rs.getString("endereco"));
        fornecedor.setContato(rs.getString("contato"));
        fornecedor.setObservacoes(rs.getString("observacoes"));
        return fornecedor;
    }
}