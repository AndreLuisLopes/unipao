package view;

import controller.PessoaController;
import controller.ProdutoController;
import controller.VendaController;
import model.Pessoa;
import model.Pessoa.TipoPessoa;
import model.Produto;
import model.Venda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.List;

public class VendaView extends JFrame {
    private PessoaController pessoaController;
    private ProdutoController produtoController;
    private VendaController vendaController;
    
    private JComboBox<Pessoa> cmbClientes;
    private JComboBox<Pessoa> cmbFuncionarios;
    private JComboBox<Produto> cmbProdutos;
    private JTextField txtQuantidade;
    private JTextField txtPrecoUnitario;
    private JTextField txtSubtotal;
    private JTextField txtValorTotal;
    private JComboBox<String> cmbFormaPagamento;
    private JTable tblItens;
    private DefaultTableModel tableModel;
    private JButton btnAdicionarItem;
    private JButton btnRemoverItem;
    private JButton btnFinalizar;
    private JButton btnCancelar;
    
    private Venda venda;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    
    public VendaView() {
        this.pessoaController = new PessoaController();
        this.produtoController = new ProdutoController();
        this.vendaController = new VendaController();
        
        this.venda = new Venda();
        
        setTitle("Nova Venda");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel vendaPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        vendaPanel.setBorder(BorderFactory.createTitledBorder("Dados da Venda"));
        
        JLabel lblCliente = new JLabel("Cliente:");
        cmbClientes = new JComboBox<>();
        
        JLabel lblFuncionario = new JLabel("Funcionário:");
        cmbFuncionarios = new JComboBox<>();
        
        JLabel lblFormaPagamento = new JLabel("Forma de Pagamento:");
        cmbFormaPagamento = new JComboBox<>(new String[]{"Dinheiro", "Cartão de Crédito", "Cartão de Débito", "PIX"});
        
        vendaPanel.add(lblCliente);
        vendaPanel.add(cmbClientes);
        vendaPanel.add(lblFuncionario);
        vendaPanel.add(cmbFuncionarios);
        vendaPanel.add(lblFormaPagamento);
        vendaPanel.add(cmbFormaPagamento);
        
        JPanel itemPanel = new JPanel(new BorderLayout(10, 10));
        itemPanel.setBorder(BorderFactory.createTitledBorder("Itens da Venda"));
        
        JPanel formItemPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel lblProduto = new JLabel("Produto:");
        cmbProdutos = new JComboBox<>();
        
        JLabel lblQuantidade = new JLabel("Quantidade:");
        txtQuantidade = new JTextField("1");
        
        JLabel lblPrecoUnitario = new JLabel("Preço Unitário:");
        txtPrecoUnitario = new JTextField();
        txtPrecoUnitario.setEditable(false);
        
        JLabel lblSubtotal = new JLabel("Subtotal:");
        txtSubtotal = new JTextField();
        txtSubtotal.setEditable(false);
        
        formItemPanel.add(lblProduto);
        formItemPanel.add(cmbProdutos);
        formItemPanel.add(lblQuantidade);
        formItemPanel.add(txtQuantidade);
        formItemPanel.add(lblPrecoUnitario);
        formItemPanel.add(txtPrecoUnitario);
        formItemPanel.add(lblSubtotal);
        formItemPanel.add(txtSubtotal);
        
        JPanel buttonItemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAdicionarItem = new JButton("Adicionar Item");
        btnAdicionarItem.setPreferredSize(new Dimension(150, 30));
        btnAdicionarItem.setBackground(new Color(230, 255, 230));
        
        btnRemoverItem = new JButton("Remover Item");
        btnRemoverItem.setPreferredSize(new Dimension(150, 30));
        btnRemoverItem.setBackground(new Color(255, 230, 230));
        
        buttonItemPanel.add(btnAdicionarItem);
        buttonItemPanel.add(btnRemoverItem);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Produto");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Preço Unitário");
        tableModel.addColumn("Subtotal");
        
