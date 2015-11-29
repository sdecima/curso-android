package org.sdecima.cursoandroid.ejemplos.storageinternal;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class StorageInternal extends Activity {

	EditText campo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_storage_internal);
		
        campo = (EditText) findViewById(R.id.editText1);
        
        // Si ya se ingresó una URL, se lee
        String url = getPreferences(MODE_PRIVATE).getString("url", "");
        campo.setText(url);
        
        findViewById(R.id.botonDescargar).setOnClickListener(
        		new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String archivo = campo.getText().toString();
				
				Intent i = new Intent(getApplicationContext(),
						ServicioBajarArchivo.class);
				i.setData(Uri.parse(archivo));
				startService(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.storage_internal, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		// Guardo la URL que se ingresa
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("url", campo.getText().toString());
        editor.commit();
	}
}
