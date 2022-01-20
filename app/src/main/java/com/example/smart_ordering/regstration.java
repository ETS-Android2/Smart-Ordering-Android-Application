package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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


public class regstration extends AppCompatActivity {
    EditText name,email,mobileno,pwd,code;
    Button signup;
    String n,m,e,p,c,t;
    private String result;
    Spinner spinner;
    String position=null;
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,Admin_menu.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationmain);

        name=(EditText) findViewById(R.id.regi_name);
        email=(EditText) findViewById(R.id.regi_email);
        mobileno=(EditText) findViewById(R.id.regi_number);
        code=(EditText) findViewById(R.id.regi_code);
        spinner=(Spinner) findViewById(R.id.spinner1);
        signup=(Button) findViewById(R.id.regi_button);
        String s="Type;Admin;Chef;staff";
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void reg(View view) {
        progressDialog = new ProgressDialog(regstration.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        n=name.getText().toString();
        if(!isValidatePhone(mobileno.getText().toString())){
            TastyToast.makeText(getApplicationContext(), "Number should be of 10 digits", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        else{
            m=mobileno.getText().toString();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            TastyToast.makeText(getApplicationContext(), "Enter Proper email address!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        else{
            e=email.getText().toString();
        }
        if(name.getText().toString().length()<3)
        {
            TastyToast.makeText(getApplicationContext(), "please,Enter your name!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        else
        {
            n=name.getText().toString();
        }
        if (code.getText().toString().matches("[0-9]+") == true && code.getText().toString().length() == 4) {

            c= code.getText().toString();
        }
        else
        {
            TastyToast.makeText(getApplicationContext(), "your code should be of 4 digits only!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        if(position!=null){
            t=position;
        }
        else{
            TastyToast.makeText(getApplicationContext(), "Please select Type!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        p=getAlphaNumericString(10);

        if(n!=null && m!=null && e!=null && p!=null && c!=null && t!=null){
            registrationdb connectMySql = new registrationdb(this);
            try {
                result=connectMySql.execute("login",n,m,e,p,c,t).get();
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if(result.equals("11")){
                progressDialog.dismiss();
                TastyToast.makeText(getApplicationContext(), "Registration Sucessfull And Password has been sent to the registered Email", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                Intent intent=new Intent(this,Admin_menu.class);
                startActivity(intent);
            }
            else{
                progressDialog.dismiss();
                TastyToast.makeText(getApplicationContext(), "EmailId Already exists!"+result, TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
            }
        }
        else{
            progressDialog.dismiss();
        }

    }
    static String getAlphaNumericString(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }
    private boolean isValidatePhone(String e)
    {
        if(e.length()==10)
        {
            return Patterns.PHONE.matcher(e).matches();
        }
        return false;
    }
}
class registrationdb extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    registrationdb(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "https://smartordering1.000webhostapp.com/register.php" ;
        if(type.equals("login")) {
            try {
                String name= params[1];
                String mob= params[2];
                String em = params[3];
                String password = params[4];
                String code= params[5];
                String typ= params[6];

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("mob","UTF-8")+"="+URLEncoder.encode(mob,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(em,"UTF-8")+"&"
                        +URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("code","UTF-8")+"="+URLEncoder.encode(code,"UTF-8")+"&"
                        +URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(typ,"UTF-8")+"&";
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

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        //alertDialog.setMessage(result);
        //alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
