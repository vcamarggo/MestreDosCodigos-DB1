package architecture;

import model.Account;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 * Created by vinicius.camargo on 10/07/2018
 */
public class DbServer extends UnicastRemoteObject implements Server {

    private static HashMap<Integer, Account> accounts = new HashMap<>();
    private static HashMap<Integer, Integer> lockedAccounts = new HashMap<>();

    private DbServer() throws RemoteException {
        super();
        initializeDatabase();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("DbServer", this);
    }

    public static void main(String args[]) throws RemoteException {
        new DbServer();
    }

    private void initializeDatabase() throws RemoteException {
        for (int id = 1; id < 3; id++) {
            accounts.put(id, new Account(1000));
        }
        printAccounts();
    }

    @Override
    public void withdraw(Integer clientId, Integer amount) throws RemoteException {
        accounts.get(clientId).withdraw(amount);
    }

    @Override
    public void deposit(Integer clientId, Integer amount) throws RemoteException {
        accounts.get(clientId).deposit(amount);
    }

    @Override
    public void applyTax(Integer clientId, Double tax) throws RemoteException {
        accounts.get(clientId).applyTax(tax);
    }

    @Override
    public void printAccounts() throws RemoteException {
        accounts.forEach((id, account) -> System.out.println("Conta: " + id + " possui saldo de R$" + account.printBalance()));
        System.out.println();
    }

    @Override
    public void willTransfer(int from, int to, int ownerPID) throws RemoteException {
        lockedAccounts.put(from, ownerPID);
        lockedAccounts.put(to, ownerPID);
    }

    @Override
    public void transferEnded(int from, int to, int ownerPID) throws RemoteException {
        lockedAccounts.remove(from, ownerPID);
        lockedAccounts.remove(to, ownerPID);
    }

    @Override
    public boolean canTransfer(int from, int to) throws RemoteException {
        return lockedAccounts.get(from) == null && lockedAccounts.get(from) == null;
    }
}
