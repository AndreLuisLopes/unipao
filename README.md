# Sistema de Gestão - Padaria Unipão

## Descrição

O Sistema de Gestão Unipão é uma aplicação desktop desenvolvida em Java para gerenciar as operações de uma padaria, incluindo cadastro de produtos, clientes, funcionários, fornecedores, controle de vendas e geração de relatórios.

Este sistema foi desenvolvido seguindo o padrão de arquitetura MVC (Model-View-Controller) e utiliza JDBC para conexão com banco de dados MySQL.

## Funcionalidades

- **Cadastro de Clientes**: Gerenciamento completo de clientes  
- **Cadastro de Funcionários**: Controle de funcionários com informações específicas  
- **Cadastro de Produtos**: Gerenciamento de produtos com controle de estoque  
- **Cadastro de Fornecedores**: Controle de fornecedores e seus produtos  
- **Registro de Vendas**: Sistema de PDV com seleção de produtos e cálculo automático  
- **Consulta de Vendas**: Visualização e filtro de vendas por período  
- **Relatórios**: Geração de relatórios de vendas e estoque em PDF 

## Tecnologias Utilizadas

- **Linguagem**: Java 8+
- **Interface Gráfica**: Java Swing
- **Banco de Dados**: MySQL
- **Conexão BD**: JDBC
- **Biblioteca de Relatórios**: iText 5.4.5 
- **Arquitetura**: MVC (Model-View-Controller)
- **IDE Recomendada**: Eclipse, NetBeans ou IntelliJ IDEA

## Estrutura do Projeto

O projeto segue o padrão MVC com a seguinte estrutura de pacotes:

```
src/
├── model/         # Classes de modelo (entidades)
├── service/       # Serviço de Relatório
├── view/          # Interfaces gráficas
├── controller/    # Controladores
├── dao/           # Classes de acesso a dados
└── util/          # Classes utilitárias
```

### Principais Classes

#### Model
- `Pessoa.java` - Classe base para clientes e funcionários
- `Funcionario.java` - Especialização de Pessoa para funcionários
- `Produto.java` - Representa os produtos da padaria
- `Fornecedor.java` - Representa os fornecedores da padaria
- `Venda.java` - Representa uma venda realizada
- `ItemVenda.java` - Representa um item de uma venda

#### View
- `MainView.java` - Tela principal do sistema
- `PessoaView.java` - Tela de cadastro de clientes
- `FuncionarioView.java` - Tela de cadastro de funcionários
- `ProdutoView.java` - Tela de cadastro de produtos
- `FornecedorView.java` - Tela de cadastro de fornecedores
- `VendaView.java` - Tela de registro de vendas
- `ConsultaVendaView.java` - Tela de consulta de vendas
- `RelatorioView.java` - Tela de geração de relatórios  

#### Controller
- `PessoaController.java` - Controla operações de clientes
- `FuncionarioController.java` - Controla operações de funcionários
- `ProdutoController.java` - Controla operações de produtos
- `FornecedorController.java` - Controla operações de fornecedores
- `VendaController.java` - Controla operações de vendas

#### DAO
- `Conexao.java` - Gerencia conexões com o banco de dados
- `PessoaDAO.java` - Acesso a dados de clientes
- `FuncionarioDAO.java` - Acesso a dados de funcionários
- `ProdutoDAO.java` - Acesso a dados de produtos
- `FornecedorDAO.java` - Acesso a dados de fornecedores
- `VendaDAO.java` - Acesso a dados de vendas

### Service

- `RelatorioService.java` - Responsável pela geração dos relatórios em PDF utilizando iText

## Instalação e Configuração

### Requisitos

- Java JDK 8 ou superior
- MySQL 5.7 ou superior
- Conector JDBC para MySQL
- Biblioteca **iText 5.4.5** (incluir no classpath)  

### Passos para Instalação

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/unipao.git
   cd unipao


## Autor
Desenvolvido por André Luís Lopes
[GitHub](https://github.com/AndreLuisLopes) • [Linkedin](https://www.linkedin.com/in/andre-luis-lopes/)
