package frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import elementiScenario.BancoRifornimenti;
import elementiScenario.Scenario;
import robot.Robot;

public class BancoRifornimentiFrame extends JFrame {

	final int FRAME_WIDTH = 500;
	final int FRAME_HEIGHT = 300;
	private Scenario scenario;
	private BancoRifornimenti bancoRifornimenti;

	public BancoRifornimentiFrame(BancoRifornimenti b, Robot r, Scenario s){
		bancoRifornimenti = b;
		scenario = s;
		Robot robotDaServire = r;
		setSize(FRAME_WIDTH, FRAME_WIDTH);
		setResizable(false);
		setTitle("Banco Rifornimenti");
		
		add(panelRobot(robotDaServire));
	}

		
	public JPanel panelRobot(Robot r){
		Robot robotDaServire = r;
		JPanel panel = new JPanel(new GridLayout(3, 1));
		Label labelRiserva = new Label("Riserva di energia disponibile: " + bancoRifornimenti.getRiservaEnergia());
		
		JRadioButton ricaricaEnergia = new JRadioButton("Ricarica energia");
		ButtonGroup gruppo = new ButtonGroup();
		gruppo.add(ricaricaEnergia);
		ricaricaEnergia.setSelected(true);
		
		JButton bottone = new JButton("Esegui operazione");
		
		panel.add(labelRiserva);
		panel.add(ricaricaEnergia);
		panel.add(bottone);

		class clickBottone implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				
				if (ricaricaEnergia.isSelected()){
					if(bancoRifornimenti.getRiservaEnergia() > 0 ){
						if(bancoRifornimenti.getRiservaEnergia() >= robotDaServire.getMaxEnergia() - robotDaServire.getEnergia()){
							bancoRifornimenti.modificaRiservaEnergia(bancoRifornimenti.getRiservaEnergia() -(robotDaServire.getMaxEnergia() - robotDaServire.getEnergia()) );
							robotDaServire.modificaEnergia(robotDaServire.getMaxEnergia());
						}
						else{
							robotDaServire.modificaEnergia(robotDaServire.getEnergia()+bancoRifornimenti.getRiservaEnergia());
							bancoRifornimenti.modificaRiservaEnergia(0);
						}
					}
				}
				
				
				scenario.modificaGrigliaRepaintCasella(robotDaServire.getX(), robotDaServire.getY());
				BancoRifornimentiFrame.this.dispose();
			}
			
		}
		
		ActionListener listener = new clickBottone();
		bottone.addActionListener(listener);

		return panel;		
	}

	
	
}
