package org.sdecima.cursoandroid.ejercicios.calculodepropinas;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.sdecima.cursoandroid.ejercicios.calculodepropinas.R;

public class CalculoDePropina_5 extends Activity {
	
	EditText etMonto;
	SeekBar seekBarPropina;
	TextView textViewPropina;
	Spinner spinnerPersonas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etMonto = (EditText) findViewById(R.id.editTextMonto);
        seekBarPropina = (SeekBar) findViewById(R.id.seekBarPropina);
        textViewPropina = (TextView) findViewById(R.id.textViewPropina);
        spinnerPersonas = (Spinner) findViewById(R.id.spinnerPersonas);
		
		registerForContextMenu(etMonto);
		
		configurarSeekBar();
        configurarSpinner();
        inicializar();
	}
	
    public void configurarSeekBar() {
        seekBarPropina.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				textViewPropina.setText("" + progress + "%");
			}
		});
    }
	
    public void configurarSpinner() {
        String[] listaPersonas = {"1", "2", "3", "4", "5", "6", "7", "8"};
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, listaPersonas);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPersonas.setAdapter(aa);
    }
    
	/**
	 * Inicializo los controles en pantalla a valores por defecto
	 */
	public void inicializar() {
		etMonto.setText("0");
		seekBarPropina.setProgress(10);
		spinnerPersonas.setSelection(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			// opción Settings
			break;

		case R.id.action_reset:
			// opción Reset
			inicializar();
			break;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	/**
	 * Lee el valor text del EditText indicado, y lo transforma en un primitivo double
	 */
	public double obtenerValor(EditText et) {
		String strMonto = et.getText().toString();
		double d = Double.parseDouble(strMonto);
		return d;
	}
	
	/**
	 * Lee el valor de un EditText, y valida si se ingresó un número valido y además verifica que no sea negativo.
	 */
	public double validarValor(EditText et, String texto) {
		double valor = -1;
		try {
			valor = obtenerValor(et);
			if(valor < 0) {
				Toast.makeText(this, "Debe ingresar un valor positivo para " + texto, Toast.LENGTH_SHORT).show();
			}
		} catch(NumberFormatException e) {
			Toast.makeText(this, "Debe ingresar " + texto, Toast.LENGTH_SHORT).show();
		}
		return valor;
	}
	
	/**
	 * evento del botón a través de la propiedad onClick
	 */
	public void calcular(View view) {
		// Obtengo el valor del EditText de Monto y muestro mensajes si es inválido
		double dMonto = validarValor(etMonto, "Monto Total");
		// Si es negativo dejo de calcular (ya se mostró mensaje en validarValor)
		if(dMonto < 0)
			return;
		
		// Obtengo el valor del SeekBar de Propina
		double dPropina = seekBarPropina.getProgress();
		
		// Obtengo el valor del EditText de Personas
		double dPersonas = spinnerPersonas.getSelectedItemPosition() + 1;
		
		// calculo el monto total a pagar, y lo que corresponde por persona
		double montoPropina = (dMonto * dPropina) / 100;
		double totalApagar = dMonto + montoPropina;
		double totalPorPersona = totalApagar / dPersonas;
		
		String mensaje = "Monto Total: " + totalApagar +
				"\nTotal por persona: " + totalPorPersona;
		
		//Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
		
		Intent i = new Intent(this, ActivityResultado.class);
		i.putExtra("mensaje", mensaje);
		startActivity(i);
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		switch(v.getId()) {
		case R.id.editTextMonto:
			getMenuInflater().inflate(R.menu.monto_context, menu);
			break;
		case R.id.spinnerPersonas:
			break;
		}
	};
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_limpiar_monto:
			etMonto.setText("0");
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
}
