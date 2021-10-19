package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProvaInterfaccia extends Remote {
	public String dammiCiao(String daChi) throws RemoteException;
}
