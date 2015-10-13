package br.alfa.gnapi_ardussivel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.util.Log;
import br.alfa.gnapi_ardussivel.command.CommandAsyncTask;
import br.alfa.gnapi_ardussivel.persistence.ComandoDataSource;
import br.alfa.gnapi_ardussivel.utensilio.Comando;

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
				datasource.open();
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
						new CommandAsyncTask(context).execute(comando.getUrl());
						// GoogleNowUtil.resetGoogleNow(context);
					} else {
						// GoogleNowUtil.resetGoogleNow(context);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
