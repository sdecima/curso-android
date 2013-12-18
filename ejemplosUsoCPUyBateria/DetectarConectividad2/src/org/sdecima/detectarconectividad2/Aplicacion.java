package org.sdecima.detectarconectividad2;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Aplicacion extends Application {

	private static Aplicacion app = new Aplicacion();
	private Aplicacion() {}
	
	public static Aplicacion getInstance() {
		return app;
	}
	
	public static boolean hayConexion(Context context) {
		ConnectivityManager cm = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		boolean conectada = false;
		//boolean mobile = false;
		boolean wifi = false;
		NetworkInfo redActiva = cm.getActiveNetworkInfo();
		if(redActiva != null) {
			conectada = redActiva.isConnectedOrConnecting();
			//mobile = redActiva.getType() == ConnectivityManager.TYPE_MOBILE;
			wifi = redActiva.getType() == ConnectivityManager.TYPE_WIFI;
		}
		return conectada && wifi;
	}
	
	public static void cambiarComponente(Context context, Class<?> c, boolean habilitar) {
		ComponentName component = new ComponentName(context, c);
	
		PackageManager pm = context.getPackageManager();
		
		int newState = (habilitar ?
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED : 
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
	
		pm.setComponentEnabledSetting(component,
		        newState,
		        PackageManager.DONT_KILL_APP);
	}
}
