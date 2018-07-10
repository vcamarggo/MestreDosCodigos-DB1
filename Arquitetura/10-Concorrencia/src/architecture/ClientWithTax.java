package architecture;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

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
        server.willTransfer(from, to, this);
        server.applyTax(to, 0.10);
        server.withdraw(from, 400);
        server.deposit(to, 400);
        System.out.println("PID do cliente que realizou a operação: " + PID);
        server.printAccounts();
        server.transferEnded(from, to, this);
    }
}
