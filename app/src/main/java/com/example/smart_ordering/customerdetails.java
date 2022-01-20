package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.shashank.sony.fancytoastlib.FancyToast;

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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class customerdetails extends AppCompatActivity
{
    ProgressDialog progressDialog;
    EditText mobile,name,email;
    String m,n,e,a;
    String tableno;
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        Intent intent=new Intent(this,watermelon.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerdetailsmain);
        mobile=(EditText) findViewById(R.id.c_mobile);
        name=(EditText) findViewById(R.id.c_name);
        email=(EditText) findViewById(R.id.c_email);
        SharedPreferences sp5=getSharedPreferences("tableno",MODE_PRIVATE);
        tableno=sp5.getString("tableno","");
        if(new InternetDialog(this).getInternetStatus()){
            TastyToast.makeText(getApplicationContext(), "This device is online", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
        }
        else{
            TastyToast.makeText(getApplicationContext(), "This device is offline", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }
    }
    public void customer_details(View view){
        progressDialog = new ProgressDialog(customerdetails.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging You in...");
        progressDialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);
        if(!isValidatePhone(mobile.getText().toString())){
            progressDialog.dismiss();
            TastyToast.makeText(getApplicationContext(), "Enter a Proper Mobile Number", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }
        else{
            m=mobile.getText().toString();
        }
        if(name.getText().toString().isEmpty()){
            n=name.getText().toString();
        }
        else{
            if(name.getText().toString().length()<=2){
                progressDialog.dismiss();
                TastyToast.makeText(getApplicationContext(), "Enter Your Name", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            }
            else{
                n=name.getText().toString();
            }
        }
        if(email.getText().toString().isEmpty()){
            e=email.getText().toString();
        }
        else{
            if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                e=email.getText().toString();
            }
            else{
                progressDialog.dismiss();
                TastyToast.makeText(getApplicationContext(), "Enter proper EmailAddress.........", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            }
        }

        if(n!=null && e!=null && m!=null && !tableno.equals("")){
            SharedPreferences sp = getSharedPreferences("name", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", n);
            editor.commit();
            SharedPreferences sp1 = getSharedPreferences("email", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sp1.edit();
            editor1.putString("email",e);
            editor1.commit();
            SharedPreferences sp2 = getSharedPreferences("phone", MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sp2.edit();
            editor2.putString("phone", m);
            editor2.commit();
            fooditemsretrive fdb=new fooditemsretrive(this);
            String a1=null;
            try {
                a1=fdb.execute().get();
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            final String[] str=a1.split(";");
            for(int i=0;i<str.length;i++){
                SharedPreferences sp3 = getSharedPreferences(str[i], MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sp3.edit();
                editor3.putInt(str[i], 0);
                editor3.commit();
            }
            load();

        }
        else if(tableno.equals("")){
            progressDialog.dismiss();
            TastyToast.makeText(getApplicationContext(), "This Device is not ready to use as Admin has not selected the table number!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
    }
    private boolean isValidatePhone(String e){
        if(mobile.getText().toString().length()==10)
        {
            return Patterns.PHONE.matcher(e).matches();
        }
        return false;
    }

    public void log(View view) {
        Intent intent=new Intent(this,login.class);
        startActivity(intent);
    }
    public void load(){
        class customerdetailsdb extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            String result = null;
            @Override
            protected String doInBackground(String... params) {
                try {
                    String login_url = "https://smartordering1.000webhostapp.com/customerdetails.php" ;
                    String name = params[0];
                    String mob= params[1];
                    String em= params[2];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                            +URLEncoder.encode("mob","UTF-8")+"="+URLEncoder.encode(mob,"UTF-8")+"&"
                            +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(em,"UTF-8")+"&";
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    result="";
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
                return result;
            }

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected void onPostExecute(String s) {
            }
        }
        customerdetailsdb c=new customerdetailsdb();
        try {
            a=c.execute(n,m,e).get();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if(a.equals("1")){
            progressDialog = new ProgressDialog(customerdetails.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Retriving Menu...");
            progressDialog.show();
            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    progressDialog.dismiss(); // when the task active then close the dialog
                    t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                }
            }, 2000);
            Intent intent=new Intent(this,category_menu.class);
            startActivity(intent);
        }
    }

}


