package elementiScenario;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import interfacce.Disegnabile;

public class BancoRifornimenti implements Disegnabile, Serializable{
	
	private Color colore;
	private int x;
	private int y;
	private int riservaEnergia;
	
	
	public BancoRifornimenti(){
		x = 0;
		y = 0;
		colore = Color.CYAN;
		riservaEnergia = 250;
	}

	/*public BancoRifornimenti(Color c){
		x = 0;
		y = 0;
		colore = c;
		riservaEnergia = 250;
	}
	*/

	public void modificaX(int xx){
		x = xx;
	}
	
	public void modificaY(int yy){
		y = yy;
	}

	public void modificaRiservaEnergia(int r){
		riservaEnergia = r;
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public int getRiservaEnergia(){
		return riservaEnergia;
	}

	public String getTipo(){
		return "bancoRifornimenti";
	}

	/**
	 * Disegna il BancoRifornimenti in un grapichs passato come parametro
	 * @param gg Grapichs su cui disegnare
	 */
	public void disegna(Graphics2D gg){
		
		Graphics2D g =  gg;
		int xLeft = 50;
		int yTop = 15;
		

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

		Rectangle croce1 = new Rectangle(xLeft +23, yTop + 50, 4, 40);
		g.setColor(Color.PINK);
		g.fill(croce1);

		Rectangle croce2 = new Rectangle(xLeft +15, yTop + 55, 20, 3);
		g.setColor(Color.PINK);
		g.fill(croce2);

		Rectangle banco = new Rectangle(xLeft -40, yTop +80 , 170, 100);
		g.setColor(Color.BLACK);
		g.draw(banco);
		//colore marrone
		g.setColor(new Color(150, 20, 20));
		g.fill(banco);
	}
	

}
