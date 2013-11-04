package org.sdecima.cursoandroid.ejemplos.activityintentos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ActivityIntentos2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void botonWeb(View view) {
		// Creo un Intent con acción "ACTION_VIEW" (ver) y un dato "http://www.android.com"
    	Intent webIntent = new Intent(Intent.ACTION_VIEW,
    			Uri.parse("http://www.android.com"));
    	startActivity(webIntent);
	}
	
	public void botonTelefono(View view) {
		// Creo un Intent con acción "ACTION_CALL" (llamar) y un dato "tel:12345678"
    	Intent callIntent = new Intent(Intent.ACTION_CALL,
    			Uri.parse("tel:12345678"));
		startActivity(callIntent);
	}
}
