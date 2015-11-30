package org.sdecima.cursoandroid.ejercicios.storagesqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LibrosDatabase extends SQLiteOpenHelper {

	public static final String LIBROS_TABLA = "libros";
	
	public static final String ID = "_id";
	public static final String ISBN = "isbn";
	public static final String TITULO = "titulo";
	public static final String DESCRIPCION = "descripcion";
	
	public LibrosDatabase(Context context) {
		super(context, "libreria", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + LIBROS_TABLA + " (" +
	    		ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		    	ISBN + " TEXT, " +
		    	TITULO + " TEXT, " +
	        	DESCRIPCION + " TEXT);";
			db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
