package br.alfa.gnapi_ardussivel.persistence;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import br.alfa.gnapi_ardussivel.domain.Comando;

public class ComandoArrayAdapter extends ArrayAdapter<Comando> {

	HashMap<Comando, Integer> mIdMap = new HashMap<Comando, Integer>();

	public ComandoArrayAdapter(Context context, int textViewResourceId, List<Comando> comandos) {
		super(context, textViewResourceId, comandos);
		for (int i = 0; i < comandos.size(); ++i) {
			mIdMap.put(comandos.get(i), i);
		}
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
