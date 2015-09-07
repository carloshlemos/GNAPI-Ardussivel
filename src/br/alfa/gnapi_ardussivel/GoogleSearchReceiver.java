package br.alfa.gnapi_ardussivel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import br.alfa.gnapi_ardussivel.command.SingletonCommands;

public class GoogleSearchReceiver extends BroadcastReceiver {
	private Context context;
	// private TextToSpeech tts;

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

			this.context = context;
			queryText = queryText.toLowerCase();
			
			queryText = queryText.toLowerCase();
			SingletonCommands commands = SingletonCommands.getInstance();
			commands.getMapCommands().get(queryText).execute(context);

//			if (queryText.contains("lâmpada")) {
//				if (queryText.contains("ligar")) {
//					new MyAsyncTask(context).execute("1");
//				}
//
//				if (queryText.contains("desligar")) {
//					new MyAsyncTask(context).execute("2");
//				}
//				intent.getBooleanExtra(GoogleSearchApi.KEY_VOICE_TYPE, false);
//			}
		}
	}
}
