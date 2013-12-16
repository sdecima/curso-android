package org.polo.googleadmob;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Crea un nuevo AdView (se debe agregar un adUnitId válido de AdMob)
	    adView = new AdView(this, AdSize.BANNER, "adUnitIdValido");
	    
	    // Agrego el adView a la pantalla
	    LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout1);
	    layout.addView(adView);

	    // Obtengo una nueva publicidad
	    AdRequest adRequest = new AdRequest();
        adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
	    adView.loadAd(adRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
