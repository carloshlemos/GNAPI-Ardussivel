package br.alfa.gnapi_ardussivel;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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

			if (queryText.contains("teste")) {
				if (queryText.contains("android")) {
					// Create a new HttpClient and Post Header
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(
							"http://192.168.1.103:8080/restArduino/rest/arduino/enviarComando/?comando=1");

					try {
						// Execute HTTP Post Request
						httpPost.setHeader("Content-type", "application/json");
						HttpResponse response = httpClient.execute(httpPost);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}

					// SingletonCommands commands =
					// SingletonCommands.getInstance();
					// Command command = commands.getMapCommands().get("lâmpada
					// ligar");
					// Util util = new Util();
					// util.postData(context, "quarto", "lâmpada", "ligar",
					// "1");
					// command.execute(context);

					Toast.makeText(context, "Funcionou!!!!", Toast.LENGTH_SHORT).show();
				}
			}

			// SingletonCommands commands = SingletonCommands.getInstance();
			// Command command = commands.getMapCommands().get(queryText);
			// if (command != null) {
			// GoogleSearchApi.speak(context, "Ok!");
			// command.execute(context);
			// }
		}
	}
}
