package br.alfa.gnapi_ardussivel.command;

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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import br.alfa.gnapi_ardussivel.util.TTSManager;

public class MyAsyncTask extends AsyncTask<String, Integer, String> {

	private Context context;
	private TTSManager ttsManager;

	public MyAsyncTask(Context context) {
		this.context = context;
	}

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
		ttsManager = new TTSManager();
		ttsManager.init(context);
		ttsManager.initQueue(result);
		Toast.makeText(context, "Resultado: " + result, Toast.LENGTH_LONG).show();
	}
}
