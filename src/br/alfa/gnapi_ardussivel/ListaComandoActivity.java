package br.alfa.gnapi_ardussivel;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import br.alfa.gnapi_ardussivel.domain.Comando;
import br.alfa.gnapi_ardussivel.persistence.ComandoArrayAdapter;
import br.alfa.gnapi_ardussivel.persistence.ExpandableListAdapter;

public class ListaComandoActivity extends SherlockActivity {

	private List<Comando> listaComandos;

	private ExpandableListAdapter listAdapter;
	private ExpandableListView expandViewComandos;
	private Set<String> listDataHeader;
	private HashMap<String, List<Comando>> listDataChild = new HashMap<String, List<Comando>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_comandos);
		montaListaComandos();
	}

	public void montaListaComandos() {
		try {
			listaComandos = Comando.find(Comando.class, null, null, null, "UTENSILIO", null);

			ListView listViewComandos = (ListView) findViewById(R.id.listViewComandos);
			final ComandoArrayAdapter comandoArrayAdapter = new ComandoArrayAdapter(ListaComandoActivity.this,
					android.R.layout.simple_list_item_1, listaComandos);
			listViewComandos.setAdapter(comandoArrayAdapter);

			listViewComandos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
					Comando comando = (Comando) parent.getItemAtPosition(position);
					
					Intent it = new Intent(ListaComandoActivity.this, NovoComandoActivity.class);
					it.putExtra("comando", comando);
					startActivity(it);
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("").setIcon(R.drawable.ic_add_white_24dp).setOnMenuItemClickListener(NovoButtonClickListener)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		menu.add("").setIcon(R.drawable.ic_replay_white_24dp).setOnMenuItemClickListener(CancelarButtonClickListener)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return super.onCreateOptionsMenu(menu);
	}

	OnMenuItemClickListener NovoButtonClickListener = new OnMenuItemClickListener() {

		public boolean onMenuItemClick(MenuItem item) {
			Intent it = new Intent(ListaComandoActivity.this, NovoComandoActivity.class);
			startActivity(it);
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
