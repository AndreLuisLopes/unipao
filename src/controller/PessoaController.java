package controller;

import dao.PessoaDAO;
import model.Pessoa;
import model.Pessoa.TipoPessoa;

import java.util.List;

public class PessoaController {
    private PessoaDAO pessoaDAO;
    
    public PessoaController() {
        this.pessoaDAO = new PessoaDAO();
    }
    
    public boolean cadastrar(Pessoa pessoa) {
        if (pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório");
        }
        
        if (pessoa.getCpf() == null || pessoa.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório");
        }
        
        if (pessoa.getTipo() == null) {
            throw new IllegalArgumentException("O tipo de pessoa é obrigatório");
        }
        
        Pessoa pessoaExistente = pessoaDAO.buscarPorCpf(pessoa.getCpf());
        if (pessoaExistente != null) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        
        return pessoaDAO.inserir(pessoa);
    }
    
    public boolean atualizar(Pessoa pessoa) {
        if (pessoa.getId() <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        if (pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório");
        }
        
        if (pessoa.getCpf() == null || pessoa.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório");
        }
        
        if (pessoa.getTipo() == null) {
            throw new IllegalArgumentException("O tipo de pessoa é obrigatório");
        }
        
        Pessoa pessoaExistente = pessoaDAO.buscarPorCpf(pessoa.getCpf());
        if (pessoaExistente != null && pessoaExistente.getId() != pessoa.getId()) {
            throw new IllegalArgumentException("CPF já cadastrado para outra pessoa");
        }
        
        return pessoaDAO.atualizar(pessoa);
    }
    
    public boolean excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        return pessoaDAO.excluir(id);
    }
    
    public Pessoa buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        return pessoaDAO.buscarPorId(id);
    }
    
    public Pessoa buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF inválido");
        }
        
        return pessoaDAO.buscarPorCpf(cpf);
    }
    
    public List<Pessoa> listarTodos() {
        return pessoaDAO.listarTodos();
    }
    
    public List<Pessoa> listarPorTipo(TipoPessoa tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo inválido");
        }
        
        return pessoaDAO.listarPorTipo(tipo);
    }
}