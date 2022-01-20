package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class delete_table extends AppCompatActivity {
Spinner table;
String position=null;
    private String result;
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,delete_details.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_table);
        ActionBar ab=getSupportActionBar();
        ab.hide();
        table=(Spinner) findViewById(R.id.delete_spinner_table);
        tabledata td=new tabledata(this);
        String s= "Select Table Number;";
        try {
            progressDialog = new ProgressDialog(delete_table.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            s += td.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String str[] = s.split(";");
        List<String> al = new ArrayList<String>();
        al = Arrays.asList(str);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, al);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        table.setAdapter(dataAdapter);
        progressDialog.dismiss();
        table.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int a = i;
                position = str[a];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void delete_table(View view) {
        String p=position;
        progressDialog = new ProgressDialog(delete_table.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
        load(p);

    }
    public void load(String p){
        class delete_tabledb extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            ProgressDialog progressDoalog;

            @Override
            protected String doInBackground(String... params) {
                try {
                    String login_url = "https://smartordering1.000webhostapp.com/deletetable.php" ;

                    String p=params[0];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("tname","UTF-8")+"="+URLEncoder.encode(p,"UTF-8")+"&";
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
            protected void onPostExecute(String s) {
            }
        }
        delete_tabledb db=new delete_tabledb();
        try {
            result=db.execute(p).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(result.equals("1")){
            progressDialog.dismiss();
            TastyToast.makeText(getApplicationContext(), "Successfully Deleted", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
            Intent intent=new Intent(this,Admin_menu.class);
            startActivity(intent);
        }
    }
}
