package org.sdecima.cursoandroid.ejercicios.storagesqlite;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import org.sdecima.cursoandroid.ejercicios.storagesqlite.R;

public class StorageSQLite_4 extends ListActivity {

    LibrosDatabase libros;
	CursorAdapter adapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    libros = new LibrosDatabase(this);

	    ListAdapter la = getAdapter();
		setListAdapter(la);
		
		ListView lv = getListView();
		registerForContextMenu(lv);
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
    	
		actualizar();
    }
    
    public void actualizar() {
    	getAdapter().changeCursor(libros.crearCursor());
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
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_lista, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		long id = info.id;
		switch (item.getItemId()) {
		case R.id.menuEditar:
			Intent i1 = new Intent(this, LibrosEditActivity.class);
			i1.putExtra("_id", id);
			startActivity(i1);
			return true;
		case R.id.menuBorrar:
			libros.borrar(id);
			actualizar();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
    
}
