package br.alfa.gnapi_ardussivel.command;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.alfa.gnapi_ardussivel.util.TTSManager;

public class TTSAsyncTask extends AsyncTask<String, Integer, String> {

	private Context context;
	private TTSManager tts;

	public TTSAsyncTask(Context context) {
		this.context = context;
		this.tts = new TTSManager();
		this.tts.init(context);
	}

	@Override
	protected String doInBackground(String... params) {
		
		for (String param : params) {
			this.tts.addQueue(param);			
		}
		
		return "Comando Executado!!";
	}
	
	@Override
	protected void onPostExecute(String result) {
		Toast.makeText(context, "Resultado: " + result, Toast.LENGTH_LONG).show();		
	}

}
