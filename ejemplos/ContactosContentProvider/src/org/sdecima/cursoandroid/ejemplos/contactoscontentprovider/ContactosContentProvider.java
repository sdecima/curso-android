package org.sdecima.cursoandroid.ejemplos.contactoscontentprovider;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContactosContentProvider extends Activity {

	final int PEDIDO_ELEGIR_CONTACTO = 1234;

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

	public void elegirContacto(View view) {
		// Crea un Intent para elegir un contacto, según lo define el URI de
		// Content Provider
		Intent intent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
		startActivityForResult(intent, PEDIDO_ELEGIR_CONTACTO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Si el pedido fue bien (OK) y el pedido fue PEDIDO_ELEGIR_CONTACTO
		if (resultCode == Activity.RESULT_OK
				&& requestCode == PEDIDO_ELEGIR_CONTACTO) {
			Toast.makeText(this, data.getDataString(), Toast.LENGTH_LONG)
					.show();

			// Hacer una consulta al Content Provider de contactos y
			// obtener el nombre del contacto
			Cursor cursor = getContentResolver().query(data.getData(),
					new String[] { Contacts.DISPLAY_NAME }, null, null, null);

			if (cursor.moveToFirst()) { // true si el cursor no está vacío
				int columnIndex = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
				String nombre = cursor.getString(columnIndex);
				// Muestro el nombre en pantalla
				((TextView) findViewById(R.id.textView1)).setText(nombre);
			}
		}
	}
}
