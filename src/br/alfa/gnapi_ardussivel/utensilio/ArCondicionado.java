package br.alfa.gnapi_ardussivel.utensilio;

import android.content.Context;

public class ArCondicionado extends Utensilio {
	
	@Override
	public void ligar(Context context) {
		this.util.postData(context, "3");
		super.ligar(context);
	}
	
	@Override
	public void desligar(Context context) {
		this.util.postData(context, "4");
		super.desligar(context);
	}

}
