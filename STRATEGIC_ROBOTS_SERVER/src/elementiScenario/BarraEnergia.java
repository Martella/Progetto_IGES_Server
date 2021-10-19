package elementiScenario;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

public class BarraEnergia implements Serializable {

	private int energia;
	private int maxEnergia;
	
	public BarraEnergia(){
		energia = 0;
		maxEnergia = 100;
	}

	public BarraEnergia(int e){
		energia = e;
		maxEnergia = 100;
	}
	
	/**
	 * Disegna la BarraEnergia in un grapichs passato come parametro
	 * @param gg Grapichs su cui disegnare
	 */
	public void disegna(Graphics2D gg){
		
		Graphics2D g = gg;
		
		Rectangle energiaRettangolo = new Rectangle(10, 127, getMaxEnergia(), 3);
		Rectangle barraEnergia = new Rectangle(10, 127, getEnergia(), 3);
		
		g.setColor(Color.BLACK);
		g.draw(energiaRettangolo);
				
		g.draw(barraEnergia);
		g.drawString("" + getEnergia(), 115, 133);
		
		if (getEnergia() > 25) g.setColor(Color.CYAN);
		else g.setColor(Color.RED);
		
		g.fill(barraEnergia);


		
	}

	public int getEnergia() {
		return energia;
	}

	public int getMaxEnergia() {
		return maxEnergia;
	}

}
