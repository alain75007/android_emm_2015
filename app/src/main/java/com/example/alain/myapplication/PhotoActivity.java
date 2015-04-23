package com.example.alain.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class PhotoActivity extends ActionBarActivity {

    private MyApplication myApplication;
    private MySingleton mySingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySingleton.restoreLang(this);
        setContentView(R.layout.activity_photo);
        setTitle(R.string.title_activity_photo);
        // TODO put this on onResume (if current lang changed)
        // TODO add restore Lang to other activity
        setImageBitmap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MySingleton.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) return;

        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = data.getExtras();
        if (extras != null) {
            MySingleton.setBitmap((Bitmap) extras.get("data"));
            setImageBitmap();
        }
    }


    private void setImageBitmap() {
        ImageView mImageView = (ImageView) findViewById(R.id.act_photo_imageView);
        mImageView.setImageBitmap(MySingleton.getBitmap());
    }
}
