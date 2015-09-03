package br.alfa.gnapi_ardussivel.utensilio;

import android.content.Context;
import br.alfa.gnapi_ardussivel.GoogleSearchApi;


public class Lampada extends Utensilio {
	
	@Override
	public void ligar(Context context) {
		this.util.postData(context, "quarto", "l�mpada", "ligar", "1");
		GoogleSearchApi.speak(context, "Ok!, L�mpada acesa");
		super.ligar(context);
	}
	
	@Override
	public void desligar(Context context) {
		this.util.postData(context, "quarto", "l�mpada", "desligar", "2");
		GoogleSearchApi.speak(context, "Ok!, L�mpada apagada");
		super.desligar(context);
	}
}
