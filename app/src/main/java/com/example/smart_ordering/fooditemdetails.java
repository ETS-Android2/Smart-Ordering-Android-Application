package com.example.smart_ordering;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.concurrent.ExecutionException;

public class fooditemdetails extends AppCompatActivity {
ImageView imageView,add,sub;
TextView name,cost,incredents,description,counter;
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        progressDialog = new ProgressDialog(fooditemdetails.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retrieving You Back...");
        progressDialog.show();
        SharedPreferences sp=getSharedPreferences("fooditemintent",MODE_PRIVATE);
        String value=sp.getString("fooditemintent","").toString();
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        if(value.equals("search")){
            Intent intent=new Intent(this,category_menu.class);
            startActivity(intent);
        }

        else if(value.equals("subcategorymenu")){
            Intent intent=new Intent(this,sub_category_menu.class);
            startActivity(intent);
        }
        else if(value.equals("fooditemmenu")){
            Intent intent=new Intent(this,fooditem_menu.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditemdetails);
        imageView=(ImageView)findViewById(R.id.fooditem_image);
        add=(ImageView)findViewById(R.id.addcount);
        sub=(ImageView)findViewById(R.id.subcount);
        name=(TextView)findViewById(R.id.item_title);
        cost=(TextView)findViewById(R.id.item_cost_detail);
        incredents=(TextView)findViewById(R.id.item_incredents_details);
        counter=(TextView)findViewById(R.id.count);
        description=(TextView)findViewById(R.id.item_desc_details);
        SharedPreferences sp=getSharedPreferences("fooditemdetail",MODE_PRIVATE);
        String title=sp.getString("fooditemdetail","").toString();
        final String[] food=title.split(";");
        Picasso.get().load("https://smartordering1.000webhostapp.com/upload/"+ food[0]+""+food[1]+""+food[2]).into(imageView);
        String s=null;
        fooditemdetailsdb db=new fooditemdetailsdb(this);
        final String a=food[0];
        try {
            s=db.execute(a).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(s.equals("0 results")){

        }
        else{
            String[] str=s.split(";");
            name.setText(str[0]);
            cost.setText("Cost:- ₹"+str[1]);
            final String[] temp=str[2].split(",");
            String inc="Incredents :-\n";
            for(int i=0;i<temp.length;i++){
                inc+="•"+temp[i]+"\n";
            }
            incredents.setText(inc);
            description.setText("Description:-\n"+str[3]);
            SharedPreferences sp2 = getSharedPreferences(a, MODE_PRIVATE);
            final int[] count = {sp2.getInt(a, 0)};
            counter.setText(count[0]+"");
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count[0]++;
                    if(count[0]>10){
                        count[0]=10;
                    }
                    counter.setText(count[0]+"");
                    SharedPreferences sp4 = getSharedPreferences(food[0], MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp4.edit();
                    editor.putInt(food[0], count[0]);
                    editor.commit();
                }
            });
            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count[0]--;
                    if(count[0]<0){
                        count[0]=0;
                    }
                    counter.setText(count[0]+"");
                    SharedPreferences sp4 = getSharedPreferences(food[0], MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp4.edit();
                    editor.putInt(food[0], count[0]);
                    editor.commit();
                }
            });
        }
    }

    public void back(View view) {
        progressDialog = new ProgressDialog(fooditemdetails.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving You Back...");
        progressDialog.show();
        SharedPreferences sp=getSharedPreferences("fooditemintent",MODE_PRIVATE);
        String value=sp.getString("fooditemintent","").toString();
        if(value.equals("search")){
            Intent intent=new Intent(this,category_menu.class);
            startActivity(intent);
        }
        else if(value.equals("fooditemmenu")){
            Intent intent=new Intent(this,fooditem_menu.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}

class fooditemdetailsdb extends AsyncTask<String,Void,String> { Context context;
    fooditemdetailsdb(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            String login_url = "https://smartordering1.000webhostapp.com/fooditemdetails.php";
            String name = params[0];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("foodname","UTF-8")+"="+URLEncoder.encode(name,"UTF-8");
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
