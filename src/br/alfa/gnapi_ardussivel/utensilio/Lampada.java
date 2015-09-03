package br.alfa.gnapi_ardussivel.utensilio;

import android.content.Context;
import br.alfa.gnapi_ardussivel.GoogleSearchApi;


public class Lampada extends Utensilio {
	
	@Override
	public void ligar(Context context) {
		this.util.postData(context, "quarto", "lâmpada", "ligar", "1");
		GoogleSearchApi.speak(context, "Ok!, Lâmpada acesa");
		super.ligar(context);
	}
	
	@Override
	public void desligar(Context context) {
		this.util.postData(context, "quarto", "lâmpada", "desligar", "2");
		GoogleSearchApi.speak(context, "Ok!, Lâmpada apagada");
		super.desligar(context);
	}
}
