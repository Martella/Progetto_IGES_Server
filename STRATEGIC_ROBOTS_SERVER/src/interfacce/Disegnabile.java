package interfacce;
import java.awt.Graphics2D;

public interface Disegnabile {
	void disegna(Graphics2D gg);
	int getX();
	int getY();
	void modificaX(int xx);
	void modificaY(int yy);
	String getTipo();
}
