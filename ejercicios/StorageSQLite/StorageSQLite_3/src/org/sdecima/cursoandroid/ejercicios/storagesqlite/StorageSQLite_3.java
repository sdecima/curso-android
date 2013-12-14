package org.sdecima.cursoandroid.ejercicios.storagesqlite;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import org.sdecima.cursoandroid.ejercicios.storagesqlite.R;

public class StorageSQLite_3 extends ListActivity {

    LibrosDatabase libros;
	CursorAdapter adapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    libros = new LibrosDatabase(this);

	    ListAdapter la = getAdapter();
		setListAdapter(la);
	}

	@SuppressWarnings("deprecation")
	CursorAdapter getAdapter() {
		if(adapter == null) {
		    String columnas[] = {LibrosDatabase.ISBN,
		    		LibrosDatabase.TITULO,
		    		LibrosDatabase.DESCRIPCION};
		    int[] columnasViews = {R.id.itemISBN, R.id.itemTitulo,
		    		R.id.itemDescripcion};
		    
		    Cursor cursor = null;
		    
		    adapter = new SimpleCursorAdapter(this,
		    		R.layout.elemento_lista_libros, cursor, columnas,
		    		columnasViews
		    		// Para Android 3.0+ se debe incluir la siguiente bandera
		    		//,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
		    		);
		}
	    return adapter;
	}
	
    @Override
    protected void onStart() {
    	super.onStart();
    	
		CursorAdapter la = getAdapter();
		la.changeCursor(libros.crearCursor());
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	libros.close();
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.menuNuevo:
				Intent i1 = new Intent(this, LibrosEditActivity.class);
				startActivity(i1);
				return true;
			default:
		    	return super.onOptionsItemSelected(item);
		}
    }
}
