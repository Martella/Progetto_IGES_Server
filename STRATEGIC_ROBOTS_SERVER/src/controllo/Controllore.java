package controllo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import elementiScenario.Scenario;
import frame.MessaggioFrame;
import interfacce.Disegnabile;
import interfacce.Energetico;
import robot.Robot;
import robot.RobotCombattente;
import robot.RobotLavoratore;

public class Controllore implements Serializable{
	
	private ArrayList<RobotCombattente> robotCombattenteArray;
	private ArrayList<RobotLavoratore> robotLavoratoreArray;
	private Scenario scenario;
	private String [][] griglia;
	private String tipo;
	private Robot rob;
	private boolean attivo;
	private static ArrayList<RobotLavoratore> insiemeRobotLavoratoreArray = new ArrayList<RobotLavoratore>();;
	private static ArrayList<RobotCombattente> insiemeRobotCombattenteArray = new ArrayList<RobotCombattente>();;
	private ArrayList<RobotLavoratore> insiemeRobotLavoratoreArray2 ;
	private ArrayList<RobotCombattente> insiemeRobotCombattenteArray2;
	private boolean mossa;
	private JFrame frame;
	
	/**
	 * 
	 * @param s scenario su cui controllare i Robot
	 * @param f frame dello scenario
	 */
	public Controllore(Scenario s, JFrame f){
		frame = f;
		scenario = s;
		robotCombattenteArray = new ArrayList<RobotCombattente>();
		robotLavoratoreArray = new ArrayList<RobotLavoratore>();
		attivo = false;
		insiemeRobotLavoratoreArray2 = insiemeRobotLavoratoreArray;
		insiemeRobotCombattenteArray2 = insiemeRobotCombattenteArray;
	}
	
	/**
	 * Inizia a controllare un Robot
	 */
	public void start(){
		
		Random i = new Random();
		
		Robot robScelto = new Robot();
		
		if(i.nextInt(2) == 0){
			tipo = "robotCombattente";
			robScelto = robotCombattenteArray.get(i.nextInt(robotCombattenteArray.size()));
		}
		
		else {
			tipo = "robotLavoratore";
			robScelto = robotLavoratoreArray.get(i.nextInt(robotLavoratoreArray.size()));
		}

		rob = robScelto;				
		
	}
	

	/**
	 * contralla un altro Robot scelto random
	 */
	public void cambiaRobot(){
		Random i = new Random();
		
		Robot robScelto = new Robot();
		
		boolean trovato = false;
		int indice;
		
		aggiornaArrayRobotCombattenti();
		if(robotCombattenteArray.size() == 0){
			MessaggioFrame messaggio = new MessaggioFrame("Partita terminata");
			messaggio.setVisible(true);
			frame.dispose();
		}
		else{
			
			while(!trovato && (robotCombattenteArray.size()>0 || robotLavoratoreArray.size()>0)){
				if(i.nextInt(2) == 0){
					tipo = "robotCombattente";
					
					while(!trovato && robotCombattenteArray.size()>0){
						indice = i.nextInt(robotCombattenteArray.size());
						
						for(int j= 0; j < insiemeRobotCombattenteArray.size(); j++){
							if( (robotCombattenteArray.get(indice)).getX() == (insiemeRobotCombattenteArray.get(j)).getX() && (robotCombattenteArray.get(indice)).getY() == (insiemeRobotCombattenteArray.get(j)).getY()){
								robScelto = robotCombattenteArray.get(indice);
								trovato = true;
							}
						}
						
						if(!trovato) robotCombattenteArray.remove(indice);
					}
				}
				
				if(! trovato) {
					tipo = "robotLavoratore";
					
					while(!trovato && robotLavoratoreArray.size()>0){
						indice = i.nextInt(robotLavoratoreArray.size());
						
						for(int j= 0; j < insiemeRobotLavoratoreArray.size(); j++){
							if( (robotLavoratoreArray.get(indice)).getX() == (insiemeRobotLavoratoreArray.get(j)).getX() && (robotLavoratoreArray.get(indice)).getY() == (insiemeRobotLavoratoreArray.get(j)).getY()){
								robScelto = robotLavoratoreArray.get(indice);
								trovato = true;
							}
						}
						
						if(!trovato) robotLavoratoreArray.remove(indice);
					}
				}
		
				rob = robScelto;
			}
		}

		if(robotCombattenteArray.size() == 0){
			MessaggioFrame messaggio = new MessaggioFrame("Partita terminata");
			messaggio.setVisible(true);
			frame.dispose();
		}

	}
	
