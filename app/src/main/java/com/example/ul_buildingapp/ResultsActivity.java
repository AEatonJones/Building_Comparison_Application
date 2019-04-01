package com.example.ul_buildingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = "ResultsActivity";
    private ImageUtility imageUtility;

    private Bitmap image;
    private String[] results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        imageUtility = new ImageUtility();

        image = getPhotoFromActivity();
        results = getResultsFromActivity();

        setImageIntoImageView(image);
        setResultsIntoFields(results);
        setButtonOnClickListeners();
    }

    private void setButtonOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class); // initialises intent.
                finish(); // ends activity.
                startActivity(intent); // starts intent.
            }
        };
        findViewById(R.id.goBackButton).setOnClickListener(listener); // initialises listener.

        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ResultsActivity.this); // creates alert dialog.
                builder.setMessage("Are you sure you wish to upload this photo into the collection?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //imageUtility.uploadPhotoToCollection(image, results[0]);
                        Toast.makeText(ResultsActivity.this,"Image has been uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ResultsActivity.this,"Action has been cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        };
        findViewById(R.id.mapsButton).setOnClickListener(listener2); // initialises listener.
    }

    private Bitmap getPhotoFromActivity() {
        Intent intent = getIntent();
        Bitmap bitmap = intent.getParcelableExtra("photo");
        Log.d(TAG,"Image has been received into ResultsActivity");
        return  bitmap;
    }

    private String[] getResultsFromActivity() {
        String[] results;
        Intent intent = getIntent();
        results = intent.getStringArrayExtra("results");
        Log.d(TAG,"results has been received into ResultsActivity");
        return  results;
    }

    private void setImageIntoImageView(Bitmap bitmap) {

        ImageView image = findViewById(R.id.resultImage);
        image.setImageBitmap(bitmap);
    }

    private void setResultsIntoFields(String[] results) {
        ScrollView scrollResults = findViewById(R.id.resultsView);
        TextView info = findViewById(R.id.infoTitle);
        info.bringToFront();
        TextView txt = new TextView(this);
        txt.setTextColor(Color.BLACK);
        txt.setText("Name: "+results[1]+"\n\nDescription: "+results[2]+"\n\nPhone Number: "+results[3]+
                "\n\nOpening Hours"+"\nMonday: "+results[4]+"\nTuesday: "+results[5]+"\nWednesday: "+results[6]+
                "\nThursday: "+results[7]+"\nFriday: "+results[8]+"\nSaturday: "+results[9]+"\nSunday: "+results[10]);
        scrollResults.addView(txt);
    }
}
