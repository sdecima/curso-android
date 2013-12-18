package org.sdecima.detectarconectividad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DetectarConectividad extends Activity {
	
	EditText editText;

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
	
	public boolean hayConexion() {
		ConnectivityManager cm = (ConnectivityManager)
				this.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		boolean conectada = false;
		NetworkInfo redActiva = cm.getActiveNetworkInfo();
		if(redActiva != null) {
			conectada = redActiva.isConnectedOrConnecting();
			//boolean wiFi = redActiva.getType() == ConnectivityManager.TYPE_WIFI;
		}
		return conectada;
	}
	
	public void ejecutar(View view) {
		
		editText = (EditText) findViewById(R.id.editText1);
		
		if(hayConexion()) {
			descargar();
		} else {
			Toast.makeText(this, "No hay conexión", Toast.LENGTH_SHORT).show();
			registerReceiver(br, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		}
	}
	
	public void descargar() {
		new ProcesoDownload().execute();
	}
	
	BroadcastReceiver br = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "se recibe", Toast.LENGTH_SHORT).show();
			if(hayConexion()) {
				unregisterReceiver(br);
				descargar();
			}
		}
	};
	
	class ProcesoDownload extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			StringBuilder str = new StringBuilder();
			try {
				URL url = new URL("http://www.google.com.ar");
				InputStream is = url.openStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);

				String line = br.readLine();
				while(line != null) {
					str.append(line + "\n");
					line = br.readLine();
				}
				
				is.close();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
			editText.setText(result);
		}
		
	}
}
