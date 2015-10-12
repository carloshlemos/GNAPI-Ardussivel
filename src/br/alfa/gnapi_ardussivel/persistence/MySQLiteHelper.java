package br.alfa.gnapi_ardussivel.persistence;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private final Context myContext;

	public static final String TABLE_COMANDO = "Comando";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_AMBIENTE = "ambiente";
	public static final String COLUMN_UTENSILIO = "utensilio";
	public static final String COLUMN_ACAO = "acao";
	public static final String COLUMN_COMANDO = "comando";
	public static final String COLUMN_URL = "url";

	private static final String DB_PATH = "/data/data/br.alfa.Ardussivel/databases/";
	private static final String DATABASE_NAME = "ardussivel.db";
	private static final int DATABASE_VERSION = 1;

	private boolean checkDataBase() {
		boolean checkdb = false;
		try {
			String myPath = DB_PATH + DATABASE_NAME;
			File dbfile = new File(myPath);
			checkdb = dbfile.exists();
		} catch (SQLiteException e) {
			Log.i("Database doesn't exist", e.getMessage());
		}

		return checkdb;
	}

	public SQLiteDatabase openDataBase() throws SQLException, IOException, InterruptedException {
		String myPath = DB_PATH + DATABASE_NAME;
		return SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_COMANDO + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_AMBIENTE + " text not null);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMANDO);
		onCreate(db);
	}

}