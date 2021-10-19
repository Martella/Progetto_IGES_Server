package robot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import eccezioni.InsufficientEnergyException;
import interfacce.Disegnabile;
import interfacce.Energetico;

public class Robot implements Disegnabile, Energetico, Cloneable, Serializable{

	private int x;
	private int y;
	private int energiaRobot; 
	private final int MAX_ENERGIA = 100;
	private int energSpost;
	private Color colore;
	
	public Robot(){
		x = 0;
		y = 0;
		energSpost = 1;
		energiaRobot = MAX_ENERGIA;
		colore = Color.LIGHT_GRAY;
	}
		
	/**
	 * 
	 * @param xx posizione x
	 * @param yy posizione y
	 */
	public Robot(int xx, int yy){
		x = xx;
		y = yy;
		energSpost = 1;
		energiaRobot = MAX_ENERGIA;
		colore = Color.LIGHT_GRAY;
	}
	
	/**
	 * 
	 * @param xx posizione x
	 * @param yy posizione y
	 * @param c colore del Robot
	 */
	public Robot(int xx, int yy,  Color c){
		x = xx;
		y = yy;
		energSpost = 1;
		energiaRobot = MAX_ENERGIA;
		colore = Color.LIGHT_GRAY;
	}

	/**
	 * Modifica l'energia del Robot.
	 * @param e rappresenta la nuova energia del Robot, non puo essere negativa e non puo superare l'energia massima.
	 */
	public void modificaEnergia(int e){
		energiaRobot = e;
		if (energiaRobot > MAX_ENERGIA) energiaRobot = MAX_ENERGIA;
		else if(energiaRobot < 0) energiaRobot = 0;
	}
	
	/**
	 * Cambia la posizione X del Robot se ha abbastanza energia per spostarsi e viene sottratta l'energia necessaria al Robot.
	 * @param xx posizione in cui spostare il Robot
	 * @return se viene spostato ritorna true
	 * @throws InsufficientEnergyException se l'energia è insufficiente
	 */
	public boolean spostamentoX(int xx) throws InsufficientEnergyException{
		
		if (energiaRobot >= energSpost){
			x= xx;
			energiaRobot -= energSpost;
			return true;
		}
		throw new InsufficientEnergyException();
	}

	/**
	 * Cambia la posizione Y del Robot se ha abbastanza energia per spostarsi e viene sottratta l'energia necessaria al Robot.
	 * @param xx posizione in cui spostare il Robot
	 * @return se viene spostato ritorna true
	 * @throws InsufficientEnergyException se l'energia è insufficiente
	 */

	public boolean spostamentoY(int yy) throws InsufficientEnergyException{

		if (energiaRobot >= energSpost){
			y= yy;
			energiaRobot -= energSpost;
			return true;
		}
		throw new InsufficientEnergyException();
	}
	
	public void modificaX(int xx){
		x = xx;
	}
	
	public void modificaY(int yy){
		y = yy;
	}

	public void modificaEnergiaSpostamento(int e){
		energSpost = e;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public int getEnergia(){
		return energiaRobot;
	}
	
	public int getEnergiaSpostamento(){
		return energSpost;
	}
	
	public int getMaxEnergia(){
		return MAX_ENERGIA;
	}
	
	public String getTipo(){
		return "robot";
	}
	
	public Color getColore(){
		return colore;
	}

	/**
	 * Disegna il Robot in un grapichs passato come parametro
	 * @param gg Grapichs su cui disegnare
	 */
	public void disegna(Graphics2D gg){
		
		Graphics2D g =  gg;
		int xLeft = 50;
		int yTop = 13;
		

		Rectangle testa = new Rectangle(xLeft + 10, yTop, 30, 30);
		g.setColor(Color.BLACK);
		g.draw(testa);
		g.setColor(colore);
		g.fill(testa);

		
		Rectangle collo = new Rectangle(xLeft + 20, yTop + 30, 10, 10);
		g.setColor(Color.BLACK);
		g.draw(collo);
		g.setColor(colore);
		g.fill(collo);

		Rectangle corpo = new Rectangle(xLeft +10, yTop + 40, 30, 50);
		g.setColor(Color.BLACK);
		g.draw(corpo);
		g.setColor(Color.BLACK);
		g.fill(corpo);

		Rectangle gamba1 = new Rectangle(xLeft + 10, yTop + 90, 10, 20);
		g.setColor(Color.BLACK);
		g.draw(gamba1);
		g.setColor(colore);
		g.fill(gamba1);

		Rectangle gamba2 = new Rectangle(xLeft + 30, yTop + 90, 10, 20);
		g.setColor(Color.BLACK);
		g.draw(gamba2);
		g.setColor(colore);
		g.fill(gamba2);

		Rectangle braccio1 = new Rectangle(xLeft , yTop + 40, 10, 40);
		g.setColor(Color.BLACK);
		g.draw(braccio1);
		g.setColor(colore);
		g.fill(braccio1);

		Rectangle braccio2 = new Rectangle(xLeft + 40 , yTop + 40, 10, 40);
		g.setColor(Color.BLACK);
		g.draw(braccio2);
		g.setColor(colore);
		g.fill(braccio2);
		
		Ellipse2D.Double occhio1 = new Ellipse2D.Double(xLeft + 15, yTop + 5, 5, 5);
		g.setColor(Color.BLACK);
		g.fill(occhio1);

		Ellipse2D.Double occhio2 = new Ellipse2D.Double(xLeft + 30, yTop + 5, 5, 5);
		g.setColor(Color.BLACK);
		g.fill(occhio2);
		
		Rectangle bocca = new Rectangle(xLeft + 15, yTop + 20, 20, 5);
		g.setColor(Color.BLACK);
		g.draw(bocca);
		g.setColor(Color.WHITE);
		g.fill(bocca);

	}
	
	public void modificaColore(Color c){
		colore = c;
	}

	/*
	public String toString(){
		return getClass().getName() + "[x=" + x +", y=" +y +", energiaRobot=" + energiaRobot+ ", energSpost="+energSpost +", MAX_ENERGIA=" + MAX_ENERGIA+ ", colore=" +colore+"]";
	}
	
	public boolean equals(Object otherObject){
		
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		
		Robot other = (Robot) otherObject;
		return x == other.getX() && y == other.getY() && energiaRobot == other.getEnergia() && MAX_ENERGIA == other.getMaxEnergia() && energSpost == other.getEnergiaSpostamento() && colore == other.getColore();
		
	}
	
	public Robot clone(){
		
		try{
			return (Robot) super.clone();
		}
		catch(CloneNotSupportedException e){
			//non succede mai perchè implementiamo clonable
			return null;
		}
	}
	*/
	
}
