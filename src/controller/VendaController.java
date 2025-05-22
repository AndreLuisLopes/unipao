package controller;

import dao.VendaDAO;
import model.ItemVenda;
import model.Produto;
import model.Venda;

import java.time.LocalDateTime;
import java.util.List;

public class VendaController {
    private VendaDAO vendaDAO;
    private ProdutoController produtoController;
    
    public VendaController() {
        this.vendaDAO = new VendaDAO();
        this.produtoController = new ProdutoController();
    }
    
    public boolean registrar(Venda venda) {
        if (venda.getFuncionario() == null || venda.getFuncionario().getId() <= 0) {
            throw new IllegalArgumentException("Funcionário inválido");
        }
        
        if (venda.getItens() == null || venda.getItens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve ter pelo menos um item");
        }
        
        for (ItemVenda item : venda.getItens()) {
            Produto produto = produtoController.buscarPorId(item.getProduto().getId());
            
            if (produto == null) {
                throw new IllegalArgumentException("Produto não encontrado: " + item.getProduto().getId());
            }
            
            if (produto.getQuantidadeEstoque() < item.getQuantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
            }
        }
        
        venda.calcularValorTotal();
        
        return vendaDAO.inserir(venda);
    }
    
    public Venda buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        return vendaDAO.buscarPorId(id);
    }
    
    public List<Venda> listarTodos() {
        return vendaDAO.listarTodos();
    }
    
    public List<Venda> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Datas inválidas");
        }
        
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data inicial deve ser anterior à data final");
        }
        
        return vendaDAO.listarPorPeriodo(inicio, fim);
    }
}