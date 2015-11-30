package org.sdecima.detectarconectividad2;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class ServicioConexion extends Service {
	
	public static final String ACTION_ACTUALIZAR = "org.sdecima.detectarconectividad.action.actualizar";
	
	String url;
	MiProceso p;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(intent != null & intent.hasExtra("url"))
			url = intent.getExtras().getString("url");
		else {
			SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
			url = sp.getString("serviceURL", "");
		}
		
		if(Aplicacion.hayConexion(this)) {
			descargar();
		} else {
			Toast.makeText(this, "No hay conexión", Toast.LENGTH_SHORT).show();
			// habilito para recibir cambios de conectividad
			Aplicacion.cambiarComponente(this, ReceiverConectividad.class, true);
		}
		
		return START_NOT_STICKY;
	}
	
	public void descargar() {
		p = new MiProceso();
		p.execute(url);
	}
	
	class MiProceso extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			String string = bajarArchivo(urls[0]);
			return string;
		}
		
		@Override
		protected void onPostExecute(String result) {
			Context c = getApplicationContext();
			Toast.makeText(c, result, Toast.LENGTH_SHORT).show();
		}
		
		@Override
		protected void onCancelled() {
			Context c = getApplicationContext();
			Toast.makeText(c, "Cancelado", Toast.LENGTH_SHORT).show();
		}
		
		public String bajarArchivo(String url) {
			SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
			int descargado = sp.getInt("descargado", 0);
			int total = sp.getInt("total", 0);
			
			int leidos = 0;
			int tamArchivo = 0;
			
			try {
				URL u = new URL(url);
				//InputStream urlStream = u.openStream();
				
				URLConnection c = u.openConnection();
				tamArchivo = total;
				if(descargado > 0) {
					c.setRequestProperty("Range", "bytes=" + descargado + "-");
				}
				c.connect();
				if(descargado == 0) {
					tamArchivo = c.getContentLength();
				}
				InputStream urlStream = c.getInputStream();
				
				//FileOutputStream outputStream = openFileOutput("mi_archivo", MODE_PRIVATE);
				
				leidos = descargado;
				int ultimoPorc = 0;
				byte[] buf = new byte[8192];
				int tam = urlStream.read(buf);
				while(!isCancelled() && tam >= 0) {
					//outputStream.write(buf, 0, tam);
					
					if(tamArchivo > 0) {
						leidos += tam;
						int porc = (leidos * 100 / tamArchivo);
						if(porc > ultimoPorc) {
							//publishProgress(porc);
							publicarMensaje(porc);
							ultimoPorc = porc;
						}
					}
					
					try {
						tam = urlStream.read(buf);
					} catch (IOException e) {
						// no se pudo leer, probable falta de conexión
						if(Aplicacion.hayConexion(getApplicationContext())) {
							// no fue problema de conexion
							throw e;
						}
						
						// Programar detectar
						Aplicacion.cambiarComponente(getApplicationContext(), ReceiverConectividad.class, true);
						return "Se cortó la conexión";
					}
				}
				
				urlStream.close();
				//outputStream.close();
				
				return "Sin errores";
				
			} catch (MalformedURLException e) {
				return "URL mal escrita: " + e.getLocalizedMessage();
			} catch (IOException e) {
				return "ERROR de E/S: " + e.getLocalizedMessage();
			} finally {
				Editor editor = sp.edit();
				if(leidos == tamArchivo || isCancelled()) {
					// se termino de leer o se canceló, resetear para el ejemplo
					editor.remove("total");
					editor.remove("descargado");
				} else {
					if(descargado == 0) {
						// descarga nueva
						editor.putInt("total", tamArchivo);
					}
					editor.putInt("descargado", leidos);
					editor.putString("serviceURL", url);
				}
				editor.commit();
			}
		}
		
		public void publicarMensaje(int i) {
			Intent intent = new Intent(ACTION_ACTUALIZAR);
			intent.putExtra("progreso", i);
			
			LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
			lbm.sendBroadcast(intent);
		}
	}

	@Override
	public void onDestroy() {
		p.cancel(true);
		
		super.onDestroy();
	}
}
