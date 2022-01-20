package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

public class check_orders extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkordersmain);
        call();
        TastyToast.makeText(getApplicationContext(), "Click Image to refresh Orders", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
    }
    public void call(){
        progressDialog = new ProgressDialog(check_orders.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving Values...");
        progressDialog.show();
        ImageView refresh=(ImageView)findViewById(R.id.refresh);
        String value=null;
        checkorderdb idb=new checkorderdb(this);
        try {
            value=idb.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(value.equals("0 results")){
            TastyToast.makeText(getApplicationContext(), "No Values Found!", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }
        else {
            String str[]=value.split(";");
            TastyToast.makeText(getApplicationContext(), "Retrived", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
            for (int i = 0; i < str.length; i++) {
                String quantity = str[i];
                i=i+1;
                final String name = str[i];
                i=i+1;
                final String status =str[i];
                i=i+1;
                final String cid=str[i];


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

                TextView textView1 = new TextView(this);
                textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView1.setTextSize(20f);
                textView1.setText("  "+quantity+" ");
                final TextView textView2 = new TextView(this);
                textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView2.setTextSize(20f);
                textView2.setText("  "+name+" ");
                final Button startpreparing=new Button(this);
                startpreparing.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                if(status.equals("0")){
                    startpreparing.setText("Start Preparing");
                    startpreparing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String result=null;
                            updateorders db=new updateorders(check_orders.this);
                            try {
                                result=db.execute(name,"1",cid).get();
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

                }
                else if(status.equals("1")){
                    startpreparing.setText("Prepared");
                    startpreparing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String result=null;
                            updateorders db=new updateorders(check_orders.this);
                            try {
                                result=db.execute(name,"2",cid).get();
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

                }
                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridLayout.removeAllViews();
                        call();
                    }
                });
                linearLayout.addView(textView1);
                linearLayout.addView(textView2);
                linearLayout.addView(startpreparing);
            }
        }
        progressDialog.dismiss();
    }

}

class checkorderdb extends AsyncTask<String,Void,String> {
    Context context;
    checkorderdb (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://smartordering1.000webhostapp.com/check_orderdb.php" ;

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
class updateorders extends AsyncTask<String,Void,String> {
    Context context;
    updateorders (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://smartordering1.000webhostapp.com/updateorders.php" ;

        try {
            String name=params[0];
            String status=params[1];
            String cid=params[2];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                    +URLEncoder.encode("status","UTF-8")+"="+URLEncoder.encode(status,"UTF-8")+"&"
                    +URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8")+"&";
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



