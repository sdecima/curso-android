package org.sdecima.cursoandroid.ejemplos.serviciocontador;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Button botonEmpezar;
	Button botonTerminar;
	TextView textViewContador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		botonEmpezar = (Button) findViewById(R.id.buttonEmpezar);
		botonTerminar = (Button) findViewById(R.id.buttonTerminar);
		textViewContador = (TextView) findViewById(R.id.textViewContador);
		
		botonEmpezar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), ServicioContador.class);
				startService(i);
			}
		});
		
		botonTerminar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), ServicioContador.class);
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

	BroadcastReceiver receiver_contador = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int valor = intent.getExtras().getInt("contador");
			textViewContador.setText("Contador: " + valor);
		}
	};
	
	public void registrarBroadcastReceiver() {
		IntentFilter filter = new IntentFilter("action.contador");
		registerReceiver(receiver_contador, filter);
	}
	public void cancelarBroadcastReceiver() {
		unregisterReceiver(receiver_contador);
	}
	
	public void registrarLocalBroadcastReceiver() {
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		IntentFilter filter = new IntentFilter("action.contador");
		lbm.registerReceiver(receiver_contador, filter);
	}
	public void cancelarLocalBroadcastReceiver() {
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		lbm.unregisterReceiver(receiver_contador);
	}
	
	protected void onStart() {
		super.onStart();
		registrarLocalBroadcastReceiver();
	};
	
	protected void onStop() {
		super.onStop();
		cancelarLocalBroadcastReceiver();
	};
}
