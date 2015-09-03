package br.alfa.gnapi_ardussivel.utensilio;

import android.content.Context;
import br.alfa.gnapi_ardussivel.GoogleSearchApi;

public class Televisor extends Utensilio {
	@Override
	public void ligar(Context context) {
		this.util.postData(context, "sala", "televis�o", "ligar", "5");
		GoogleSearchApi.speak(context, "Ok!, TV ligada");
		super.ligar(context);
	}
	
	@Override
	public void desligar(Context context) {
		this.util.postData(context, "sala", "televis�o", "desligar", "6");
		GoogleSearchApi.speak(context, "Ok!, TV desligada");
		super.desligar(context);
	}
}
