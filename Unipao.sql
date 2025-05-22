CREATE DATABASE unipao;
USE unipao;

CREATE TABLE Cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf CHAR(11) UNIQUE,
    endereco VARCHAR(200),
    telefone VARCHAR(15),
    data_cadastro DATE
);

CREATE TABLE Funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf CHAR(11) UNIQUE,
    telefone VARCHAR(15),
    data_contratacao DATE,
    cargo VARCHAR(50)
);

CREATE TABLE Fornecedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_empresa VARCHAR(100) NOT NULL,
    cnpj CHAR(14) UNIQUE,
    telefone VARCHAR(15),
    tipo_produto VARCHAR(100)
);

CREATE TABLE Produto (
    codigo INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    quantidade_estoque INT NOT NULL,
    categoria VARCHAR(50),
    id_fornecedor INT,
    FOREIGN KEY (id_fornecedor) REFERENCES Fornecedor(id)
);

CREATE TABLE Venda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_venda DATE NOT NULL,
    id_cliente INT,
    id_funcionario INT NOT NULL,
    forma_pagamento ENUM('Dinheiro', 'Cartão de Crédito') NOT NULL,
    valor_total DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id),
    FOREIGN KEY (id_funcionario) REFERENCES Funcionario(id)
);

CREATE TABLE Venda_Produto (
    id_venda INT,
    id_produto INT,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_venda, id_produto),
    FOREIGN KEY (id_venda) REFERENCES Venda(id),
    FOREIGN KEY (id_produto) REFERENCES Produto(codigo)
);