	/**
	 * Aggiunge un Robot al Controllore e viene disegnato nello scenario in una posizione casuale
	 * @param r Robot da aggiungere
	 */
	public void aggiungiRobot(Robot r){

		Disegnabile oggetto = r;

		
		griglia = new String [scenario.getMaxRighe()][scenario.getMaxColonne()];
		griglia = scenario.getGriglia();
		
		Random random = new Random();
		
		int rig = random.nextInt(scenario.getMaxRighe());
		int col = random.nextInt(scenario.getMaxColonne());		
		
		while( ! griglia[rig][col].equals("null")){
			rig = random.nextInt(scenario.getMaxRighe());
			col = random.nextInt(scenario.getMaxColonne());		
		}
		
		
		oggetto.modificaX(rig);
		oggetto.modificaY(col);

		scenario.modificaGrigliaAggiungiOggetto(oggetto, oggetto.getTipo());
		
		scenario.modificaGrigliaAggiungiEnergia((Energetico)oggetto);
		
		if ((oggetto.getTipo()).equals("robotLavoratore")){
			robotLavoratoreArray.add((RobotLavoratore) oggetto);
			insiemeRobotLavoratoreArray.add((RobotLavoratore) oggetto);
		}
		if ((oggetto.getTipo()).equals("robotCombattente")){
			robotCombattenteArray.add((RobotCombattente) oggetto);
			insiemeRobotCombattenteArray.add((RobotCombattente) oggetto);
		}


	}
	
	/**
	 * Attiva il Controllore e viene selezionato il Robot Controllato.
	 */
	public void attiva(){
		attivo = true;
		mossa = true;
		scenario.modificaGrigliaSelezionaCasella(rob.getX(), rob.getY());
	}
	
	/**
	 * Disattiva il Controllore e viene deselezionato il Robot Controllato.
	 */
	public void disattiva(){
		attivo = false;
		scenario.modificaGrigliaDeSelezionaCasella(rob.getX(), rob.getY());
	}
	
	/**
	 * Modifica lo stato della mossa, se è true il Robot controllare può effettuare una mossa altrimenti no.
	 * @param stato della mossa
	 */
	public void modificaStatoMossa(boolean b){
		mossa = b;
	}

	public boolean getStatoMossa(){
		return mossa;
	}
	
	
	public boolean getStato(){
		return attivo;
	}
			
	public Robot getRobotControllato(){
		return rob;
	}
	
	public String getTipoRobotControllato() {
		return tipo;
	}

	public ArrayList<RobotCombattente> getRobotCombattenteArray(){
		return robotCombattenteArray;
	}
	
	public ArrayList<RobotLavoratore> getRobotLavoratoreeArray(){
		return robotLavoratoreArray;
	}

	public ArrayList<RobotCombattente> getInsiemeRobotCombattenteArray(){
		return insiemeRobotCombattenteArray;
	}
	
	public ArrayList<RobotLavoratore> getInsiemeRobotLavoratoreeArray(){
		return insiemeRobotLavoratoreArray;
	}
	
	/**
	 * Il controllore controlla i Robot dello stato salvato
	 */
	public void aggiornaRobot(){
		insiemeRobotCombattenteArray = insiemeRobotCombattenteArray2;
		insiemeRobotLavoratoreArray = insiemeRobotLavoratoreArray2;
	}
	
	public void aggiornaArrayRobotCombattenti() {
		
		for (int i = 0; i < robotCombattenteArray.size(); i++) {
			Boolean trovato = false;
			for(int j = 0; j < insiemeRobotCombattenteArray.size(); j++) {
				if( (robotCombattenteArray.get(i)).getX() == (insiemeRobotCombattenteArray.get(j)).getX() && (robotCombattenteArray.get(i)).getY() == (insiemeRobotCombattenteArray.get(j)).getY()){
					trovato = true;
				}
			}
			if(!trovato) robotCombattenteArray.remove(i);
		}
		
		/*for(int j = 0; j < insiemeRobotCombattenteArray.size(); j++){
			if( (robotCombattenteArray.get(indice)).getX() == (insiemeRobotCombattenteArray.get(j)).getX() && (robotCombattenteArray.get(indice)).getY() == (insiemeRobotCombattenteArray.get(j)).getY()){
				robScelto = robotCombattenteArray.get(indice);
				trovato = true;
			}
		}
		
		if(!trovato) robotCombattenteArray.remove(indice);*/
	
	}

}
