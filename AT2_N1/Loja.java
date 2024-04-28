class Loja {
    private Conta conta;
    private Funcionario[] funcionarios;

    public Loja(Conta conta, Funcionario[] funcionarios) {
        this.conta = conta;
        this.funcionarios = funcionarios;
    }

    public void pagarSalarios() {
        double totalSalarios = funcionarios.length * 1400;
        synchronized (conta) {
            if (conta.sacar(totalSalarios)) {
                System.out.println("Loja pagou salários dos funcionários no valor total de R$ " + totalSalarios);
            }
        }
    }

    public double getSaldoConta() {
        return conta.getSaldo();
    }
}