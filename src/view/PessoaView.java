package view;

import controller.PessoaController;
import model.Pessoa;
import model.Pessoa.TipoPessoa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PessoaView extends JFrame {
    private PessoaController pessoaController;
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtEndereco;
    private JComboBox<String> cmbTipo;
    private JTable tblPessoas;
    private DefaultTableModel tableModel;
    private JButton btnNovo;
    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnCancelar;
    
    private String tipoFiltro;
    
    public PessoaView(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
        this.pessoaController = new PessoaController();
        
        setTitle("Cadastro de " + (tipoFiltro.equals("CLIENTE") ? "Clientes" : "Funcionários"));
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
        
        JLabel lblCpf = new JLabel("CPF:");
        txtCpf = new JTextField();
        
        JLabel lblTelefone = new JLabel("Telefone:");
        txtTelefone = new JTextField();
        
        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();
        
        JLabel lblEndereco = new JLabel("Endereço:");
        txtEndereco = new JTextField();
        
        JLabel lblTipo = new JLabel("Tipo:");
        cmbTipo = new JComboBox<>(new String[]{"CLIENTE", "FUNCIONARIO"});
        
        if (tipoFiltro != null && !tipoFiltro.isEmpty()) {
            cmbTipo.setSelectedItem(tipoFiltro);
            cmbTipo.setEnabled(false);
        }
        
        formPanel.add(lblId);
        formPanel.add(txtId);
        formPanel.add(lblNome);
        formPanel.add(txtNome);
        formPanel.add(lblCpf);
        formPanel.add(txtCpf);
        formPanel.add(lblTelefone);
        formPanel.add(txtTelefone);
        formPanel.add(lblEmail);
        formPanel.add(txtEmail);
        formPanel.add(lblEndereco);
        formPanel.add(txtEndereco);
        formPanel.add(lblTipo);
        formPanel.add(cmbTipo);
        
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
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            tipoFiltro.equals("CLIENTE") ? "Clientes Cadastrados" : "Funcionários Cadastrados"));
        
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("CPF");
        tableModel.addColumn("Telefone");
        tableModel.addColumn("Email");
        tableModel.addColumn("Tipo");
        
        tblPessoas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblPessoas);
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
        
        tblPessoas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblPessoas.getSelectedRow();
                    if (row >= 0) {
                        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                        carregarPessoa(id);
                    }
                }
            }
        });
    }
    
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        
        if (tipoFiltro != null && !tipoFiltro.isEmpty()) {
            cmbTipo.setSelectedItem(tipoFiltro);
        } else {
            cmbTipo.setSelectedIndex(0);
        }
        
        btnExcluir.setEnabled(false);
        txtNome.requestFocus();
    }
    
    private void carregarDados() {
        try {
            tableModel.setRowCount(0);
            
            List<Pessoa> pessoas;
            
            if (tipoFiltro != null && !tipoFiltro.isEmpty()) {
                pessoas = pessoaController.listarPorTipo(TipoPessoa.valueOf(tipoFiltro));
            } else {
                pessoas = pessoaController.listarTodos();
            }
            
            if (pessoas != null) {
                for (Pessoa pessoa : pessoas) {
                    tableModel.addRow(new Object[]{
                        pessoa.getId(),
                        pessoa.getNome(),
                        pessoa.getCpf(),
                        pessoa.getTelefone(),
                        pessoa.getEmail(),
                        pessoa.getTipo()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar pessoas: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void carregarPessoa(int id) {
        try {
            Pessoa pessoa = pessoaController.buscarPorId(id);
            
            if (pessoa != null) {
                txtId.setText(String.valueOf(pessoa.getId()));
                txtNome.setText(pessoa.getNome());
                txtCpf.setText(pessoa.getCpf());
                txtTelefone.setText(pessoa.getTelefone());
                txtEmail.setText(pessoa.getEmail());
                txtEndereco.setText(pessoa.getEndereco());
                cmbTipo.setSelectedItem(pessoa.getTipo().toString());
                
                btnExcluir.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar pessoa: " + e.getMessage(), 
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
            
            if (txtCpf.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O CPF é obrigatório", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtCpf.requestFocus();
                return;
            }
            
            Pessoa pessoa = new Pessoa();
            
            if (!txtId.getText().isEmpty()) {
                pessoa.setId(Integer.parseInt(txtId.getText()));
            }
            
            pessoa.setNome(txtNome.getText());
            pessoa.setCpf(txtCpf.getText());
            pessoa.setTelefone(txtTelefone.getText());
            pessoa.setEmail(txtEmail.getText());
            pessoa.setEndereco(txtEndereco.getText());
            pessoa.setTipo(TipoPessoa.valueOf(cmbTipo.getSelectedItem().toString()));
            
            boolean sucesso;
            
            if (pessoa.getId() > 0) {
                sucesso = pessoaController.atualizar(pessoa);
            } else {
                sucesso = pessoaController.cadastrar(pessoa);
            }
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Pessoa salva com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                limparCampos();
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao salvar pessoa.", 
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
                "Erro ao salvar pessoa: " + e.getMessage(), 
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
            "Deseja realmente excluir esta pessoa?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                boolean sucesso = pessoaController.excluir(id);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Pessoa excluída com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    limparCampos();
                    carregarDados();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao excluir pessoa.", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao excluir pessoa: " + e.getMessage(), 
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
                new PessoaView("CLIENTE");
            }
        });
    }
}