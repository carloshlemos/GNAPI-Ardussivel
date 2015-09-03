package br.alfa.gnapi_ardussivel.utensilio;

import android.content.Context;
import br.alfa.gnapi_ardussivel.GoogleSearchApi;

public class Portao extends Utensilio {

	@Override
	public void ligar(Context context) {
		this.util.postData(context, "entrada", "portao", "abrir", "7");
		GoogleSearchApi.speak(context, "Ok!, Portão aberto");
		super.ligar(context);
	}

	@Override
	public void desligar(Context context) {
		this.util.postData(context, "entrada", "portao", "fechar", "8");
		GoogleSearchApi.speak(context, "Ok!, Portão fechado");
		super.desligar(context);
	}

}
