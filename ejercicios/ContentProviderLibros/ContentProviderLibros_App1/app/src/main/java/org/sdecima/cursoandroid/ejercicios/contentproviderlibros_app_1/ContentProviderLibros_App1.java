package org.sdecima.cursoandroid.ejercicios.contentproviderlibros_app_1;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ContentProviderLibros_App1 extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Registro para que lista pueda tener menu de contexto
		ListView lv = getListView();
	    registerForContextMenu(lv);
		
		ListAdapter la = getAdapter();
		setListAdapter(la);
		
		// Creo el loader del cursor
		getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
			@Override
			public Loader<Cursor> onCreateLoader(int id, Bundle args) {
				String[] projection = new String[] {
						LibrosContract.ID,
						LibrosContract.ISBN,
						LibrosContract.TITULO,
						LibrosContract.DESCRIPCION
					};
				
				CursorLoader cl = new CursorLoader(getApplicationContext(),
						LibrosContract.CONTENT_URI,
						projection, null, null, null);
				
				return cl;
			}

			@Override
			public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
				getAdapter().swapCursor(cursor);
			}

			@Override
			public void onLoaderReset(Loader<Cursor> arg0) {
				getAdapter().swapCursor(null);
			}
		});
	}
	
	protected void onStart() {
		super.onStart();
	};
	
	SimpleCursorAdapter adapter;
	SimpleCursorAdapter getAdapter() {
		if(adapter == null) {
			String[] columnas = new String[] {
				LibrosContract.ISBN,
				LibrosContract.TITULO,
				LibrosContract.DESCRIPCION
			};
		    int[] columnasViews = {
		    	R.id.lista_item_isbn,
		    	R.id.lista_item_titulo,
		    	R.id.lista_item_descripcion
		    };
		    //Cursor cursor = createCursor();    
		    adapter = new SimpleCursorAdapter(this,
		    	R.layout.listadapter_item,
		    	null, columnas, columnasViews, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		}
	    return adapter;
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.action_nuevo:
				Intent i1 = new Intent(this, LibrosEditorActivity.class);
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
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        long id = info.id;
    	switch(item.getItemId()) {
	    	case R.id.menuEditar:
				Intent i1 = new Intent(this, LibrosEditorActivity.class);
				i1.putExtra("_id", id);
				startActivity(i1);
	    		return true;
	    	case R.id.menuBorrar:
		        getContentResolver().delete(
		        		ContentUris.withAppendedId(LibrosContract.CONTENT_URI, id),
		        		null, null);
	    		return true;
    		default:
    	    	return super.onContextItemSelected(item);
    	}
    }

	
}
