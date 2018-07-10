package architecture;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;

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
        server.willTransfer(from, to, this);
        server.withdraw(from, 200);
        Thread.sleep(5000L); // Simulação de um processo com delay para exigir enfileiramento
        server.deposit(to, 200);
        System.out.println("PID do cliente que realizou a operação: " + PID);
        server.printAccounts();
        server.transferEnded(from, to, this);
    }

}
