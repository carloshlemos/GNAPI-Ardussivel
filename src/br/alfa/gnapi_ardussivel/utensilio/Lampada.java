package br.alfa.gnapi_ardussivel.utensilio;

import android.content.Context;
import br.alfa.gnapi_ardussivel.GoogleSearchApi;


public class Lampada extends Utensilio {
	
	@Override
	public void ligar(Context context) {
		this.util.postData(context, "1");
		super.ligar(context);
	}
	
	@Override
	public void desligar(Context context) {
		this.util.postData(context, "2");
		super.desligar(context);
	}
}
