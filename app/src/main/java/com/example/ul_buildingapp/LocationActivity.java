package com.example.ul_buildingapp;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        setButtonOnClickListeners();
    }

    private void setButtonOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        };
        findViewById(R.id.backButton).setOnClickListener(listener);

        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buildingCode = "KBS";
                String[] info = getBuildingInfo(buildingCode);
                //longitude and latitude passed into array
                info[11] = "52.672622";
                info[12] = "-8.576763";

                Intent intent = new Intent(LocationActivity.this, InfoActivity.class);
                intent.putExtra("info", info);
                startActivity(intent);
                finish();
            }
        };
        findViewById(R.id.KBS_Button).setOnClickListener(listener2);

        View.OnClickListener listener3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buildingCode = "GL";
                String[] info = getBuildingInfo(buildingCode);
                //longitude and latitude passed into array
                info[11] = "52.673465";
                info[12] = "-8.573506";

                Intent intent = new Intent(LocationActivity.this, InfoActivity.class);
                intent.putExtra("info", info);
                startActivity(intent);
                finish();
            }
        };
        findViewById(R.id.GL_Button).setOnClickListener(listener3);

        View.OnClickListener listener4 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buildingCode = "PESS";
                String[] info = getBuildingInfo(buildingCode);
                //longitude and latitude passed into array
                info[11] = "52.674644";
                info[12] = "-8.567533";

                Intent intent = new Intent(LocationActivity.this, InfoActivity.class);
                intent.putExtra("info", info);
                startActivity(intent);
                finish();
            }
        };
        findViewById(R.id.PESS_Button).setOnClickListener(listener4);

        View.OnClickListener listener5 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buildingCode = "CSIS";
                String[] info = getBuildingInfo(buildingCode);
                //longitude and latitude passed into array
                info[11] = "52.673923";
                info[12] = "-8.575564";


                Intent intent = new Intent(LocationActivity.this, InfoActivity.class);
                intent.putExtra("info", info);
                startActivity(intent);
                finish();
            }
        };
        findViewById(R.id.CSIS_Button).setOnClickListener(listener5);

        View.OnClickListener listener6 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buildingCode = "SB";
                String[] info = getBuildingInfo(buildingCode);
                //longitude and latitude passed into array
                info[11] = "52.673158";
                info[12] = "-8.577823";

                Intent intent = new Intent(LocationActivity.this, InfoActivity.class);
                intent.putExtra("info", info);
                startActivity(intent);
                finish();
            }
        };
        findViewById(R.id.SB_Button).setOnClickListener(listener6);

        View.OnClickListener listener7 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buildingCode = "TB";
                String[] info = getBuildingInfo(buildingCode);
                //longitude and latitude passed into array
                info[11] = "52.674554";
                info[12] = "-8.577193";

                Intent intent = new Intent(LocationActivity.this, InfoActivity.class);
                intent.putExtra("info", info);
                startActivity(intent);
                finish();
            }
        };
        findViewById(R.id.TB_Button).setOnClickListener(listener7);
    }

    public String[] getBuildingInfo(String buildingCode) {

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        String result[] = new String[13];
        result[0] = buildingCode;
        result[1] = databaseAccess.getSubjectInfo(buildingCode, "NAME");
        result[2] = databaseAccess.getSubjectInfo(buildingCode, "DESCRIPTION");
        result[3] = databaseAccess.getSubjectInfo(buildingCode, "PNUMBER");
        result[4] = databaseAccess.getSubjectInfo(buildingCode, "MONDAY");
        result[5] = databaseAccess.getSubjectInfo(buildingCode, "TUESDAY");
        result[6] = databaseAccess.getSubjectInfo(buildingCode, "WEDNESDAY");
        result[7] = databaseAccess.getSubjectInfo(buildingCode, "THURSDAY");
        result[8] = databaseAccess.getSubjectInfo(buildingCode, "FRIDAY");
        result[9] = databaseAccess.getSubjectInfo(buildingCode, "SATURDAY");
        result[10] = databaseAccess.getSubjectInfo(buildingCode, "SUNDAY");

        databaseAccess.close();
        return result;

    }
}
