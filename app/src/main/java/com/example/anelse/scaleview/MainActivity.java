package com.example.anelse.scaleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private CustomScaleView scale_view;
    private Button b;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scale_view=findViewById(R.id.scale_view);
        scale_view.setMinScale(1.0f);
        scale_view.setMaxScale(12.0f);
        scale_view.setCurrentScale(6.0f);
        iv=findViewById(R.id.imageView2);
        b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
    }

    float[] test={6.5f,7.1f,12f,3.4f};
    Random r=new Random();
    private void change(){
        float now=test[r.nextInt(4)];
        scale_view.setCurrentScale(now);
        if(now>7){
            iv.setImageResource(R.mipmap.fasting_normal_blood_sugar);
        }else{
            iv.setImageResource(R.mipmap.normal_blood_glucose);
        }
        b.setText(now+"");
    }
}
