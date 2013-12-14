package org.sdecima.cursoandroid.ejemplos.storageexternal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StorageExternal extends Activity {

	EditText campo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_storage_external);
		
        campo = (EditText) findViewById(R.id.editText1);
        
        findViewById(R.id.botonBajar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = campo.getText().toString();

				Tarea t = new Tarea();
				t.execute(url);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.storage_external, menu);
		return true;
	}

    class Tarea extends AsyncTask<String, String, Void> {

		@Override
		protected Void doInBackground(String... params) {
			bajarArchivoExterno(params[0]);
			return null;
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			Toast.makeText(getApplicationContext(),
					values[0], Toast.LENGTH_SHORT).show();
		}
		
	    public void bajarArchivoExterno(String strURL) {
	    	
	    	String estadoExterno = Environment.getExternalStorageState();
	    	
	    	if(estadoExterno.equals(Environment.MEDIA_MOUNTED)) {
				publishProgress("Está montado, se va a escribir");
	    	} else if(estadoExterno.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
	    		publishProgress("Está montado sólo lectura");
	    	} else {
	    		publishProgress("Hay un problema con el externo: " + estadoExterno);
	    	}
	    	
	    	try {
	    		
				URL url = new URL(strURL);
				
				InputStream urlStream = url.openStream();
				
				String name = new File(url.getPath()).getName();
				
				File externalFile = new File(getExternalFilesDir(null), name);
				
				FileOutputStream outputStream = new FileOutputStream(externalFile);
				byte[] buf = new byte[8192];
				
				int length = urlStream.read(buf);
			    while (length >= 0) {
			      outputStream.write(buf, 0, length);
			      length = urlStream.read(buf);
			    }
				
			    urlStream.close();
				outputStream.close();
				
				publishProgress("Se copió el archivo correctamente");
				
			} catch (MalformedURLException e) {
				publishProgress("La dirección del archivo es inválida");
			} catch (IOException e) {
				publishProgress("Hubo un error al bajar el archivo");
				publishProgress(e.getMessage());
			}
	    }		
    	
    }
	
}
