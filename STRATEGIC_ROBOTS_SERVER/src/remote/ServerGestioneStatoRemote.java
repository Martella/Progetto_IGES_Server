package remote;
import java.rmi.Remote;
import java.rmi.RemoteException;

import robot.Robot;

/*
 * registrazione del server verso il client
 * */

public interface ServerGestioneStatoRemote extends Remote {
	public void aggiornaStatoServer(DatiPartita dp) throws RemoteException;
}
