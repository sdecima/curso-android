package org.sdecima.cursoandroid.ejercicios.contentproviderlibros;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class LibrosProvider extends ContentProvider {
	
	LibrosSQLOpenHelper openHelper;

	@Override
	public boolean onCreate() {
		openHelper = new LibrosSQLOpenHelper(getContext());
		return true;
	}

	long chequearId(Uri uri) {
		try {
			long id = ContentUris.parseId(uri);
			
			return id;
		} catch(Exception e){
			return -1;
		}
	}
	
	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		
		long id = chequearId(uri);
		if(id != -1) {
			whereClause = "_ID=?";
			whereArgs = new String[]{""+id};
		}
		
		int n = db.delete(LibrosContract.LIBROS, whereClause, whereArgs);
		db.close();
		
		getContext().getContentResolver().notifyChange(uri, null);
		return n;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri newUri = null;
		
		SQLiteDatabase db = openHelper.getWritableDatabase();
		long n = db.insert(LibrosContract.LIBROS, null, values);
		if(n > -1) {
			newUri = ContentUris.withAppendedId(LibrosContract.CONTENT_URI, n);
		}
		
		db.close();
		getContext().getContentResolver().notifyChange(uri, null);
		return newUri;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		
		long id = chequearId(uri);
		if(id != -1) {
			whereClause = "_ID=?";
			whereArgs = new String[]{""+id};
		}
		
		int n = db.update(LibrosContract.LIBROS, values, whereClause, whereArgs);
		db.close();
		getContext().getContentResolver().notifyChange(uri, null);
		return n;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		
		long id = chequearId(uri);
		if(id != -1) {
			selection = "_ID=?";
			selectionArgs = new String[]{""+id};
		}
		
		Cursor cursor = db.query(LibrosContract.LIBROS, projection, selection, selectionArgs,
				null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}
}
