package model;

import java.time.LocalDateTime;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double precoUnitario;
    private String categoria;
    private String unidadeMedida;
    private double quantidadeEstoque;
    private LocalDateTime dataCadastro;
    
    public Produto() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Produto(String nome, double precoUnitario) {
        this();
        this.nome = nome;
        this.precoUnitario = precoUnitario;
    }
    
    public Produto(int id, String nome, String descricao, double precoUnitario, 
                  String categoria, String unidadeMedida, double quantidadeEstoque, 
                  LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.categoria = categoria;
        this.unidadeMedida = unidadeMedida;
        this.quantidadeEstoque = quantidadeEstoque;
        this.dataCadastro = dataCadastro;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getPrecoUnitario() {
        return precoUnitario;
    }
    
    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getUnidadeMedida() {
        return unidadeMedida;
    }
    
    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
    
    public double getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public void setQuantidadeEstoque(double quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", precoUnitario=" + precoUnitario +
                ", quantidadeEstoque=" + quantidadeEstoque +
                '}';
    }
}