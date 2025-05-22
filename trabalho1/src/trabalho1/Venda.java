package trabalho1;

import java.util.*;

class Venda {
    private String dataVenda;
    private Cliente cliente;
    private Funcionario funcionario;
    private Map<Produto, Integer> itensVendidos;
    private double valorTotal;
    private String formaPagamento;

    private static final List<String> FORMAS_PAGAMENTO_VALIDAS = Arrays.asList("Dinheiro", "Cartão de Crédito");

    public Venda(String dataVenda, Cliente cliente, Funcionario funcionario, String formaPagamento) {
        this.dataVenda = dataVenda;
        this.cliente = cliente;
        this.funcionario = funcionario;
        setFormaPagamento(formaPagamento);
        this.itensVendidos = new HashMap<>();
        this.valorTotal = 0;
    }

    public void setFormaPagamento(String formaPagamento) {
        if (FORMAS_PAGAMENTO_VALIDAS.contains(formaPagamento)) {
            this.formaPagamento = formaPagamento;
        } else {
            System.out.println("Erro: Forma de pagamento inválida. Use 'Dinheiro' ou 'Cartão de Crédito'.");
        }
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        if (produto != null && quantidade > 0 && produto.getEstoque() >= quantidade) {
            itensVendidos.put(produto, quantidade);
            produto.reduzirEstoque(quantidade);
            valorTotal += produto.getPreco() * quantidade;
        } else {
            System.out.println("Erro: Estoque insuficiente ou quantidade inválida!");
        }
    }

    @Override
    public String toString() {
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("Data: ").append(dataVenda).append("\n");
        detalhes.append("Cliente: ").append(cliente != null ? cliente.getNome() : "Não informado").append("\n");
        detalhes.append("Funcionário: ").append(funcionario.getNome()).append("\n");
        detalhes.append("Forma de Pagamento: ").append(formaPagamento).append("\n");
        detalhes.append("Itens Vendidos:\n");
        for (Map.Entry<Produto, Integer> entry : itensVendidos.entrySet()) {
            detalhes.append(entry.getKey().getNome()).append(" - Quantidade: ").append(entry.getValue()).append("\n");
        }
        detalhes.append("Total: R$").append(valorTotal);
        return detalhes.toString();
    }
}