        tblItens = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblItens);
        scrollPane.setPreferredSize(new Dimension(850, 200));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        JLabel lblValorTotal = new JLabel("Valor Total:");
        lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
        
        txtValorTotal = new JTextField(10);
        txtValorTotal.setEditable(false);
        txtValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
        txtValorTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtValorTotal.setText("0,00");
        
        totalPanel.add(lblValorTotal);
        totalPanel.add(txtValorTotal);
        
        JPanel itemTopPanel = new JPanel(new BorderLayout());
        itemTopPanel.add(formItemPanel, BorderLayout.CENTER);
        itemTopPanel.add(buttonItemPanel, BorderLayout.SOUTH);
        
        itemPanel.add(itemTopPanel, BorderLayout.NORTH);
        itemPanel.add(tablePanel, BorderLayout.CENTER);
        itemPanel.add(totalPanel, BorderLayout.SOUTH);
        
        JPanel buttonVendaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnFinalizar = new JButton("Finalizar Venda");
        btnFinalizar.setPreferredSize(new Dimension(150, 40));
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFinalizar.setBackground(new Color(200, 255, 200));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(150, 40));
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(255, 200, 200));
        
        buttonVendaPanel.add(btnFinalizar);
        buttonVendaPanel.add(btnCancelar);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(vendaPanel, BorderLayout.NORTH);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(itemPanel, BorderLayout.CENTER);
        mainPanel.add(buttonVendaPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        configurarEventos();
        
        carregarDados();
        atualizarTotal();
        
        setVisible(true);
    }
    
    private void configurarEventos() {
        cmbProdutos.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    atualizarPrecoUnitario();
                }
            }
        });
        
        txtQuantidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarSubtotal();
            }
        });
        
        txtQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                atualizarSubtotal();
            }
        });
        
        btnAdicionarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarItem();
            }
        });
        
        btnRemoverItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerItem();
            }
        });
        
        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarVenda();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcao = JOptionPane.showConfirmDialog(VendaView.this, 
                    "Deseja realmente cancelar esta venda?", 
                    "Confirmação", 
                    JOptionPane.YES_NO_OPTION);
                
                if (opcao == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }
    
    private void carregarDados() {
        try {
            List<Pessoa> clientes = pessoaController.listarPorTipo(TipoPessoa.CLIENTE);
            
            cmbClientes.removeAllItems();
            cmbClientes.addItem(null);
            
            if (clientes != null) {
                for (Pessoa cliente : clientes) {
                    cmbClientes.addItem(cliente);
                }
            }
            
            List<Pessoa> funcionarios = pessoaController.listarPorTipo(TipoPessoa.FUNCIONARIO);
            
            cmbFuncionarios.removeAllItems();
            
            if (funcionarios != null) {
                for (Pessoa funcionario : funcionarios) {
                    cmbFuncionarios.addItem(funcionario);
                }
            }
            
            List<Produto> produtos = produtoController.listarTodos();
            
            cmbProdutos.removeAllItems();
            
            if (produtos != null) {
                for (Produto produto : produtos) {
                    cmbProdutos.addItem(produto);
                }
            }
            
            atualizarPrecoUnitario();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar dados: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void atualizarPrecoUnitario() {
        Produto produto = (Produto) cmbProdutos.getSelectedItem();
        
        if (produto != null) {
            txtPrecoUnitario.setText(df.format(produto.getPrecoUnitario()));
            atualizarSubtotal();
        } else {
            txtPrecoUnitario.setText("");
            txtSubtotal.setText("");
        }
    }
    
    private void atualizarSubtotal() {
        try {
            double quantidade = Double.parseDouble(txtQuantidade.getText().replace(",", "."));
            double precoUnitario = Double.parseDouble(txtPrecoUnitario.getText().replace(".", "").replace(",", "."));
            double subtotal = quantidade * precoUnitario;
            
            txtSubtotal.setText(df.format(subtotal));
        } catch (NumberFormatException e) {
            txtSubtotal.setText("");
        }
    }
    
    private void adicionarItem() {
        try {
            Produto produto = (Produto) cmbProdutos.getSelectedItem();
            
            if (produto == null) {
                JOptionPane.showMessageDialog(this, 
                    "Selecione um produto.", 
                    "Erro", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double quantidade;
            try {
                quantidade = Double.parseDouble(txtQuantidade.getText().replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Quantidade inválida. Use apenas números e ponto ou vírgula para decimais.", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtQuantidade.requestFocus();
                return;
            }
            
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "A quantidade deve ser maior que zero.", 
                    "Erro", 
                    JOptionPane.WARNING_MESSAGE);
                txtQuantidade.requestFocus();
                return;
            }
            
            if (quantidade > produto.getQuantidadeEstoque()) {
                JOptionPane.showMessageDialog(this, 
                    "Estoque insuficiente. Disponível: " + produto.getQuantidadeEstoque(), 
                    "Erro", 
                    JOptionPane.WARNING_MESSAGE);
                txtQuantidade.requestFocus();
                return;
            }
            
            double precoUnitario = Double.parseDouble(txtPrecoUnitario.getText().replace(".", "").replace(",", "."));
            double subtotal = quantidade * precoUnitario;
            
            venda.adicionarItem(produto, quantidade);
            
            tableModel.addRow(new Object[]{
                produto.getNome(),
                df.format(quantidade),
                df.format(precoUnitario),
                df.format(subtotal)
            });
            
            atualizarTotal();
            
            txtQuantidade.setText("1");
            atualizarSubtotal();
            
            JOptionPane.showMessageDialog(this, 
                "Item adicionado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao adicionar item: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void removerItem() {
        int row = tblItens.getSelectedRow();
        
        if (row >= 0) {
            venda.removerItem(row);
            tableModel.removeRow(row);
            atualizarTotal();
            
            JOptionPane.showMessageDialog(this, 
                "Item removido com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Selecione um item para remover.", 
                "Erro", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void atualizarTotal() {
        txtValorTotal.setText(df.format(venda.getValorTotal()));
    }
    
    private void finalizarVenda() {
        try {
            if (venda.getItens().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Adicione pelo menos um item à venda.", 
                    "Erro", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Pessoa funcionario = (Pessoa) cmbFuncionarios.getSelectedItem();
            
            if (funcionario == null) {
                JOptionPane.showMessageDialog(this, 
                    "Selecione um funcionário.", 
                    "Erro", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            venda.setCliente((Pessoa) cmbClientes.getSelectedItem());
            venda.setFuncionario(funcionario);
            venda.setFormaPagamento(cmbFormaPagamento.getSelectedItem().toString());
            
            boolean sucesso = vendaController.registrar(venda);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Venda finalizada com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao finalizar venda.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Erro de Validação", 
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao finalizar venda: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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
                new VendaView();
            }
        });
    }
}