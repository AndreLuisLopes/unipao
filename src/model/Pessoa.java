package model;

import java.time.LocalDateTime;

public class Pessoa {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;
    private TipoPessoa tipo;
    private LocalDateTime dataCadastro;
    
    public enum TipoPessoa {
        CLIENTE, FUNCIONARIO
    }
    
    public Pessoa() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Pessoa(String nome, String cpf, TipoPessoa tipo) {
        this();
        this.nome = nome;
        this.cpf = cpf;
        this.tipo = tipo;
    }
    
    public Pessoa(int id, String nome, String cpf, String telefone, String email, 
                 String endereco, TipoPessoa tipo, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.tipo = tipo;
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
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public TipoPessoa getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }
    
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}