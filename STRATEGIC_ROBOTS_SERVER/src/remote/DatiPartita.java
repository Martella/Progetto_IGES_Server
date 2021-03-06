package remote;

import java.io.Serializable;

import controllo.ControlloreInterattivo;
import elementiScenario.Scenario;

public class DatiPartita implements Serializable{

	private String modalitāPartita;
	private Scenario scenario;
	private int [] numeroMossa;
	private ControlloreInterattivo controlloreInterattivo1;
	private ControlloreInterattivo controlloreInterattivo2;
	
	public DatiPartita() {
		modalitāPartita = null;
		scenario = null;
		numeroMossa = null;
		controlloreInterattivo1 = null;
		controlloreInterattivo2 = null;
	}

	;
	
	public void modificaModalitāPartita(String m){
		modalitāPartita = m;
	}
	
	public void modificaNumeroMossa(int [] n){
		numeroMossa = n;
	}
	
	public void modificaScenario(Scenario s){
		scenario = s;
	}
	
	public void modificaControlloreInterattivo1(ControlloreInterattivo contInt){
		controlloreInterattivo1 = contInt;
	}

	public void modificaControlloreInterattivo2(ControlloreInterattivo contInt){
		controlloreInterattivo2 = contInt;
	}

	public String getModalitāPartita(){
		return modalitāPartita;
	}
	
	public int [] getNumeroMossa(){
		return numeroMossa;
	}
	
	public Scenario getScenario(){
		return scenario;
	}
	
	public ControlloreInterattivo getControlloreInterattivo1(){
		return controlloreInterattivo1;
	}

	public ControlloreInterattivo getControlloreInterattivo2(){
		return controlloreInterattivo2;
	}
	
}
