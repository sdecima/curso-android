package org.sdecima.cursoandroid.ejemplos.activityintentocamara;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class ActivityIntentoCamara extends Activity {
	
	ImageView imageView;
	Bitmap imagen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usar_cam);

		imageView = (ImageView) findViewById(R.id.imageView);
		
		if(savedInstanceState != null && savedInstanceState.containsKey("imagen")) {
			imagen = (Bitmap) savedInstanceState.getParcelable("imagen");
			imageView.setImageBitmap(imagen);
		}
	}

    public void sacarFoto(View view) {
    	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	if(requestCode == 1 && resultCode == RESULT_OK) {
    		Bundle extras = intent.getExtras();
    		
    		imagen = (Bitmap) extras.get("data");
    	    imageView.setImageBitmap(imagen);
    	}
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	outState.putParcelable("imagen", imagen);
    }
}
