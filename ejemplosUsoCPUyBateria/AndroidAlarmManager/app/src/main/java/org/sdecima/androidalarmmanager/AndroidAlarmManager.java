package org.sdecima.androidalarmmanager;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TimePicker;

public class AndroidAlarmManager extends Activity {

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

	public void programarAlarma(View view) {
		TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
		int hora = tp.getCurrentHour();
		int min = tp.getCurrentMinute();
		
		Calendar now = Calendar.getInstance();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, hora);
		c.set(Calendar.MINUTE, min);
		c.set(Calendar.SECOND, 0);
		
		if(c.compareTo(now) < 0) {
			c.add(Calendar.DATE, 1);
		}
		
		// Programar Alarma
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC,
				c.getTimeInMillis(),
				PendingIntent.getBroadcast(this, 0,
						new Intent("org.sdecima.androidalarmmanager.alarma"), 0)
			);
		
		// cierro la Activity
		finish();
	}
}
