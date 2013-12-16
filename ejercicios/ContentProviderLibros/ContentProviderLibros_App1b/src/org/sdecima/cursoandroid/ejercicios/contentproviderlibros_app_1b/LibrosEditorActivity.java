package org.sdecima.cursoandroid.ejercicios.contentproviderlibros_app_1b;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;

public class LibrosEditorActivity extends Activity {

	EditText editTextISBN;
	EditText editTextTitulo;
	EditText editTextDescripcion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editTextISBN = (EditText) findViewById(R.id.editTextISBN);
		editTextTitulo = (EditText) findViewById(R.id.editTextTitulo);
		editTextDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
		
		cargarIntent();
	}
	
	//long id;
	boolean editando;
	
	void cargarIntent() {
	    Intent intent = getIntent();
		//Bundle b = intent.getExtras();
	    
	    Uri uri = intent.getData();
	    
	    if(uri.compareTo(LibrosContract.CONTENT_URI) != 0) {
		    //Long tempId = b.getLong("_id");
		    //id = tempId;
		    
			String[] projection = new String[] {
					LibrosContract.ID,
					LibrosContract.ISBN,
					LibrosContract.TITULO,
					LibrosContract.DESCRIPCION
				};
	        Cursor cursor = getContentResolver().query(uri,
	            	projection, null, null, null);
	        
	        if(cursor.moveToNext()) {
	        	
	        	String isbn = cursor.getString(cursor.getColumnIndex(LibrosContract.ISBN));
	        	String titulo = cursor.getString(cursor.getColumnIndex(LibrosContract.TITULO));
	        	String descripcion = cursor.getString(cursor.getColumnIndex(LibrosContract.DESCRIPCION));
	        	
			    editTextISBN.setText(isbn);
			    editTextTitulo.setText(titulo);
			    editTextDescripcion.setText(descripcion);
			    
			    editando = true;
	        } else {
	        	finish();
	        }
	    }
	}

	
	public void aceptar(View view) {
		ContentValues cv = new ContentValues();
		cv.put(LibrosContract.ISBN, editTextISBN.getText().toString());
		cv.put(LibrosContract.TITULO, editTextTitulo.getText().toString());
		cv.put(LibrosContract.DESCRIPCION, editTextDescripcion.getText().toString());
		
		if(editando) {
			getContentResolver().update(getIntent().getData(),
					cv,
					null, null);
		} else {
			getContentResolver().insert(LibrosContract.CONTENT_URI, cv);
		}
		
		finish();
	}

	public void cancelar(View view) {
		finish();
	}
}
