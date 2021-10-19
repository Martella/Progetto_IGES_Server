package remote;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Timer;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JFrame;

import controllo.ControlloreInterattivo;
import elementiScenario.Scenario;
import robot.Robot;
import robot.RobotCombattente;
import robot.RobotLavoratore;

public class Server extends UnicastRemoteObject implements ServerCallbackRemote, ServerGestioneStatoRemote{

	
	private Vector <ClientCallbackRemote> partita;
	private DatiPartita datiPartita;

	
	protected Server() throws RemoteException {
		super();
		partita = new Vector <ClientCallbackRemote> ();
		datiPartita = null;
	}

	private static Logger log;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		log = Logger.getLogger("log");
		
		try{
			
			log.info("Creo l'oggetto remoto...");
			Server server = new Server();
			
			log.info("ora ne effettuo il rebind");
			Naming.rebind("ServerRobot", server);
			
			log.info("Server avviato...");
			
			server.nuovaPartita();

		
		}catch (RemoteException e){
			log.info("Problemi con oggetti remoti: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e){
			log.info("C'è qualche altro problema " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public synchronized void registerForCallBack(ClientCallbackRemote cl) throws RemoteException {
		
		if (partita.size() < 2) {
			partita.add(cl);
			log.info("Aggiungo il client N°" + partita.size());
			
			
			if (partita.size() == 2) {
				//cl.startNuovaPartita("classic", "controGiocatore", 6);
				log.info("Partita iniziata");
				cl.assegnaControllore(2);
				cl.aggiornaStatoClient(datiPartita);
				
				
				ClientCallbackRemote cl0 = (ClientCallbackRemote) partita.elementAt(0);
				cl0.assegnaControllore(1);
				cl0.aggiornaStatoClient(datiPartita);
				//datiPartita = cl.dammiStatoClient();*/
			}
		}
		else log.info("Partita già in corso"); 
	}

	@Override
	public synchronized void unregisterCallBack(ClientCallbackRemote cl) throws RemoteException {
		// TODO Auto-generated method stub
		partita.remove(cl);
		log.info("Elimino un client");
		
		if(partita.size() == 0) {
			//nel caso in cui si collegano nuovi client
			nuovaPartita();
		}
	}

	@Override
	public synchronized void aggiornaStatoServer(DatiPartita dp) throws RemoteException {
		// TODO Auto-generated method stub
		datiPartita = dp;
		
		//System.out.println("dimensione" + partita.size());
		for (int i = 0; i < partita.size(); i++) {
			System.out.println("entra " + i);
			ClientCallbackRemote cl = (ClientCallbackRemote) partita.elementAt(i);
			cl.aggiornaStatoClient(datiPartita);
			
		}
	}

	
	public void nuovaPartita() {
		String tipoScenario = "classic";
		String modalitàPartita = "controGiocatore";
		int numRobots = 4;
		JFrame frame = new JFrame(); 
		
		
		Scenario scenario = new Scenario(tipoScenario);
		ControlloreInterattivo controlloreGiocatore1  = new ControlloreInterattivo(scenario, frame);
		ControlloreInterattivo controlloreGiocatore2  = new ControlloreInterattivo(scenario, frame);
		
		
		for(int i = 1; i <= numRobots/2; i++) controlloreGiocatore1.aggiungiRobot(new RobotCombattente(Color.ORANGE));
		for(int i = 1; i <= numRobots/2; i++) controlloreGiocatore1.aggiungiRobot(new RobotLavoratore(Color.ORANGE));
		controlloreGiocatore1.start(scenario.getArrayListOstacoli(), scenario.getArrayListBancoRifornimenti());

		
		if (modalitàPartita.equals("controGiocatore")){
			for(int i = 1; i <= numRobots/2; i++) controlloreGiocatore2.aggiungiRobot(new RobotCombattente(Color.GREEN));
			for(int i = 1; i <= numRobots/2; i++) controlloreGiocatore2.aggiungiRobot(new RobotLavoratore(Color.GREEN));
			controlloreGiocatore2.start(scenario.getArrayListOstacoli(), scenario.getArrayListBancoRifornimenti());
		}

		
		//se la dichiaravo come varabile non me la faceva modificare nell'evento
		int [] numeroMossa = new int [1];
		numeroMossa[0] = 0;
		
		Random mossaRandom = new Random();
		
		if(mossaRandom.nextInt(2)== 0)controlloreGiocatore1.attiva();
		else if (modalitàPartita.equals("controGiocatore")) controlloreGiocatore2.attiva();

		datiPartita = new DatiPartita();
		datiPartita.modificaModalitàPartita(modalitàPartita);
		datiPartita.modificaNumeroMossa(numeroMossa);
		datiPartita.modificaScenario(scenario);
		datiPartita.modificaControlloreInterattivo1(controlloreGiocatore1);
		datiPartita.modificaControlloreInterattivo2(controlloreGiocatore2);
	}

}
