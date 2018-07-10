package architecture;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * Created by vinicius.camargo on 10/07/2018
 */
public class ClientWithoutTax extends Client {


    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        new ClientWithoutTax();
    }

    private ClientWithoutTax() throws InterruptedException, RemoteException, NotBoundException {
        super(3, 4);
    }

    public void doOperation() throws InterruptedException, RemoteException {
        server.willTransfer(senderAccount, recipientAccount, this);
        server.withdraw(senderAccount, 200);
        Thread.sleep(9000L); // Simulação de um processo com delay para exigir enfileiramento
        server.deposit(recipientAccount, 200);
        System.out.println("PID do cliente que realizou a operação: " + PID);
        server.printAccounts(Arrays.asList(recipientAccount, senderAccount)); // Nao printa tudo pois teria que ter pedido lock de todas as variaveis
        server.transferEnded(senderAccount, recipientAccount, this);
    }

}
