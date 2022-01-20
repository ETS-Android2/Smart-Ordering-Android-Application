package com.example.smart_ordering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

public class thankyou extends AppCompatActivity {
ImageView fee1,fee2,fee3,thankyou;
EditText review,pin;
Button sub,pinbutton;
TextView textView,staffpintext;
    @Override
    public void onBackPressed() {
        TastyToast.makeText(getApplicationContext(), "you cannot go back", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyoumain);
        fee1=(ImageView) findViewById(R.id.feedback1);
        fee2=(ImageView) findViewById(R.id.feedback2);
        fee3=(ImageView) findViewById(R.id.feedback3);
        thankyou=(ImageView) findViewById(R.id.thankyouimg);
        review=(EditText) findViewById(R.id.editText);
        pin=(EditText) findViewById(R.id.digitpin);
        textView=(TextView)findViewById(R.id.retext);
        staffpintext=(TextView)findViewById(R.id.staffpin);
        sub=(Button)findViewById(R.id.submit);
        pinbutton=(Button)findViewById(R.id.submitpin);
        fee1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review.setVisibility(View.VISIBLE);
                sub.setVisibility(View.VISIBLE);
                SharedPreferences sp=getSharedPreferences("feedback",MODE_PRIVATE);
                SharedPreferences.Editor er=sp.edit();
                er.putString("feedback","1");
                er.commit();
            }
        });
        fee2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review.setVisibility(View.VISIBLE);
                sub.setVisibility(View.VISIBLE);
                SharedPreferences sp=getSharedPreferences("feedback",MODE_PRIVATE);
                SharedPreferences.Editor er=sp.edit();
                er.putString("feedback","2");
                er.commit();
            }
        });
        fee3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review.setVisibility(View.VISIBLE);
                sub.setVisibility(View.VISIBLE);
                SharedPreferences sp=getSharedPreferences("feedback",MODE_PRIVATE);
                SharedPreferences.Editor er=sp.edit();
                er.putString("feedback","3");
                er.commit();
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp=getSharedPreferences("feedback",MODE_PRIVATE);
                String feed=sp.getString("feedback","").toString();

                String r=review.getText().toString();

                textView.setVisibility(View.GONE);
                fee1.setVisibility(View.GONE);
                fee2.setVisibility(View.GONE);
                fee3.setVisibility(View.GONE);
                sub.setVisibility(View.GONE);
                review.setVisibility(View.GONE);
                thankyou.setVisibility(View.VISIBLE);
                thankyou.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thankyou.setVisibility(View.GONE);
                        pin.setVisibility(View.VISIBLE);
                        pinbutton.setVisibility(View.VISIBLE);
                        staffpintext.setVisibility(View.VISIBLE);
                        pinbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(pin.getText().toString().equals("0987")){
                                    Intent intent=new Intent(com.example.smart_ordering.thankyou.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
