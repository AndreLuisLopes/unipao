package trabalho1;

class Cliente extends Pessoa {
    private String endereco;
    private String dataCadastro;
    
    public Cliente(String nome, String cpf, String telefone, String endereco, String dataCadastro) {
        super(nome, cpf, telefone);
        this.endereco = endereco;
        this.dataCadastro = dataCadastro;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Endereço: " + endereco + ", Data de Cadastro: " + dataCadastro;
    }
}
