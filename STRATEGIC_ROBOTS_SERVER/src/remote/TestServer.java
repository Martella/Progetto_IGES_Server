package remote;

import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;


public class TestServer extends UnicastRemoteObject implements InterfacciaCiao{

	public TestServer() throws RemoteException {
		//sempre vuoto
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		//System.setSecurityManager(new RMISecurityManager());
		
		Logger log = Logger.getLogger("log");
		
		try{
			
			log.info("Creo l'oggetto remoto...");
			TestServer server = new TestServer();
			
			log.info("ora ne effettuo il rebind");
			Naming.rebind("CiaoServer", server);
			
			log.info("Server avviato...");

		
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public String dammiCiao(String daChi) throws RemoteException {
		return "Ciao, ti saluta" + daChi;
	}

}
