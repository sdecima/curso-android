package org.sdecima.cursoandroid.ejemplos.storageinternal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ServicioBajarArchivo extends IntentService {

	public ServicioBajarArchivo() {
		super("ServicioBajarArchivo");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String url = intent.getDataString();
		bajarArchivo(url);
	}
	
    public void bajarArchivo(String strURL) {
    	try {
			URL url = new URL(strURL);
			InputStream urlStream = url.openStream();
			String name = new File(url.getPath()).getName();
			
			FileOutputStream outputStream = openFileOutput(name,
					MODE_PRIVATE);
			byte[] buf = new byte[8192];
			int length = urlStream.read(buf);
		    while (length >= 0) {
		      outputStream.write(buf, 0, length);
		      length = urlStream.read(buf);
		    }
		    urlStream.close();
			outputStream.close();
			//mostrarMensaje("Se copió el archivo correctamente");
			notificarAlUsuario(strURL);
		} catch (MalformedURLException e) {
			mostrarMensaje("La dirección del archivo es inválida");
		} catch (IOException e) {
			mostrarMensaje("Hubo un error al bajar el archivo: " + e.getMessage());
		}
    }
    
    /*
     * Si quiero mostrar un mensaje (Toast) o ejecutar cualquier cosa en
     * el Thread principal, debo utilizar un Handler de la manera siguiente
     */
    public void mostrarMensaje(final String msg) {
    	Handler h = new Handler(Looper.getMainLooper());
    	h.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), msg,
						Toast.LENGTH_LONG).show();
			}
		});
    }

    /*
     * Crear una notificaci�n de Sistema
     */
	void notificarAlUsuario(String url) {
		// Obtener una referencia al NotificationManager
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager)
			getSystemService(ns);
		
		// Instanciar una Notification
		int icon = R.drawable.ic_launcher;
		CharSequence notifText = "Se termin� de bajar el archivo: " + url;
		long cuando = System.currentTimeMillis();
		
		// creo un PendingIntent
		Intent notificationIntent = new Intent(this, StorageInternal.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		Notification notification = new NotificationCompat.Builder(this)
			// titulo de la notificaci�n
			.setContentTitle("Terminado.")
			// contenido
			.setContentText(notifText)
			// icono
			.setSmallIcon(icon)
			// definir cuando aparece 
			.setWhen(cuando)
			// qu� Intent env�a cuando se toca la notificaci�n
			.setContentIntent(contentIntent)
			// autoCancel = se cancela autom�ticamente cuando se toca
			.setAutoCancel(true)
			// texto que aparece en la barra de notificaciones cuando ocurre la misma
			.setTicker("Archivo descargado.")
			//.setSound(sound)
			//.setVibrate(pattern)
			//.setLights(argb, onMs, offMs)
			.build();
		
		// pasar la Notification al NotificationManager
		mNotificationManager.notify(1, notification);
	}
}
