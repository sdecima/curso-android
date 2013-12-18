package org.sdecima.androidkeepalive3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiverOnBoot extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		MiAplicacion.getAplicacion().programarAlarmas(context);
	}

}
