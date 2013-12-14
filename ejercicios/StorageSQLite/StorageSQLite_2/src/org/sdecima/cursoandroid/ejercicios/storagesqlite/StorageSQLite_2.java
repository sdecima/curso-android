package org.sdecima.cursoandroid.ejercicios.storagesqlite;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import org.sdecima.cursoandroid.ejercicios.storagesqlite.R;

public class StorageSQLite_2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LibrosDatabase libros = new LibrosDatabase(this);
//		libros.insertar("123", "tit1", "blah blah");
//		libros.insertar("1234", "tit2", "blah blah");
//		libros.insertar("12345", "tit3", "blah blah");
		
		libros.consultar("tit");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
