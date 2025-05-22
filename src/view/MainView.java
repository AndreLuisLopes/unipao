package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    
    public MainView() {
        setTitle("Sistema de Gestão - Padaria Unipão");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuCadastros = new JMenu("Cadastros");
        
        JMenuItem menuItemClientes = new JMenuItem("Clientes");
        menuItemClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaPessoas("CLIENTE");
            }
        });
        
        JMenuItem menuItemFuncionarios = new JMenuItem("Funcionários");
        menuItemFuncionarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaFuncionarios();
            }
        });
        
        JMenuItem menuItemProdutos = new JMenuItem("Produtos");
        menuItemProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaProdutos();
            }
        });
        
        JMenuItem menuItemFornecedores = new JMenuItem("Fornecedores");
        menuItemFornecedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaFornecedores();
            }
        });
        
        menuCadastros.add(menuItemClientes);
        menuCadastros.add(menuItemFuncionarios);
        menuCadastros.add(menuItemProdutos);
        menuCadastros.add(menuItemFornecedores);
        
        JMenu menuVendas = new JMenu("Vendas");
        
        JMenuItem menuItemNovaVenda = new JMenuItem("Nova Venda");
        menuItemNovaVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaVendas();
            }
        });
        
        JMenuItem menuItemConsultarVendas = new JMenuItem("Consultar Vendas");
        menuItemConsultarVendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaConsultaVendas();
            }
        });
        
        menuVendas.add(menuItemNovaVenda);
        menuVendas.add(menuItemConsultarVendas);
        
        menuBar.add(menuCadastros);
        menuBar.add(menuVendas);
        
        setJMenuBar(menuBar);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel logoLabel = new JLabel("UNIPÃO", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 48));
        logoLabel.setForeground(new Color(139, 69, 19));
        
        JLabel subtitleLabel = new JLabel("Sistema de Gestão para Padarias", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        
        infoPanel.add(logoLabel, BorderLayout.CENTER);
        infoPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 20, 20));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Acesso Rápido"));
        
        JButton btnNovaVenda = new JButton("Nova Venda");
        btnNovaVenda.setFont(new Font("Arial", Font.BOLD, 16));
        btnNovaVenda.setPreferredSize(new Dimension(150, 80));
        btnNovaVenda.setBackground(new Color(240, 240, 255));
        btnNovaVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaVendas();
            }
        });
        
        JButton btnProdutos = new JButton("Produtos");
        btnProdutos.setFont(new Font("Arial", Font.BOLD, 16));
        btnProdutos.setPreferredSize(new Dimension(150, 80));
        btnProdutos.setBackground(new Color(240, 255, 240));
        btnProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaProdutos();
            }
        });
        
        JButton btnClientes = new JButton("Clientes");
        btnClientes.setFont(new Font("Arial", Font.BOLD, 16));
        btnClientes.setPreferredSize(new Dimension(150, 80));
        btnClientes.setBackground(new Color(255, 240, 240));
        btnClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaPessoas("CLIENTE");
            }
        });
        
        JButton btnFuncionarios = new JButton("Funcionários");
        btnFuncionarios.setFont(new Font("Arial", Font.BOLD, 16));
        btnFuncionarios.setPreferredSize(new Dimension(150, 80));
        btnFuncionarios.setBackground(new Color(255, 240, 255));
        btnFuncionarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaFuncionarios();
            }
        });
        
        JButton btnFornecedores = new JButton("Fornecedores");
        btnFornecedores.setFont(new Font("Arial", Font.BOLD, 16));
        btnFornecedores.setPreferredSize(new Dimension(150, 80));
        btnFornecedores.setBackground(new Color(255, 255, 240));
        btnFornecedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaFornecedores();
            }
        });
               
        JButton btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Arial", Font.BOLD, 16));
        btnSair.setPreferredSize(new Dimension(150, 80));
        btnSair.setBackground(new Color(255, 200, 200));
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcao = JOptionPane.showConfirmDialog(MainView.this, 
                    "Deseja realmente sair do sistema?", 
                    "Confirmação", 
                    JOptionPane.YES_NO_OPTION);
                
                if (opcao == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        buttonPanel.add(btnNovaVenda);
        buttonPanel.add(btnProdutos);
        buttonPanel.add(btnClientes);
        buttonPanel.add(btnFuncionarios);
        buttonPanel.add(btnFornecedores);
        buttonPanel.add(btnSair);
        
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        setVisible(true);
    }
    
    private void abrirTelaPessoas(String tipo) {
        PessoaView pessoaView = new PessoaView(tipo);
        pessoaView.setVisible(true);
    }
    
    private void abrirTelaFuncionarios() {
        FuncionarioView funcionarioView = new FuncionarioView();
        funcionarioView.setVisible(true);
    }
    
    private void abrirTelaProdutos() {
        ProdutoView produtoView = new ProdutoView();
        produtoView.setVisible(true);
    }
    
    private void abrirTelaVendas() {
        VendaView vendaView = new VendaView();
        vendaView.setVisible(true);
    }
    
    private void abrirTelaConsultaVendas() {
        ConsultaVendaView consultaVendaView = new ConsultaVendaView();
        consultaVendaView.setVisible(true);
    }
    
    private void abrirTelaFornecedores() {
        FornecedorView fornecedorView = new FornecedorView();
        fornecedorView.setVisible(true);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainView mainView = new MainView();
            }
        });
    }
}