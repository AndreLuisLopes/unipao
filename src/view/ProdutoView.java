package view;

import controller.ProdutoController;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProdutoView extends JFrame {
    private ProdutoController produtoController;
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextField txtPrecoUnitario;
    private JTextField txtCategoria;
    private JTextField txtUnidadeMedida;
    private JTextField txtQuantidadeEstoque;
    private JTable tblProdutos;
    private DefaultTableModel tableModel;
    private JButton btnNovo;
    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnCancelar;
    
    public ProdutoView() {
        this.produtoController = new ProdutoController();
        
        setTitle("Cadastro de Produtos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        
        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField();
        txtId.setEditable(false);
        
        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField();
        
        JLabel lblDescricao = new JLabel("Descrição:");
        txtDescricao = new JTextField();
        
        JLabel lblPrecoUnitario = new JLabel("Preço Unitário:");
        txtPrecoUnitario = new JTextField();
        
        JLabel lblCategoria = new JLabel("Categoria:");
        txtCategoria = new JTextField();
        
        JLabel lblUnidadeMedida = new JLabel("Unidade de Medida:");
        txtUnidadeMedida = new JTextField();
        
        JLabel lblQuantidadeEstoque = new JLabel("Quantidade em Estoque:");
        txtQuantidadeEstoque = new JTextField("0");
        
        formPanel.add(lblId);
        formPanel.add(txtId);
        formPanel.add(lblNome);
        formPanel.add(txtNome);
        formPanel.add(lblDescricao);
        formPanel.add(txtDescricao);
        formPanel.add(lblPrecoUnitario);
        formPanel.add(txtPrecoUnitario);
        formPanel.add(lblCategoria);
        formPanel.add(txtCategoria);
        formPanel.add(lblUnidadeMedida);
        formPanel.add(txtUnidadeMedida);
        formPanel.add(lblQuantidadeEstoque);
        formPanel.add(txtQuantidadeEstoque);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Ações"));
        
        btnNovo = new JButton("Novo");
        btnNovo.setPreferredSize(new Dimension(100, 30));
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(100, 30));
        
        btnExcluir = new JButton("Excluir");
        btnExcluir.setPreferredSize(new Dimension(100, 30));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 30));
        
        buttonPanel.add(btnNovo);
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnCancelar);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Produtos Cadastrados"));
        
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede a edição direta na tabela
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Preço Unitário");
        tableModel.addColumn("Categoria");
        tableModel.addColumn("Estoque");
        
        tblProdutos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblProdutos);
        scrollPane.setPreferredSize(new Dimension(750, 200));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        configurarEventos();
        
        limparCampos();
        carregarDados();
        
        setVisible(true);
    }
    
    private void configurarEventos() {
        btnNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvar();
            }
        });
        
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        
        tblProdutos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblProdutos.getSelectedRow();
                    if (row >= 0) {
                        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                        carregarProduto(id);
                    }
                }
            }
        });
    }
    
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtDescricao.setText("");
        txtPrecoUnitario.setText("");
        txtCategoria.setText("");
        txtUnidadeMedida.setText("");
        txtQuantidadeEstoque.setText("0");
        
        btnExcluir.setEnabled(false);
        txtNome.requestFocus();
    }
    
    private void carregarDados() {
        try {
            tableModel.setRowCount(0);
            
            List<Produto> produtos = produtoController.listarTodos();
            
            if (produtos != null) {
                for (Produto produto : produtos) {
                    tableModel.addRow(new Object[]{
                        produto.getId(),
                        produto.getNome(),
                        String.format("%.2f", produto.getPrecoUnitario()),
                        produto.getCategoria(),
                        String.format("%.2f", produto.getQuantidadeEstoque())
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar produtos: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void carregarProduto(int id) {
        try {
            Produto produto = produtoController.buscarPorId(id);
            
            if (produto != null) {
                txtId.setText(String.valueOf(produto.getId()));
                txtNome.setText(produto.getNome());
                txtDescricao.setText(produto.getDescricao());
                txtPrecoUnitario.setText(String.valueOf(produto.getPrecoUnitario()));
                txtCategoria.setText(produto.getCategoria());
                txtUnidadeMedida.setText(produto.getUnidadeMedida());
                txtQuantidadeEstoque.setText(String.valueOf(produto.getQuantidadeEstoque()));
                
                btnExcluir.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar produto: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void salvar() {
        try {
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O nome é obrigatório", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtNome.requestFocus();
                return;
            }
            
            if (txtPrecoUnitario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O preço unitário é obrigatório", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtPrecoUnitario.requestFocus();
                return;
            }
            
            Produto produto = new Produto();
            
            if (!txtId.getText().isEmpty()) {
                produto.setId(Integer.parseInt(txtId.getText()));
            }
            
            produto.setNome(txtNome.getText());
            produto.setDescricao(txtDescricao.getText());
            
            try {
                double precoUnitario = Double.parseDouble(txtPrecoUnitario.getText().replace(",", "."));
                produto.setPrecoUnitario(precoUnitario);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Preço unitário inválido. Use apenas números e ponto ou vírgula para decimais.", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtPrecoUnitario.requestFocus();
                return;
            }
            
            produto.setCategoria(txtCategoria.getText());
            produto.setUnidadeMedida(txtUnidadeMedida.getText());
            
            try {
                double quantidadeEstoque = Double.parseDouble(txtQuantidadeEstoque.getText().replace(",", "."));
                produto.setQuantidadeEstoque(quantidadeEstoque);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Quantidade em estoque inválida. Use apenas números e ponto ou vírgula para decimais.", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtQuantidadeEstoque.requestFocus();
                return;
            }
            
            boolean sucesso;
            
            if (produto.getId() > 0) {
                sucesso = produtoController.atualizar(produto);
            } else {
                sucesso = produtoController.cadastrar(produto);
            }
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Produto salvo com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                limparCampos();
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao salvar produto.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao salvar produto: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void excluir() {
        if (txtId.getText().isEmpty()) {
            return;
        }
        
        int id = Integer.parseInt(txtId.getText());
        
        int opcao = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este produto?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                boolean sucesso = produtoController.excluir(id);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Produto excluído com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    limparCampos();
                    carregarDados();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao excluir produto.", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao excluir produto: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProdutoView();
            }
        });
    }
}