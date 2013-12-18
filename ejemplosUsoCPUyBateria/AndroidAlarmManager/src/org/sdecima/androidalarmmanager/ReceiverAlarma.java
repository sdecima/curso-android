package org.sdecima.androidalarmmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class ReceiverAlarma extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// Creo un Intent a mandar cuando se aprete en la notificacion
		Intent notificationIntent = new Intent(context, AndroidAlarmManager.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		
		// Creo una URI de un recurso de sonido de la aplicación para la notificación 
		Uri sonido = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + R.raw.alarma);
		
		// Creo la notificación
		Notification notification = new NotificationCompat.Builder(context)
		// titulo
			.setContentTitle("Alarma!!!")
		// texto de contenido
			.setContentText("Se activó la alarma...")
		// sonido a ejecutar cuando aparezca
			.setSound(sonido)
		// icono
			.setSmallIcon(R.drawable.ic_launcher)
		// Intento que va a mandar cuando se aprete
			.setContentIntent(contentIntent)
		// AutoCancel es para que desapazca la notificación cuando se aprete en ella
			.setAutoCancel(true)
			.build();
		
		// Obtengo el Administrador de Notificaciones del Sistema
		NotificationManager mNotificationManager =
			    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// Envío la notificación
		mNotificationManager.notify(1, notification);
	}

}
