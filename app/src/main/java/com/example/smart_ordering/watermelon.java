package com.example.smart_ordering;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.concurrent.ExecutionException;

public class watermelon extends AppCompatActivity {
ImageView seed1,seed2,seed3,seed4,seed5,seed6,seed7,seed8,seed9,seed10,seed11,seed12,seed13;
    ObjectAnimator objectanimator,objectanimator1,objectanimator2,objectanimator3,objectanimator4,objectanimator5,objectanimator6,objectanimator7,objectanimator8,objectanimator9,objectanimator10,objectanimator11,objectanimator12,objectanimator13,objectanimator14,objectanimator15,objectanimator16,objectanimator17,objectanimator18,objectanimator19,objectanimator20,objectanimator21,objectanimator22,objectanimator23,objectanimator24,objectanimator25;
    private static int tout = 1700;
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        Intent intent=new Intent(this,details.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watermelonmain);
        seed1=(ImageView)findViewById(R.id.seed1);
        seed2=(ImageView)findViewById(R.id.seed2);
        seed3=(ImageView)findViewById(R.id.seed3);
        seed4=(ImageView)findViewById(R.id.seed4);
        seed5=(ImageView)findViewById(R.id.seed5);
        seed6=(ImageView)findViewById(R.id.seed6);
        seed7=(ImageView)findViewById(R.id.seed7);
        seed8=(ImageView)findViewById(R.id.seed8);
        seed9=(ImageView)findViewById(R.id.seed9);
        seed10=(ImageView)findViewById(R.id.seed10);
        seed11=(ImageView)findViewById(R.id.seed11);
        seed12=(ImageView)findViewById(R.id.seed12);
        seed13=(ImageView)findViewById(R.id.seed13);


