package org.sdecima.cursoandroid.ejemplos.serviciocontador;

import org.sdecima.cursoandroid.ejemplos.serviciocontador.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button botonEmpezar;
	Button botonTerminar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		botonEmpezar = (Button) findViewById(R.id.buttonEmpezar);
		botonTerminar = (Button) findViewById(R.id.buttonTerminar);
		
		botonEmpezar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, ServicioContador.class);
				startService(i);
			}
		});
		
		botonTerminar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, ServicioContador.class);
				stopService(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
