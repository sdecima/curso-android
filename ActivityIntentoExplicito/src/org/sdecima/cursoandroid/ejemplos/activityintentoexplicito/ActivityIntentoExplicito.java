package org.sdecima.cursoandroid.ejemplos.activityintentoexplicito;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ActivityIntentoExplicito extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intento_explicito);
	}

	public void otraActivity(View view) {
		Intent intent = new Intent(this, Activity2.class);
		startActivity(intent);
	}
}
