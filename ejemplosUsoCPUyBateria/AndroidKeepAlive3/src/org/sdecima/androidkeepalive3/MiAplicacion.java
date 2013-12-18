package org.sdecima.androidkeepalive3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.PowerManager.WakeLock;

public class MiAplicacion {

	private static MiAplicacion app = new MiAplicacion();
	private MiAplicacion() {}
	
	public static MiAplicacion getAplicacion() {
		return app;
	}
	
	public void programarAlarmas(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime(),
				15000,
				PendingIntent.getBroadcast(context, 0,
						new Intent("org.sdecima.androidalarmmanager.alarma"), 0)
			);
	}
	
	public void cancelarAlarmas(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(PendingIntent.getBroadcast(context, 0,
						new Intent("org.sdecima.androidalarmmanager.alarma"), 0)
			);
	}

	public void programarBoot(Context context, boolean habilitar) {
		ComponentName receiver = new ComponentName(context, BroadcastReceiverOnBoot.class);

		PackageManager pm = context.getPackageManager();
		
		int newState = (habilitar ?
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED : 
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED);

		pm.setComponentEnabledSetting(receiver,
		        newState,
		        PackageManager.DONT_KILL_APP);
	}
	
	private WakeLock applicationLock;
	public synchronized void acquireAppLock(Context c) {
		if(applicationLock == null) {
			PowerManager pm = (PowerManager) c.getSystemService(Context.POWER_SERVICE);
			applicationLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "KeepAlive-lock");
		}
		applicationLock.acquire();
	}
	public void releaseAppLock() {
		if(applicationLock != null && applicationLock.isHeld()) {
			applicationLock.release();
		}
	}
}
