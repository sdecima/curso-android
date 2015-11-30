package org.sdecima.cursoandroid.ejercicios.storagesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

	
	public long insertar(String isbn, String titulo, String desc) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Valores a insertar
	    ContentValues cv = new ContentValues();
		cv.put(LibrosDatabase.ISBN, isbn);
		cv.put(LibrosDatabase.TITULO, titulo);
		cv.put(LibrosDatabase.DESCRIPCION, desc);
		
		long s = db.insert(LibrosDatabase.LIBROS_TABLA, null, cv);
		db.close();
		return s;
	}
	
	public int modificar(long id, String isbn, String titulo, String desc) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Valores a modificar
	    ContentValues cv = new ContentValues();
	    if(isbn != null)
	    	cv.put(LibrosDatabase.ISBN, isbn);
	    if(titulo != null)
	    	cv.put(LibrosDatabase.TITULO, titulo);
	    if(desc != null)
	    	cv.put(LibrosDatabase.DESCRIPCION, desc);
	    
	    // Filtro / WHERE para seleccionar qué modificar
	    String clausulaWhere = LibrosDatabase.ID+"=?";
	    String[] argumentosWhere = new String[] { String.valueOf(id) };
		
	    // Realizo el update en la base de datos
		int registrosAfectados = db.update(LibrosDatabase.LIBROS_TABLA,
				cv, clausulaWhere, argumentosWhere);
		
		db.close();
		return registrosAfectados;
	}
	
	public int borrar(long id) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Filtro / WHERE para seleccionar qué borrar
	    String clausulaWhere = LibrosDatabase.ID+"=?";
	    String[] argumentosWhere = new String[] { String.valueOf(id) };
	    
		int registrosAfectados = db.delete(LibrosDatabase.LIBROS_TABLA, clausulaWhere, argumentosWhere);
		this.close();
		return registrosAfectados;
	}
	
	public void consultar(String t) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    // Columnas a consultar
	    String columnas[] = {LibrosDatabase.ID, LibrosDatabase.ISBN,
	    		LibrosDatabase.TITULO, LibrosDatabase.DESCRIPCION};
	    
		// Filtro / WHERE para seleccionar qué consultar
	    String clausulaWhere = LibrosDatabase.TITULO+" LIKE ?";
	    String[] argumentosWhere = new String[] { "%" + t + "%" };
	    
	    // GROUP BY / HAVING
	    //String groupBy = "columna1, columna2";
	    String groupBy = null;
	    //String having = "columna1 > x AND columna2 < y";
	    String having = null;
	    
	    // Orden de los resultados
	    String orderBy = LibrosDatabase.TITULO + " ASC";
	    
	    Cursor cursor = db.query(LibrosDatabase.LIBROS_TABLA,
	    		columnas,
	    		clausulaWhere, argumentosWhere,
	    		groupBy,
	    		having,
	    		orderBy);
	    
	    if(cursor.moveToFirst()) {
	    	do {
	    		int indexISBN = cursor.getColumnIndex("isbn");
	    		
	    		String id = cursor.getString(0);
	    		String isbn = cursor.getString(indexISBN);
	    		String titulo = cursor.getString(2);
	    		String desc = cursor.getString(3);
	    		
	    		System.out.println("ID: " + id + ", ISBN: " + isbn +
	    			", Titulo: " + titulo + ", Descripcion: " + desc);
	    		
	    	} while(cursor.moveToNext());
	    }
		
	}
	
	Cursor crearCursor() {
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    String columnas[] = {LibrosDatabase.ID, LibrosDatabase.ISBN,
	    		LibrosDatabase.TITULO, LibrosDatabase.DESCRIPCION};
	    
	    // Si solo paso las columnas, devuelve todo el contenido.
	    Cursor cursor = db.query(LibrosDatabase.LIBROS_TABLA,
	    		columnas,
	    		null, null, null, null, null);
	    
	    return cursor;
	}

}
