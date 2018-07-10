package architecture;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote {
    void withdraw(Integer clientId, Integer amount) throws RemoteException;

    void deposit(Integer clientId, Integer amount) throws RemoteException;

    void applyTax(Integer clientId, Double tax) throws RemoteException;

    void printAccounts(List ids) throws RemoteException;

    void willTransfer(int from, int to, Client client) throws RemoteException;

    void transferEnded(int from, int to, Client client) throws RemoteException, InterruptedException;

    void wantLock(int from, int to, Client client) throws RemoteException, InterruptedException;

}