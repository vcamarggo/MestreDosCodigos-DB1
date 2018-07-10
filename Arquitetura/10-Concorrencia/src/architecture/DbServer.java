package architecture;

import model.Account;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by vinicius.camargo on 10/07/2018
 */
public class DbServer extends UnicastRemoteObject implements Server {

    private HashMap<Integer, Account> accounts = new HashMap<>();
    private HashMap<Integer, Client> lockedAccounts = new HashMap<>();
    private LinkedList<Client> lockQueue = new LinkedList<>();


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
        List<Integer> elements = Arrays.asList(1, 2, 3, 4, 5);
        elements.forEach(id -> accounts.put(id, new Account()));
        printAccounts(elements);
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
    public void printAccounts(List ids) throws RemoteException {
        accounts.forEach((id, account) -> {
            if (ids.contains(id)) {
                System.out.println("Conta: " + id + " possui saldo de R$" + account.printBalance());
            }
        });
        System.out.println();
    }

    @Override
    public void willTransfer(int from, int to, Client client) throws RemoteException {
        lockedAccounts.put(from, client);
        lockedAccounts.put(to, client);
    }

    @Override
    public void transferEnded(int from, int to, Client client) throws RemoteException, InterruptedException {
        lockedAccounts.remove(from, client);
        lockedAccounts.remove(to, client);
        if (!lockQueue.isEmpty()) {
            lockQueue.stream().filter(cli -> lockedAccounts.containsValue(cli)).findFirst().ifPresent(cli -> {
                try {
                    lockQueue.remove(cli);
                    cli.doOperation();
                } catch (InterruptedException | RemoteException e) {
                    e.printStackTrace();
                }
            });
//            lockQueue.poll().doOperation();
        }
    }

    @Override
    public void wantLock(int from, int to, Client client) throws RemoteException, InterruptedException {
        if ((lockedAccounts.get(from) == null && lockedAccounts.get(from) == null) || lockQueue.isEmpty()) {
            client.doOperation();
        } else {
            lockQueue.offer(client);
        }
    }

}