        objectanimator = ObjectAnimator.ofFloat(seed1,"x",7);
        objectanimator1 = ObjectAnimator.ofFloat(seed1,"y",100);
        objectanimator2 = ObjectAnimator.ofFloat(seed2,"x",74);
        objectanimator3 = ObjectAnimator.ofFloat(seed2,"y",104);
        objectanimator4 = ObjectAnimator.ofFloat(seed3,"x",306);
        objectanimator5 = ObjectAnimator.ofFloat(seed3,"y",24);
        objectanimator6 = ObjectAnimator.ofFloat(seed4,"x",657);
        objectanimator7 = ObjectAnimator.ofFloat(seed4,"y",90);
        objectanimator8 = ObjectAnimator.ofFloat(seed5,"x",685);
        objectanimator9 = ObjectAnimator.ofFloat(seed5,"y",60);
        objectanimator10 = ObjectAnimator.ofFloat(seed6,"x",936);
        objectanimator11 = ObjectAnimator.ofFloat(seed6,"y",100);
        objectanimator12 = ObjectAnimator.ofFloat(seed7,"x",10);
        objectanimator13 = ObjectAnimator.ofFloat(seed7,"y",340);
        objectanimator14 = ObjectAnimator.ofFloat(seed8,"x",316);
        objectanimator15 = ObjectAnimator.ofFloat(seed8,"y",470);
        objectanimator16 = ObjectAnimator.ofFloat(seed9,"x",806);
        objectanimator17 = ObjectAnimator.ofFloat(seed9,"y",510);
        objectanimator18 = ObjectAnimator.ofFloat(seed10,"x",996);
        objectanimator19 = ObjectAnimator.ofFloat(seed10,"y",300);
        objectanimator20 = ObjectAnimator.ofFloat(seed11,"x",36);
        objectanimator21 = ObjectAnimator.ofFloat(seed11,"y",630);
        objectanimator22 = ObjectAnimator.ofFloat(seed12,"x",450);
        objectanimator23 = ObjectAnimator.ofFloat(seed12,"y",680);
        objectanimator24 = ObjectAnimator.ofFloat(seed13,"x",486);
        objectanimator25 = ObjectAnimator.ofFloat(seed13,"y",680);
        start();
    }
    private void start() {

        objectanimator.setDuration(1);
        objectanimator1.setDuration(1);
        objectanimator2.setDuration(1);
        objectanimator3.setDuration(1);
        objectanimator4.setDuration(1);
        objectanimator5.setDuration(1);
        objectanimator6.setDuration(1);
        objectanimator7.setDuration(1);
        objectanimator8.setDuration(1);
        objectanimator9.setDuration(1);
        objectanimator10.setDuration(1);
        objectanimator11.setDuration(1);
        objectanimator12.setDuration(1);
        objectanimator13.setDuration(1);
        objectanimator14.setDuration(1);
        objectanimator15.setDuration(1);
        objectanimator16.setDuration(1);
        objectanimator17.setDuration(1);
        objectanimator18.setDuration(1);
        objectanimator19.setDuration(1);
        objectanimator20.setDuration(1);
        objectanimator21.setDuration(1);
        objectanimator22.setDuration(1);
        objectanimator23.setDuration(1);
        objectanimator24.setDuration(1);
        objectanimator25.setDuration(1);

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
        objectanimator16.start();
        objectanimator17.start();
        objectanimator18.start();
        objectanimator19.start();
        objectanimator20.start();
        objectanimator21.start();
        objectanimator22.start();
        objectanimator23.start();
        objectanimator25.start();
        objectanimator24.start();
        new Handler().postDelayed(new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                Animation2();
            }
        },tout);

    }

    private void Animation2() {


        objectanimator = ObjectAnimator.ofFloat(seed1,"x",7);
        objectanimator1 = ObjectAnimator.ofFloat(seed1,"y",900);
        objectanimator2 = ObjectAnimator.ofFloat(seed2,"x",74);
        objectanimator3 = ObjectAnimator.ofFloat(seed2,"y",904);
        objectanimator4 = ObjectAnimator.ofFloat(seed3,"x",306);
        objectanimator5 = ObjectAnimator.ofFloat(seed3,"y",904);
        objectanimator6 = ObjectAnimator.ofFloat(seed4,"x",657);
        objectanimator7 = ObjectAnimator.ofFloat(seed4,"y",900);
        objectanimator8 = ObjectAnimator.ofFloat(seed5,"x",685);
        objectanimator9 = ObjectAnimator.ofFloat(seed5,"y",900);
        objectanimator10 = ObjectAnimator.ofFloat(seed6,"x",936);
        objectanimator11 = ObjectAnimator.ofFloat(seed6,"y",900);
        objectanimator12 = ObjectAnimator.ofFloat(seed7,"x",10);
        objectanimator13 = ObjectAnimator.ofFloat(seed7,"y",940);
        objectanimator14 = ObjectAnimator.ofFloat(seed8,"x",316);
        objectanimator15 = ObjectAnimator.ofFloat(seed8,"y",970);
        objectanimator16 = ObjectAnimator.ofFloat(seed9,"x",806);
        objectanimator17 = ObjectAnimator.ofFloat(seed9,"y",910);
        objectanimator18 = ObjectAnimator.ofFloat(seed10,"x",996);
        objectanimator19 = ObjectAnimator.ofFloat(seed10,"y",900);
        objectanimator20 = ObjectAnimator.ofFloat(seed11,"x",36);
        objectanimator21 = ObjectAnimator.ofFloat(seed11,"y",930);
        objectanimator22 = ObjectAnimator.ofFloat(seed12,"x",450);
        objectanimator23 = ObjectAnimator.ofFloat(seed12,"y",980);
        objectanimator24 = ObjectAnimator.ofFloat(seed13,"x",486);
        objectanimator25 = ObjectAnimator.ofFloat(seed13,"y",980);
        animationstart2();
    }

    private void animationstart2() {

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
        objectanimator16.setDuration(1800);
        objectanimator17.setDuration(1800);
        objectanimator18.setDuration(1800);
        objectanimator19.setDuration(1800);
        objectanimator20.setDuration(1800);
        objectanimator21.setDuration(1800);
        objectanimator22.setDuration(1800);
        objectanimator23.setDuration(1800);
        objectanimator24.setDuration(1800);
        objectanimator25.setDuration(1800);

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
        objectanimator16.start();
        objectanimator17.start();
        objectanimator18.start();
        objectanimator19.start();
        objectanimator20.start();
        objectanimator21.start();
        objectanimator22.start();
        objectanimator23.start();
        objectanimator25.start();
        objectanimator24.start();
    }

    public void next(View view) {
        progressDialog = new ProgressDialog(watermelon.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Welcome to The  Dream Restaurant");
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        getdetails();
    }

    private void getdetails() {
        progressDialog = new ProgressDialog(watermelon.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Welcome to The  Dream Restaurant");
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        spinnerdb sc = new spinnerdb(this);
        String s = null;
        try {
            s = sc.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(s.equals("0 results")){
            TastyToast.makeText(getApplicationContext(), "No Values Found!", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }
        else {
            SharedPreferences preferences1 = getSharedPreferences("categorynames", Context.MODE_PRIVATE);
            SharedPreferences.Editor preferenceEditor1 = preferences1.edit();
            preferenceEditor1.putString("categorynames", s);
            preferenceEditor1.apply();
        }
        final String str[] = s.split(";");
        for(int i=0;i<str.length;i++){

            sub_catspinnerdb sub = new sub_catspinnerdb(this);
            String s1 = null;
            try {
                s1 = sub.execute(str[i]).get();
                SharedPreferences preferences2 = getSharedPreferences(str[i], Context.MODE_PRIVATE);
                SharedPreferences.Editor preferenceEditor2 = preferences2.edit();
                preferenceEditor2.putString(str[i], s1);
                preferenceEditor2.apply();
                final String str1[] = s1.split(";");
                for(int j=0;j<str1.length;j++){
                    fooditems_spinner fooditems = new fooditems_spinner(this);
                    String s3 = null;
                    try {
                        s3 = fooditems.execute(str1[j]).get();
                        SharedPreferences preferences3 = getSharedPreferences(str1[j], Context.MODE_PRIVATE);
                        SharedPreferences.Editor preferenceEditor3 = preferences3.edit();
                        preferenceEditor3.putString(str1[j], s3);
                        preferenceEditor3.apply();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        String s2=null;
        fooditemsretrive fir = new fooditemsretrive(this);
        try {
            s2 = fir.execute().get();
            SharedPreferences preferences2 = getSharedPreferences("fooditemsretrive", Context.MODE_PRIVATE);
            SharedPreferences.Editor preferenceEditor2 = preferences2.edit();
            preferenceEditor2.putString("fooditemsretrive", s2);
            preferenceEditor2.apply();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String s3=null;
        fooditems_price fip = new fooditems_price(this);
        try {
            s3 = fip.execute("*").get();
            SharedPreferences preferences2 = getSharedPreferences("fooditems_price", Context.MODE_PRIVATE);
            SharedPreferences.Editor preferenceEditor2 = preferences2.edit();
            preferenceEditor2.putString("fooditems_price", s3);
            preferenceEditor2.apply();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        call();
    }
    private void call(){
        Intent intent=new Intent(this,customerdetails.class);
        startActivity(intent);
    }
}
