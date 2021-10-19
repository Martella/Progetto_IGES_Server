package robot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import eccezioni.InsufficientEnergyException;

public class RobotLavoratore extends Robot {

	private int energiaCura;
	private int capacit‡Cura;
	
	public RobotLavoratore(){
		super();
		energiaCura = 5;
		capacit‡Cura = 20;
	}
	
	/**
	 * 
	 * @param c colore del Robot
	 */
	public RobotLavoratore(Color c) {
		super();
		modificaColore(c);
		energiaCura = 5;
		capacit‡Cura = 20;
	}

	/**
	 * 
	 * @param xx posizione x
	 * @param yy posizione y
	 */
	public RobotLavoratore(int xx, int yy){
		super(xx, yy);
		energiaCura = 3;
		capacit‡Cura = 20;
	}

	/**
	 * 
	 * @param xx posizione x
	 * @param yy posizione y
	 * @param c colore del Robot
	 */
	public RobotLavoratore(int xx, int yy, Color c) {
		super();
		energiaCura = 3;
		capacit‡Cura = 20;
	}
	
	/**
	 * @param i peso dell'oggetto.
	 * @return se viene sottratta l'energia ritorna true.
	 * @throws InsufficientEnergyException se l'energia Ë insufficiente.
	 */
	public boolean spostaOggetto() throws InsufficientEnergyException{
		
		if (getEnergia() >= getEnergiaSpostamento()){
			modificaEnergia(getEnergia());
			return true;
		}
		
		throw new InsufficientEnergyException();
	}

	/**
	 * Viene sottratta all'energia del RobotLavoratore l'energia che serve per curare un Robot, se il RobotLavoratore ha energia a sufficienza.
	 * Viene aggiunta all'energia del Robot da curare la capacit‡ di cura del RobotLavoratore se ha energia a sufficienza per curare.
	 * L'energia del Robot da curare non puÚ superare la sua massima energia.
	 * @param r Robot da curare.
	 * @throws InsufficientEnergyException se l'energia Ë insufficiente.
	 */
	public void curaRobot(Robot r) throws InsufficientEnergyException{
		Robot robotDaCurare = r;
		
		if (getEnergia() > energiaCura){
			if( !(robotDaCurare.getEnergia() == robotDaCurare.getMaxEnergia()) ){
				robotDaCurare.modificaEnergia(robotDaCurare.getEnergia() + capacit‡Cura);
				modificaEnergia(getEnergia() - energiaCura); 
			}
		}
		else throw new InsufficientEnergyException();
	}
	
	
	public void modificaEnergiaCura(int e){
		energiaCura = e;
	}
	
	public void modificaCapacit‡cura(int c){
		capacit‡Cura = c;
	}
		
	public String getTipo(){
		return "robotLavoratore";
	}
	
	public int getEnergiaCura(){
		return energiaCura;
	}

	public int getCapacit‡Cura(){
		return capacit‡Cura;
	}

	/**
	 * Disegna il Robot in un grapichs passato come parametro
	 * @param gg Grapichs su cui disegnare
	 */
	public void disegna(Graphics2D gg){

		super.disegna(gg);
		
		Graphics2D g =  gg;

		int xLeft = 50;
		int yTop = 13;

		
		Rectangle r1 = new Rectangle(xLeft +23, yTop + 55, 5, 25);
		g.setColor(Color.BLACK);
		g.draw(r1);
		g.setColor(Color.LIGHT_GRAY);
		g.fill(r1);
		
		Rectangle r2 = new Rectangle(xLeft +16, yTop + 55, 20, 3);
		g.setColor(Color.BLACK);
		g.draw(r2);
		g.setColor(Color.LIGHT_GRAY);
		g.fill(r2);
		
		Rectangle r3 = new Rectangle(xLeft +16, yTop + 50, 3, 7);
		g.setColor(Color.BLACK);
		g.draw(r3);
		g.setColor(Color.LIGHT_GRAY);
		g.fill(r3);

		Rectangle r4 = new Rectangle(xLeft +33, yTop + 50, 3, 7);
		g.setColor(Color.BLACK);
		g.draw(r4);
		g.setColor(Color.LIGHT_GRAY);
		g.fill(r4);

	}
	
	/*
	public String toString(){
		return super.toString()+"[energiaCura="+ energiaCura+", capacit‡Cura=" +capacit‡Cura +"]";
	}
	
	public boolean equals(Object otherObject){
		
		if (!super.equals(otherObject)) return false;
		
		RobotLavoratore other = (RobotLavoratore) otherObject;
		
		return energiaCura == other.getEnergiaCura() && capacit‡Cura == other.getCapacit‡Cura();

	}
	
	public RobotLavoratore clone(){

		RobotLavoratore cloned = (RobotLavoratore) super.clone();
		
		return cloned;
	}

*/
	
}
