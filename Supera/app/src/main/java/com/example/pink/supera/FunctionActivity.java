package com.example.pink.supera;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.opencv.imgproc.Imgproc.GaussianBlur;
import static org.opencv.imgproc.Imgproc.cvtColor;

public class FunctionActivity extends Activity implements View.OnClickListener {
    private FuncUI UI;
    private String type;
    private String imgPath;
    private Bitmap image;
    Mat imgMat;

    public double window_W, window_H;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        UI = new FuncUI(this);
        UI.setToOnClickListener(this);

        // screen window's size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        window_W = metrics.widthPixels;
        window_H = metrics.heightPixels;


        /*Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imgPath = "IMG" + timeStamp + ".jpg";
        File tmpFile = new File(Environment.getExternalStorageDirectory(), imgPath);
        intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tmpFile));
        startActivityForResult(intent_camera, 0);*/

        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");


        UI.textView.setText(type);
        if(type.equals("camera")) {
            Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/";

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            imgPath = mediaStorageDir + "/" + "IMG_" + timeStamp + ".jpg"; // set the image file name
            intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgPath)));

            startActivityForResult(intent_camera, 0); // start the image capture Intent
        }
        else if(type.equals("album")) {
            Intent intent_album = new Intent(Intent.ACTION_PICK);
            intent_album.setType("image/*");
            intent_album.setAction(Intent.ACTION_GET_CONTENT);
            //startActivityForResult(intent_album, REQUEST_IMAGE_SELECT);
            startActivityForResult(intent_album, 0);
        }
    }

    @Override
    public void onClick(View v) {
        //Size size = new Size(image.getWidth(), image.getHeight());
        switch (v.getId()) {
            case R.id.button:
                image = image.copy(Bitmap.Config.ARGB_8888, true);
                //imgMat = new Mat(image.getHeight(), image.getHeight(), CvType.CV_8UC4);
                Utils.bitmapToMat(image, imgMat);
                //Imgproc.Canny(imgMat,imgMatResult,123,250);
                //Imgproc.GaussianBlur(imgMat, imgMatResult, new Size(21,21), 11, 11);
                //cvtColor(imgMat, image, CV_BGR2GRAY);
                //Bitmap imageResult = Bitmap.createBitmap(imgMatResult.cols(),imgMatResult.rows(),Bitmap.Config.ARGB_8888);
                UI.imageView.setImageBitmap(image);
                UI.textView.setText("Q__Q");
                break;
        }
    }
    @Override
    /*取得並顯示照片*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 0)
        {
            //利用BitmapFactory取得剛剛拍照的影像
            if(type.equals("camera")) {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imgPath, bmOptions);
                // Determine how much to scale down the image
                double photoW = bmOptions.outWidth;
                double photoH = bmOptions.outHeight *0.6;
                int scaleFactor = (int)Math.ceil(Math.max((photoW / window_W), (photoH / window_W)));

                // Decode the image file into a Bitmap sized to fill the View
                image = BitmapFactory.decodeFile(imgPath);
                image = ImageTransform.resize(image, scaleFactor);
            }
            //用URI取得相簿中的影像
            else{
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();

                try {//讀取照片，型態為Bitmap
                    image = BitmapFactory.decodeStream(cr.openInputStream(uri));
                }
                catch (FileNotFoundException e)
                {
                }
            }
            UI.imageView.setImageBitmap(image);

            //Mat imgMat = new Mat();
            //Mat imgMatResult = null;
            //Utils.bitmapToMat(image, imgMat);
            //Imgproc.GaussianBlur(imgMat, imgMatResult, new Size(21,21), 11, 11);
            //Bitmap imageResult = Bitmap.createBitmap(imgMatResult.cols(),imgMatResult.rows(),Bitmap.Config.ARGB_8888);
           // UI.imageView.setImageBitmap(imageResult);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
