package com.example.smart_ordering;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.concurrent.ExecutionException;

public class details extends AppCompatActivity {
    ObjectAnimator objectanimator,objectanimator1,objectanimator2,objectanimator3,objectanimator4,objectanimator5,objectanimator6,objectanimator7,objectanimator8,objectanimator9,objectanimator10,objectanimator11,objectanimator12,objectanimator13,objectanimator14,objectanimator15;
    ImageView imgview1,imgview2,imgview3,imgview4,imgview5,imgview6,imgview7,imgview8;
    TextView textView;

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgview1 = (ImageView)findViewById(R.id.imageView1);
        imgview2 = (ImageView)findViewById(R.id.imageView2);
        imgview3 = (ImageView)findViewById(R.id.imageView3);
        imgview4 = (ImageView)findViewById(R.id.imageView4);
        imgview5 = (ImageView)findViewById(R.id.imageView5);
        imgview6 = (ImageView)findViewById(R.id.imageView6);
        imgview7 = (ImageView)findViewById(R.id.imageView7);
        imgview8 = (ImageView)findViewById(R.id.imageView8);
        textView=(TextView)findViewById(R.id.textView2);

        objectanimator = ObjectAnimator.ofFloat(imgview1,"x",112);
        objectanimator1 = ObjectAnimator.ofFloat(imgview1,"y",140);
        objectanimator2 = ObjectAnimator.ofFloat(imgview2,"x",250);
        objectanimator3 = ObjectAnimator.ofFloat(imgview2,"y",100);
        objectanimator4 = ObjectAnimator.ofFloat(imgview3,"x",201);
        objectanimator5 = ObjectAnimator.ofFloat(imgview3,"y",100);
        objectanimator6 = ObjectAnimator.ofFloat(imgview4,"x",310);
        objectanimator7 = ObjectAnimator.ofFloat(imgview4,"y",119);
        objectanimator8 = ObjectAnimator.ofFloat(imgview5,"x",500);
        objectanimator9 = ObjectAnimator.ofFloat(imgview5,"y",490);
        objectanimator10 = ObjectAnimator.ofFloat(imgview6,"x",424);
        objectanimator11 = ObjectAnimator.ofFloat(imgview6,"y",380);
        objectanimator12 = ObjectAnimator.ofFloat(imgview7,"x",110);
        objectanimator13 = ObjectAnimator.ofFloat(imgview7,"y",500);
        objectanimator14 = ObjectAnimator.ofFloat(imgview8,"x",00);
        objectanimator15 = ObjectAnimator.ofFloat(imgview8,"y",365);
        start();
    }

    private void start() {
        objectanimator.setDuration(1800);
        objectanimator1.setDuration(1800);
        objectanimator2.setDuration(1800);
        objectanimator3.setDuration(1800);
        objectanimator4.setDuration(1800);
        objectanimator5.setDuration(1800);
        objectanimator6.setDuration(1800);
        objectanimator7.setDuration(1800);
        objectanimator8.setDuration(1800);
        objectanimator9.setDuration(1800);
        objectanimator10.setDuration(1800);
        objectanimator11.setDuration(1800);
        objectanimator12.setDuration(1800);
        objectanimator13.setDuration(1800);
        objectanimator14.setDuration(1800);
        objectanimator15.setDuration(1800);
        objectanimator.start();
        objectanimator1.start();
        objectanimator2.start();
        objectanimator3.start();
        objectanimator4.start();
        objectanimator5.start();
        objectanimator6.start();
        objectanimator7.start();
        objectanimator8.start();
        objectanimator9.start();
        objectanimator10.start();
        objectanimator11.start();
        objectanimator12.start();
        objectanimator13.start();
        objectanimator14.start();
        objectanimator15.start();

    }

    public void next(View view) {
        Intent intent=new Intent(this,watermelon.class);
        startActivity(intent);
    }
}
