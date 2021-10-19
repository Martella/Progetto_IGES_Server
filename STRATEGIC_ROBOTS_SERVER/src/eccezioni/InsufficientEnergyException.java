package eccezioni;



public class InsufficientEnergyException extends Exception{

	public InsufficientEnergyException(){
		super("Mossa non eseguibile per mancanza di energia");
	}
	
	public InsufficientEnergyException(String msg){
		super(msg);
	}

	
}
