package remote;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * metodi del client verso il server
 * */

public interface ClientCallbackRemote extends Remote {
	public void aggiornaStatoClient(DatiPartita dp) throws RemoteException;
	public void assegnaControllore(int numeroControllore) throws RemoteException;
	public void comunicaServerOccupato() throws RemoteException;
}
