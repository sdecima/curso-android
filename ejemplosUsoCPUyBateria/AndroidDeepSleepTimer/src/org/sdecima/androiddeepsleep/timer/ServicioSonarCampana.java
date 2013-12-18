package org.sdecima.androiddeepsleep.timer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class ServicioSonarCampana extends Service {
	
	Notification notification;
	Timer timer;
	MediaPlayer mediaPlayer;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		notification = createNotification();
		timer = new Timer();
		mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.campana);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startForeground(1, notification);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				sonarCampana();
			}
		}, 0, 15000);
		
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		timer.cancel();
		stopForeground(true);
		mediaPlayer.release();
		
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
	
	Notification createNotification() {
		Intent notificationIntent = new Intent(this, AndroidDeepSleepTimer.class);
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
