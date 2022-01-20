package com.example.smart_ordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

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

public class check_bill extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,staffmenu.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkbillmain);
        call();
        TastyToast.makeText(getApplicationContext(), "Click Image to refresh Orders", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
    }
    public void call(){
        progressDialog = new ProgressDialog(check_bill.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving Values...");
        progressDialog.show();
        ImageView ref=(ImageView)findViewById(R.id.refresh1);
        String value=null;
        checkbillstatus idb=new checkbillstatus(this);
        try {
            value=idb.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(value.equals("0 results")){
            TastyToast.makeText(getApplicationContext(), "No Values Found!", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            progressDialog.dismiss();
        }
        else
        {
            TastyToast.makeText(getApplicationContext(), "Retrived", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
            String str[]=value.split(";");
            for (int i = 0; i < str.length; i++) {
                final String tid=str[i];
                i=i+1;
                final String phone = str[i];
                i=i+1;
                final String price = str[i];


                final GridLayout gridLayout=(GridLayout) findViewById(R.id.g1);
                CardView cardView=new CardView(this);
                cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                cardView.setCardElevation(20f);
                cardView.setRadius(20f);
                gridLayout.addView(cardView);
                LinearLayout linearLayout=new LinearLayout(this);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                cardView.addView(linearLayout);

                TextView textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setTextSize(20f);
                textView.setText("  "+tid+" ");
                TextView textView1 = new TextView(this);
                textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView1.setTextSize(20f);
                textView1.setText("  "+phone+" ");
                final TextView textView2 = new TextView(this);
                textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView2.setTextSize(20f);
                textView2.setText("  "+price+" ");
                final Button startpreparing=new Button(this);
                startpreparing.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                startpreparing.setText("Paid");
                startpreparing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String result=null;
                        updatebillstatus db=new updatebillstatus(check_bill.this);
                        try {
                            result=db.execute(phone,"paid").get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(result.equals("1")){
                            gridLayout.removeAllViews();
                            call();
                        }
                    }
                });

                linearLayout.addView(textView);
                linearLayout.addView(textView1);
                linearLayout.addView(textView2);
                linearLayout.addView(startpreparing);
            }
        }
        progressDialog.dismiss();
    }

    public void refreshcall(View view) {
        call();
    }
}

class checkbillstatus extends AsyncTask<String,Void,String> {
    Context context;
    checkbillstatus (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://smartordering1.000webhostapp.com/checkbills.php" ;

        try {
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
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
class updatebillstatus extends AsyncTask<String,Void,String> {
    Context context;
    updatebillstatus (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://smartordering1.000webhostapp.com/updatebillstatus.php" ;

        try {
            String phone=params[0];
            String status=params[1];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&"
                    +URLEncoder.encode("status","UTF-8")+"="+URLEncoder.encode(status,"UTF-8")+"&";
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
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
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
