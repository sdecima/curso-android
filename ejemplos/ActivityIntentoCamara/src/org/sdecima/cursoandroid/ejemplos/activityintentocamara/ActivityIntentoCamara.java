package org.sdecima.cursoandroid.ejemplos.activityintentocamara;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class ActivityIntentoCamara extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usar_cam);
	}

    public void sacarFoto(View view) {
    	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	if(requestCode == 1 && resultCode == RESULT_OK) {
    		Bundle extras = intent.getExtras();
    		
    		ImageView imageView = (ImageView) findViewById(R.id.imageView);
    	    Bitmap b = (Bitmap) extras.get("data");
    	    imageView.setImageBitmap(b);
    	}
    }
}
