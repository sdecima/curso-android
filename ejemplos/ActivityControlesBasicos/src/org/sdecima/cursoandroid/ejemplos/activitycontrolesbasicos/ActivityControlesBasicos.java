package org.sdecima.cursoandroid.ejemplos.activitycontrolesbasicos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityControlesBasicos extends Activity {

	// Propiedad de la clase
	TextView etiqueta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        View v = findViewById(R.id.etiqueta);
        if(v != null) {
        	etiqueta = (TextView) v;
        	etiqueta.setText("Hola!!!!");
        }
	}

    public void apretar(View view) {
    	EditText et = (EditText) findViewById(R.id.campoTexto);
    	String texto = et.getText().toString();
    	
    	etiqueta.setText(texto);
    	
    	Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
    }
}
