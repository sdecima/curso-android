package org.sdecima.androidkeepalive;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;

public class MiAplicacion {

	private static MiAplicacion app = new MiAplicacion();
	private MiAplicacion() {}
	
	public static MiAplicacion getAplicacion() {
		return app;
	}
	
	public void programarAlarmas(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME,
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
}
