package br.alfa.gnapi_ardussivel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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

			if (queryText.contains("lâmpada")) {
				if (queryText.contains("ligar") && !queryText.startsWith("acender")) {
					GoogleSearchApi.speak(context, "Ok!, Acendendo a lâmpada");
					this.postData(context, "1");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				} else if (queryText.contains("desligar") && !queryText.startsWith("apagar")) {
					GoogleSearchApi.speak(context, "Ok!, Apagando a lâmpada");
					this.postData(context, "2");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				}
			}

			if (queryText.contains("ar condicionado")) {
				if (queryText.contains("ligar")) {
					GoogleSearchApi.speak(context, "Ok!, Ligando o ar condicionado");
					this.postData(context, "3");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				} else if (queryText.contains("desligar")) {
					GoogleSearchApi.speak(context, "Ok!, Desligando o ar condicionado");
					this.postData(context, "4");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				}
			}
			
			if (queryText.contains("tv")) {
				if (queryText.contains("ligar")) {
					GoogleSearchApi.speak(context, "Ok!, Ligando a TV");
					this.postData(context, "5");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				} else if (queryText.contains("desligar")) {
					GoogleSearchApi.speak(context, "Ok!, Desligando a TV");
					this.postData(context, "6");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				}
			}
			
			if (queryText.contains("portão")) {
				if (queryText.contains("abrir")) {
					GoogleSearchApi.speak(context, "Ok!, Abrindo o portão");
					this.postData(context, "7");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				} else if (queryText.contains("fechar")) {
					GoogleSearchApi.speak(context, "Ok!, Fechando o portão");
					this.postData(context, "8");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private HttpResponse postData(Context context, String comando) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://192.168.1.103:8080/restArduino/rest/arduino/enviarComando/?comando=" + comando);
		HttpResponse response = null;
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("ambiente", "quarto"));
			nameValuePairs.add(new BasicNameValuePair("utensilio", "lâmpada"));
			nameValuePairs.add(new BasicNameValuePair("acao", "ligar"));
			nameValuePairs.add(new BasicNameValuePair("comando", "1"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

			// Execute HTTP Post Request
			response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			Toast.makeText(context, "Erro ao Enviar o comando: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(context, "Erro ao Enviar o comando: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}

		return response;
	}
}
