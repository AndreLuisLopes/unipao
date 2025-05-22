package controller;

import dao.ProdutoDAO;
import model.Produto;

import java.util.List;

public class ProdutoController {
    private ProdutoDAO produtoDAO;
    
    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO();
    }
    
    public boolean cadastrar(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório");
        }
        
        if (produto.getPrecoUnitario() <= 0) {
            throw new IllegalArgumentException("O preço unitário deve ser maior que zero");
        }
        
        return produtoDAO.inserir(produto);
    }
    
    public boolean atualizar(Produto produto) {
        if (produto.getId() <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório");
        }
        
        if (produto.getPrecoUnitario() <= 0) {
            throw new IllegalArgumentException("O preço unitário deve ser maior que zero");
        }
        
        return produtoDAO.atualizar(produto);
    }
    
    public boolean excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        return produtoDAO.excluir(id);
    }
    
    public Produto buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        return produtoDAO.buscarPorId(id);
    }
    
    public List<Produto> listarTodos() {
        return produtoDAO.listarTodos();
    }
    
    public List<Produto> listarPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria inválida");
        }
        
        return produtoDAO.listarPorCategoria(categoria);
    }
    
    public boolean atualizarEstoque(int produtoId, double quantidade) {
        if (produtoId <= 0) {
            throw new IllegalArgumentException("ID de produto inválido");
        }
        
        Produto produto = produtoDAO.buscarPorId(produtoId);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        
        if (quantidade < 0 && (produto.getQuantidadeEstoque() + quantidade) < 0) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        
        return produtoDAO.atualizarEstoque(produtoId, quantidade);
    }
}