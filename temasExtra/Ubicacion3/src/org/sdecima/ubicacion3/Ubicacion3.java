package org.sdecima.ubicacion3;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Ubicacion3 extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {
	LocationClient lc;
	private TextView latitud;
	private TextView longitud;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		latitud = (TextView) findViewById(R.id.textViewLatitud);
		longitud = (TextView) findViewById(R.id.textViewLongitud);
		
		int resultCode = GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == resultCode) {
        	
        	lc = new LocationClient(this, this, this);
        }

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(lc!=null) {
			lc.connect();
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if(lc!=null) {
			lc.disconnect();
		}
	}
	
	public void obtener(View view) {
		if(lc!=null) {
			Location location = lc.getLastLocation();
			if (location != null) {
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				latitud.setText(String.valueOf(lat));
				longitud.setText(String.valueOf(lng));
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
