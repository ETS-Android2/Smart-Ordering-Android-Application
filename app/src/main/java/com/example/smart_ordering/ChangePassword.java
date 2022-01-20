package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.concurrent.ExecutionException;import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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


public class ChangePassword extends AppCompatActivity {
EditText current,new_pass,re_pass,email;
Button save;
String c1,n,r,e;
String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ActionBar ab=getSupportActionBar();
        ab.hide();
        current=(EditText) findViewById(R.id.currentpass);
        new_pass=(EditText) findViewById(R.id.newpass);
        re_pass=(EditText) findViewById(R.id.repass);

    }
    public void savepass(View view){
        c1=current.getText().toString();
        n=new_pass.getText().toString();
        if(re_pass.getText().toString().equals(n)){
            r=re_pass.getText().toString();
        }
        else{
            TastyToast.makeText(getApplicationContext(), "Password donot Match", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }
        SharedPreferences sp=getSharedPreferences("email",MODE_PRIVATE);
        String e=sp.getString("email","").toString();
        if(r!=null && e!=null){
            load(e,c1,n);
        }
    }
    public void load(final String e1, final String c1, final String pass1){
        class changepassworddb extends AsyncTask<String,Void,String> {
            ProgressDialog loading;


            @Override
            protected String doInBackground(String... params) {
                String result = null;
                try {
                    String login_url = "https://smartordering1.000webhostapp.com/changepassword.php" ;

                    String e=e1;
                    String c = c1;
                    String pass = pass1;
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(e,"UTF-8")+"&"
                            +URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(c,"UTF-8")+"&"
                            +URLEncoder.encode("newpass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+"&";
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
                super.onPreExecute();
                loading = ProgressDialog.show(ChangePassword.this,"change Passwors","pleasewait",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }
        }
        changepassworddb db=new changepassworddb();
        try {
            result=db.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(result.equals("Password changed")){
            TastyToast.makeText(getApplicationContext(), "Password Changed", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
        }
        else{
            TastyToast.makeText(getApplicationContext(), "Error", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }
    }
}