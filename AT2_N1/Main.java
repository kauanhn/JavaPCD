import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Conta[] contasClientes = new Conta[5];
        Cliente[] clientes = new Cliente[5];
        Conta[] contasLojas = new Conta[2];
        Funcionario[][] funcionarios = new Funcionario[2][2];
        Conta[][] contasInvestimentos = new Conta[2][2];

        for (int i = 0; i < contasClientes.length; i++) {
            contasClientes[i] = new Conta(1000);
            clientes[i] = new Cliente(contasClientes[i], i + 1);
            clientes[i].start();
        }

        for (int i = 0; i < contasLojas.length; i++) {
            contasLojas[i] = new Conta(0);
            contasInvestimentos[i] = new Conta[2];
            for (int j = 0; j < funcionarios[i].length; j++) {
                contasInvestimentos[i][j] = new Conta(0);
                funcionarios[i][j] = new Funcionario(contasLojas[i], contasInvestimentos[i][j], (i * 2) + j + 1);
                funcionarios[i][j].start();
            }
        }

        Loja[] lojas = new Loja[2];
        lojas[0] = new Loja(contasLojas[0], funcionarios[0]);
        lojas[1] = new Loja(contasLojas[1], funcionarios[1]);

        Banco banco = new Banco();
        ExecutorService executor = Executors.newFixedThreadPool(7);

        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Loja loja : lojas) {
                    loja.pagarSalarios();
                }
            }
        });

        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Conta conta : contasLojas) {
                    double valorTransferencia = Math.random() < 0.5 ? 1000 : 2000;
                    Conta contaDestino = contasClientes[(int) (Math.random() * contasClientes.length)];
                    banco.transferir(conta, contaDestino, valorTransferencia);
                }
            }
        });

        executor.shutdown();

        // Aguardar a finalização de todas as threads
        while (!executor.isTerminated()) {}

        // Exibir saldos finais
        System.out.println("\nSaldo final das contas das lojas:");
        for (int i = 0; i < lojas.length; i++) {
            System.out.println("Loja " + (i + 1) + ": R$ " + lojas[i].getSaldoConta());
        }

        System.out.println("\nSaldo final dos salários dos funcionários:");
        for (int i = 0; i < funcionarios.length; i++) {
            for (int j = 0; j < funcionarios[i].length; j++) {
                System.out.println("Funcionário " + ((i * 2) + j + 1) + ": R$ " + funcionarios[i][j].getSalarioFinal());
            }
        }

        System.out.println("\nSaldo final dos investimentos dos funcionários:");
        for (int i = 0; i < contasInvestimentos.length; i++) {
            for (int j = 0; j < contasInvestimentos[i].length; j++) {
                System.out.println("Funcionário " + ((i * 2) + j + 1) + ": R$ " + contasInvestimentos[i][j].getSaldo());
            }
        }

        System.out.println("\nSaldo final das contas dos clientes:");
        for (int i = 0; i < contasClientes.length; i++) {
            System.out.println("Cliente " + (i + 1) + ": R$ " + contasClientes[i].getSaldo());
        }
    }
}