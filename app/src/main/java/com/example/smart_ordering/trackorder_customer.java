package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.squareup.picasso.Picasso;

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
import java.util.List;
import java.util.concurrent.ExecutionException;

public class trackorder_customer extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,orderplaced.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackorder_customer);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        SharedPreferences sp7 = getSharedPreferences("phone", MODE_PRIVATE);
        String phone = sp7.getString("phone", "").toString();
        String value=null;
        itemTrackData idb=new itemTrackData(this);
        try {
            value=idb.execute(phone).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String str[]=value.split(";");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.l1);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(50f);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(0,0,0,50);
        textView.setGravity(Gravity.CENTER);
        textView.setText("Track Order");
        linearLayout.addView(textView);

        for (int i = 0; i < str.length; i++) {
            String quantity = str[i];
            i=i+1;
            String name = str[i];
            i=i+1;
            int status;
            final TextView textView2 = new TextView(this);
            textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView2.setTextSize(20f);
            textView2.setTextColor(Color.WHITE);
            textView2.setText(quantity+"  "+name+" ");

            HorizontalStepView setpview5 = new HorizontalStepView(this);
            List<StepBean> stepsBeanList = new ArrayList<>();


            if(str[i].equals("0")){

                StepBean stepBean0 = new StepBean("Waiting For \nChef to accept\n the order",0);
                StepBean stepBean1 = new StepBean("Preparing",-1);
                StepBean stepBean2 = new StepBean("Prepared",-1);
                stepsBeanList.add(stepBean0);
                stepsBeanList.add(stepBean1);
                stepsBeanList.add(stepBean2);
                setpview5
                        .setStepViewTexts(stepsBeanList)
                        .setTextSize(10)
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(trackorder_customer.this, R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(trackorder_customer.this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(trackorder_customer.this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
                linearLayout.addView(textView2);
                linearLayout.addView(setpview5);
            }
            else if(str[i].equals("1")){
                StepBean stepBean0 = new StepBean("Waiting For \nChef to accept\n the order",1);
                StepBean stepBean1 = new StepBean("Preparing",0);
                StepBean stepBean2 = new StepBean("Prepared",-1);
                stepsBeanList.add(stepBean0);
                stepsBeanList.add(stepBean1);
                stepsBeanList.add(stepBean2);
                setpview5
                        .setStepViewTexts(stepsBeanList)
                        .setTextSize(12)
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(trackorder_customer.this, R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(trackorder_customer.this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(trackorder_customer.this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
                linearLayout.addView(textView2);
                linearLayout.addView(setpview5);
            }
            else if(str[i].equals("2")){
                StepBean stepBean0 = new StepBean("Waiting For \nChef to accept\n the order",1);
                StepBean stepBean1 = new StepBean("Preparing",1);
                StepBean stepBean2 = new StepBean("Prepared",1);
                stepsBeanList.add(stepBean0);
                stepsBeanList.add(stepBean1);
                stepsBeanList.add(stepBean2);
                setpview5
                        .setStepViewTexts(stepsBeanList)
                        .setTextSize(12)
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(trackorder_customer.this, R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(trackorder_customer.this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(trackorder_customer.this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
                linearLayout.addView(textView2);
                linearLayout.addView(setpview5);
            }

        }
    }
}
class itemTrackData extends AsyncTask<String,Void,String> {
    Context context;
    itemTrackData (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://smartordering1.000webhostapp.com/itemdetails.php" ;

        try {
            String phone=params[0];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&";
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

