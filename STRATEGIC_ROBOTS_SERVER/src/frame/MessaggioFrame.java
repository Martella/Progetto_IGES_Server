package frame;

import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MessaggioFrame extends JFrame {

	private final int FRAME_WIDTH = 100;
	private final int FRAME_HEIGHT = 100;
	
	public MessaggioFrame(String m){

		String mex = m;
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		Panel panel = new Panel();
		Label label = new Label(mex);
		
		panel.add(label);
		panel.add(creaButton());
		
		add(panel);
	}
	
	public MessaggioFrame(String m, int larghezza, int altezza){

		String mex = m;
		setSize(larghezza, altezza);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		Panel panel = new Panel();
		Label label = new Label(mex);
		
		panel.add(label);
		panel.add(creaButton());
		
		add(panel);
	}
	
	public JButton creaButton(){
		
		JButton button = new JButton("Ok");

		
		class clickButton implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				MessaggioFrame.this.dispose();				
			}			
		}
		
		ActionListener listener = new clickButton();
		button.addActionListener(listener);
		
		return button;
	}
}
