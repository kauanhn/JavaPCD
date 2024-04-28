class Cliente extends Thread {
    private Conta conta;
    private int id;

    public Cliente(Conta conta, int id) {
        this.conta = conta;
        this.id = id;
    }

    public void run() {
        while (true) {
            double valorCompra = Math.random() < 0.5 ? 100 : 200;
            synchronized (conta) {
                if (conta.sacar(valorCompra)) {
                    System.out.println("Cliente " + id + " realizou uma compra de R$ " + valorCompra);
                    System.out.println("Saldo da conta do cliente " + id + ": R$ " + conta.getSaldo());
                } else {
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}