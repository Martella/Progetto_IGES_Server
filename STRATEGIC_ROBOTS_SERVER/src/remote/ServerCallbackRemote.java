package remote;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * registrazione del server verso il client
 * */

public interface ServerCallbackRemote extends Remote {
	public void registerForCallBack(ClientCallbackRemote cl) throws RemoteException;
	public void unregisterCallBack(ClientCallbackRemote cl) throws RemoteException;
}
