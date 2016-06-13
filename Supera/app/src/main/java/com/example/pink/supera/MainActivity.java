package com.example.pink.supera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends Activity implements View.OnClickListener {
    private MainUI UI;
    private String type;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UI = new MainUI(this);
        UI.setToOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCamera:
                type = "camera";

                break;
            case R.id.btnAlbum:
                type = "album";

                break;
        }
        Intent intent = new Intent(MainActivity.this, FunctionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        intent.putExtras(bundle);

        startActivity(intent);


    }
}
