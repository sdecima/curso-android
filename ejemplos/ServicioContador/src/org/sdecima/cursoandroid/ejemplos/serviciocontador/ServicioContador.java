package org.sdecima.cursoandroid.ejemplos.serviciocontador;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class ServicioContador extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	int contador;
	MiProceso mp;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(mp == null) {
			mp = new MiProceso();
			mp.execute();
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	class MiProceso extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			while(!isCancelled()) {
				contador = contador +1;
				publishProgress(contador);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			Toast.makeText(getApplicationContext(), "" + values[0], Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(mp != null) {
			mp.cancel(true);
		}
		
		super.onDestroy();
	}
}
