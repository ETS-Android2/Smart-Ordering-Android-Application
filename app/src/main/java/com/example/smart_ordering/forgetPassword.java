package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.concurrent.ExecutionException;

public class forgetPassword extends AppCompatActivity {
    String e,c;
    EditText email,code;
    Button reset;
    private String result;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetmain);

        email=(EditText) findViewById(R.id.f_email);
        code=(EditText) findViewById(R.id.f_code);
    }
    public void forget(View view)
    {
        progressDialog = new ProgressDialog(forgetPassword.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("<Mailing Password...");
        progressDialog.show();
        if(email.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show();
        }
        else{
            e=email.getText().toString();
        }
        if(code.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter 4-digit Code", Toast.LENGTH_SHORT).show();
        }
        else{
            c=code.getText().toString();
        }
        if(e!=null && c!=null){
            database c1 = new database(this);
            try {
                result=c1.execute("forgetpass",e,c).get();
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if(result.equals("Check Your Email For Password.")){
                progressDialog.dismiss();
                TastyToast.makeText(getApplicationContext(), "Password has been sent you to the mail", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                Intent intent=new Intent(this,login.class);
                startActivity(intent);
            }
            else{
                progressDialog.dismiss();
                TastyToast.makeText(getApplicationContext(), "Unable to Retrive the password!", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            }
        }

    }
}
