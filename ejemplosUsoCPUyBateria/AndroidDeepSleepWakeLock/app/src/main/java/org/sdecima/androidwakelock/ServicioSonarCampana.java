package org.sdecima.androidwakelock;

import java.io.IOException;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;

public class ServicioSonarCampana extends Service {
	
	MiProceso proceso;
	Notification notification;
	MediaPlayer mediaPlayer;
	WakeLock wakeLock;
	
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
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "etiqueta");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(proceso == null || proceso.getStatus() == Status.FINISHED) {
			proceso = new MiProceso();
		}
		
		if(proceso.getStatus() == Status.PENDING) {
			wakeLock.acquire();
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
		wakeLock.release();
		
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
			while(!isCancelled()) {
				sonarCampana();
				
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					System.out.println("hubo un error o se cancelo");
				}
			}
			
			return null;
		}
	};
	
	Notification createNotification() {
		Intent notificationIntent = new Intent(this, AndroidDeepSleepWakeLock.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		
		Notification notification = new NotificationCompat.Builder(this)
			.setContentTitle("Servicio AndroidWakeLock")
			.setContentText("servicio iniciado")
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(contentIntent)
			.build();
		
		return notification;
	}
}
