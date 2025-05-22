package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private int id;
    private Pessoa cliente;
    private Pessoa funcionario;
    private LocalDateTime dataVenda;
    private double valorTotal;
    private String formaPagamento;
    private List<ItemVenda> itens;
    
    public Venda() {
        this.dataVenda = LocalDateTime.now();
        this.itens = new ArrayList<>();
    }
    
    public Venda(Pessoa funcionario) {
        this();
        this.funcionario = funcionario;
    }
    
    public Venda(int id, Pessoa cliente, Pessoa funcionario, LocalDateTime dataVenda, 
                double valorTotal, String formaPagamento) {
        this.id = id;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.itens = new ArrayList<>();
    }
    
    public void adicionarItem(Produto produto, double quantidade) {
        double precoUnitario = produto.getPrecoUnitario();
        double subtotal = precoUnitario * quantidade;
        
        ItemVenda item = new ItemVenda(produto, quantidade, precoUnitario, subtotal);
        this.itens.add(item);
        
        calcularValorTotal();
    }
    
    public void removerItem(int index) {
        if (index >= 0 && index < itens.size()) {
            itens.remove(index);
            calcularValorTotal();
        }
    }
    
    public void calcularValorTotal() {
        this.valorTotal = 0;
        for (ItemVenda item : itens) {
            this.valorTotal += item.getSubtotal();
        }
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Pessoa getCliente() {
        return cliente;
    }
    
    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }
    
    public Pessoa getFuncionario() {
        return funcionario;
    }
    
    public void setFuncionario(Pessoa funcionario) {
        this.funcionario = funcionario;
    }
    
    public LocalDateTime getDataVenda() {
        return dataVenda;
    }
    
    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }
    
    public double getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public String getFormaPagamento() {
        return formaPagamento;
    }
    
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    public List<ItemVenda> getItens() {
        return itens;
    }
    
    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
        calcularValorTotal();
    }
    
    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", cliente=" + (cliente != null ? cliente.getNome() : "Não informado") +
                ", funcionario=" + funcionario.getNome() +
                ", dataVenda=" + dataVenda +
                ", valorTotal=" + valorTotal +
                ", itens=" + itens.size() +
                '}';
    }
}