package elementiScenario;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Random;

import interfacce.Disegnabile;
import interfacce.Energetico;

public class Ostacolo implements Disegnabile, Energetico, Serializable{


	private int energia;
	private int x;
	private int y;
	private Color colore;

	public Ostacolo(){
		x= 0;
		y= 0;
		energia = 100;
		colore = Color.LIGHT_GRAY;
	}

	/**
	 * 
	 * @param xx posizione x
	 * @param yy posizione y
	 */
	public Ostacolo(int xx, int yy){
		x = xx;
		y = yy;
		energia = 100;
		colore = Color.LIGHT_GRAY;
	}
	
	/**
	 * 
	 * @param xx posizione x
	 * @param yy posizione y
	 * @param c colore dell'Ostacolo
	 */
	public Ostacolo(int xx, int yy, Color c){
		x = xx;
		y = yy;
		energia = 100;
		colore = c;
	}

	
	public void spostamentoX(int xx){
		x= xx;
	}

	public void spostamentoY(int yy){
		y= yy;
	}
	
	public void modificaX(int xx){
		x= xx;
	}

	public void modificaY(int yy){
		y= yy;
	}

	
	public void modificaEnergia(int r){
		energia = r;
	}

	
	public int getEnergia(){
		return energia;
	}
	
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	/**
	 * Disegna l'Ostacolo in un grapichs passato come parametro
	 * @param gg Grapichs su cui disegnare
	 */
	public void disegna(Graphics2D gg){
		
		Graphics2D g =  gg;
		
		int xLeft = 25;
		int yTop = 18;

		Rectangle ost = new Rectangle(xLeft , yTop, 100,100 );
		

		g.setColor(Color.BLACK);
		g.draw(ost);
		g.setColor(colore);
		g.fill(ost);
		g.setColor(Color.BLACK);

	}
	
	public String getTipo(){
		return "ostacolo";
	}

}
