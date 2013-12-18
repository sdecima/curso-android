package org.sdecima.ubicacion2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class ReceiverLocation extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		
		if(extras.containsKey(LocationManager.KEY_LOCATION_CHANGED)) {
			// cambio de ubicación
			Location location = (Location) extras.get(LocationManager.KEY_LOCATION_CHANGED);
			if (location != null) {
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				
				Toast.makeText(context, "lat: " + lat + ", long: " + lng, Toast.LENGTH_LONG).show();
			}
		}
	}

}
