package controller;

import dao.FuncionarioDAO;
import model.Funcionario;

import java.util.List;

public class FuncionarioController {
    private FuncionarioDAO funcionarioDAO;
    
    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAO();
    }
    
    public boolean cadastrar(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório");
        }
        
        if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório");
        }
        
        if (funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()) {
            throw new IllegalArgumentException("O cargo é obrigatório");
        }
        
        if (funcionario.getSalario() <= 0) {
            throw new IllegalArgumentException("O salário deve ser maior que zero");
        }
        
        if (funcionario.getDataContratacao() == null || funcionario.getDataContratacao().trim().isEmpty()) {
            throw new IllegalArgumentException("A data de contratação é obrigatória");
        }
        
        Funcionario funcionarioExistente = funcionarioDAO.buscarPorCpf(funcionario.getCpf());
        if (funcionarioExistente != null) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        
        return funcionarioDAO.inserir(funcionario);
    }
    
    public boolean atualizar(Funcionario funcionario) {
        if (funcionario.getId() <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório");
        }
        
        if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório");
        }
        
        if (funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()) {
            throw new IllegalArgumentException("O cargo é obrigatório");
        }
        
        if (funcionario.getSalario() <= 0) {
            throw new IllegalArgumentException("O salário deve ser maior que zero");
        }
        
        if (funcionario.getDataContratacao() == null || funcionario.getDataContratacao().trim().isEmpty()) {
            throw new IllegalArgumentException("A data de contratação é obrigatória");
        }
        
        Funcionario funcionarioExistente = funcionarioDAO.buscarPorCpf(funcionario.getCpf());
        if (funcionarioExistente != null && funcionarioExistente.getId() != funcionario.getId()) {
            throw new IllegalArgumentException("CPF já cadastrado para outro funcionário");
        }
        
        return funcionarioDAO.atualizar(funcionario);
    }
    
    public boolean excluir(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        return funcionarioDAO.excluir(id);
    }
    
    public Funcionario buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        return funcionarioDAO.buscarPorId(id);
    }
    
    public Funcionario buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF inválido");
        }
        
        return funcionarioDAO.buscarPorCpf(cpf);
    }
    
    public List<Funcionario> listarTodos() {
        return funcionarioDAO.listarTodos();
    }
    
    public List<Funcionario> listarPorCargo(String cargo) {
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo inválido");
        }
        
        return funcionarioDAO.listarPorCargo(cargo);
    }
}