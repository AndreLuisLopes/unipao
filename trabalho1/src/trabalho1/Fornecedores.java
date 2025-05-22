package trabalho1;

class Fornecedor {
    private String nomeEmpresa;
    private String cnpj;
    private String telefone;
    private String tipoProduto;

    public Fornecedor(String nomeEmpresa, String cnpj, String telefone, String tipoProduto) {
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.tipoProduto = tipoProduto;
    }

    public String getCnpj() {
        return cnpj;
    }

    @Override
    public String toString() {
        return "Empresa: " + nomeEmpresa + ", CNPJ: " + cnpj + ", Telefone: " + telefone + ", Tipo de Produto: " + tipoProduto;
    }
}
