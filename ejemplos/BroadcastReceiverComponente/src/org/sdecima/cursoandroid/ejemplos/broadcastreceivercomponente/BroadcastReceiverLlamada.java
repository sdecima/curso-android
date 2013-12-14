package org.sdecima.cursoandroid.ejemplos.broadcastreceivercomponente;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class BroadcastReceiverLlamada extends BroadcastReceiver {

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
				String numero = extras
						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
				
		    	Toast.makeText(context, numero, Toast.LENGTH_SHORT).show();
			} else {
				// Si no está sonando, muestro el estado actual en un Toast
		    	Toast.makeText(context, "Estado teléfono: " + estadoTelefono, Toast.LENGTH_SHORT).show();
			}
		}
	}

}
