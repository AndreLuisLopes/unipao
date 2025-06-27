package service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dao.VendaDAO;
import dao.ProdutoDAO;
import dao.PessoaDAO;
import model.Venda;
import model.Produto;
import model.Pessoa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RelatorioService {
    private VendaDAO vendaDAO;
    private ProdutoDAO produtoDAO;
    private PessoaDAO pessoaDAO;
    
    public RelatorioService() {
        this.vendaDAO = new VendaDAO();
        this.produtoDAO = new ProdutoDAO();
        this.pessoaDAO = new PessoaDAO();
    }
    
    public boolean gerarRelatorioVendasPorMes(String caminhoArquivo, int ano) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            
            document.open();
            
            // Título
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Relatório de Vendas por Mês - " + ano, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);
            
            // Data de geração
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 10);
            Paragraph dataGeracao = new Paragraph("Gerado em: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), normalFont);
            dataGeracao.setAlignment(Element.ALIGN_RIGHT);
            dataGeracao.setSpacingAfter(20);
            document.add(dataGeracao);
            
            // Buscar vendas do ano
            LocalDateTime inicioAno = LocalDateTime.of(ano, 1, 1, 0, 0);
            LocalDateTime fimAno = LocalDateTime.of(ano, 12, 31, 23, 59);
            List<Venda> vendas = vendaDAO.listarPorPeriodo(inicioAno, fimAno);
            
            // Agrupar vendas por mês
            Map<Integer, Double> vendasPorMes = new HashMap<>();
            Map<Integer, Integer> quantidadeVendasPorMes = new HashMap<>();
            
            for (int i = 1; i <= 12; i++) {
                vendasPorMes.put(i, 0.0);
                quantidadeVendasPorMes.put(i, 0);
            }
            
            if (vendas != null) {
                for (Venda venda : vendas) {
                    int mes = venda.getDataVenda().getMonthValue();
                    vendasPorMes.put(mes, vendasPorMes.get(mes) + venda.getValorTotal());
                    quantidadeVendasPorMes.put(mes, quantidadeVendasPorMes.get(mes) + 1);
                }
            }
            
            // Criar tabela
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            
            // Cabeçalho da tabela
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            PdfPCell headerCell;
            
            headerCell = new PdfPCell(new Phrase("Mês", headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            
            headerCell = new PdfPCell(new Phrase("Quantidade de Vendas", headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            
            headerCell = new PdfPCell(new Phrase("Valor Total", headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            
            headerCell = new PdfPCell(new Phrase("Valor Médio", headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            
            // Dados da tabela
            String[] nomesMeses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                                  "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
            
            double totalGeral = 0;
            int totalVendas = 0;
            
            for (int i = 1; i <= 12; i++) {
                table.addCell(new PdfPCell(new Phrase(nomesMeses[i-1], normalFont)));
                
                int qtdVendas = quantidadeVendasPorMes.get(i);
                double valorTotal = vendasPorMes.get(i);
                double valorMedio = qtdVendas > 0 ? valorTotal / qtdVendas : 0;
                
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(qtdVendas), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.format("R$ %.2f", valorTotal), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.format("R$ %.2f", valorMedio), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
                
                totalGeral += valorTotal;
                totalVendas += qtdVendas;
            }
            
            // Linha de total
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            PdfPCell totalCell = new PdfPCell(new Phrase("TOTAL", boldFont));
            totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell);
            
            totalCell = new PdfPCell(new Phrase(String.valueOf(totalVendas), boldFont));
            totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell);
            
            totalCell = new PdfPCell(new Phrase(String.format("R$ %.2f", totalGeral), boldFont));
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell);
            
            double mediaTotalGeral = totalVendas > 0 ? totalGeral / totalVendas : 0;
            totalCell = new PdfPCell(new Phrase(String.format("R$ %.2f", mediaTotalGeral), boldFont));
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell);
            
            document.add(table);
            
            document.close();
            return true;
            
        } catch (DocumentException | IOException e) {
            System.err.println("Erro ao gerar relatório de vendas por mês: " + e.getMessage());
            return false;
        }
    }
    
    public boolean gerarRelatorioVendasPorCliente(String caminhoArquivo, int ano) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            
            document.open();
            
            // Título
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Relatório de Vendas por Cliente - " + ano, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);
            
            // Data de geração
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 10);
            Paragraph dataGeracao = new Paragraph("Gerado em: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), normalFont);
            dataGeracao.setAlignment(Element.ALIGN_RIGHT);
            dataGeracao.setSpacingAfter(20);
            document.add(dataGeracao);
            
            // Buscar vendas do ano
            LocalDateTime inicioAno = LocalDateTime.of(ano, 1, 1, 0, 0);
            LocalDateTime fimAno = LocalDateTime.of(ano, 12, 31, 23, 59);
            List<Venda> vendas = vendaDAO.listarPorPeriodo(inicioAno, fimAno);
            
            if (vendas == null || vendas.isEmpty()) {
                Paragraph noData = new Paragraph("Nenhuma venda encontrada para o período.", normalFont);
                noData.setAlignment(Element.ALIGN_CENTER);
                document.add(noData);
                document.close();
                return true;
            }
            
            // Agrupar vendas por cliente e mês
            Map<String, Map<Integer, Double>> vendasPorClienteMes = new HashMap<>();
            
            for (Venda venda : vendas) {
                String nomeCliente = venda.getCliente() != null ? venda.getCliente().getNome() : "Cliente não informado";
                int mes = venda.getDataVenda().getMonthValue();
                
                vendasPorClienteMes.putIfAbsent(nomeCliente, new HashMap<>());
                Map<Integer, Double> vendasMes = vendasPorClienteMes.get(nomeCliente);
                vendasMes.put(mes, vendasMes.getOrDefault(mes, 0.0) + venda.getValorTotal());
            }
            
            // Criar tabela para cada cliente
            String[] nomesMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
                                  "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
            
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font clienteFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            
            for (Map.Entry<String, Map<Integer, Double>> clienteEntry : vendasPorClienteMes.entrySet()) {
                String nomeCliente = clienteEntry.getKey();
                Map<Integer, Double> vendasMes = clienteEntry.getValue();
                
                // Nome do cliente
                Paragraph clienteTitle = new Paragraph(nomeCliente, clienteFont);
                clienteTitle.setSpacingBefore(15);
                clienteTitle.setSpacingAfter(10);
                document.add(clienteTitle);
                
                // Tabela do cliente
                PdfPTable table = new PdfPTable(14); // 12 meses + cliente + total
                table.setWidthPercentage(100);
                
                // Cabeçalho
                PdfPCell headerCell = new PdfPCell(new Phrase("Cliente", headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(headerCell);
                
                for (String mes : nomesMeses) {
                    headerCell = new PdfPCell(new Phrase(mes, headerFont));
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(headerCell);
                }
                
                headerCell = new PdfPCell(new Phrase("Total", headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(headerCell);
                
                // Dados do cliente
                table.addCell(new PdfPCell(new Phrase(nomeCliente, normalFont)));
                
                double totalCliente = 0;
                for (int i = 1; i <= 12; i++) {
                    double valor = vendasMes.getOrDefault(i, 0.0);
                    totalCliente += valor;
                    
                    PdfPCell cell = new PdfPCell(new Phrase(valor > 0 ? String.format("%.2f", valor) : "-", normalFont));
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(cell);
                }
                
                PdfPCell totalCell = new PdfPCell(new Phrase(String.format("R$ %.2f", totalCliente), headerFont));
                totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(totalCell);
                
                document.add(table);
            }
            
            document.close();
            return true;
            
        } catch (DocumentException | IOException e) {
            System.err.println("Erro ao gerar relatório de vendas por cliente: " + e.getMessage());
            return false;
        }
    }
    
    public boolean gerarRelatorioGestaoEstoque(String caminhoArquivo) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            
            document.open();
            
            // Título
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Relatório de Gestão de Estoque", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);
            
            // Data de geração
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 10);
            Paragraph dataGeracao = new Paragraph("Gerado em: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), normalFont);
            dataGeracao.setAlignment(Element.ALIGN_RIGHT);
            dataGeracao.setSpacingAfter(20);
            document.add(dataGeracao);
            
            // Buscar todos os produtos
            List<Produto> produtos = produtoDAO.listarTodos();
            
            if (produtos == null || produtos.isEmpty()) {
                Paragraph noData = new Paragraph("Nenhum produto cadastrado.", normalFont);
                noData.setAlignment(Element.ALIGN_CENTER);
                document.add(noData);
                document.close();
                return true;
            }
            
            // Criar tabela
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            
            // Definir larguras das colunas
            float[] columnWidths = {1f, 3f, 1.5f, 1.5f, 1.5f, 2f};
            table.setWidths(columnWidths);
            
            // Cabeçalho da tabela
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            PdfPCell headerCell;
            
            headerCell = new PdfPCell(new Phrase("ID", headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            
            headerCell = new PdfPCell(new Phrase("Produto", headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            
            headerCell = new PdfPCell(new Phrase("Categoria", headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            
            headerCell = new PdfPCell(new Phrase("Estoque", headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            
            headerCell = new PdfPCell(new Phrase("Preço Unit.", headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            
            headerCell = new PdfPCell(new Phrase("Valor Total", headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
            
            // Dados da tabela
            double valorTotalEstoque = 0;
            int totalItens = 0;
            
            for (Produto produto : produtos) {
                // ID
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(produto.getId()), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                
                // Nome do produto
                table.addCell(new PdfPCell(new Phrase(produto.getNome(), normalFont)));
                
                // Categoria
                cell = new PdfPCell(new Phrase(produto.getCategoria() != null ? produto.getCategoria() : "-", normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                
                // Estoque
                cell = new PdfPCell(new Phrase(String.format("%.2f %s", 
                    produto.getQuantidadeEstoque(), 
                    produto.getUnidadeMedida() != null ? produto.getUnidadeMedida() : "un"), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                // Verificar estoque baixo
                if (produto.getQuantidadeEstoque() <= 10) {
                    cell.setBackgroundColor(BaseColor.YELLOW);
                }
                if (produto.getQuantidadeEstoque() <= 5) {
                    cell.setBackgroundColor(BaseColor.ORANGE);
                }
                if (produto.getQuantidadeEstoque() <= 0) {
                    cell.setBackgroundColor(BaseColor.RED);
                }
                
                table.addCell(cell);
                
                // Preço unitário
                cell = new PdfPCell(new Phrase(String.format("R$ %.2f", produto.getPrecoUnitario()), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
                
                // Valor total do produto em estoque
                double valorTotalProduto = produto.getQuantidadeEstoque() * produto.getPrecoUnitario();
                cell = new PdfPCell(new Phrase(String.format("R$ %.2f", valorTotalProduto), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
                
                valorTotalEstoque += valorTotalProduto;
                totalItens++;
            }
            
            // Linha de total
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            PdfPCell totalCell = new PdfPCell(new Phrase("TOTAL", boldFont));
            totalCell.setColspan(2);
            totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell);
            
            totalCell = new PdfPCell(new Phrase(totalItens + " itens", boldFont));
            totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell);
            
            totalCell = new PdfPCell(new Phrase("-", boldFont));
            totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell);
            
            totalCell = new PdfPCell(new Phrase("-", boldFont));
            totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell);
            
            totalCell = new PdfPCell(new Phrase(String.format("R$ %.2f", valorTotalEstoque), boldFont));
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(totalCell);
            
            document.add(table);
            
            // Legenda para cores do estoque
            Paragraph legenda = new Paragraph("\nLegenda:", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
            legenda.setSpacingBefore(20);
            document.add(legenda);
            
            Paragraph legendaTexto = new Paragraph(
                "• Estoque normal: sem cor\n" +
                "• Estoque baixo (≤ 10): amarelo\n" +
                "• Estoque crítico (≤ 5): laranja\n" +
                "• Sem estoque (0): vermelho", normalFont);
            document.add(legendaTexto);
            
            document.close();
            return true;
            
        } catch (DocumentException | IOException e) {
            System.err.println("Erro ao gerar relatório de gestão de estoque: " + e.getMessage());
            return false;
        }
    }
}
