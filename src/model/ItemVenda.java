package model;

public class ItemVenda {
    private int id;
    private int vendaId;
    private Produto produto;
    private double quantidade;
    private double precoUnitario;
    private double subtotal;
    
    public ItemVenda() {
    }
    
    public ItemVenda(Produto produto, double quantidade, double precoUnitario, double subtotal) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
    }
    
    public ItemVenda(int id, int vendaId, Produto produto, double quantidade, 
                    double precoUnitario, double subtotal) {
        this.id = id;
        this.vendaId = vendaId;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getVendaId() {
        return vendaId;
    }
    
    public void setVendaId(int vendaId) {
        this.vendaId = vendaId;
    }
    
    public Produto getProduto() {
        return produto;
    }
    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public double getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal();
    }
    
    public double getPrecoUnitario() {
        return precoUnitario;
    }
    
    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
        calcularSubtotal();
    }
    
    public double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    private void calcularSubtotal() {
        this.subtotal = this.quantidade * this.precoUnitario;
    }
    
    @Override
    public String toString() {
        return "ItemVenda{" +
                "produto=" + produto.getNome() +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}