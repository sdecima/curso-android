package org.sdecima.ubicacion1;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class Ubicacion1 extends Activity {
	private TextView latitud;
	private TextView longitud;
	private LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		latitud = (TextView) findViewById(R.id.textViewLatitud);
		longitud = (TextView) findViewById(R.id.textViewLongitud);

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
		
		locationManager.requestLocationUpdates(provider, 15000, 0,
				l, null);
	}
	
	LocationListener l = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
		@Override
		public void onProviderEnabled(String provider) {}
		@Override
		public void onProviderDisabled(String provider) {}
		
		@Override
		public void onLocationChanged(Location location) {
			cambiarPantalla(location);
		}
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		locationManager.removeUpdates(l);
	}

	void cambiarPantalla(Location location) {
		if (location != null) {
			System.out.println("Se seleccionó el Provider " + provider + ".");
			
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latitud.setText(String.valueOf(lat));
			longitud.setText(String.valueOf(lng));
		} else {
			latitud.setText("Provider no disponible");
			longitud.setText("Provider no disponible");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
