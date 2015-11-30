package org.sdecima.cursoandroid.ejercicios.storagesqlite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.sdecima.cursoandroid.ejercicios.storagesqlite.R;

public class LibrosEditActivity extends Activity {
	
	long id;
	EditText editISBN;
	EditText editTitulo;
	EditText editDescripcion;
	LibrosDatabase libros;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.libro_edit);
	    
		libros = new LibrosDatabase(this);
	    
	    editISBN = (EditText) findViewById(R.id.editISBN);
	    editTitulo = (EditText) findViewById(R.id.editTitulo);
	    editDescripcion = (EditText) findViewById(R.id.editDescripcion);
	    
	    cargarIntent();
	}

	void cargarIntent() {
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		
		this.id = 0;

		if (b != null && b.containsKey("_id")) {
			
			this.id = b.getLong("_id");

			SQLiteDatabase db = libros.getReadableDatabase();

			String columnas[] = { LibrosDatabase.ID, LibrosDatabase.ISBN, LibrosDatabase.TITULO,
					LibrosDatabase.DESCRIPCION };
			String[] args = new String[] { String.valueOf(this.id) };

			Cursor cursor = db.query(LibrosDatabase.LIBROS_TABLA, columnas,
					LibrosDatabase.ID+"=?", args, null, null, null);
			
			if (cursor.moveToNext()) {
				
				String isbn = cursor.getString(cursor
						.getColumnIndex(LibrosDatabase.ISBN));
				String titulo = cursor.getString(cursor
						.getColumnIndex(LibrosDatabase.TITULO));
				String descripcion = cursor.getString(cursor
						.getColumnIndex(LibrosDatabase.DESCRIPCION));

				editISBN.setText(isbn);
				editTitulo.setText(titulo);
				editDescripcion.setText(descripcion);
				
			} else {
				finish();
			}

		}
	}

	public void aceptar(View v) {
		String isbn = editISBN.getText().toString();
		String titulo = editTitulo.getText().toString();
		String desc = editDescripcion.getText().toString();
		
		long res = 0;
		
		if(this.id == 0) {
			res = libros.insertar(isbn, titulo, desc);
		} else {
			res = libros.modificar(this.id, isbn, titulo, desc);
		}
		
		if(res > 0) {
			finish();
		} else {
			Toast.makeText(getApplicationContext(),
					"Error guardando el registro",
					Toast.LENGTH_LONG).show();
		}
	}

	public void cancelar(View v) {
		finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		libros.close();
	}
}
