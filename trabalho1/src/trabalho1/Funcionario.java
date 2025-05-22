package trabalho1;

class Funcionario extends Pessoa {
    private String cargo;
    private double salario;
    private String dataContratacao;

    public Funcionario(String nome, String cpf, String telefone, String cargo, double salario, String dataContratacao) {
        super(nome, cpf, telefone);
        this.cargo = cargo;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if (salario < 0) {
            System.out.println("Erro: Salário não pode ser negativo.");
        } else {
            this.salario = salario;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Cargo: " + cargo + ", Salário: R$" + salario + ", Data de Contratação: " + dataContratacao;
    }
}
