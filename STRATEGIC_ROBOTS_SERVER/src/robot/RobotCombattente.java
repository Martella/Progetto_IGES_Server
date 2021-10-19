package robot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import eccezioni.InsufficientEnergyException;

public class RobotCombattente extends Robot {

	private int energiaAttacco;
	private int dannoAttacco;
	
	public RobotCombattente(){
		super();
		dannoAttacco = 40;
		energiaAttacco = 5;
	}
	
	/**
	 * 
	 * @param c colore del Robot
	 */
	public RobotCombattente(Color c) {
		super();
		modificaColore(c);
		dannoAttacco = 40;
		energiaAttacco = 5;
	}

	/**
	 * 
	 * @param xx posizione x
	 * @param yy posizione y
	 */
	public RobotCombattente(int xx, int yy){
		super(xx, yy);
		dannoAttacco = 40;
		energiaAttacco = 5;
	}

	/**
	 * 
	 * @param xx posizione x
	 * @param yy posizione y
	 * @param c colore del Robot
	 */
	public RobotCombattente(int xx, int yy, Color c) {
		super();
		dannoAttacco = 40;
		energiaAttacco = 5;
	}
	
	/**
	 * Viene sottratta all'energia del Robot l'energia che serve per fare un attacco, se il Robot ha energia a sufficienza.
	 * @return se viene sottratta l'energia ritorna true
	 * @throws InsufficientEnergyException se l'energia è insufficiente
	 */
	public boolean attacca()throws InsufficientEnergyException{
		if(getEnergia() >= energiaAttacco){
			modificaEnergia(getEnergia() - energiaAttacco);
			return true;
		}
		
		throw new InsufficientEnergyException();
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

		Rectangle s1 = new Rectangle(xLeft +23, yTop + 50, 4, 30);
		g.setColor(Color.LIGHT_GRAY);
		g.fill(s1);


		Rectangle s2 = new Rectangle(xLeft +15, yTop + 72, 20, 3);
		g.setColor(Color.YELLOW);

		g.fill(s2);

		Rectangle s3 = new Rectangle(xLeft +23, yTop + 75, 4, 10);
		g.setColor(Color.YELLOW);

		g.fill(s3);
		
	}


	public int getDannoAttacco(){
		return dannoAttacco;
	}
	
	public int getEnergiaAttacco(){
		return energiaAttacco;
	}

	public String getTipo(){
		return "robotCombattente";
	}
	
	
/*
	public String toString(){
		return super.toString() +"[energiaAttacco="+ energiaAttacco +", dannoAttacco=" + dannoAttacco + "]";
	}
	
	public boolean equals(Object otherObject){
		
		if (!super.equals(otherObject)) return false;
		
		RobotCombattente other = (RobotCombattente) otherObject;
		
		return energiaAttacco == other.getEnergiaAttacco() && dannoAttacco == other.getDannoAttacco();
	}

	public RobotCombattente clone(){
		
		RobotCombattente cloned = (RobotCombattente) super.clone();

		return cloned;
	}
*/
}
