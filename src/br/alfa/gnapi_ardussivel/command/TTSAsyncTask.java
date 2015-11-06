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
	}

	@Override
	protected String doInBackground(String... params) {
		this.tts.initQueue(params.toString());
		
		return "Comando Executado!!";
	}
	
	@Override
	protected void onPostExecute(String result) {
		Toast.makeText(context, "Resultado: " + result, Toast.LENGTH_LONG).show();		
	}

	public TTSManager getTts() {
		return tts;
	}

	public void setTts(TTSManager tts) {
		this.tts = tts;
	}
	
	

}
