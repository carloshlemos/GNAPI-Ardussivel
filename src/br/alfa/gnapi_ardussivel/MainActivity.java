package br.alfa.gnapi_ardussivel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import br.alfa.gnapi_ardussivel.command.CommandAsyncTask;
import br.alfa.gnapi_ardussivel.domain.Comando;
import br.alfa.gnapi_ardussivel.persistence.ComandoDataSource;
import br.alfa.gnapi_ardussivel.persistence.ExpandableListAdapter;

public class MainActivity extends SherlockActivity {

	private static ComandoDataSource datasource;
	private List<Comando> listaComandos;

	private ExpandableListAdapter listAdapter;
	private ExpandableListView expandViewComandos;
	private Set<String> listDataHeader;
	private HashMap<String, List<Comando>> listDataChild = new HashMap<String, List<Comando>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		montaListaComandos();
	}

	public static ComandoDataSource getDatasource() {
		return MainActivity.datasource;
	}

	public List<Comando> getListaComandos() {
		return listaComandos;
	}

	public void montaListaComandos() {
		try {
			MainActivity.datasource = new ComandoDataSource(this);
			MainActivity.datasource.open();

			listaComandos = Comando.find(Comando.class, null, null, null, "UTENSILIO", null);

			expandViewComandos = (ExpandableListView) findViewById(R.id.expandViewComandos);

			expandViewComandos.setOnChildClickListener(new OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
						long id) {

					Comando comando = listDataChild.get(new ArrayList<String>(listDataHeader).get(groupPosition))
							.get(childPosition);
					
					new CommandAsyncTask(getApplicationContext()).execute(comando.getUrl());

					Toast.makeText(getApplicationContext(),
							new ArrayList<String>(listDataHeader).get(groupPosition) + " : " + listDataChild
									.get(new ArrayList<String>(listDataHeader).get(groupPosition)).get(childPosition),
							Toast.LENGTH_SHORT).show();
					return false;
				}
			});

			prepareListData();
			listAdapter = new ExpandableListAdapter(this, new ArrayList<String>(listDataHeader),
					listDataChild);
			expandViewComandos.setAdapter(listAdapter);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		List<Comando> listaChilds = new ArrayList<Comando>();

		listDataHeader = new HashSet<String>();
		for (Comando comando : this.listaComandos) {
			if (this.listDataChild != null && this.listDataChild.size() > 0) {
				listaChilds = this.listDataChild.get(comando.getUtensilio());
			}

			listDataHeader.add(comando.getUtensilio());

			if (listaChilds != null && listaChilds.size() > 0) {
				listaChilds.add(comando);
				this.listDataChild.put(comando.getUtensilio(), listaChilds);
			} else {
				List<Comando> comandos = new ArrayList<Comando>();
				comandos.add(comando);
				this.listDataChild.put(comando.getUtensilio(), comandos);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// First Menu Button
		menu.add("Cadastro").setIcon(R.drawable.ic_add_white_24dp)
				.setOnMenuItemClickListener(CadastroButtonClickListener)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		// Third Menu Button
		menu.add("Sair").setIcon(R.drawable.ic_home_white_24dp)
				.setOnMenuItemClickListener(ExitButtonClickListener)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return super.onCreateOptionsMenu(menu);
	}
	
	OnMenuItemClickListener CadastroButtonClickListener = new OnMenuItemClickListener() {

		public boolean onMenuItemClick(MenuItem item) {
			Intent it = new Intent(MainActivity.this, ListaComandoActivity.class);
			startActivityForResult(it, RESULT_OK);
			return false;
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		prepareListData();
	};
	

	OnMenuItemClickListener ExitButtonClickListener = new OnMenuItemClickListener() {

		public boolean onMenuItemClick(MenuItem item) {
			// Create a simple toast message
			Toast.makeText(MainActivity.this, "Botão Sair", Toast.LENGTH_SHORT).show();

			// Do something else
			return false;
		}
	};

}
