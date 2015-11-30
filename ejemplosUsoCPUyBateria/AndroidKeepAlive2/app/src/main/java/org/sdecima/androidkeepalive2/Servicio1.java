package org.sdecima.androidkeepalive2;

import java.io.IOException;
import org.sdecima.androidkeepalive2.R;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class Servicio1 extends Service {
	
	MiProceso proceso;
	Notification notification;
	MediaPlayer mediaPlayer;
	Intent i;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		notification = createNotification();
		mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.campana);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		i = intent;
		if(proceso == null) {
			proceso = new MiProceso();
			startForeground(1, notification);
			proceso.execute();
		}
		
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		proceso.cancel(true);
		stopForeground(true);
		mediaPlayer.release();
		BroadcastReceiverAlarma.completeWakefulIntent(i);
		
		super.onDestroy();
	}
	
	void sonarCampana() {
		try {
			mediaPlayer.stop();
			mediaPlayer.prepare();
			mediaPlayer.seekTo(0);
			mediaPlayer.start();
			System.out.println("Se está haciendo algo");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class MiProceso extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			sonarCampana();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			stopSelf();
		}
	};
	
	Notification createNotification() {
		Intent notificationIntent = new Intent(this, AndroidKeepAlive2.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		
		Notification notification = new NotificationCompat.Builder(this)
			.setContentTitle("Servicio Android Keep Alive")
			.setContentText("servicio iniciado")
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(contentIntent)
			.setTicker("Iniciando Servicio...")
			.build();
		
		return notification;
	}
}
