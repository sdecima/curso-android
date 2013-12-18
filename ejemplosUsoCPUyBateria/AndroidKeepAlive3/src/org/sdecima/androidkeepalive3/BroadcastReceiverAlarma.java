package org.sdecima.androidkeepalive3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiverAlarma extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		MiAplicacion.getAplicacion().acquireAppLock(context);
		
		Intent i = new Intent(context, Servicio1.class);
		context.startService(i);
	}

}
