package org.sdecima.cursoandroid.ejemplos.activityintentos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

public class ActivityIntentos2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Verifico si el usuario dió permiso a hacer llamadas telefónicas
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
			// si no tiene el permiso, lo pido
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
		}
	}

	public void botonWeb(View view) {
		// Creo un Intent con acción "ACTION_VIEW" (ver) y un dato "http://www.android.com"
    	Intent webIntent = new Intent(Intent.ACTION_VIEW,
    			Uri.parse("http://www.android.com"));
    	startActivity(webIntent);
	}
	
	public void botonTelefono(View view) {
		// Verifico si el usuario dió permiso a hacer llamadas telefónicas
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
			// si no tiene el permiso
			Toast.makeText(ActivityIntentos2.this, R.string.sin_permiso, Toast.LENGTH_SHORT).show();
		} else {
			// Creo un Intent con acción "ACTION_CALL" (llamar) y un dato "tel:12345678"
			Intent callIntent = new Intent(Intent.ACTION_CALL,
					Uri.parse("tel:12345678"));
			startActivity(callIntent);
		}
	}
}
