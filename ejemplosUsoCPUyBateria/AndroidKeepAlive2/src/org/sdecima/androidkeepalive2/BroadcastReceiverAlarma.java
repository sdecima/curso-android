package org.sdecima.androidkeepalive2;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class BroadcastReceiverAlarma extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, Servicio1.class);
		//context.startService(i);
		startWakefulService(context, i);
	}

}
