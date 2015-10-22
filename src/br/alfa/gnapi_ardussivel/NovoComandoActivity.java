package br.alfa.gnapi_ardussivel;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.alfa.gnapi_ardussivel.domain.Comando;

public class NovoComandoActivity extends SherlockActivity {

	private static final int REQUEST_CODE = 1234;
	private Dialog matchTextDialog;
	private TextView txtComandoVoz;
	private List<String> matchesText;
	private ListView textList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_comando);

		EditText txtComandoVoz = (EditText) findViewById(R.id.txtComandoVoz);
		txtComandoVoz.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (isConnected()) {
						Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
						intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
								RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
						startActivityForResult(intent, REQUEST_CODE);
					} else {
						Toast.makeText(getApplicationContext(), "Conexão não encontrada.", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
	}

	public boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = cm.getActiveNetworkInfo();
		if (net != null && net.isAvailable() && net.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

			matchTextDialog = new Dialog(NovoComandoActivity.this);
			matchTextDialog.setContentView(R.layout.dialog_matches_frag);
			matchTextDialog.setTitle("Selecione o comando de voz");
			textList = (ListView) matchTextDialog.findViewById(R.id.list);
			matchesText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
					matchesText);
			textList.setAdapter(adapter);
			textList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					((EditText) findViewById(R.id.txtComandoVoz)).setText(matchesText.get(position));
					matchTextDialog.hide();
				}
			});
			matchTextDialog.show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Salvar").setOnMenuItemClickListener(SalvarButtonClickListener)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		menu.add("Cancelar").setOnMenuItemClickListener(CancelarButtonClickListener)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return super.onCreateOptionsMenu(menu);
	}

	OnMenuItemClickListener SalvarButtonClickListener = new OnMenuItemClickListener() {

		public boolean onMenuItemClick(MenuItem item) {

			Comando comando = new Comando();
			comando.setAmbiente(((EditText) findViewById(R.id.txtAmbiente)).getText().toString());
			comando.setUtensilio(((EditText) findViewById(R.id.txtUtensilio)).getText().toString());
			comando.setAcao(((EditText) findViewById(R.id.txtAcao)).getText().toString());
			comando.setComando(((EditText) findViewById(R.id.txtComandoVoz)).getText().toString());
			comando.setUrl(((EditText) findViewById(R.id.txtURL)).getText().toString());
			comando.save();

			Toast.makeText(NovoComandoActivity.this, "Comando Salvo com Sucesso.", Toast.LENGTH_SHORT).show();
			finish();
			return false;
		}
	};

	OnMenuItemClickListener CancelarButtonClickListener = new OnMenuItemClickListener() {

		public boolean onMenuItemClick(MenuItem item) {
			finish();
			return false;
		}
	};

}
