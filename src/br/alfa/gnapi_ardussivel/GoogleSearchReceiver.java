package br.alfa.gnapi_ardussivel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import br.alfa.gnapi_ardussivel.command.SingletonCommands;
import br.alfa.gnapi_ardussivel.util.Command;
import br.alfa.gnapi_ardussivel.util.GoogleNowUtil;

public class GoogleSearchReceiver extends BroadcastReceiver {
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

			queryText = queryText.toLowerCase();
			SingletonCommands commands = SingletonCommands.getInstance();
			Command command = commands.getMapCommands().get(queryText);
			if (command != null) {
				command.execute(context);
				GoogleNowUtil.resetGoogleNowOnly(context);
			} else {
				GoogleNowUtil.resetGoogleNowOnly(context);
			}
		}
	}
}
