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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.sdsmdg.tastytoast.TastyToast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class table_details extends AppCompatActivity {
Spinner spinner;
    private String position;
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,Admin_menu.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_details);
        ActionBar ab=getSupportActionBar();
        ab.hide();
        progressDialog = new ProgressDialog(table_details.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving...");
        progressDialog.show();
        spinner=findViewById(R.id.tablede);
        tabledata td=new tabledata(this);
        String s= "Select Table Number;";
        try {
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
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int a=i;
                position= str[a];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        progressDialog.dismiss();
    }

    public void call_menu(View view) {
        if(!position.equals("Select Table Number")&& !position.equals("null")){
            SharedPreferences preferences = getSharedPreferences("tableno", Context.MODE_PRIVATE);
            SharedPreferences.Editor preferenceEditor = preferences.edit();
            preferenceEditor.putString("tableno", position);
            preferenceEditor.apply();
            Intent intent=new Intent(this,Admin_menu.class);
            startActivity(intent);
        }
        else{
            TastyToast.makeText(getApplicationContext(), "Please select Table Number!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
    }
}
class tabledata extends AsyncTask<String,Void,String> {
    Context context;
    tabledata (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://smartordering1.000webhostapp.com/table.php" ;

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

