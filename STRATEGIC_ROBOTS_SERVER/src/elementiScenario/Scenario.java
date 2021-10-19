package elementiScenario;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import interfacce.Disegnabile;
import interfacce.Energetico;

public class Scenario extends JPanel implements Serializable{
	
	private final int RIGHE = 5;
	private final int COLONNE = 10;
	private int rig;
	private int col;
	private String [][] griglia;
	private ArrayList<Casella> casella;
	private String tipoScenario;
	private ArrayList<Ostacolo> ostacoloArray;
	private ArrayList<BancoRifornimenti> bancoRifornimentiArray;

	/**
	 * 
	 * @param s tipo dello scenario
	 */
	public Scenario(String s){
		
		tipoScenario = s;
		
		if (tipoScenario.equals("casuale")){
			Random random = new Random();
			if(random.nextInt(2) == 0)tipoScenario = "classic";
			else tipoScenario = "fantasy";
		}
		
		final int NUM_OSTACOLI = 10;
		final int NUM_BANCO_RIFORN = 1;
		
		ostacoloArray = new ArrayList<Ostacolo>();
		bancoRifornimentiArray = new ArrayList<BancoRifornimenti>();

		setLayout(new GridLayout(RIGHE, COLONNE));

		griglia = new String[RIGHE][COLONNE]; 
		casella = new ArrayList<Casella>();

		int i = 0;
		for( rig = 0; rig < RIGHE; rig++){
			for( col= 0; col < COLONNE; col++ ){
				casella.add(new Casella(tipoScenario));
				add(casella.get(i));
				griglia[rig][col] = "null";
				i++;
			}
		}
		
		for(i = 1; i <= NUM_OSTACOLI; i++){
			Ostacolo o = new Ostacolo();
			aggiungiOggettoRandom(o);
		}
		
		for(i = 1; i <= NUM_BANCO_RIFORN; i++){
			aggiungiOggettoRandom(new BancoRifornimenti());
		}
		

	}
	
	/**
	 * Disegna un oggetto Disegnabile in una casella vuota dello scenario scelta casualmente.
	 * @param o oggetto da disegnare
	 */
	private void aggiungiOggettoRandom(Disegnabile o){

		Disegnabile oggetto = o;
		
		Random random = new Random();
		
		rig = random.nextInt(RIGHE);
		col = random.nextInt(COLONNE);		
		
		while( ! griglia[rig][col].equals("null")){
			rig = random.nextInt(RIGHE);
			col = random.nextInt(COLONNE);		
		}
		
		
		oggetto.modificaX(rig);
		oggetto.modificaY(col);

		modificaGrigliaAggiungiOggetto(oggetto, oggetto.getTipo());
		
		if( ! (oggetto.getTipo()).equals("bancoRifornimenti")) modificaGrigliaAggiungiEnergia((Energetico)oggetto);
		
		if ((oggetto.getTipo()).equals("ostacolo")) ostacoloArray.add((Ostacolo) oggetto);
		if ((oggetto.getTipo()).equals("bancoRifornimenti")) bancoRifornimentiArray.add((BancoRifornimenti) oggetto);

	}
	
	public ArrayList<Ostacolo> getArrayListOstacoli(){
		return ostacoloArray;
	}
	
	public ArrayList<BancoRifornimenti> getArrayListBancoRifornimenti(){
		return bancoRifornimentiArray;
	}

	/**
	 * Disegna un oggetto Disegnabile nella casella in posizione x e y dell'oggetto Disegnabile
	 * @param o oggetto da disegnare
	 * @param s tipo oggetto
	 */
	public void modificaGrigliaAggiungiOggetto(Disegnabile o, String s){
		Disegnabile oggetto = o;
		casella.get(oggetto.getX() * COLONNE + oggetto.getY()).disegnaOggetto(oggetto);
		griglia[oggetto.getX()][oggetto.getY()] = s;
	}
	
	/**
	 * Disegna una BarraEnergia di un oggetto Energetico nella casella in posizione x e y dell'oggetto Energetico
	 * @param e oggetto da prendere in considerazione per disegnare la sua BarraEnergia
	 */
	public void modificaGrigliaAggiungiEnergia(Energetico e){
		Energetico oggettoEnergetico = e;
		casella.get(oggettoEnergetico.getX() * COLONNE + oggettoEnergetico.getY()).disegnaBarraEnergia(oggettoEnergetico);
	}
	
	public void modificaGrigliaSelezionaCasella(int xx, int yy){
		int x = xx;
		int y = yy;
		casella.get(x * COLONNE + y).coloraCasella(Color.PINK);
	}
	
	public void modificaGrigliaDeSelezionaCasella(int xx, int yy){
		int x = xx;
		int y = yy;
		casella.get(x * COLONNE + y).deColoraCasella();
	}

	public void modificaGrigliaCancella(int xx, int yy){
		int x = xx;
		int y = yy;
		griglia[x][y] = "null";
		casella.get(x * COLONNE + y).svuotaCasella();
	}
	
	public void modificaGrigliaRepaintCasella(int xx, int yy){
		int x = xx;
		int y = yy;
		casella.get(x * COLONNE + y).repaint();;
	}

	public String[][] getGriglia(){
		return griglia;
	}
	
	public int getMaxRighe(){
		return RIGHE;
	}
	
	public int getMaxColonne(){
		return COLONNE;
	}

	public void paintComponent(Graphics gg) {

		Graphics2D g = (Graphics2D) gg;

		
		if(tipoScenario.equals("classic")){
			g.drawImage(new ImageIcon("img\\scenarioClassic.jpg").getImage(), 0, 0, null); 
			//g.drawImage(new ImageIcon("C:\\Users\\Antonio\\Desktop\\FileStrategicRobots\\scenarioClassic.jpg").getImage(), 0, 0, null); 			
		}

		else if(tipoScenario.equals("fantasy")){
			g.drawImage(new ImageIcon("img\\scenarioFantasy.jpg").getImage(), 0, 0, null); 	
			//g.drawImage(new ImageIcon("C:\\Users\\Antonio\\Desktop\\FileStrategicRobots\\scenarioFantasy.jpg").getImage(), 0, 0, null); 			

		}

	}
	

}
