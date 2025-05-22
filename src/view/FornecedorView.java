package view;

import controller.FornecedorController;
import model.Fornecedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FornecedorView extends JFrame {
    private FornecedorController fornecedorController;
    
    private JTextField txtId;
    private JTextField txtNomeEmpresa;
    private JTextField txtCnpj;
    private JTextField txtTelefone;
    private JTextField txtTipoProduto;
    private JTextField txtEmail;
    private JTextField txtEndereco;
    private JTextField txtContato;
    private JTextArea txtObservacoes;
    
    private JTable tblFornecedores;
    private DefaultTableModel tableModel;
    
    private JButton btnNovo;
    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnCancelar;
    
    private JTextField txtBusca;
    private JComboBox<String> cmbTipoBusca;
    private JButton btnBuscar;
    
    private boolean editando = false;

    public FornecedorView() {
        this.fornecedorController = new FornecedorController();
        
        setTitle("Cadastro de Fornecedores");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Fornecedor"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        txtId = new JTextField(10);
        txtId.setVisible(false);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Nome da Empresa:*"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        txtNomeEmpresa = new JTextField(30);
        formPanel.add(txtNomeEmpresa, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("CNPJ:*"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        txtCnpj = new JTextField(15);
        formPanel.add(txtCnpj, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Telefone:"), gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        txtTelefone = new JTextField(15);
        formPanel.add(txtTelefone, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Tipo de Produto:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        txtTipoProduto = new JTextField(30);
        formPanel.add(txtTipoProduto, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        txtEmail = new JTextField(30);
        formPanel.add(txtEmail, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Endereço:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        txtEndereco = new JTextField(30);
        formPanel.add(txtEndereco, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Contato:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        txtContato = new JTextField(30);
        formPanel.add(txtContato, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Observações:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        txtObservacoes = new JTextArea(4, 30);
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        JScrollPane scrollObservacoes = new JScrollPane(txtObservacoes);
        formPanel.add(scrollObservacoes, gbc);
        
        JPanel buttonFormPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnNovo = new JButton("Novo");
        btnNovo.setPreferredSize(new Dimension(100, 30));
        btnNovo.setBackground(new Color(230, 230, 255));
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(100, 30));
        btnSalvar.setBackground(new Color(230, 255, 230));
        
        btnExcluir = new JButton("Excluir");
        btnExcluir.setPreferredSize(new Dimension(100, 30));
        btnExcluir.setBackground(new Color(255, 230, 230));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 30));
        btnCancelar.setBackground(new Color(255, 255, 230));
        
        buttonFormPanel.add(btnNovo);
        buttonFormPanel.add(btnSalvar);
        buttonFormPanel.add(btnExcluir);
        buttonFormPanel.add(btnCancelar);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar Fornecedores"));
        
        cmbTipoBusca = new JComboBox<>(new String[]{"Nome", "CNPJ", "Tipo de Produto"});
        txtBusca = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(230, 230, 255));
        
        searchPanel.add(new JLabel("Buscar por:"));
        searchPanel.add(cmbTipoBusca);
        searchPanel.add(txtBusca);
        searchPanel.add(btnBuscar);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Fornecedores Cadastrados"));
        
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome da Empresa");
        tableModel.addColumn("CNPJ");
        tableModel.addColumn("Telefone");
        tableModel.addColumn("Tipo de Produto");
        
        tblFornecedores = new JTable(tableModel);
        tblFornecedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblFornecedores.setRowHeight(25);
        
        tblFornecedores.getColumnModel().getColumn(0).setPreferredWidth(50);  
        tblFornecedores.getColumnModel().getColumn(1).setPreferredWidth(200); 
        tblFornecedores.getColumnModel().getColumn(2).setPreferredWidth(120); 
        tblFornecedores.getColumnModel().getColumn(3).setPreferredWidth(100); 
        tblFornecedores.getColumnModel().getColumn(4).setPreferredWidth(150);
        
        JScrollPane scrollPane = new JScrollPane(tblFornecedores);
        scrollPane.setPreferredSize(new Dimension(850, 200));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonFormPanel, BorderLayout.SOUTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        configurarEventos();
        
        carregarFornecedores();
        
        habilitarCampos(false);
        
        setVisible(true);
    }

    private void configurarEventos() {
        btnNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                habilitarCampos(true);
                txtNomeEmpresa.requestFocus();
                editando = false;
            }
        });
        
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarFornecedor();
            }
        });
        
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirFornecedor();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                habilitarCampos(false);
                editando = false;
            }
        });
        
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarFornecedores();
            }
        });
        
        tblFornecedores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    selecionarFornecedor();
                }
            }
        });
        
        txtBusca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarFornecedores();
            }
        });
    }

    private void carregarFornecedores() {
        try {
            tableModel.setRowCount(0);
            
            List<Fornecedor> fornecedores = fornecedorController.listarTodos();
            
            if (fornecedores != null) {
                for (Fornecedor fornecedor : fornecedores) {
                    tableModel.addRow(new Object[]{
                        fornecedor.getId(),
                        fornecedor.getNomeEmpresa(),
                        fornecedor.getCnpj(),
                        fornecedor.getTelefone(),
                        fornecedor.getTipoProduto()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar fornecedores: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void buscarFornecedores() {
        try {
            String termoBusca = txtBusca.getText().trim();
            String tipoBusca = cmbTipoBusca.getSelectedItem().toString();
            
            if (termoBusca.isEmpty()) {
                carregarFornecedores();
                return;
            }
            
            tableModel.setRowCount(0);
            
            List<Fornecedor> fornecedores = null;
            
            if (tipoBusca.equals("Nome")) {
                fornecedores = fornecedorController.buscarPorNome(termoBusca);
            } else if (tipoBusca.equals("CNPJ")) {
                Fornecedor fornecedor = fornecedorController.buscarPorCnpj(termoBusca);
                if (fornecedor != null) {
                    fornecedores = List.of(fornecedor);
                }
            } else if (tipoBusca.equals("Tipo de Produto")) {
                fornecedores = fornecedorController.buscarPorTipoProduto(termoBusca);
            }
            
            if (fornecedores != null && !fornecedores.isEmpty()) {
                for (Fornecedor fornecedor : fornecedores) {
                    tableModel.addRow(new Object[]{
                        fornecedor.getId(),
                        fornecedor.getNomeEmpresa(),
                        fornecedor.getCnpj(),
                        fornecedor.getTelefone(),
                        fornecedor.getTipoProduto()
                    });
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Foram encontrados " + fornecedores.size() + " fornecedores.", 
                    "Busca Realizada", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum fornecedor encontrado com os critérios informados.", 
                    "Busca Realizada", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao buscar fornecedores: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void selecionarFornecedor() {
        int row = tblFornecedores.getSelectedRow();
        
        if (row >= 0) {
            try {
                int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                
                Fornecedor fornecedor = fornecedorController.buscarPorId(id);
                
                if (fornecedor != null) {
                    txtId.setText(String.valueOf(fornecedor.getId()));
                    txtNomeEmpresa.setText(fornecedor.getNomeEmpresa());
                    txtCnpj.setText(fornecedor.getCnpj());
                    txtTelefone.setText(fornecedor.getTelefone());
                    txtTipoProduto.setText(fornecedor.getTipoProduto());
                    txtEmail.setText(fornecedor.getEmail());
                    txtEndereco.setText(fornecedor.getEndereco());
                    txtContato.setText(fornecedor.getContato());
                    txtObservacoes.setText(fornecedor.getObservacoes());
                    
                    habilitarCampos(true);
                    editando = true;
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao carregar dados do fornecedor.", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao selecionar fornecedor: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void salvarFornecedor() {
        try {
            if (txtNomeEmpresa.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O nome da empresa é obrigatório.", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtNomeEmpresa.requestFocus();
                return;
            }
            
            if (txtCnpj.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O CNPJ é obrigatório.", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtCnpj.requestFocus();
                return;
            }
            
            Fornecedor fornecedor = new Fornecedor();
            
            if (!txtId.getText().isEmpty()) {
                fornecedor.setId(Integer.parseInt(txtId.getText()));
            }
            
            fornecedor.setNomeEmpresa(txtNomeEmpresa.getText().trim());
            fornecedor.setCnpj(txtCnpj.getText().trim());
            fornecedor.setTelefone(txtTelefone.getText().trim());
            fornecedor.setTipoProduto(txtTipoProduto.getText().trim());
            fornecedor.setEmail(txtEmail.getText().trim());
            fornecedor.setEndereco(txtEndereco.getText().trim());
            fornecedor.setContato(txtContato.getText().trim());
            fornecedor.setObservacoes(txtObservacoes.getText().trim());
            
            boolean sucesso = fornecedorController.cadastrar(fornecedor);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Fornecedor " + (editando ? "atualizado" : "cadastrado") + " com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                limparCampos();
                habilitarCampos(false);
                carregarFornecedores();
                editando = false;
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao " + (editando ? "atualizar" : "cadastrar") + " fornecedor.", 
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
                "Erro ao salvar fornecedor: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void excluirFornecedor() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um fornecedor para excluir.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int opcao = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este fornecedor?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                
                boolean sucesso = fornecedorController.excluir(id);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Fornecedor excluído com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    limparCampos();
                    habilitarCampos(false);
                    carregarFornecedores();
                    editando = false;
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao excluir fornecedor.", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao excluir fornecedor: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNomeEmpresa.setText("");
        txtCnpj.setText("");
        txtTelefone.setText("");
        txtTipoProduto.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtContato.setText("");
        txtObservacoes.setText("");
    }

    private void habilitarCampos(boolean habilitar) {
        txtNomeEmpresa.setEditable(habilitar);
        txtCnpj.setEditable(habilitar);
        txtTelefone.setEditable(habilitar);
        txtTipoProduto.setEditable(habilitar);
        txtEmail.setEditable(habilitar);
        txtEndereco.setEditable(habilitar);
        txtContato.setEditable(habilitar);
        txtObservacoes.setEditable(habilitar);
        
        btnSalvar.setEnabled(habilitar);
        btnExcluir.setEnabled(habilitar && editando);
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
                new FornecedorView();
            }
        });
    }
}