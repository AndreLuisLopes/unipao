package model;

import java.time.LocalDateTime;

public class Funcionario extends Pessoa {
    private String cargo;
    private double salario;
    private String dataContratacao;

    public Funcionario() {
        super();
        this.setTipo(TipoPessoa.FUNCIONARIO);
    }
    
    public Funcionario(String nome, String cpf, String telefone, String cargo, double salario, String dataContratacao) {
        super();
        this.setNome(nome);
        this.setCpf(cpf);
        this.setTelefone(telefone);
        this.setTipo(TipoPessoa.FUNCIONARIO);
        this.cargo = cargo;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
    }
    
    public Funcionario(int id, String nome, String cpf, String telefone, String email, 
                      String endereco, String cargo, double salario, String dataContratacao, 
                      LocalDateTime dataCadastro) {
        super(id, nome, cpf, telefone, email, endereco, TipoPessoa.FUNCIONARIO, dataCadastro);
        this.cargo = cargo;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if (salario < 0) {
            throw new IllegalArgumentException("Salário não pode ser negativo.");
        } else {
            this.salario = salario;
        }
    }

    public String getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(String dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    @Override
    public String toString() {
        return super.toString() + ", Cargo: " + cargo + ", Salário: R$" + salario + ", Data de Contratação: " + dataContratacao;
    }
}