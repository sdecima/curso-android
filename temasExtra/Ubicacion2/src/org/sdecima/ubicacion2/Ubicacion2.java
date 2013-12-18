package org.sdecima.ubicacion2;

import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class Ubicacion2 extends Activity {
	private LocationManager locationManager;
	private String provider;
	PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Obtener el LocationManager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		// Definir el criterio para seleccionar el LocationProvider
		// Usamos el default
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setCostAllowed(true);
		provider = locationManager.getBestProvider(criteria, true);
		
		// obtener ubicación rápido, puede no estar actualizada
//		Location location = locationManager.getLastKnownLocation(provider);
//		cambiarPantalla(location);
		
		Intent i = new Intent(this, ReceiverLocation.class);
		pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0); 
		locationManager.requestLocationUpdates(provider, 10000, 0, pendingIntent);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		locationManager.removeUpdates(pendingIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
