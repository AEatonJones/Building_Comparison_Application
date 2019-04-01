package com.example.ul_buildingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import org.opencv.android.Utils;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImageUtility {

    private String correctResultName = "";
    private double correctResultValue = 0.0;

    private static final String TAG = "ImageUtility";

    public Mat convertBitMapToMat(Bitmap bitmap) {

        Mat mat = new Mat();
        Bitmap bitmap32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bitmap32, mat);

        return mat;
    }

    public String calculateResult(Mat mat) {

        String storage = "/storage/emulated/0";
        File myDir = new File(storage + "/FYP_Buildings");
        String name = " ";
        for (File f : myDir.listFiles()) {
            if (f.isFile()) {
                name = f.getName();
                Log.d(TAG, name);
                String filePath = f.getPath();
                Bitmap bitmapToCompare = BitmapFactory.decodeFile(filePath);
                Mat matToCompare = convertBitMapToMat(bitmapToCompare);

                MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
                MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
                Mat descriptors1 = new Mat();
                Mat descriptors2 = new Mat();

                FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
                DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);

                detector.detect(mat, keypoints1);
                detector.detect(matToCompare, keypoints2);
                extractor.compute(mat, keypoints1, descriptors1);
                extractor.compute(matToCompare, keypoints2, descriptors2);

                DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
                MatOfDMatch matches = new MatOfDMatch();

                matcher.match(descriptors1,descriptors2 ,matches);
                List<DMatch> matches_list = matches.toList();

                double minimum_distance = 64;
                for (int i = 0; i < matches_list.size(); i++) {
                    double distance = matches_list.get(i).distance;
                    if (distance < minimum_distance) {
                        minimum_distance = distance;
                    }
                }

                List<DMatch> good_matches_list = new ArrayList<>();
                for (int i = 0; i < matches_list.size(); i++) {
                    if (matches_list.get(i).distance < 2 * minimum_distance) {

                        good_matches_list.add(matches_list.get(i));
                    }
                }

                if(correctResultValue != 0.0) {
                    if(good_matches_list.size() > correctResultValue) {
                        correctResultValue = good_matches_list.size();
                        correctResultName = name;
                    }
                }
                else {
                    correctResultValue = good_matches_list.size();
                    correctResultName = name;
                }
            }
        }
        Log.d(TAG, name);
        String value = Double.toString(correctResultValue);
        Log.d(TAG, value);
        return correctResultName;
    }

    public String getBuildingCode(String name) {
        String code = "";
        code = name.substring( 0, name.indexOf("_"));
        return code;
    }

    public void uploadPhotoToCollection(Bitmap bitmap, String code) {
        String storage = "/storage/emulated/0";
        File myDir = new File(storage + "/FYP_Buildings");
        myDir.mkdirs();

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = code + "_" + n + ".jpg";
        File file = new File(myDir, fname);

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
