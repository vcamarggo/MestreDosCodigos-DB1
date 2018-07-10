package architecture;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by vinicius.camargo on 10/07/2018
 */
public class Client2 {

    private static int PID = 258963147;

    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        Registry registry = LocateRegistry.getRegistry();
        Server server = (Server) registry.lookup("DbServer");


        final int from = 1;
        final int to = 2;
        while (!server.canTransfer(from, to)) {
            System.out.println("Desculpe, o recurso desejado ainda encontra-se ocupado");
            Thread.sleep(3000);
        }
        server.willTransfer(from, to, PID);
        server.withdraw(from, 500);
        Thread.sleep(5000L);
        server.deposit(to, 500);
        server.printAccounts();
        server.transferEnded(from, to, PID);
    }
}
