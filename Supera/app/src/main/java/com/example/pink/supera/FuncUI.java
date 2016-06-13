package com.example.pink.supera;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PINK on 2016/6/11.
 */
public class FuncUI {
    public TextView textView;
    public ImageView imageView;
    public Button button, btnBlur;

    public FuncUI(Activity activity){
        textView = (TextView)activity.findViewById(R.id.textView);
        imageView = (ImageView)activity.findViewById(R.id.imageView);
        button = (Button)activity.findViewById(R.id.button);
        //btnBlur = (Button)activity.findViewById(R.id.btnBlur);
    }

    public void setToOnClickListener(View.OnClickListener listener) {
        button.setOnClickListener(listener);
        //btnBlur.setOnClickListener(listener);
    }
}
