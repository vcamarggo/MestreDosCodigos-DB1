package architecture;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Objects;
import java.util.Random;

/**
 * Created by vinicius.camargo on 10/07/2018
 */
abstract class Client implements Serializable {
    final int PID;
    Server server;
    final int from;
    final int to;

    abstract void doOperation() throws InterruptedException, RemoteException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return PID == client.PID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(PID);
    }

    public Client(int from, int to) throws RemoteException, InterruptedException, NotBoundException {
        this.from = from;
        this.to = to;
        this.PID = new Random().nextInt(999);
        System.out.println("PID gerado foi: " + PID);
        server = (Server) LocateRegistry.getRegistry().lookup("DbServer");
        server.wantLock(from, to, this);
    }
}
