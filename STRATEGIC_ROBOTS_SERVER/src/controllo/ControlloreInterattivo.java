package controllo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JFrame;

import eccezioni.CriticalStatusException;
import eccezioni.InsufficientEnergyException;
import elementiScenario.BancoRifornimenti;
import elementiScenario.Ostacolo;
import elementiScenario.Scenario;
import frame.BancoRifornimentiFrame;
import frame.MessaggioFrame;
import interfacce.Disegnabile;
import interfacce.Energetico;
import robot.Robot;
import robot.RobotCombattente;
import robot.RobotLavoratore;

public class ControlloreInterattivo extends Controllore implements Serializable {
	
	private static final long serialVersionUID = 6529685098267757690L;
	
	private Scenario scenario;
	private String [][] griglia;
	private String tipo;
	private Robot rob;
	private JFrame frame;
	private ArrayList<Ostacolo> ostacoloArray;
	private ArrayList<BancoRifornimenti> bancoRifornimentiArray;
	private boolean robotDanneggiato75;
	
	/**
	 * 
	 * @param s scenario su cui controllare i Robot
	 * @param f frame dello scenario
	 */
	public ControlloreInterattivo(Scenario s, JFrame f){
		super(s, f);
		frame = f;
		scenario = s;
	}
	

	/**
	 * Inizia a controllare un Robot a attiava l'evento InputTasti
	 * @param o ArrayList contenenti gli Ostacoli dello scenario
	 * @param b ArrayList contenenti i BancoRifornimeti dello scenario
	 */
	public void start( ArrayList<Ostacolo> o, ArrayList<BancoRifornimenti> b){
		super.start();
	
		ostacoloArray = o;
		bancoRifornimentiArray = b;
		
		rob = getRobotControllato();
		
		KeyListener muoviTastoListener = new InputTasti();
		frame.addKeyListener(muoviTastoListener);
	}	
	

	/**
	 * Evento per ricevere input da tastiera da dare al Robot
	 */
	class InputTasti implements KeyListener{

