package org.sdecima.detectarconectividad2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import org.sdecima.detectarconectividad2.R;

public class DetectarConectividad2 extends Activity {
	
	EditText editText;
	ProgressBar progress;
	LocalBroadcastManager lbm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText = (EditText) findViewById(R.id.editTextURL);
		progress = (ProgressBar) findViewById(R.id.progressBar1);;
		progress.setProgress(0);
		progress.setMax(100);
		
		lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		String url = sp.getString("url", "http://");
		editText.setText(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void onStart() {
		super.onStart();
		IntentFilter filter = new IntentFilter(ServicioConexion.ACTION_ACTUALIZAR);
		
		lbm.registerReceiver(actualizaciones, filter);
	};
	
	protected void onStop() {
		super.onStop();
		lbm.unregisterReceiver(actualizaciones);
	};
	
	BroadcastReceiver actualizaciones = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int progreso = intent.getExtras().getInt("progreso");
			progress.setProgress(progreso);
		}
	};
	
	public void ejecutar(View view) {
		Intent i = new Intent(this, ServicioConexion.class);
		
		String url = editText.getText().toString();
		i.putExtra("url", url);
		
		startService(i);
		
		guardarUrl();
	}
	
	public void detener(View view) {
		Intent i = new Intent(this, ServicioConexion.class);
		stopService(i);
	}
	
	public void guardarUrl() {
		String url = editText.getText().toString();
		
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("url", url);
		editor.commit();
	}
}
