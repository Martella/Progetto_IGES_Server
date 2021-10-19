package eccezioni;

public class CriticalStatusException extends RuntimeException{

	public CriticalStatusException(){
		super("un robot risulta danneggiato per almeno il 75% dopo un attacco avversario");
	}

	public CriticalStatusException(String msg){
		super(msg);
	}

}
