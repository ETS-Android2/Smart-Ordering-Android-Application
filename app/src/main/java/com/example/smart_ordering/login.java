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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.concurrent.ExecutionException;

public class login extends AppCompatActivity {
    EditText username,password;
    Button login;
    String n,pw;
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,customerdetails.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginmain);
        username=(EditText)findViewById(R.id.loginname);
        password=(EditText)findViewById(R.id.loginpw);

    }
    public void loginfunction(View view){
        progressDialog = new ProgressDialog(login.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        n=username.getText().toString();
        pw=password.getText().toString();
        SharedPreferences sp=getSharedPreferences("email",MODE_PRIVATE);
        SharedPreferences.Editor er=sp.edit();
        er.putString("email",n);
        er.commit();
        load(n,pw);
    }
    public void load(final String n, final String pw){

        class logindb extends AsyncTask<String,Void,String> {
            Context context;
            logindb(Context ctx) {
                context = ctx;
            }
            @Override
            protected void onPreExecute() {
            }

            @Override
            protected void onPostExecute(String s) {

            }
            @Override
            protected String doInBackground(String... params) {
                String type = "login";
                String login_url = "https://smartordering1.000webhostapp.com/login.php" ;
                if(type.equals("login")) {
                    try {
                        String em = n;
                        String password = pw;
                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(em,"UTF-8")+"&"
                                +URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&";
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
                }
                return null;
            }
        }

        logindb lg=new logindb(this);
        String result = null;
        try {
            result=lg.execute(n,pw).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(result.equals("Admin")){
            TastyToast.makeText(getApplicationContext(), "Success", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
            Intent intent=new Intent(this,Admin_menu.class);
            startActivity(intent);
        }
        else if(result.equals("Chef")){
            TastyToast.makeText(getApplicationContext(), "Success", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
            Intent intent=new Intent(this,chef_menu.class);
            startActivity(intent);
        }
        else if(result.equals("staff")){
            TastyToast.makeText(getApplicationContext(), "Success", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
            Intent intent=new Intent(this,staffmenu.class);
            startActivity(intent);
        }
        else{
            progressDialog.dismiss();
            TastyToast.makeText(getApplicationContext(), "Invalid Username or Password!", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }


    }

    public void forget(View view) {
        Intent intent=new Intent(this,forgetPassword.class);
        startActivity(intent);
    }
}

