package br.alfa.gnapi_ardussivel.utensilio;

import android.content.Context;
import android.widget.Toast;
import br.alfa.gnapi_ardussivel.util.Util;

public class Utensilio {
	
	protected Util util;

	public Utensilio() {
		this.util = new Util();
	}

	public void ligar(Context context) {
		Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
	}

	public void desligar(Context context) {
		Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
	}

}
