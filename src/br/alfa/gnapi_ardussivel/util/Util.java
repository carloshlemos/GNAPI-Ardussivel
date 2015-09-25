package br.alfa.gnapi_ardussivel.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.widget.Toast;
import br.alfa.gnapi_ardussivel.GoogleSearchApi;
import br.alfa.gnapi_ardussivel.command.CommandAsyncTask;

public class Util {
	
	public void postData(Context context, String comando) {
		new CommandAsyncTask(context).execute(comando);
	}
	
	public HttpResponse postData(Context context, String ambiente, String utensilio, String acao, String comando) {
		new CommandAsyncTask(context).execute(comando);
		
		// Create a new HttpClient and Post Header
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://10.6.156.103:8080/restArduino/rest/arduino/enviarComando/?comando=" + comando);
		HttpResponse httpResponse = null;
		try {
//			String json = "";
//
//			// build jsonObject
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.accumulate("ambiente", ambiente);
//			jsonObject.accumulate("utensilio", utensilio);
//			jsonObject.accumulate("acao", acao);
//			jsonObject.accumulate("comando", comando);
//
//			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//			WifiInfo wInfo = wifiManager.getConnectionInfo();
//			String macAddress = wInfo.getMacAddress();
//
//			jsonObject.accumulate("macAddress", macAddress);
//
//			// convert JSONObject to JSON to String
//			json = jsonObject.toString();

			// ** Alternative way to convert Person object to JSON string using
			// Jackson Lib
			// ObjectMapper mapper = new ObjectMapper();
			// json = mapper.writeValueAsString(person);

			// set json to StringEntity
//			StringEntity se = new StringEntity(json);

			// set httpPost Entity
			// httpPost.setEntity(se);

			httpPost.setHeader("Content-type", "application/json");
			httpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			GoogleSearchApi.speak(context, "Ops!, Não consegui realizar a ação, tente novamente.");
			Toast.makeText(context, "Erro ao Enviar o comando: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			GoogleSearchApi.speak(context, "Ops!, Não consegui realizar a ação, tente novamente.");
			Toast.makeText(context, "Erro ao Enviar o comando: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
		}

		return httpResponse;
	}

}
