package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.lang.System.load;

public class delete_sub_category extends AppCompatActivity {
    Spinner subcat,spinner;
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
        setContentView(R.layout.activity_delete_sub_category);
        ActionBar ab=getSupportActionBar();
        ab.hide();
        subcat = (Spinner) findViewById(R.id.delete_subcat);
        spinner=(Spinner) findViewById(R.id.display_cat);
        spinnerdb sd=new spinnerdb(this);
        String s= "Select Category;";
        try {
            s += sd.execute().get();
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
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position=str[i]+"";
                run(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void run(String pos){

        sub_catspinnerdb sc = new sub_catspinnerdb(this);
        String s = "Select subCategory;";
        try {
            s += sc.execute(pos).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String str[] = s.split(";");
        List<String> al = new ArrayList<String>();
        al = Arrays.asList(str);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, al);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcat.setAdapter(dataAdapter);
        subcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    public void deleteCat(View view) {

        String p=position;
        progressDialog = new ProgressDialog(delete_sub_category.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
        load(p);
    }
    public void load(final String p1){
        class delete_subcatdb extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
            }

            @Override
            protected void onPostExecute(String s) {
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String login_url = "https://smartordering1.000webhostapp.com/deletesubcat.php" ;
                    String p=p1;
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("sname","UTF-8")+"="+URLEncoder.encode(p,"UTF-8")+"&";

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


        }
        delete_subcatdb db=new delete_subcatdb();
        try {
            result=db.execute().get();
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
        else{
            progressDialog.dismiss();
            TastyToast.makeText(getApplicationContext(), "Unable to delete!", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }
    }
}


