package org.sdecima.cursoandroid.ejemplos.broadcastreceiveractivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class BroadcastReceiverActivity extends Activity {

	int contador = 0;
	TextView recibidas;
	TextView ultimo;
	BroadcastReceiver br;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_broadcast_receiver);
		
        recibidas = (TextView)findViewById(R.id.textViewRecibidas);
        ultimo = (TextView)findViewById(R.id.textViewUltimoNumero);
        
        br = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// Obtengo los datos extra del Intento
		    	Bundle extras = intent.getExtras();
				if (extras != null) {
					// Pregunto el estado del teléfono, el cambio que generó este mensaje
					String estadoTelefono = extras.getString(TelephonyManager.EXTRA_STATE);
					// Pregunto si el estado actual es que está sonando
					if (estadoTelefono.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
						// Si está sonando, muestro el número en un Toast
						contador++;
						String numero = extras
								.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
						
						recibidas.setText("Llamadas recibidas: "+contador);
						ultimo.setText("Último número recibido: " + numero);
					}
					// muestro el estado actual
					Toast.makeText(getApplicationContext(), "Estado teléfono: " + estadoTelefono, Toast.LENGTH_SHORT).show();
				}
			}
		};
		registerReceiver(br, new IntentFilter("android.intent.action.PHONE_STATE"));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.broadcast_receiver, menu);
		return true;
	}
	
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	unregisterReceiver(br);
    }
    
}
