package trabalho1;

class Produto {
    private String codigo;
    private String nome;
    private double preco;
    private int estoque;
    private String categoria;

    public Produto(String codigo, String nome, double preco, int estoque, String categoria) {
        this.codigo = codigo;
        this.nome = nome;
        setPreco(preco);  // Usando o setter para validar preço
        setEstoque(estoque);  // Usando o setter para validar estoque
        this.categoria = categoria;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            System.out.println("Erro: Preço não pode ser negativo.");
        } else {
            this.preco = preco;
        }
    }

    public void setEstoque(int estoque) {
        if (estoque < 0) {
            System.out.println("Erro: Estoque não pode ser negativo.");
        } else {
            this.estoque = estoque;
        }
    }

    public int getEstoque() {
        return estoque;
    }

    public void reduzirEstoque(int quantidade) {
        if (quantidade > 0 && quantidade <= estoque) {
            estoque -= quantidade;
        } else {
            System.out.println("Erro: Quantidade inválida!");
        }
    }

    public double getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }
    
    public String getCodigo() {
        return nome;
    }
    
    @Override
    public String toString() {
        return "Código: " + codigo + ", Nome: " + nome + ", Preço: R$" + preco + ", Estoque: " + estoque + ", Categoria: " + categoria;
    }
}
