package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

public class dummy extends AppCompatActivity {

    private Bitmap bitmap;
    ImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        ActionBar ab=getSupportActionBar();
        ab.hide();
        Bitmap bm;
        spinnerdb sc = new spinnerdb(this);
        String s = null;
        try {
            s = sc.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String str[] = s.split(";");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

        for (int i = 0; i < str.length; i++) {
            final String a=str[i];
            RelativeLayout m_ll = (RelativeLayout) findViewById(R.id.rl1);
            GridLayout gridLayout=(GridLayout) findViewById(R.id.g1);
            TextView text = new TextView(this);
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            text.setText("Category Menu");
            text.setTextSize(30f);
            m_ll.addView(text);
            CardView cardView=new CardView(this);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cardView.setCardElevation(20f);
            cardView.setRadius(20f);
            gridLayout.addView(cardView);
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(0,0,0,50);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call(a);
                }
            });
            cardView.addView(linearLayout);

            imageView = new ImageView(this);
            Picasso.get().load("https://smartordering1.000webhostapp.com/upload/"+str[i]).into(imageView);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(900, 580));
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView1.setTextSize(15f);
            textView1.setText(a);
            linearLayout.addView(imageView);
            linearLayout.addView(textView1);
        }
    }
    public void call(String a){
        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
    }
}