		public void keyPressed(KeyEvent e) {
			
			try{
				rob = getRobotControllato();
				tipo = getTipoRobotControllato();
				ArrayList<RobotLavoratore> insiemeRobotLavoratoreArray = getInsiemeRobotLavoratoreeArray();
				ArrayList<RobotCombattente> insiemeRobotCombattenteArray = getInsiemeRobotCombattenteArray();
				robotDanneggiato75 = false;
				
				if(getStato() && getStatoMossa()){
					
					boolean spostato = false;
					griglia = new String [scenario.getMaxRighe()][scenario.getMaxColonne()];
					griglia = scenario.getGriglia();
	
					// se premi tasto destro
					if (e.getKeyCode() == 39){					
						if (rob.getY() < scenario.getMaxColonne() - 1 && griglia[rob.getX()][rob.getY()+1].equals("null")){
							spostato = rob.spostamentoY(rob.getY()+1);
							if(spostato) scenario.modificaGrigliaCancella(rob.getX(), rob.getY()-1);
						}
					}
					
					// se premi tasto sinistro
					if (e.getKeyCode() == 37){					
						if (rob.getY() > 0 && griglia[rob.getX()][rob.getY()-1].equals("null")){
							spostato = rob.spostamentoY(rob.getY()-1);
							if(spostato) scenario.modificaGrigliaCancella(rob.getX(), rob.getY()+1);
						}
					}
					
					// se premi tasto sopra
					if (e.getKeyCode() == 38){					
						if (rob.getX() > 0 && griglia[rob.getX() -1][rob.getY()].equals("null")){
							spostato = rob.spostamentoX(rob.getX()-1);
							if(spostato) scenario.modificaGrigliaCancella(rob.getX()+1, rob.getY());
						}
					}
					
					// se premi tasto sotto
					if (e.getKeyCode() == 40){					
						if (rob.getX() < scenario.getMaxRighe() - 1 && griglia[rob.getX()+1][rob.getY()].equals("null")){
							spostato = rob.spostamentoX(rob.getX()+1);
							if(spostato) scenario.modificaGrigliaCancella(rob.getX()-1, rob.getY());
						}
					}
					
					
					
					if (tipo.equals("robotCombattente") && !spostato){
						RobotCombattente robComb = (RobotCombattente) rob;
						boolean attaccato = false;
						
						// se premi tasto destro
						if (e.getKeyCode() == 39){	
							if (robComb.getY() < scenario.getMaxColonne() - 1 && ! griglia[robComb.getX()][robComb.getY()+1].equals("null") && ! griglia[robComb.getX()][robComb.getY()+1].equals("bancoRifornimenti") ){
								if(robComb.attacca()){
									attaccato = true;
									
									for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
										if ((ostacoloArray.get(ii)).getX()== robComb.getX() && (ostacoloArray.get(ii)).getY() == robComb.getY()+1){
											(ostacoloArray.get(ii)).modificaEnergia((ostacoloArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((ostacoloArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()+1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()+1);
												(ostacoloArray.get(ii)).modificaX(scenario.getMaxRighe()+1);
												(ostacoloArray.get(ii)).modificaY(scenario.getMaxColonne()+1);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++ ){
										if ((insiemeRobotCombattenteArray.get(ii)).getX()== robComb.getX() && (insiemeRobotCombattenteArray.get(ii)).getY() == robComb.getY()+1){
											(insiemeRobotCombattenteArray.get(ii)).modificaEnergia((insiemeRobotCombattenteArray.get(ii)).getEnergia()-( 100 * robComb.getDannoAttacco()/100) );
										
											if ( insiemeRobotCombattenteArray.get(ii).getEnergia() < (insiemeRobotCombattenteArray.get(ii).getMaxEnergia() * 25 / 100) && insiemeRobotCombattenteArray.get(ii).getEnergia() > 0) robotDanneggiato75 = true;
										
											if((insiemeRobotCombattenteArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()+1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()+1);
												insiemeRobotCombattenteArray.remove(ii);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++ ){
										if ((insiemeRobotLavoratoreArray.get(ii)).getX()== robComb.getX() && (insiemeRobotLavoratoreArray.get(ii)).getY() == robComb.getY()+1){
											(insiemeRobotLavoratoreArray.get(ii)).modificaEnergia((insiemeRobotLavoratoreArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											
											if ( insiemeRobotLavoratoreArray.get(ii).getEnergia() < (insiemeRobotLavoratoreArray.get(ii).getMaxEnergia() * 25 / 100) && insiemeRobotLavoratoreArray.get(ii).getEnergia() > 0) robotDanneggiato75 = true;

											if((insiemeRobotLavoratoreArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()+1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()+1);
												insiemeRobotLavoratoreArray.remove(ii);
											}
										}
									}

								}
							}
						}

						
						// se premi tasto sinistro
						if (e.getKeyCode() == 37){	
							if (robComb.getY() > 0 && ! griglia[robComb.getX()][robComb.getY()-1].equals("null") && ! griglia[robComb.getX()][robComb.getY()-1].equals("bancoRifornimenti") ){
								if(robComb.attacca()){
									attaccato = true;

									for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
										if ((ostacoloArray.get(ii)).getX()== robComb.getX() && (ostacoloArray.get(ii)).getY() == robComb.getY()-1){
											(ostacoloArray.get(ii)).modificaEnergia((ostacoloArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((ostacoloArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()-1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()-1);
												(ostacoloArray.get(ii)).modificaX(scenario.getMaxRighe()+1);
												(ostacoloArray.get(ii)).modificaY(scenario.getMaxColonne()+1);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++ ){
										if ((insiemeRobotCombattenteArray.get(ii)).getX()== robComb.getX() && (insiemeRobotCombattenteArray.get(ii)).getY() == robComb.getY()-1){
											(insiemeRobotCombattenteArray.get(ii)).modificaEnergia((insiemeRobotCombattenteArray.get(ii)).getEnergia()-( 100 * robComb.getDannoAttacco()/100));
					
											if ( insiemeRobotCombattenteArray.get(ii).getEnergia() < (insiemeRobotCombattenteArray.get(ii).getMaxEnergia() * 25 / 100) && insiemeRobotCombattenteArray.get(ii).getEnergia() > 0) robotDanneggiato75 = true;

											if((insiemeRobotCombattenteArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()-1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()-1);
												insiemeRobotCombattenteArray.remove(ii);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++ ){
										if ((insiemeRobotLavoratoreArray.get(ii)).getX()== robComb.getX() && (insiemeRobotLavoratoreArray.get(ii)).getY() == robComb.getY()-1){
											(insiemeRobotLavoratoreArray.get(ii)).modificaEnergia((insiemeRobotLavoratoreArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											
											if ( insiemeRobotLavoratoreArray.get(ii).getEnergia() < (insiemeRobotLavoratoreArray.get(ii).getMaxEnergia() * 25 / 100) && insiemeRobotLavoratoreArray.get(ii).getEnergia() > 0) robotDanneggiato75 = true;

											if((insiemeRobotLavoratoreArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()-1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()-1);
												insiemeRobotLavoratoreArray.remove(ii);
											}
										}
									}


								}
							}
						}
						
						// se premi tasto sopra
						if (e.getKeyCode() == 38){	
							if (robComb.getX() > 0 && ! griglia[robComb.getX()-1][robComb.getY()].equals("null") && ! griglia[robComb.getX()-1][robComb.getY()].equals("bancoRifornimenti") ){
								if(robComb.attacca()){
									attaccato = true;

									for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
										if ((ostacoloArray.get(ii)).getX()== robComb.getX()-1 && (ostacoloArray.get(ii)).getY() == robComb.getY()){
											(ostacoloArray.get(ii)).modificaEnergia((ostacoloArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((ostacoloArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()-1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()-1, robComb.getY());
												(ostacoloArray.get(ii)).modificaX(scenario.getMaxRighe()+1);
												(ostacoloArray.get(ii)).modificaY(scenario.getMaxColonne()+1);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++ ){
										if ((insiemeRobotCombattenteArray.get(ii)).getX()== robComb.getX()-1 && (insiemeRobotCombattenteArray.get(ii)).getY() == robComb.getY()){
											(insiemeRobotCombattenteArray.get(ii)).modificaEnergia((insiemeRobotCombattenteArray.get(ii)).getEnergia()-( 100 * robComb.getDannoAttacco()/100));
										
											if ( insiemeRobotCombattenteArray.get(ii).getEnergia() < (insiemeRobotCombattenteArray.get(ii).getMaxEnergia() * 25 / 100) && insiemeRobotCombattenteArray.get(ii).getEnergia() > 0) robotDanneggiato75 = true;

											if((insiemeRobotCombattenteArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()-1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()-1, robComb.getY());
												insiemeRobotCombattenteArray.remove(ii);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++ ){
										if ((insiemeRobotLavoratoreArray.get(ii)).getX()== robComb.getX()-1 && (insiemeRobotLavoratoreArray.get(ii)).getY() == robComb.getY()){
											(insiemeRobotLavoratoreArray.get(ii)).modificaEnergia((insiemeRobotLavoratoreArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											
											if ( insiemeRobotLavoratoreArray.get(ii).getEnergia() < (insiemeRobotLavoratoreArray.get(ii).getMaxEnergia() * 25 / 100) && insiemeRobotLavoratoreArray.get(ii).getEnergia() > 0) robotDanneggiato75 = true;

											if((insiemeRobotLavoratoreArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()-1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()-1, robComb.getY());
												insiemeRobotLavoratoreArray.remove(ii);
											}
										}
									}


								}
							}
						}

						// se premi tasto sotto
						if (e.getKeyCode() == 40){	
							if (robComb.getX() < scenario.getMaxRighe() - 1 && ! griglia[robComb.getX()+1][robComb.getY()].equals("null") && ! griglia[robComb.getX()+1][robComb.getY()].equals("bancoRifornimenti") ){
								if(robComb.attacca()){
									attaccato = true;

									for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
										if ((ostacoloArray.get(ii)).getX()== robComb.getX()+1 && (ostacoloArray.get(ii)).getY() == robComb.getY()){
											(ostacoloArray.get(ii)).modificaEnergia((ostacoloArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((ostacoloArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()+1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()+1, robComb.getY());
												(ostacoloArray.get(ii)).modificaX(scenario.getMaxRighe()+1);
												(ostacoloArray.get(ii)).modificaY(scenario.getMaxColonne()+1);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++ ){
										if ((insiemeRobotCombattenteArray.get(ii)).getX()== robComb.getX()+1 && (insiemeRobotCombattenteArray.get(ii)).getY() == robComb.getY()){
											(insiemeRobotCombattenteArray.get(ii)).modificaEnergia((insiemeRobotCombattenteArray.get(ii)).getEnergia()-( 100 * robComb.getDannoAttacco()/100));
											
											if ( insiemeRobotCombattenteArray.get(ii).getEnergia() < (insiemeRobotCombattenteArray.get(ii).getMaxEnergia() * 25 / 100) && insiemeRobotCombattenteArray.get(ii).getEnergia() > 0) robotDanneggiato75 = true;

											if((insiemeRobotCombattenteArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()+1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()+1, robComb.getY());
												insiemeRobotCombattenteArray.remove(ii);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++ ){
										if ((insiemeRobotLavoratoreArray.get(ii)).getX()== robComb.getX()+1 && (insiemeRobotLavoratoreArray.get(ii)).getY() == robComb.getY()){
											(insiemeRobotLavoratoreArray.get(ii)).modificaEnergia((insiemeRobotLavoratoreArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											
											if ( insiemeRobotLavoratoreArray.get(ii).getEnergia() < (insiemeRobotLavoratoreArray.get(ii).getMaxEnergia() * 25 / 100) && insiemeRobotLavoratoreArray.get(ii).getEnergia() > 0) robotDanneggiato75 = true;

											if((insiemeRobotLavoratoreArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()+1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()+1, robComb.getY());
												insiemeRobotLavoratoreArray.remove(ii);
											}
										}
									}


								}
							}
						}


						// se premi barra spaziatrice
						if (e.getKeyCode() == 32){
							int xBanco = 0;
							int yBanco = 0;
							boolean trovatoBanco = false;
							
							if (robComb.getX() > 0){
								if (griglia[robComb.getX()-1][robComb.getY()].equals("bancoRifornimenti")){
									trovatoBanco = true;
									xBanco = robComb.getX()-1;
									yBanco = robComb.getY();
								}
							}

							if (robComb.getX() < scenario.getMaxRighe() -1){
								if (griglia[robComb.getX()+1][robComb.getY()].equals("bancoRifornimenti")){
									trovatoBanco = true;
									xBanco = robComb.getX()+1;
									yBanco = robComb.getY();
								}
							}
							
							if(robComb.getY() < scenario.getMaxColonne() -1){
								if (griglia[robComb.getX()][robComb.getY()+1].equals("bancoRifornimenti")){
									trovatoBanco = true;
									xBanco = robComb.getX();
									yBanco = robComb.getY()+1;
								}
							}

							if (robComb.getY() > 0){
								if (griglia[robComb.getX()][robComb.getY()-1].equals("bancoRifornimenti")){
									trovatoBanco = true;
									xBanco = robComb.getX();
									yBanco = robComb.getY()-1;
								}
							}
							
							if (trovatoBanco){
								for(int i = 0; i < bancoRifornimentiArray.size(); i++){
									if((bancoRifornimentiArray.get(i)).getX() == xBanco && (bancoRifornimentiArray.get(i)).getY() == yBanco){
										BancoRifornimentiFrame frameBancoRifornimenti = new BancoRifornimentiFrame(bancoRifornimentiArray.get(i), robComb, scenario);
										frameBancoRifornimenti.setLocationRelativeTo(null);
										frameBancoRifornimenti.setVisible(true);
										modificaStatoMossa(false);
									}
								}
							}
						}

						if (attaccato) modificaStatoMossa(false);
						
					}
					
					
					if (tipo.equals("robotLavoratore") && !spostato){
						
						
						RobotLavoratore robLav =  (RobotLavoratore) rob;
						boolean curato = false;
						
						// se premi tasto destro
						if (e.getKeyCode() == 39){												
							if (robLav.getY() < scenario.getMaxColonne() - 2 && griglia[robLav.getX()][robLav.getY()+1].equals("ostacolo")&& griglia[robLav.getX()][robLav.getY()+2].equals("null")){								
								for(int i = 0; i < ostacoloArray.size(); i++ ){
									if((ostacoloArray.get(i)).getX()== robLav.getX() && (ostacoloArray.get(i)).getY() == robLav.getY()+1){
										spostato = true;
										(ostacoloArray.get(i)).modificaY((ostacoloArray.get(i)).getY()+1);
										scenario.modificaGrigliaAggiungiOggetto((Disegnabile)ostacoloArray.get(i), "ostacolo");
										scenario.modificaGrigliaAggiungiEnergia((Energetico)ostacoloArray.get(i));	
									}
								}	
								if (spostato){
									scenario.modificaGrigliaCancella(robLav.getX(), robLav.getY());
									robLav.spostamentoY(robLav.getY()+1);
									scenario.modificaGrigliaAggiungiOggetto((Disegnabile)robLav, tipo);
								}
							}
							
							if (robLav.getY() < scenario.getMaxColonne() - 1 && (griglia[robLav.getX()][robLav.getY()+1].equals("robotCombattente") || griglia[robLav.getX()][robLav.getY()+1].equals("robotLavoratore") )){								
								
								for(int i = 0; i < insiemeRobotCombattenteArray.size(); i++){
									if( (insiemeRobotCombattenteArray.get(i)).getX() == robLav.getX() && (insiemeRobotCombattenteArray.get(i)).getY() == robLav.getY() + 1){
										robLav.curaRobot(insiemeRobotCombattenteArray.get(i));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotCombattenteArray.get(i).getX(), insiemeRobotCombattenteArray.get(i).getY());
										curato = true;
									}
								}
								
								for(int i = 0; i < insiemeRobotLavoratoreArray.size(); i++){
									if( (insiemeRobotLavoratoreArray.get(i)).getX() == robLav.getX() && (insiemeRobotLavoratoreArray.get(i)).getY() == robLav.getY() + 1){
										robLav.curaRobot(insiemeRobotLavoratoreArray.get(i));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotLavoratoreArray.get(i).getX(), insiemeRobotLavoratoreArray.get(i).getY());
										curato = true;
									}
								}
							}
						}
						
						// se premi tasto sinistro
						if (e.getKeyCode() == 37){												
							if (robLav.getY() > 1 && griglia[robLav.getX()][robLav.getY()-1].equals("ostacolo")&& griglia[robLav.getX()][robLav.getY()-2].equals("null")){								
								for(int i = 0; i < ostacoloArray.size(); i++ ){
									if((ostacoloArray.get(i)).getX()== robLav.getX() && (ostacoloArray.get(i)).getY() == robLav.getY()-1){
										spostato = true;
										(ostacoloArray.get(i)).modificaY((ostacoloArray.get(i)).getY()-1);
										scenario.modificaGrigliaAggiungiOggetto((Disegnabile)ostacoloArray.get(i), "ostacolo");
										scenario.modificaGrigliaAggiungiEnergia((Energetico)ostacoloArray.get(i));
										
									}
								}		
								if (spostato){
									scenario.modificaGrigliaCancella(robLav.getX(), robLav.getY());
									robLav.spostamentoY(robLav.getY()-1);
									scenario.modificaGrigliaAggiungiOggetto((Disegnabile)robLav, tipo);
								}
							}
							
							if (robLav.getY() > 0 && (griglia[robLav.getX()][robLav.getY()-1].equals("robotCombattente") || griglia[robLav.getX()][robLav.getY()-1].equals("robotLavoratore") )){								
								
								for(int i = 0; i < insiemeRobotCombattenteArray.size(); i++){
									if( (insiemeRobotCombattenteArray.get(i)).getX() == robLav.getX() && (insiemeRobotCombattenteArray.get(i)).getY() == robLav.getY() - 1){
										robLav.curaRobot(insiemeRobotCombattenteArray.get(i));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotCombattenteArray.get(i).getX(), insiemeRobotCombattenteArray.get(i).getY());
										curato = true;
									}
								}
								
								for(int i = 0; i < insiemeRobotLavoratoreArray.size(); i++){
									if( (insiemeRobotLavoratoreArray.get(i)).getX() == robLav.getX() && (insiemeRobotLavoratoreArray.get(i)).getY() == robLav.getY() - 1){
										robLav.curaRobot(insiemeRobotLavoratoreArray.get(i));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotLavoratoreArray.get(i).getX(), insiemeRobotLavoratoreArray.get(i).getY());
										curato = true;
									}
								}
							}

						}
						
						// se premi tasto sopra
						if (e.getKeyCode() == 38){												
							if (robLav.getX() > 1 && griglia[robLav.getX()-1][robLav.getY()].equals("ostacolo")&& griglia[robLav.getX()-2][robLav.getY()].equals("null")){								
								for(int i = 0; i < ostacoloArray.size(); i++ ){
									if((ostacoloArray.get(i)).getX()== robLav.getX()-1 && (ostacoloArray.get(i)).getY() == robLav.getY()){
										spostato = true;
										(ostacoloArray.get(i)).modificaX((ostacoloArray.get(i)).getX()-1);
										scenario.modificaGrigliaAggiungiOggetto((Disegnabile)ostacoloArray.get(i), "ostacolo");
										scenario.modificaGrigliaAggiungiEnergia((Energetico)ostacoloArray.get(i));
									}
								}
								if(spostato){
									scenario.modificaGrigliaCancella(robLav.getX(), robLav.getY());
									robLav.spostamentoX(robLav.getX()-1);
									scenario.modificaGrigliaAggiungiOggetto((Disegnabile)robLav, tipo);
								}
							}
							
							if (robLav.getX() > 0 && (griglia[robLav.getX() - 1][robLav.getY()].equals("robotCombattente") || griglia[robLav.getX()-1][robLav.getY()].equals("robotLavoratore") )){								
								
								for(int i = 0; i < insiemeRobotCombattenteArray.size(); i++){
									if( (insiemeRobotCombattenteArray.get(i)).getX() == robLav.getX()-1 && (insiemeRobotCombattenteArray.get(i)).getY() == robLav.getY()){
										robLav.curaRobot(insiemeRobotCombattenteArray.get(i));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotCombattenteArray.get(i).getX(), insiemeRobotCombattenteArray.get(i).getY());
										curato = true;
									}
								}
								
								for(int i = 0; i < insiemeRobotLavoratoreArray.size(); i++){
									if( (insiemeRobotLavoratoreArray.get(i)).getX() == robLav.getX()-1 && (insiemeRobotLavoratoreArray.get(i)).getY() == robLav.getY() ){
										robLav.curaRobot(insiemeRobotLavoratoreArray.get(i));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotLavoratoreArray.get(i).getX(), insiemeRobotLavoratoreArray.get(i).getY());
										curato = true;
									}
								}
							}

						} 

						// se premi tasto sotto
						if (e.getKeyCode() == 40){												
							if (robLav.getX() < scenario.getMaxRighe() - 2 && griglia[robLav.getX()+1][robLav.getY()].equals("ostacolo")&& griglia[robLav.getX()+2][robLav.getY()].equals("null")){								
								for(int i = 0; i < ostacoloArray.size(); i++ ){
									if((ostacoloArray.get(i)).getX()== robLav.getX()+1 && (ostacoloArray.get(i)).getY() == robLav.getY()){
										spostato = true;
										(ostacoloArray.get(i)).modificaX((ostacoloArray.get(i)).getX()+1);
										scenario.modificaGrigliaAggiungiOggetto((Disegnabile)ostacoloArray.get(i), "ostacolo");
										scenario.modificaGrigliaAggiungiEnergia((Energetico)ostacoloArray.get(i));	
									}
								}	
								if (spostato){
									scenario.modificaGrigliaCancella(robLav.getX(), robLav.getY());
									robLav.spostamentoX(robLav.getX()+1);
									scenario.modificaGrigliaAggiungiOggetto((Disegnabile)robLav, tipo);
								}
							}
							
							if (robLav.getX() < scenario.getMaxRighe()-1 && (griglia[robLav.getX() + 1][robLav.getY()].equals("robotCombattente") || griglia[robLav.getX()+1][robLav.getY()].equals("robotLavoratore") )){								
								
								for(int i = 0; i < insiemeRobotCombattenteArray.size(); i++){
									if( (insiemeRobotCombattenteArray.get(i)).getX() == robLav.getX()+1 && (insiemeRobotCombattenteArray.get(i)).getY() == robLav.getY()){
										robLav.curaRobot(insiemeRobotCombattenteArray.get(i));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotCombattenteArray.get(i).getX(), insiemeRobotCombattenteArray.get(i).getY());
										curato = true;
									}
								}
								
								for(int i = 0; i < insiemeRobotLavoratoreArray.size(); i++){
									if( (insiemeRobotLavoratoreArray.get(i)).getX() == robLav.getX()+1 && (insiemeRobotLavoratoreArray.get(i)).getY() == robLav.getY() ){
										robLav.curaRobot(insiemeRobotLavoratoreArray.get(i));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotLavoratoreArray.get(i).getX(), insiemeRobotLavoratoreArray.get(i).getY());
										curato = true;
									}
								}
							}
							
							
							
						}
						
						if(curato) modificaStatoMossa(false);
						
						
						
						// se premi barra spaziatrice
						if (e.getKeyCode() == 32){
							int xBanco = 0;
							int yBanco = 0;
							boolean trovatoBanco = false;
							
							if (robLav.getX() > 0){
								if (griglia[robLav.getX()-1][robLav.getY()].equals("bancoRifornimenti")){
									trovatoBanco = true;
									xBanco = robLav.getX()-1;
									yBanco = robLav.getY();
								}
							}

							if (robLav.getX() < scenario.getMaxRighe() -1){
								if (griglia[robLav.getX()+1][robLav.getY()].equals("bancoRifornimenti")){
									trovatoBanco = true;
									xBanco = robLav.getX()+1;
									yBanco = robLav.getY();
								}
							}
							
							if(robLav.getY() < scenario.getMaxColonne() -1){
								if (griglia[robLav.getX()][robLav.getY()+1].equals("bancoRifornimenti")){
									trovatoBanco = true;
									xBanco = robLav.getX();
									yBanco = robLav.getY()+1;
								}
							}

							if (robLav.getY() > 0){
								if (griglia[robLav.getX()][robLav.getY()-1].equals("bancoRifornimenti")){
									trovatoBanco = true;
									xBanco = robLav.getX();
									yBanco = robLav.getY()-1;
								}
							}
							
							if (trovatoBanco){
								for(int i = 0; i < bancoRifornimentiArray.size(); i++){
									if((bancoRifornimentiArray.get(i)).getX() == xBanco && (bancoRifornimentiArray.get(i)).getY() == yBanco){
										BancoRifornimentiFrame frameBancoRifornimenti = new BancoRifornimentiFrame(bancoRifornimentiArray.get(i), robLav, scenario);
										frameBancoRifornimenti.setLocationRelativeTo(null);
										frameBancoRifornimenti.setVisible(true);
										modificaStatoMossa(false);
									}
								}
							}
						}


					}
					
					scenario.modificaGrigliaSelezionaCasella(rob.getX(), rob.getY());
					scenario.modificaGrigliaAggiungiOggetto((Disegnabile)rob, tipo);
					scenario.modificaGrigliaAggiungiEnergia((Energetico)rob);
				}
				
				else scenario.modificaGrigliaDeSelezionaCasella(rob.getX(), rob.getY());
			
			}catch (InsufficientEnergyException e1){
				MessaggioFrame messaggioFrame= new MessaggioFrame("Energia insufficiente");
				messaggioFrame.setVisible(true);
			}
			
			try{
				verificaEccezioni();
			}catch(CriticalStatusException e2){
				MessaggioFrame messaggioFrame= new MessaggioFrame("Robot quasi distrutto");
				messaggioFrame.setVisible(true);
			}
		}
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}

		
	}	


		
	
	
	public void modificaFrame(JFrame f){
		frame = f;
	}
	
	public void verificaEccezioni() throws CriticalStatusException{

		if (robotDanneggiato75) throw new CriticalStatusException();	
	}
	
	/**
	 * Il controllore controlla i Robot dello stato salvato
	 */
	public void aggiungiKeyListner(){
		
		KeyListener muoviTastoListener = new InputTasti();
		frame.addKeyListener(muoviTastoListener);
	}
	
		
}
