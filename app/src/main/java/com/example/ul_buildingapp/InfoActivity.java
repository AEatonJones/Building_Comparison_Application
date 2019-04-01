package com.example.ul_buildingapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = "InfoActivity";
    private String[] info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        info = retrieveInfo();
        setResultsIntoFields(info);
        setButtonOnClickListeners();
    }

    private void setButtonOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:"+info[11]+","+info[12]+"?z=17");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        };
        findViewById(R.id.mapsButton).setOnClickListener(listener);

        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, LocationActivity.class);
                finish();
                startActivity(intent);
            }
        };
        findViewById(R.id.goBackButton).setOnClickListener(listener2);
    }

    public String[] retrieveInfo() {
        String[] Info;
        Intent intent = getIntent();
        Info = intent.getStringArrayExtra("info");
        Log.d(TAG,"results has been received into InfoActivity");
        return  Info;
    }

    private void setResultsIntoFields(String[] info) {
        ScrollView scrollInfo = findViewById(R.id.resultsView);
        TextView txt = new TextView(this);
        txt.setTextColor(Color.BLACK);
        txt.setText("Name: "+info[1]+"\n\nDescription: "+info[2]+"\n\nPhone Number: "+info[3]+
                "\n\nOpening Hours"+"\nMonday: "+info[4]+"\nTuesday: "+info[5]+"\nWednesday: "+info[6]+
                "\nThursday: "+info[7]+"\nFriday: "+info[8]+"\nSaturday: "+info[9]+"\nSunday: "+info[10]);
        scrollInfo.addView(txt);
    }

}
