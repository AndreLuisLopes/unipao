package view;

import controller.VendaController;
import model.Venda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsultaVendaView extends JFrame {
    private VendaController vendaController;
    private JTextField txtDataInicio;
    private JTextField txtDataFim;
    private JTable tblVendas;
    private DefaultTableModel tableModel;
    private JButton btnConsultar;
    private JButton btnDetalhes;
    private JButton btnFechar;
    
    public ConsultaVendaView() {
        this.vendaController = new VendaController();
        
        setTitle("Consulta de Vendas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));
        
        JLabel lblDataInicio = new JLabel("Data Início:");
        txtDataInicio = new JTextField(10);
        txtDataInicio.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        JLabel lblDataFim = new JLabel("Data Fim:");
        txtDataFim = new JTextField(10);
        txtDataFim.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        btnConsultar = new JButton("Consultar");
        
        filterPanel.add(lblDataInicio);
        filterPanel.add(txtDataInicio);
        filterPanel.add(lblDataFim);
        filterPanel.add(txtDataFim);
        filterPanel.add(btnConsultar);
        
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Data");
        tableModel.addColumn("Cliente");
        tableModel.addColumn("Funcionário");
        tableModel.addColumn("Valor Total");
        tableModel.addColumn("Forma Pagamento");
        
        tblVendas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblVendas);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnDetalhes = new JButton("Ver Detalhes");
        btnFechar = new JButton("Fechar");
        
        buttonPanel.add(btnDetalhes);
        buttonPanel.add(btnFechar);
        
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        configurarEventos();
        
        consultarVendas();
    }
    
    private void configurarEventos() {
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarVendas();
            }
        });
        
        btnDetalhes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetalhes();
            }
        });
        
        btnFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void consultarVendas() {
        try {
            LocalDateTime dataInicio = LocalDateTime.of(
                LocalDate.parse(txtDataInicio.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.MIN
            );
            
            LocalDateTime dataFim = LocalDateTime.of(
                LocalDate.parse(txtDataFim.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.MAX
            );
            
            tableModel.setRowCount(0);
            
            List<Venda> vendas = vendaController.listarPorPeriodo(dataInicio, dataFim);
            
            if (vendas != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                
                for (Venda venda : vendas) {
                    tableModel.addRow(new Object[]{
                        venda.getId(),
                        venda.getDataVenda().format(formatter),
                        venda.getCliente() != null ? venda.getCliente().getNome() : "Não informado",
                        venda.getFuncionario().getNome(),
                        String.format("%.2f", venda.getValorTotal()),
                        venda.getFormaPagamento()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao consultar vendas: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void verDetalhes() {
        int row = tblVendas.getSelectedRow();
        
        if (row >= 0) {
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            
            Venda venda = vendaController.buscarPorId(id);
            
            if (venda != null) {
                StringBuilder detalhes = new StringBuilder();
                
                detalhes.append("Venda #").append(venda.getId()).append("\n\n");
                detalhes.append("Data: ").append(venda.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
                detalhes.append("Cliente: ").append(venda.getCliente() != null ? venda.getCliente().getNome() : "Não informado").append("\n");
                detalhes.append("Funcionário: ").append(venda.getFuncionario().getNome()).append("\n");
                detalhes.append("Forma de Pagamento: ").append(venda.getFormaPagamento()).append("\n\n");
                
                detalhes.append("Itens:\n");
                detalhes.append("--------------------------------------------------\n");
                detalhes.append(String.format("%-30s %10s %10s %10s\n", "Produto", "Qtde", "Preço", "Subtotal"));
                detalhes.append("--------------------------------------------------\n");
                
                for (int i = 0; i < venda.getItens().size(); i++) {
                    detalhes.append(String.format("%-30s %10.2f %10.2f %10.2f\n", 
                        venda.getItens().get(i).getProduto().getNome(),
                        venda.getItens().get(i).getQuantidade(),
                        venda.getItens().get(i).getPrecoUnitario(),
                        venda.getItens().get(i).getSubtotal()
                    ));
                }
                
                detalhes.append("--------------------------------------------------\n");
                detalhes.append(String.format("%52s %10.2f\n", "Total:", venda.getValorTotal()));
                
                JTextArea textArea = new JTextArea(detalhes.toString());
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));
                
                JOptionPane.showMessageDialog(this, 
                    scrollPane, 
                    "Detalhes da Venda", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma venda para ver os detalhes.", 
                "Erro", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
}