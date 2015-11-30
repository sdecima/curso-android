package org.sdecima.androidkeepalive3;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import org.sdecima.androidkeepalive3.R;

public class AndroidKeepAlive3 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void empezar(View view) {
		MiAplicacion.getAplicacion().programarAlarmas(this);
		MiAplicacion.getAplicacion().programarBoot(this, true);
	}

	public void terminar(View view) {
		Intent i = new Intent(this, Servicio1.class);
		stopService(i);
		MiAplicacion.getAplicacion().cancelarAlarmas(this);
		MiAplicacion.getAplicacion().programarBoot(this, false);
	}
}
