package br.alfa.gnapi_ardussivel.utensilio;

import br.alfa.gnapi_ardussivel.GoogleSearchApi;
import android.content.Context;

public class ArCondicionado extends Utensilio {
	
	@Override
	public void ligar(Context context) {
		this.util.postData(context, "quarto", "ar condicionado", "ligar", "3");
		GoogleSearchApi.speak(context, "Ok!, Ar condicionado ligado");
		super.ligar(context);
	}
	
	@Override
	public void desligar(Context context) {
		this.util.postData(context, "quarto", "ar condicionado", "desligar", "4");
		GoogleSearchApi.speak(context, "Ok!, Ar condicionado desligado");
		super.desligar(context);
	}

}
