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

public class CommandAsyncTask extends AsyncTask<String, Integer, String> {

	private Context context;

	public CommandAsyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		String resposta = null;
		if (params != null && params.length > 0) {
			HttpClient httpclient = new DefaultHttpClient();
			Log.w(CommandAsyncTask.class.getName(),
					"################### HOST RECEBIDO GNAPI ##################" + params[0]);
			HttpPost httpPost = new HttpPost(params[0]);

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("ambiente", "quarto"));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httpPost.setHeader("Content-type", "application/json");
				HttpResponse response = httpclient.execute(httpPost);
				resposta = EntityUtils.toString(response.getEntity());
				Log.v("MyAsyncTask", resposta);
			} catch (ClientProtocolException e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		return resposta;
	}

	@Override
	protected void onPostExecute(String result) {
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		// new TTSAsyncTask(context).execute(result);
	}
}
