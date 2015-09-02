package br.alfa.gnapi_ardussivel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GoogleSearchReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String queryText = intent
				.getStringExtra(GoogleSearchApi.KEY_QUERY_TEXT);
		if (queryText.contains("lâmpada")) {
			if (queryText.contains("ligar") && !queryText.startsWith("acender")) {
				Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
