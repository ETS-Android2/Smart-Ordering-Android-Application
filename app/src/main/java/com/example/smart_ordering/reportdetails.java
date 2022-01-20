package com.example.smart_ordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class reportdetails extends AppCompatActivity {
ImageView refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportdetailmain);
        refresh=(ImageView) findViewById(R.id.refreshreport);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
        call();

    }
    public void call(){
        SharedPreferences sp=getSharedPreferences("reports",MODE_PRIVATE);
        String report=sp.getString("reports","").toString();;

        String s=null;
        if(report.equals("overall")){
            overallreport fip = new overallreport(this);
            try {
                s = fip.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GridLayout gridLayout1=(GridLayout) findViewById(R.id.g1);
            CardView cardView1=new CardView(this);
            cardView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cardView1.setCardElevation(20f);
            cardView1.setRadius(20f);
            gridLayout1.addView(cardView1);
            LinearLayout linearLayout1=new LinearLayout(this);
            linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout1.setGravity(Gravity.CENTER);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            cardView1.addView(linearLayout1);
            final TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView1.setTextSize(20f);
            textView1.setText("                                                                     oid    cid      Phone number     tid         price                    date                        status");
            textView1.setPadding(10,10,10,10);
            linearLayout1.addView(textView1);

            final String str[] = s.split("/");
            for (int i = 0; i < str.length; i++) {
                final String temp = str[i];
                final String str1[] = temp.split(";");
                final String oid = str1[0];
                final String cid = str1[1];
                final String phone = str1[2];
                final String tid = str1[3];
                final String price = str1[4];
                final String date = str1[5];
                final String status = str1[6];

                GridLayout gridLayout=(GridLayout) findViewById(R.id.g1);
                CardView cardView=new CardView(this);
                cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                cardView.setCardElevation(20f);
                cardView.setRadius(20f);
                gridLayout.addView(cardView);
                LinearLayout linearLayout=new LinearLayout(this);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                cardView.addView(linearLayout);
                final TextView textView2 = new TextView(this);
                textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView2.setTextSize(20f);
                textView2.setText("                                                                     "+oid+"     "+cid+"     "+phone+"       "+tid+"       "+price+"         "+date+"             "+status);
                textView2.setPadding(10,10,10,10);
                linearLayout.addView(textView2);

            }
        }
        else if(report.equals("today")){
            todayreport fip = new todayreport(this);
            try {
                s = fip.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(s.equals("0")){
                Toast.makeText(this, "No orders today", Toast.LENGTH_SHORT).show();
            }
            else{
                GridLayout gridLayout1=(GridLayout) findViewById(R.id.g1);
                CardView cardView1=new CardView(this);
                cardView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                cardView1.setCardElevation(20f);
                cardView1.setRadius(20f);
                gridLayout1.addView(cardView1);
                LinearLayout linearLayout1=new LinearLayout(this);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout1.setGravity(Gravity.CENTER);
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                cardView1.addView(linearLayout1);
                final TextView textView1 = new TextView(this);
                textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView1.setTextSize(20f);
                textView1.setText("                                                                     oid    cid      Phone number     tid         price                    date                        status");
                textView1.setPadding(10,10,10,10);
                linearLayout1.addView(textView1);

                final String str[] = s.split("/");

                for (int i = 0; i < str.length; i++) {
                    final String temp = str[i];
                    final String str1[] = temp.split(";");
                    final String oid = str1[0];
                    final String cid = str1[1];
                    final String phone = str1[2];
                    final String tid = str1[3];
                    final String price = str1[4];
                    final String date = str1[5];
                    final String status = str1[6];

                    GridLayout gridLayout=(GridLayout) findViewById(R.id.g1);
                    CardView cardView=new CardView(this);
                    cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    cardView.setCardElevation(20f);
                    cardView.setRadius(20f);
                    gridLayout.addView(cardView);
                    LinearLayout linearLayout=new LinearLayout(this);
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    linearLayout.setGravity(Gravity.CENTER);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    cardView.addView(linearLayout);
                    final TextView textView2 = new TextView(this);
                    textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    textView2.setTextSize(20f);
                    textView2.setText("                                                                     "+oid+"     "+cid+"     "+phone+"       "+tid+"       "+price+"         "+date+"             "+status);
                    textView2.setPadding(10,10,10,10);
                    linearLayout.addView(textView2);

                }
            }
        }
    }
}
class overallreport extends AsyncTask<String,Void,String> { Context context;
    overallreport(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            String login_url = "https://smartordering1.000webhostapp.com/overallreport.php" ;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

class todayreport extends AsyncTask<String,Void,String> { Context context;
    todayreport(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            String login_url = "https://smartordering1.000webhostapp.com/todayreport.php" ;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

