package view;

import service.RelatorioService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;

public class RelatorioView extends JFrame {
    private RelatorioService relatorioService;
    private JSpinner spnAno;
    private JButton btnVendasMes;
    private JButton btnVendasCliente;
    private JButton btnEstoque;
    private JButton btnFechar;
    
    public RelatorioView() {
        this.relatorioService = new RelatorioService();
        
        setTitle("Geração de Relatórios - Padaria Unipão");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        configurarEventos();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel("Geração de Relatórios PDF");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Painel de configurações
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        configPanel.setBorder(BorderFactory.createTitledBorder("Configurações"));
        
        JLabel lblAno = new JLabel("Ano:");
        spnAno = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 2020, 2030, 1));
        
        configPanel.add(lblAno);
        configPanel.add(spnAno);
        
        // Painel de botões de relatórios
        JPanel relatoriosPanel = new JPanel();
        relatoriosPanel.setLayout(new GridLayout(3, 1, 10, 10));
        relatoriosPanel.setBorder(BorderFactory.createTitledBorder("Relatórios Disponíveis"));
        
        btnVendasMes = new JButton("📊 Relatório de Vendas por Mês");
        btnVendasMes.setPreferredSize(new Dimension(300, 40));
        btnVendasMes.setBackground(new Color(240, 240, 255));
        
        btnVendasCliente = new JButton("👥 Relatório de Vendas por Cliente");
        btnVendasCliente.setPreferredSize(new Dimension(300, 40));
        btnVendasCliente.setBackground(new Color(240, 255, 240));
        
        btnEstoque = new JButton("📦 Relatório de Gestão de Estoque");
        btnEstoque.setPreferredSize(new Dimension(300, 40));
        btnEstoque.setBackground(new Color(255, 240, 240));
        
        relatoriosPanel.add(btnVendasMes);
        relatoriosPanel.add(btnVendasCliente);
        relatoriosPanel.add(btnEstoque);
        
        // Painel de botões de ação
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnFechar = new JButton("Fechar");
        buttonPanel.add(btnFechar);
        
        // Reorganizar layout
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(configPanel, BorderLayout.NORTH);
        centerPanel.add(relatoriosPanel, BorderLayout.CENTER);
        
        mainPanel.add(lblTitulo, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void configurarEventos() {
        btnVendasMes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarRelatorioVendasMes();
            }
        });
        
        btnVendasCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarRelatorioVendasCliente();
            }
        });
        
        btnEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarRelatorioEstoque();
            }
        });
        
        btnFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void gerarRelatorioVendasMes() {
        try {
            int ano = (Integer) spnAno.getValue();
            String nomeArquivo = "relatorio_vendas_mes_" + ano + ".pdf";
            String caminhoArquivo = selecionarArquivo(nomeArquivo);
            
            if (caminhoArquivo != null) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                
                boolean sucesso = relatorioService.gerarRelatorioVendasPorMes(caminhoArquivo, ano);
                
                setCursor(Cursor.getDefaultCursor());
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                        "Relatório gerado com sucesso!\nArquivo: " + caminhoArquivo,
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Perguntar se deseja abrir o arquivo
                    int opcao = JOptionPane.showConfirmDialog(this,
                        "Deseja abrir o relatório agora?",
                        "Abrir Relatório",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (opcao == JOptionPane.YES_OPTION) {
                        abrirArquivo(caminhoArquivo);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Erro ao gerar o relatório.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            setCursor(Cursor.getDefaultCursor());
            JOptionPane.showMessageDialog(this,
                "Erro ao gerar relatório: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gerarRelatorioVendasCliente() {
        try {
            int ano = (Integer) spnAno.getValue();
            String nomeArquivo = "relatorio_vendas_cliente_" + ano + ".pdf";
            String caminhoArquivo = selecionarArquivo(nomeArquivo);
            
            if (caminhoArquivo != null) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                
                boolean sucesso = relatorioService.gerarRelatorioVendasPorCliente(caminhoArquivo, ano);
                
                setCursor(Cursor.getDefaultCursor());
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                        "Relatório gerado com sucesso!\nArquivo: " + caminhoArquivo,
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Perguntar se deseja abrir o arquivo
                    int opcao = JOptionPane.showConfirmDialog(this,
                        "Deseja abrir o relatório agora?",
                        "Abrir Relatório",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (opcao == JOptionPane.YES_OPTION) {
                        abrirArquivo(caminhoArquivo);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Erro ao gerar o relatório.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            setCursor(Cursor.getDefaultCursor());
            JOptionPane.showMessageDialog(this,
                "Erro ao gerar relatório: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gerarRelatorioEstoque() {
        try {
            String nomeArquivo = "relatorio_gestao_estoque.pdf";
            String caminhoArquivo = selecionarArquivo(nomeArquivo);
            
            if (caminhoArquivo != null) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                
                boolean sucesso = relatorioService.gerarRelatorioGestaoEstoque(caminhoArquivo);
                
                setCursor(Cursor.getDefaultCursor());
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                        "Relatório gerado com sucesso!\nArquivo: " + caminhoArquivo,
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Perguntar se deseja abrir o arquivo
                    int opcao = JOptionPane.showConfirmDialog(this,
                        "Deseja abrir o relatório agora?",
                        "Abrir Relatório",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (opcao == JOptionPane.YES_OPTION) {
                        abrirArquivo(caminhoArquivo);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Erro ao gerar o relatório.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            setCursor(Cursor.getDefaultCursor());
            JOptionPane.showMessageDialog(this,
                "Erro ao gerar relatório: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String selecionarArquivo(String nomeArquivoSugerido) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório");
        fileChooser.setSelectedFile(new File(nomeArquivoSugerido));
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos PDF", "pdf");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showSaveDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String caminho = selectedFile.getAbsolutePath();
            
            // Garantir que o arquivo tenha extensão .pdf
            if (!caminho.toLowerCase().endsWith(".pdf")) {
                caminho += ".pdf";
            }
            
            return caminho;
        }
        
        return null;
    }
    
    private void abrirArquivo(String caminhoArquivo) {
        try {
            File arquivo = new File(caminhoArquivo);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(arquivo);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Não é possível abrir o arquivo automaticamente.\nArquivo salvo em: " + caminhoArquivo,
                    "Informação",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao abrir o arquivo: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
