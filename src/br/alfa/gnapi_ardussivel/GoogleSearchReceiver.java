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
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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
					this.postData(context, "quarto", "lâmpada", "ligar", "1");
					GoogleSearchApi.speak(context, "Ok!, Lâmpada acesa");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				} else if (queryText.contains("desligar") && !queryText.startsWith("apagar")) {
					this.postData(context, "quarto", "lâmpada", "desligar", "2");
					GoogleSearchApi.speak(context, "Ok!, Lâmpada apagada");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				}
			}

			if (queryText.contains("ar condicionado")) {
				if (queryText.contains("ligar")) {
					this.postData(context, "quarto", "ar condicionado", "ligar", "3");
					GoogleSearchApi.speak(context, "Ok!, Ar condicionado ligado");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				} else if (queryText.contains("desligar")) {
					this.postData(context, "quarto", "ar condicionado", "desligar", "4");
					GoogleSearchApi.speak(context, "Ok!, Ar condicionado desligado");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				}
			}

			if (queryText.contains("tv")) {
				if (queryText.contains("ligar")) {
					this.postData(context, "sala", "tv", "ligar", "5");
					GoogleSearchApi.speak(context, "Ok!, TV ligada");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				} else if (queryText.contains("desligar")) {
					this.postData(context, "sala", "tv", "desligar", "6");
					GoogleSearchApi.speak(context, "Ok!, TV desligada");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				}
			}

			if (queryText.contains("portão")) {
				if (queryText.contains("abrir")) {
					this.postData(context, "entrada", "portao", "abrir", "7");
					GoogleSearchApi.speak(context, "Ok!, Portão aberto");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				} else if (queryText.contains("fechar")) {
					GoogleSearchApi.speak(context, "Ok!, Portão fechado");
					this.postData(context, "entrada", "portao", "fechar", "8");
					Toast.makeText(context, "Comando recebido!", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private HttpResponse postData(Context context, String ambiente, String utensilio, String acao, String comando) {
		// Create a new HttpClient and Post Header
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://192.168.1.103:8080/restArduino/rest/arduino/enviarComando/?comando=" + comando);
		HttpResponse response = null;
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("ambiente", ambiente));
			nameValuePairs.add(new BasicNameValuePair("utensilio", utensilio));
			nameValuePairs.add(new BasicNameValuePair("acao", acao));
			nameValuePairs.add(new BasicNameValuePair("comando", comando));

			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wInfo = wifiManager.getConnectionInfo();
			String macAddress = wInfo.getMacAddress();

			nameValuePairs.add(new BasicNameValuePair("macAddress", macAddress));

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

			// Execute HTTP Post Request
			response = httpClient.execute(httpPost);

		} catch (ClientProtocolException e) {
			GoogleSearchApi.speak(context, "Ops!, Não consegui realizar a ação, tente novamente.");
			Toast.makeText(context, "Erro ao Enviar o comando: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			GoogleSearchApi.speak(context, "Ops!, Não consegui realizar a ação, tente novamente.");
			Toast.makeText(context, "Erro ao Enviar o comando: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}

		return response;
	}
}
