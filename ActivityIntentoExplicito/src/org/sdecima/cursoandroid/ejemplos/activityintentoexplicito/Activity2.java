package org.sdecima.cursoandroid.ejemplos.activityintentoexplicito;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Activity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
	}

	public void volver(View view) {
		finish();
	}
}
