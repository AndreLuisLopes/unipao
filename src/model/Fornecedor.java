package model;

public class Fornecedor {
    private int id;
    private String nomeEmpresa;
    private String cnpj;
    private String telefone;
    private String tipoProduto;
    private String email;
    private String endereco;
    private String contato;
    private String observacoes;

    public Fornecedor() {
    }

    public Fornecedor(String nomeEmpresa, String cnpj, String telefone, String tipoProduto) {
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.tipoProduto = tipoProduto;
    }

    public Fornecedor(int id, String nomeEmpresa, String cnpj, String telefone, String tipoProduto, 
                      String email, String endereco, String contato, String observacoes) {
        this.id = id;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.tipoProduto = tipoProduto;
        this.email = email;
        this.endereco = endereco;
        this.contato = contato;
        this.observacoes = observacoes;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
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

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return nomeEmpresa + " (CNPJ: " + cnpj + ")";
    }
}