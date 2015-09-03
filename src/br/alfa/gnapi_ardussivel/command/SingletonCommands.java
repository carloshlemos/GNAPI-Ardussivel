package br.alfa.gnapi_ardussivel.command;

import java.util.HashMap;
import java.util.Map;

import br.alfa.gnapi_ardussivel.utensilio.ArCondicionado;
import br.alfa.gnapi_ardussivel.utensilio.Lampada;
import br.alfa.gnapi_ardussivel.utensilio.Portao;
import br.alfa.gnapi_ardussivel.utensilio.Televisor;
import br.alfa.gnapi_ardussivel.util.Command;

public class SingletonCommands {

	private static SingletonCommands instance;

	public Map<String, Command> mapCommands;

	public static SingletonCommands getInstance() {
		if (instance == null) {
			instance = new SingletonCommands();
		}
		return instance;
	}
	
	public SingletonCommands() {
		this.mapCommands = new HashMap<String, Command>();
		mapCommands.put("l�mpada ligar", new UtensilioLigarCommand(new Lampada()));
		mapCommands.put("l�mpada desligar", new UtensilioDesligarCommand(new Lampada()));
		mapCommands.put("ar condicionado ligar", new UtensilioLigarCommand(new ArCondicionado()));
		mapCommands.put("ar condicionado desligar", new UtensilioDesligarCommand(new ArCondicionado()));
		mapCommands.put("televisor ligar", new UtensilioLigarCommand(new Televisor()));
		mapCommands.put("televisor desligar", new UtensilioDesligarCommand(new Televisor()));
		mapCommands.put("port�o abrir", new UtensilioLigarCommand(new Portao()));
		mapCommands.put("port�o fechar", new UtensilioDesligarCommand(new Portao()));
	}

	public Map<String, Command> getMapCommands() {
		return mapCommands;
	}
}
