package trabalho1;

import java.util.*;

public class SistemaPadaria {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Funcionario> funcionarios = new ArrayList<>();
    private static List<Fornecedor> fornecedores = new ArrayList<>();
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Venda> vendas = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Sistema Unipão ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Cadastrar Produto");
            System.out.println("4. Listar Produtos");
            System.out.println("5. Cadastrar Fornecedor");
            System.out.println("6. Listar Fornecedores");
            System.out.println("7. Cadastrar Funcionário");
            System.out.println("8. Listar Funcionários");
            System.out.println("9. Registrar Venda");
            System.out.println("10. Listar Vendas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: cadastrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: cadastrarProduto(); break;
                case 4: listarProdutos(); break;
                case 5: cadastrarFornecedor(); break;
                case 6: listarFornecedores(); break;
                case 7: cadastrarFuncionario(); break;
                case 8: listarFuncionarios(); break;
                case 9: registrarVenda(); break;
                case 10: listarVendas(); break;
                case 0: System.exit(0);
                default: System.out.println("Opção inválida!");
            }
        }
    }

    private static void registrarVenda() {
        System.out.print("Data da Venda (dd/mm/aaaa): ");
        String dataVenda = scanner.nextLine();
        
        System.out.println("Escolha um cliente:");
        listarClientes();
        System.out.print("Digite o CPF do cliente: ");
        String cpfCliente = scanner.nextLine();
        Cliente cliente = buscarClientePorCpf(cpfCliente);
        
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Escolha um funcionário:");
        listarFuncionarios();
        System.out.print("Digite o CPF do funcionário: ");
        String cpfFuncionario = scanner.nextLine();
        Funcionario funcionario = buscarFuncionarioPorCpf(cpfFuncionario);
        
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }

        System.out.print("Forma de pagamento (Dinheiro ou Cartão de Crédito): ");
        String formaPagamento = scanner.nextLine();

        Venda venda = new Venda(dataVenda, cliente, funcionario, formaPagamento);

        while (true) {
            System.out.println("Escolha um produto para adicionar à venda:");
            listarProdutos();
            System.out.print("Digite o código do produto (ou digite '0' para finalizar): ");
            String codigoProduto = scanner.nextLine();

            if (codigoProduto.equals("0")) break;

            Produto produto = buscarProdutoPorCodigo(codigoProduto);
            
            if (produto == null) {
                System.out.println("Produto não encontrado!");
                continue;
            }

            System.out.print("Quantos unidades deseja comprar? ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            venda.adicionarProduto(produto, quantidade);
        }

        vendas.add(venda);
        System.out.println("Venda registrada com sucesso!");
    }

    private static void listarVendas() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
        } else {
            for (Venda v : vendas) {
                System.out.println(v);
            }
        }
    }

    private static Cliente buscarClientePorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    private static Funcionario buscarFuncionarioPorCpf(String cpf) {
        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) {
                return f;
            }
        }
        return null;
    }

    private static Produto buscarProdutoPorCodigo(String codigo) {
        for (Produto p : produtos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (Produto p : produtos) {
                System.out.println(p);
            }
        }
    }

    private static void cadastrarProduto() {
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        System.out.print("Estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();
        
        produtos.add(new Produto(codigo, nome, preco, estoque, categoria));
        System.out.println("Produto cadastrado com sucesso!");
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Data de Cadastro: ");
        String dataCadastro = scanner.nextLine();
        
        clientes.add(new Cliente(nome, cpf, telefone, endereco, dataCadastro));
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }

    private static void cadastrarFornecedor() {
        System.out.print("Nome da Empresa: ");
        String nomeEmpresa = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Tipo de Produto: ");
        String tipoProduto = scanner.nextLine();
        
        fornecedores.add(new Fornecedor(nomeEmpresa, cnpj, telefone, tipoProduto));
        System.out.println("Fornecedor cadastrado com sucesso!");
    }

    private static void listarFornecedores() {
        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor cadastrado.");
        } else {
            for (Fornecedor f : fornecedores) {
                System.out.println(f);
            }
        }
    }

    private static void cadastrarFuncionario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();
        System.out.print("Salário: ");
        double salario = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Data de Contratação: ");
        String dataContratacao = scanner.nextLine();
        
        funcionarios.add(new Funcionario(nome, cpf, telefone, cargo, salario, dataContratacao));
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private static void listarFuncionarios() {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
        } else {
            for (Funcionario f : funcionarios) {
                System.out.println(f);
            }
        }
    }
}
