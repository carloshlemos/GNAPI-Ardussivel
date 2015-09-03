package br.alfa.gnapi_ardussivel.command;

import android.content.Context;
import br.alfa.gnapi_ardussivel.utensilio.Utensilio;
import br.alfa.gnapi_ardussivel.util.Command;

public class UtensilioLigarCommand implements Command {

	private Utensilio utensilio;

	public UtensilioLigarCommand(Utensilio utensilio) {
		this.utensilio = utensilio;
	}

	@Override
	public void execute(Context context) {
		this.utensilio.ligar(context);
	}

}
