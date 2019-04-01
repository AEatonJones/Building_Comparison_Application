package com.example.ul_buildingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.opencv.core.Mat;


public class LoadingActivity extends AppCompatActivity {

    private static final String TAG = "LoadingActivity";
    private ImageUtility imageUtility;
    private Mat mat;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        imageUtility = new ImageUtility();
        bitmap = getPhotoFromActivity();
        mat = imageUtility.convertBitMapToMat(bitmap);
        Log.d("IMAGE","Mat of Image has been created");

        startAsyncTask();

    }

    private Bitmap getPhotoFromActivity() {
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        Log.d(TAG,"Image has been received into LoadingActivity");
        return  bitmap;
    }

    private void sendInfoToResults(String[] resultValues,Bitmap bitmap) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("results", resultValues);
        intent.putExtra("photo",bitmap);
        startActivity(intent);
        finish();
    }

    public void startAsyncTask() {
        AsyncTaskComputation AsyncTask = new AsyncTaskComputation();
        AsyncTask.execute(mat);
    }

    private class AsyncTaskComputation extends AsyncTask<Mat,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Mat... mats) {
            String buildingResult = imageUtility.calculateResult(mat);
            return buildingResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String buildingCode = imageUtility.getBuildingCode(s);
            Log.d(TAG, buildingCode);

            //get info from database
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
            databaseAccess.open();
            String result[] = new String[11];
            result[0] = buildingCode;
            result[1] = databaseAccess.getSubjectInfo(buildingCode, "Name");
            result[2] = databaseAccess.getSubjectInfo(buildingCode, "Description");
            result[3] = databaseAccess.getSubjectInfo(buildingCode, "PNumber");
            result[4] = databaseAccess.getSubjectInfo(buildingCode, "Monday");
            result[5] = databaseAccess.getSubjectInfo(buildingCode, "Tuesday");
            result[6] = databaseAccess.getSubjectInfo(buildingCode, "Wednesday");
            result[7] = databaseAccess.getSubjectInfo(buildingCode, "Thursday");
            result[8] = databaseAccess.getSubjectInfo(buildingCode, "Friday");
            result[9] = databaseAccess.getSubjectInfo(buildingCode, "Saturday");
            result[10] = databaseAccess.getSubjectInfo(buildingCode, "Sunday");

            databaseAccess.close();

            sendInfoToResults(result,bitmap);
        }
    }
}
