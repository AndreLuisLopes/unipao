package controller;

import dao.FornecedorDAO;
import model.Fornecedor;

import java.util.List;

public class FornecedorController {
    private FornecedorDAO fornecedorDAO;

    public FornecedorController() {
        this.fornecedorDAO = new FornecedorDAO();
    }

    public boolean cadastrar(Fornecedor fornecedor) {
        if (fornecedor.getNomeEmpresa() == null || fornecedor.getNomeEmpresa().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da empresa é obrigatório");
        }
        
        if (fornecedor.getCnpj() == null || fornecedor.getCnpj().trim().isEmpty()) {
            throw new IllegalArgumentException("O CNPJ é obrigatório");
        }
        
        Fornecedor fornecedorExistente = fornecedorDAO.buscarPorCnpj(fornecedor.getCnpj());
        if (fornecedorExistente != null && fornecedorExistente.getId() != fornecedor.getId()) {
            throw new IllegalArgumentException("Já existe um fornecedor cadastrado com este CNPJ");
        }
        
        if (fornecedor.getId() == 0) {
            return fornecedorDAO.inserir(fornecedor);
        } else {
            return fornecedorDAO.atualizar(fornecedor);
        }
    }

    public boolean excluir(int id) {
        return fornecedorDAO.excluir(id);
    }

    public Fornecedor buscarPorId(int id) {
        return fornecedorDAO.buscarPorId(id);
    }

    public Fornecedor buscarPorCnpj(String cnpj) {
        return fornecedorDAO.buscarPorCnpj(cnpj);
    }

    public List<Fornecedor> listarTodos() {
        return fornecedorDAO.listarTodos();
    }

    public List<Fornecedor> buscarPorTipoProduto(String tipoProduto) {
        return fornecedorDAO.buscarPorTipoProduto(tipoProduto);
    }

    public List<Fornecedor> buscarPorNome(String nome) {
        return fornecedorDAO.buscarPorNome(nome);
    }
}