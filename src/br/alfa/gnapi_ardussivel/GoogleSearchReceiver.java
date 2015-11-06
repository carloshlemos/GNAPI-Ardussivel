package br.alfa.gnapi_ardussivel;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.util.Log;
import android.widget.Toast;
import br.alfa.gnapi_ardussivel.command.CommandAsyncTask;
import br.alfa.gnapi_ardussivel.domain.Comando;
import br.alfa.gnapi_ardussivel.persistence.ComandoDataSource;

public class GoogleSearchReceiver extends BroadcastReceiver {

	private Map<String, Comando> mapComandos;

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (action == null) {
			return;
		}

		if (action.equals(GoogleSearchApi.INTENT_NEW_SEARCH)) {
			String queryText = intent.getStringExtra(GoogleSearchApi.KEY_QUERY_TEXT);

			if (queryText == null) {
				return;
			}

			queryText = queryText.toLowerCase(new Locale("pt", "br"));
			ComandoDataSource datasource = MainActivity.getDatasource();

			try {
				if (datasource != null) {
					List<Comando> comandos = datasource.listarTodos();

					if (comandos != null && comandos.size() > 0) {
						Log.w(GoogleSearchReceiver.class.getName(),
								"################### COMANDOS GNAPI ##################" + comandos.size());
						this.mapComandos = new HashMap<String, Comando>();

						for (Comando comando : comandos) {
							this.mapComandos.put(comando.getComando(), comando);
						}

						Comando comando = this.mapComandos.get(queryText);

						if (comando != null) {
							MainActivity.getTts().initQueue("Enviando Comando.");
							new CommandAsyncTask(context).execute(comando.getUrl());
						}					}
				}else {
					Log.w(GoogleSearchReceiver.class.getName(),
							"################### COMANDOS GNAPI - SEM CONEXÃO ##################");
				}
			} catch (SQLException e) {
				MainActivity.getTts().initQueue("Erro ao consultar comando");
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}
}
