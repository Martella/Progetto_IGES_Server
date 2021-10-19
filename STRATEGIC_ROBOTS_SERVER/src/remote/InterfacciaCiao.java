package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfacciaCiao extends Remote {
	public String dammiCiao(String daChi) throws RemoteException;
}
