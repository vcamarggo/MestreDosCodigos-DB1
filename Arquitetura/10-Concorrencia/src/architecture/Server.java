package architecture;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void withdraw(Integer clientId, Integer amount) throws RemoteException;

    void deposit(Integer clientId, Integer amount) throws RemoteException;

    void applyTax(Integer clientId, Double tax) throws RemoteException;

    void printAccounts() throws RemoteException;

    void willTransfer(int from, int to, int ownerPID) throws RemoteException;

    void transferEnded(int from, int to, int ownerPID) throws RemoteException;

    boolean canTransfer(int from, int to) throws RemoteException;
}