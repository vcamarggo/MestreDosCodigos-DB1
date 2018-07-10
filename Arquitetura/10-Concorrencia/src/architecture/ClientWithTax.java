package architecture;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * Created by vinicius.camargo on 10/07/2018
 */
public class ClientWithTax extends Client {


    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        new ClientWithTax();
    }

    private ClientWithTax() throws RemoteException, NotBoundException, InterruptedException {
        super(1, 2);
    }

    public void doOperation() throws InterruptedException, RemoteException {
        server.willTransfer(senderAccount, recipientAccount, this);
        server.applyTax(recipientAccount, 0.00);
        server.withdraw(senderAccount, 0);
        server.deposit(recipientAccount, 0);
        System.out.println("PID do cliente que realizou a operação: " + PID);
        server.printAccounts(Arrays.asList(recipientAccount, senderAccount));// Nao printa tudo pois teria que ter pedido lock de todas as variaveis
        server.transferEnded(senderAccount, recipientAccount, this);
    }
}
