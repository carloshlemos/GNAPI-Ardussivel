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
import org.apache.http.util.EntityUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GoogleSearchReceiver extends BroadcastReceiver {
	private Context context;

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
				if (queryText.contains("ligar")) {
					this.context = context;
					new MyAsyncTask().execute("1");
					Toast.makeText(context, "Funcionou!!!!", Toast.LENGTH_SHORT).show();
				}
				
				if (queryText.contains("desligar")) {
					this.context = context;
					new MyAsyncTask().execute("2");
					Toast.makeText(context, "Funcionou!!!!", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private class MyAsyncTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://192.168.1.103:8080/restArduino/rest/arduino/enviarComando/?comando=" + params[0]);
			String resposta = null;

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("ambiente", "quarto"));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httpPost.setHeader("Content-type", "application/json");
				HttpResponse response = httpclient.execute(httpPost);
				resposta = EntityUtils.toString(response.getEntity());
				Log.v("MainActivity", resposta);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return resposta;
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(context, "Comando Executado, Resultado: " + result, Toast.LENGTH_LONG).show();
		}
	}

}
