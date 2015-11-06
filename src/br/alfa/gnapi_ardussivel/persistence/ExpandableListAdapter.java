package br.alfa.gnapi_ardussivel.persistence;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.alfa.gnapi_ardussivel.MainActivity;
import br.alfa.gnapi_ardussivel.R;
import br.alfa.gnapi_ardussivel.command.CommandAsyncTask;
import br.alfa.gnapi_ardussivel.domain.Comando;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Activity context;
	private List<String> listDataHeader;
	private HashMap<String, List<Comando>> listDataChild;
	private List<Comando> comandos;

	public ExpandableListAdapter(Activity context, List<String> listDataHeader,
			HashMap<String, List<Comando>> listChildData) {
		this.context = context;
		this.listDataHeader = listDataHeader;
		this.listDataChild = listChildData;
	}

	@Override
	public Comando getChild(int groupPosition, int childPosititon) {
		comandos = this.listDataChild.get(this.listDataHeader.get(groupPosition));
		return comandos.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosititon) {
		return childPosititon;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {

		final Comando comando = getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item_main, null);
		}

		Button btnAcao = (Button) convertView.findViewById(R.id.btnAcao);
		btnAcao.setText(comando.getAcao());
		btnAcao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivity.getTts().initQueue("Enviando Comando.");
				Toast.makeText(context, "Enviando Comando.", Toast.LENGTH_SHORT).show();
				new CommandAsyncTask(context).execute(comando.getUrl());
			}
		});
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group_main, null);
		}

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
