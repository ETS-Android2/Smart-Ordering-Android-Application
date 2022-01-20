package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class orderplaced extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        TastyToast.makeText(getApplicationContext(), "you cannot go back", TastyToast.LENGTH_LONG, TastyToast.INFO).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderplacedmain);
    }

    public void add(View view) {
        progressDialog = new ProgressDialog(orderplaced.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Intent intent=new Intent(this,category_menu.class);
        startActivity(intent);
    }

    public void trackorder(View view) {
        progressDialog = new ProgressDialog(orderplaced.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Tracking Your Order...");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        Intent intent=new Intent(this,trackorder_customer.class);
        startActivity(intent);
    }

    public void Bill(View view) {
        final AlertDialog.Builder alBuilder=new AlertDialog.Builder(this);
        alBuilder.setTitle("Are You Sure You Want To Check Out?");
        alBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp7 = getSharedPreferences("phone", MODE_PRIVATE);
                String phone = sp7.getString("phone", "").toString();
                sms smsdb=new sms(orderplaced.this);
                String result=null;
                try {
                    result=smsdb.execute(phone).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                if(result.equals("1")){
                    updatebillstatus1 db=new updatebillstatus1(orderplaced.this);
                    db.execute(phone,"waiting");
                    Intent intent=new Intent(orderplaced.this,thankyou.class);
                    startActivity(intent);
                }
            }
        });
        alBuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TastyToast.makeText(getApplicationContext(), "cancelled", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
            }
        });
        alBuilder.setCancelable(false);
        alBuilder.show();
    }
}
class sms extends AsyncTask<String,Void,String> {
    Context context;
    sms (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://smartordering1.000webhostapp.com/sms.php" ;

        try {
            String phone=params[0];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&";
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

class updatebillstatus1 extends AsyncTask<String,Void,String> {
    Context context;
    updatebillstatus1 (Context ctx) {
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


