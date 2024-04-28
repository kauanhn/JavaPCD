class Funcionario extends Thread {
    private Conta salarioLoja;
    private Conta investimentos;
    private int id;

    public Funcionario(Conta salarioLoja, Conta investimentos, int id) {
        this.salarioLoja = salarioLoja;
        this.investimentos = investimentos;
        this.id = id;
    }

    public void run() {
        synchronized (salarioLoja) {
            salarioLoja.depositar(1400);
            System.out.println("Funcionário " + id + " recebeu o salário de R$ 1400,00");
            double valorInvestimento = 0.20 * 1400;
            investimentos.depositar(valorInvestimento);
            System.out.println("Funcionário " + id + " investiu R$ " + valorInvestimento + " em sua conta de investimentos");
            System.out.println("Saldo da conta de investimentos do funcionário " + id + ": R$ " + investimentos.getSaldo());
        }
    }

    public double getSalarioFinal() {
        return salarioLoja.getSaldo();
    }

    public double getInvestimentos() {
        return investimentos.getSaldo();
    }
}