package org.sdecima.cursoandroid.ejercicios.contentproviderlibros;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LibrosSQLOpenHelper extends SQLiteOpenHelper {

	public LibrosSQLOpenHelper(Context context) {
		super(context, "Libreria", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + LibrosContract.LIBROS + " (" +
				LibrosContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				LibrosContract.ISBN + " TEXT, " +
				LibrosContract.TITULO + " TEXT, " +
				LibrosContract.DESCRIPCION + " TEXT);";
			db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
