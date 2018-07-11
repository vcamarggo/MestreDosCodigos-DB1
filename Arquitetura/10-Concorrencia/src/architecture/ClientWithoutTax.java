package architecture;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by vinicius.camargo on 10/07/2018
 */
public class ClientWithoutTax extends Client {


    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        new ClientWithoutTax();
    }

    private ClientWithoutTax() throws InterruptedException, RemoteException, NotBoundException {
        super(new Random().nextInt(4) + 1, new Random().nextInt(4) + 1);
    }

    public void doOperation() throws InterruptedException, RemoteException {
        server.willTransfer(senderAccount, recipientAccount, this);
        server.withdraw(senderAccount, 200);
        Thread.sleep(4000L); // Simulação de um processo com delay para exigir enfileiramento
        server.deposit(recipientAccount, 200);
        System.out.println("PID do cliente que realizou a operacao: " + PID);
        System.out.println("Transferiu: 200 de " + senderAccount + " para " + recipientAccount);
        server.printAccounts(Arrays.asList(recipientAccount, senderAccount)); // Nao printa tudo pois teria que ter pedido lock de todas as variaveis
        server.transferEnded(senderAccount, recipientAccount, this);
    }

}
