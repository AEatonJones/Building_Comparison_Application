package com.example.ul_buildingapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV initialised");
        }
        else {
            Log.d(TAG, "OpenCV not initialised");
        }

        verifyPermissions();
        setButtonOnClickListeners();
    }

    private void setButtonOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Please take the photo with an vertical orientation!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intentCamera,0);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        };
        findViewById(R.id.takePhotoButton).setOnClickListener(listener); // initialises listener.

        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // ends activity.
                System.exit(0);
            }
        };
        findViewById(R.id.quitButton).setOnClickListener(listener2); // initialises listener.

        View.OnClickListener listener3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                finish();
                startActivity(intent);
            }
        };
        findViewById(R.id.locationButton).setOnClickListener(listener3); // initialises listener.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.putExtra( "BitmapImage", bitmap );
        startActivity(intent);
        finish();
    }

    private void verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permission");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {

            //nothing happens if user agrees to permissions.
            //Application requires these permissions to work.
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
    }
}
