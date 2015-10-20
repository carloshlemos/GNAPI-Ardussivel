package br.alfa.gnapi_ardussivel.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.alfa.gnapi_ardussivel.domain.Comando;

public class ComandoDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_AMBIENTE,
			MySQLiteHelper.COLUMN_UTENSILIO, MySQLiteHelper.COLUMN_ACAO, MySQLiteHelper.COLUMN_COMANDO,
			MySQLiteHelper.COLUMN_URL };

	public ComandoDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException, IOException, InterruptedException {
		database = dbHelper.openDataBase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<Comando> listarTodos() {
		List<Comando> comandos = new ArrayList<Comando>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMANDO, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Comando comando = cursorToComando(cursor);
			comandos.add(comando);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return comandos;
	}

	private Comando cursorToComando(Cursor cursor) {
		Comando comando = new Comando();
		comando.setAmbiente(cursor.getString(1));
		comando.setUtensilio(cursor.getString(2));
		comando.setAcao(cursor.getString(3));
		comando.setComando(cursor.getString(4));
		comando.setUrl(cursor.getString(5));
		return comando;
	}
}
