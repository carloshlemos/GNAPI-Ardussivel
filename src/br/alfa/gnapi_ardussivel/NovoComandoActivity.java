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
	private List<String> matchesText;
	private ListView textList;
	private Comando comando;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_comando);
		comando = new Comando();

		Long idComando = (Long) getIntent().getSerializableExtra("idComando");
		comando = comando.findById(Comando.class, idComando);

		if (comando != null) {
			TextView txtAmbiente = (EditText) findViewById(R.id.txtAmbiente);
			txtAmbiente.setText(comando.getAmbiente() != null ? comando.getAmbiente() : "");
			TextView txtAcao = (EditText) findViewById(R.id.txtAcao);
			txtAcao.setText(comando.getAcao() != null ? comando.getAcao() : "");
			TextView txtUtensilio = (EditText) findViewById(R.id.txtUtensilio);
			txtUtensilio.setText(comando.getUtensilio() != null ? comando.getUtensilio() : "");
			TextView txtComandoVoz = (EditText) findViewById(R.id.txtComandoVoz);
			txtComandoVoz.setText(comando.getComando() != null ? comando.getComando() : "");
			TextView txtUrl = (EditText) findViewById(R.id.txtURL);
			txtUrl.setText(comando.getUrl() != null ? comando.getUrl() : "");
		}

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

	public Comando getComando() {
		return comando;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("").setIcon(R.drawable.ic_done_white_24dp).setOnMenuItemClickListener(SalvarButtonClickListener)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		menu.add("").setIcon(R.drawable.ic_replay_white_24dp).setOnMenuItemClickListener(CancelarButtonClickListener)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return super.onCreateOptionsMenu(menu);
	}

	OnMenuItemClickListener SalvarButtonClickListener = new OnMenuItemClickListener() {

		public boolean onMenuItemClick(MenuItem item) {
			Comando comando = getComando();

			if (comando == null) {
				comando = new Comando();
			}

			comando.setAmbiente(((EditText) findViewById(R.id.txtAmbiente)).getText().toString());
			comando.setUtensilio(((EditText) findViewById(R.id.txtUtensilio)).getText().toString());
			comando.setAcao(((EditText) findViewById(R.id.txtAcao)).getText().toString());
			comando.setComando(((EditText) findViewById(R.id.txtComandoVoz)).getText().toString());
			comando.setUrl(((EditText) findViewById(R.id.txtURL)).getText().toString());

			comando.save();

			Toast.makeText(NovoComandoActivity.this, "Comando Salvo com Sucesso.", Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK);
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
