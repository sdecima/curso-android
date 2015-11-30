package org.sdecima.cursoandroid.ejercicios.storagesqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.sdecima.cursoandroid.ejercicios.storagesqlite.R;

public class LibrosEditActivity extends Activity {
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
	}
	
	public void aceptar(View v) {
		String isbn = editISBN.getText().toString();
		String titulo = editTitulo.getText().toString();
		String desc = editDescripcion.getText().toString();

		long res = libros.insertar(isbn, titulo, desc);
		if(res >= 0) {
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
