package org.sdecima.cursoandroid.ejercicios.calculodepropinas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import org.sdecima.cursoandroid.ejercicios.calculodepropinas.R;

public class ActivityResultado extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_result);
		
		Intent intento = getIntent();
		Bundle extras = intento.getExtras();
		
		String m = extras.getString("mensaje");
		
		TextView t = (TextView) findViewById(R.id.textViewResultado);
		t.setText(m);
	}
}
