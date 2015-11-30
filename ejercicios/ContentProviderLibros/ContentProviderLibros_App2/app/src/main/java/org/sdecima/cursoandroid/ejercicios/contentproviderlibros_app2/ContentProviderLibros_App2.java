package org.sdecima.cursoandroid.ejercicios.contentproviderlibros_app2;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;

public class ContentProviderLibros_App2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void apretar(View view) {
		String[] projection = new String[] {
				LibrosContract.ID,
				LibrosContract.ISBN,
				LibrosContract.TITULO,
				LibrosContract.DESCRIPCION
			};
        Cursor cursor = getContentResolver().query(
        		LibrosContract.CONTENT_URI,
            	projection, null, null, null);
        
        if(cursor.moveToNext()) {
        	long id = cursor.getLong(0);
			Intent i = new Intent(Intent.ACTION_EDIT,
					ContentUris.withAppendedId(LibrosContract.CONTENT_URI, id));
			startActivity(i);
        }
	}
}
