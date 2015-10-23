package br.alfa.gnapi_ardussivel.persistence;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.alfa.gnapi_ardussivel.R;
import br.alfa.gnapi_ardussivel.domain.Comando;

public class ComandoArrayAdapter extends ArrayAdapter<Comando> {

	private Activity context;
	private HashMap<Comando, Integer> mIdMap = new HashMap<Comando, Integer>();

	public ComandoArrayAdapter(Activity context, int textViewResourceId, List<Comando> comandos) {
		super(context, textViewResourceId, comandos);
		this.context = context;
		for (int i = 0; i < comandos.size(); ++i) {
			mIdMap.put(comandos.get(i), i);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Comando comando = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_comandos, parent, false);
		}

		ImageView btnExcluir = (ImageView) convertView.findViewById(R.id.btnExcluir);
		TextView txtUtensilioAcao = (TextView) convertView.findViewById(R.id.txtUtensilioAcao);
		txtUtensilioAcao.setText(comando.getUtensilio() + " - " + comando.getAcao());

		btnExcluir.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Excluir registro selecionado?");
				builder.setCancelable(false);
				builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						comando.delete();
						notifyDataSetChanged();
					}
				});
				builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
			}
		});

		return convertView;
	}

	@Override
	public long getItemId(int position) {
		Comando item = getItem(position);
		return mIdMap.get(item);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}
