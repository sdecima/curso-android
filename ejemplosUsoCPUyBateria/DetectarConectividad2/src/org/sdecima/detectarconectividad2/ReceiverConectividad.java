package org.sdecima.detectarconectividad2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReceiverConectividad extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Cambio en conectividad", Toast.LENGTH_SHORT).show();
		if(Aplicacion.hayConexion(context)) {
			Toast.makeText(context, "Nueva conexión", Toast.LENGTH_SHORT).show();
			
			// deshabilito la deteccion de conexion
			Aplicacion.cambiarComponente(context, ReceiverConectividad.class, false);
			
			// inicio la descarga
			Intent i = new Intent(context, ServicioConexion.class);
			context.startService(i);
		}
	}

}
