package dao;

import model.ItemVenda;
import model.Pessoa;
import model.Produto;
import model.Venda;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    public boolean inserir(Venda venda) {
        String sqlVenda = "INSERT INTO vendas (cliente_id, funcionario_id, valor_total, forma_pagamento) " +
                          "VALUES (?, ?, ?, ?)";
        
        String sqlItem = "INSERT INTO itens_venda (venda_id, produto_id, quantidade, preco_unitario, subtotal) " +
                         "VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmtVenda = null;
        PreparedStatement stmtItem = null;
        
        try {
            conn = Conexao.getConnection();
            conn.setAutoCommit(false);
            
            stmtVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
            
            if (venda.getCliente() != null) {
                stmtVenda.setInt(1, venda.getCliente().getId());
            } else {
                stmtVenda.setNull(1, Types.INTEGER);
            }
            
            stmtVenda.setInt(2, venda.getFuncionario().getId());
            stmtVenda.setDouble(3, venda.getValorTotal());
            stmtVenda.setString(4, venda.getFormaPagamento());
            
            int linhasAfetadas = stmtVenda.executeUpdate();
            
            if (linhasAfetadas > 0) {
                ResultSet rs = stmtVenda.getGeneratedKeys();
                if (rs.next()) {
                    int vendaId = rs.getInt(1);
                    venda.setId(vendaId);
                    
                    stmtItem = conn.prepareStatement(sqlItem);
                    
                    for (ItemVenda item : venda.getItens()) {
                        stmtItem.setInt(1, vendaId);
                        stmtItem.setInt(2, item.getProduto().getId());
                        stmtItem.setDouble(3, item.getQuantidade());
                        stmtItem.setDouble(4, item.getPrecoUnitario());
                        stmtItem.setDouble(5, item.getSubtotal());
                        
                        stmtItem.executeUpdate();
                        
                        ProdutoDAO produtoDAO = new ProdutoDAO();
                        produtoDAO.atualizarEstoque(item.getProduto().getId(), -item.getQuantidade());
                    }
                    
                    conn.commit();
                    return true;
                }
            }
            
            conn.rollback();
            return false;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao desfazer transação: " + ex.getMessage());
            }
            
            System.err.println("Erro ao inserir venda: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmtItem != null) stmtItem.close();
                if (stmtVenda != null) stmtVenda.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    Conexao.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    public Venda buscarPorId(int id) {
        String sqlVenda = "SELECT v.*, " +
                          "c.id as cliente_id, c.nome as cliente_nome, " +
                          "f.id as funcionario_id, f.nome as funcionario_nome " +
                          "FROM vendas v " +
                          "LEFT JOIN pessoas c ON v.cliente_id = c.id " +
                          "JOIN pessoas f ON v.funcionario_id = f.id " +
                          "WHERE v.id = ?";
        
        String sqlItens = "SELECT i.*, p.id as produto_id, p.nome as produto_nome, " +
                          "p.preco_unitario as produto_preco " +
                          "FROM itens_venda i " +
                          "JOIN produtos p ON i.produto_id = p.id " +
                          "WHERE i.venda_id = ?";
        
        Connection conn = null;
        PreparedStatement stmtVenda = null;
        PreparedStatement stmtItens = null;
        ResultSet rsVenda = null;
        ResultSet rsItens = null;
        Venda venda = null;
        
        try {
            conn = Conexao.getConnection();
            
            stmtVenda = conn.prepareStatement(sqlVenda);
            stmtVenda.setInt(1, id);
            rsVenda = stmtVenda.executeQuery();
            
            if (rsVenda.next()) {
                Pessoa cliente = null;
                if (rsVenda.getObject("cliente_id") != null) {
                    cliente = new Pessoa();
                    cliente.setId(rsVenda.getInt("cliente_id"));
                    cliente.setNome(rsVenda.getString("cliente_nome"));
                }
                
                Pessoa funcionario = new Pessoa();
                funcionario.setId(rsVenda.getInt("funcionario_id"));
                funcionario.setNome(rsVenda.getString("funcionario_nome"));
                
                venda = new Venda();
                venda.setId(rsVenda.getInt("id"));
                venda.setCliente(cliente);
                venda.setFuncionario(funcionario);
                venda.setValorTotal(rsVenda.getDouble("valor_total"));
                venda.setFormaPagamento(rsVenda.getString("forma_pagamento"));
                
                Timestamp timestamp = rsVenda.getTimestamp("data_venda");
                if (timestamp != null) {
                    venda.setDataVenda(timestamp.toLocalDateTime());
                }
                
                stmtItens = conn.prepareStatement(sqlItens);
                stmtItens.setInt(1, id);
                rsItens = stmtItens.executeQuery();
                
                List<ItemVenda> itens = new ArrayList<>();
                
                while (rsItens.next()) {
                    Produto produto = new Produto();
                    produto.setId(rsItens.getInt("produto_id"));
                    produto.setNome(rsItens.getString("produto_nome"));
                    produto.setPrecoUnitario(rsItens.getDouble("produto_preco"));
                    
                    ItemVenda item = new ItemVenda();
                    item.setId(rsItens.getInt("id"));
                    item.setVendaId(rsItens.getInt("venda_id"));
                    item.setProduto(produto);
                    item.setQuantidade(rsItens.getDouble("quantidade"));
                    item.setPrecoUnitario(rsItens.getDouble("preco_unitario"));
                    item.setSubtotal(rsItens.getDouble("subtotal"));
                    
                    itens.add(item);
                }
                
                venda.setItens(itens);
            }
            
            return venda;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar venda por ID: " + e.getMessage());
            return null;
        } finally {
            try {
                if (rsItens != null) rsItens.close();
                if (stmtItens != null) stmtItens.close();
                if (rsVenda != null) rsVenda.close();
                if (stmtVenda != null) stmtVenda.close();
                Conexao.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
    
    public List<Venda> listarTodos() {
        String sql = "SELECT v.*, " +
                     "c.id as cliente_id, c.nome as cliente_nome, " +
                     "f.id as funcionario_id, f.nome as funcionario_nome " +
                     "FROM vendas v " +
                     "LEFT JOIN pessoas c ON v.cliente_id = c.id " +
                     "JOIN pessoas f ON v.funcionario_id = f.id " +
                     "ORDER BY v.data_venda DESC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Venda> vendas = new ArrayList<>();
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pessoa cliente = null;
                if (rs.getObject("cliente_id") != null) {
                    cliente = new Pessoa();
                    cliente.setId(rs.getInt("cliente_id"));
                    cliente.setNome(rs.getString("cliente_nome"));
                }
                
                Pessoa funcionario = new Pessoa();
                funcionario.setId(rs.getInt("funcionario_id"));
                funcionario.setNome(rs.getString("funcionario_nome"));
                
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setCliente(cliente);
                venda.setFuncionario(funcionario);
                venda.setValorTotal(rs.getDouble("valor_total"));
                venda.setFormaPagamento(rs.getString("forma_pagamento"));
                
                Timestamp timestamp = rs.getTimestamp("data_venda");
                if (timestamp != null) {
                    venda.setDataVenda(timestamp.toLocalDateTime());
                }
                
                vendas.add(venda);
            }
            
            return vendas;
        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
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
    
    public List<Venda> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT v.*, " +
                     "c.id as cliente_id, c.nome as cliente_nome, " +
                     "f.id as funcionario_id, f.nome as funcionario_nome " +
                     "FROM vendas v " +
                     "LEFT JOIN pessoas c ON v.cliente_id = c.id " +
                     "JOIN pessoas f ON v.funcionario_id = f.id " +
                     "WHERE v.data_venda BETWEEN ? AND ? " +
                     "ORDER BY v.data_venda DESC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Venda> vendas = new ArrayList<>();
        
        try {
            conn = Conexao.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setTimestamp(1, Timestamp.valueOf(inicio));
            stmt.setTimestamp(2, Timestamp.valueOf(fim));
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pessoa cliente = null;
                if (rs.getObject("cliente_id") != null) {
                    cliente = new Pessoa();
                    cliente.setId(rs.getInt("cliente_id"));
                    cliente.setNome(rs.getString("cliente_nome"));
                }
                
                Pessoa funcionario = new Pessoa();
                funcionario.setId(rs.getInt("funcionario_id"));
                funcionario.setNome(rs.getString("funcionario_nome"));
                
                Venda venda = new Venda();
                venda.setId(rs.getInt("id"));
                venda.setCliente(cliente);
                venda.setFuncionario(funcionario);
                venda.setValorTotal(rs.getDouble("valor_total"));
                venda.setFormaPagamento(rs.getString("forma_pagamento"));
                
                Timestamp timestamp = rs.getTimestamp("data_venda");
                if (timestamp != null) {
                    venda.setDataVenda(timestamp.toLocalDateTime());
                }
                
                vendas.add(venda);
            }
            
            return vendas;
        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas por período: " + e.getMessage());
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