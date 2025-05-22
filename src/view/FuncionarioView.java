package view;

import controller.FuncionarioController;
import model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FuncionarioView extends JFrame {
    private FuncionarioController funcionarioController;
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtEndereco;
    private JTextField txtCargo;
    private JTextField txtSalario;
    private JTextField txtDataContratacao;
    private JTable tblFuncionarios;
    private DefaultTableModel tableModel;
    private JButton btnNovo;
    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnCancelar;
    
    public FuncionarioView() {
        this.funcionarioController = new FuncionarioController();
        
        setTitle("Cadastro de Funcionários");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Funcionário"));
        
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
        
        JLabel lblCargo = new JLabel("Cargo:");
        txtCargo = new JTextField();
        
        JLabel lblSalario = new JLabel("Salário:");
        txtSalario = new JTextField();
        
        JLabel lblDataContratacao = new JLabel("Data de Contratação (dd/mm/aaaa):");
        txtDataContratacao = new JTextField();
        
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
        formPanel.add(lblCargo);
        formPanel.add(txtCargo);
        formPanel.add(lblSalario);
        formPanel.add(txtSalario);
        formPanel.add(lblDataContratacao);
        formPanel.add(txtDataContratacao);
        
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
        tablePanel.setBorder(BorderFactory.createTitledBorder("Funcionários Cadastrados"));
        
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
        tableModel.addColumn("Cargo");
        tableModel.addColumn("Salário");
        tableModel.addColumn("Data Contratação");
        
        tblFuncionarios = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblFuncionarios);
        scrollPane.setPreferredSize(new Dimension(850, 200));
        
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
        
        tblFuncionarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblFuncionarios.getSelectedRow();
                    if (row >= 0) {
                        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                        carregarFuncionario(id);
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
        txtCargo.setText("");
        txtSalario.setText("");
        txtDataContratacao.setText("");
        
        btnExcluir.setEnabled(false);
        txtNome.requestFocus();
    }
    
    private void carregarDados() {
        try {
            tableModel.setRowCount(0);
            
            List<Funcionario> funcionarios = funcionarioController.listarTodos();
            
            if (funcionarios != null) {
                for (Funcionario funcionario : funcionarios) {
                    tableModel.addRow(new Object[]{
                        funcionario.getId(),
                        funcionario.getNome(),
                        funcionario.getCpf(),
                        funcionario.getTelefone(),
                        funcionario.getCargo(),
                        String.format("%.2f", funcionario.getSalario()),
                        funcionario.getDataContratacao()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar funcionários: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void carregarFuncionario(int id) {
        try {
            Funcionario funcionario = funcionarioController.buscarPorId(id);
            
            if (funcionario != null) {
                txtId.setText(String.valueOf(funcionario.getId()));
                txtNome.setText(funcionario.getNome());
                txtCpf.setText(funcionario.getCpf());
                txtTelefone.setText(funcionario.getTelefone());
                txtEmail.setText(funcionario.getEmail());
                txtEndereco.setText(funcionario.getEndereco());
                txtCargo.setText(funcionario.getCargo());
                txtSalario.setText(String.valueOf(funcionario.getSalario()));
                txtDataContratacao.setText(funcionario.getDataContratacao());
                
                btnExcluir.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar funcionário: " + e.getMessage(), 
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
            
            if (txtCargo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O cargo é obrigatório", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtCargo.requestFocus();
                return;
            }
            
            if (txtSalario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "O salário é obrigatório", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtSalario.requestFocus();
                return;
            }
            
            if (txtDataContratacao.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "A data de contratação é obrigatória", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtDataContratacao.requestFocus();
                return;
            }
            
            Funcionario funcionario = new Funcionario();
            
            if (!txtId.getText().isEmpty()) {
                funcionario.setId(Integer.parseInt(txtId.getText()));
            }
            
            funcionario.setNome(txtNome.getText());
            funcionario.setCpf(txtCpf.getText());
            funcionario.setTelefone(txtTelefone.getText());
            funcionario.setEmail(txtEmail.getText());
            funcionario.setEndereco(txtEndereco.getText());
            funcionario.setCargo(txtCargo.getText());
            
            try {
                double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
                funcionario.setSalario(salario);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Salário inválido. Use apenas números e ponto ou vírgula para decimais.", 
                    "Erro de Validação", 
                    JOptionPane.WARNING_MESSAGE);
                txtSalario.requestFocus();
                return;
            }
            
            funcionario.setDataContratacao(txtDataContratacao.getText());
            
            boolean sucesso;
            
            if (funcionario.getId() > 0) {
                sucesso = funcionarioController.atualizar(funcionario);
            } else {
                sucesso = funcionarioController.cadastrar(funcionario);
            }
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Funcionário salvo com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                limparCampos();
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao salvar funcionário.", 
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
                "Erro ao salvar funcionário: " + e.getMessage(), 
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
            "Deseja realmente excluir este funcionário?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                boolean sucesso = funcionarioController.excluir(id);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, 
                        "Funcionário excluído com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    limparCampos();
                    carregarDados();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao excluir funcionário.", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao excluir funcionário: " + e.getMessage(), 
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
                new FuncionarioView();
            }
        });
    }
}