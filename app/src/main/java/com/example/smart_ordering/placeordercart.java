package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.shashank.sony.fancytoastlib.FancyToast;
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



public class placeordercart extends AppCompatActivity {
    String sub_category,category;
    String add="";
    String s,s1=null;
    int count=0,totalcost=0;
    ProgressDialog progressDialog;
    ImageView imageView = null;
    private ImageView imageView1,imageView2;
    private TextView textView4;
    private LinearLayout linearLayout2;
    private TextView textView5;
    String tableno,price;
    private String tableno1;
    private String name,phone,email;
    private String imagename;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,fooditem_menu.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeordermain);
        SharedPreferences sp=getSharedPreferences("cat",MODE_PRIVATE);
        category=sp.getString("cat","").toString();
        SharedPreferences sp1 = getSharedPreferences("cat_sub", MODE_PRIVATE);
        sub_category = sp1.getString("cat_sub", "").toString();
        ActionBar ab = getSupportActionBar();
        ab.hide();
        SharedPreferences sp5=getSharedPreferences("fooditemsretrive",MODE_PRIVATE);
        String s=sp5.getString("fooditemsretrive","");
        final String str[] = s.split(";");
        SharedPreferences sp6=getSharedPreferences("fooditems_price",MODE_PRIVATE);
        String s1=sp6.getString("fooditems_price","");
        final String str1[] = s1.split(";");
        int arr[]=null;
        for (int i = 0; i < str.length; i++) {
            final String a = str[i];
            String a1 = str1[i];
            textView5=(TextView) findViewById(R.id.price);
            textView5.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView5.setTextSize(25f);
            final int price = Integer.parseInt(a1);
            SharedPreferences sp2 = getSharedPreferences(a, MODE_PRIVATE);
            final int[] temp = {sp2.getInt(a, -1)};
            final int[] total = new int[1];
            total[0]=0;
            SharedPreferences sp3=getSharedPreferences(a+"1",MODE_PRIVATE);
            imagename=sp3.getString(a+"1","").toString();
            if(temp[0] >0){
                total[0] = temp[0] *price;
                totalcost+=total[0];
                count+=temp[0];
                GridLayout gridLayout=(GridLayout) findViewById(R.id.g1);
                CardView cardView=new CardView(this);
                cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                cardView.setCardElevation(20f);
                cardView.setRadius(20f);
                gridLayout.addView(cardView);
                LinearLayout linearLayout=new LinearLayout(this);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        call(a);
                    }
                });
                cardView.addView(linearLayout);

                imageView = new ImageView(this);
                Picasso.get().load("https://smartordering1.000webhostapp.com/upload/"+imagename).into(imageView);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(150, 200));
                TextView textView1 = new TextView(this);
                textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView1.setTextSize(20f);
                textView1.setText("  "+a+" ");
                final TextView textView2 = new TextView(this);
                textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView2.setTextSize(20f);
                textView2.setText("  "+ total[0]);
                textView4=(TextView) findViewById(R.id.place);
                textView4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView4.setTextSize(25f);
                textView4.setText("Place Order");
                linearLayout2=(LinearLayout) findViewById(R.id.placeorder);
                final TextView textView3 = new TextView(this);
                textView3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView3.setTextSize(20f);
                textView3.setText(" "+temp[0]+" ");
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(65, 65);
                linearLayout2.setVisibility(View.VISIBLE);
                imageView1 = new ImageView(this);
                imageView1.setImageResource(R.drawable.add);
                imageView1.requestLayout();
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        temp[0]+=1;
                        count++;
                        total[0]+=price;
                        totalcost+=price;
                        if(temp[0]>10){
                            temp[0]=10;
                            count--;
                            total[0]-=price;
                            totalcost-=price;
                        }
                        if(temp[0]>0){
                            linearLayout2.setVisibility(View.VISIBLE);
                        }
                        textView3.setText(" "+temp[0]+" ");
                        textView2.setText("₹"+ total[0]);
                        textView5.setText("Total ₹"+totalcost);
                        SharedPreferences sp4 = getSharedPreferences(a, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp4.edit();
                        editor.putInt(a, temp[0]);
                        editor.commit();
                    }
                });
                imageView1.setLayoutParams(layoutParams1);
                imageView2 = new ImageView(this);
                imageView2.setImageResource(R.drawable.subtract);
                imageView2.setLayoutParams(layoutParams1);
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        temp[0]-=1;
                        count--;
                        total[0]-=price;
                        totalcost-=price;
                        if(temp[0]<0){
                            temp[0]=0;
                            count++;
                            total[0] +=price;
                            totalcost+=price;
                        }
                        textView3.setText(" "+temp[0]+" ");
                        textView2.setText("₹"+ total[0]);
                        if(count==0){
                            linearLayout2.setVisibility(View.INVISIBLE);
                        }
                        textView5.setText("₹"+totalcost);
                        SharedPreferences sp4 = getSharedPreferences(a, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp4.edit();
                        editor.putInt(a, temp[0]);
                        editor.commit();
                    }
                });
                textView5.setText("₹"+totalcost);
                linearLayout.addView(imageView2);
                linearLayout.addView(textView3);
                linearLayout.addView(imageView1);
                linearLayout.addView(imageView);
                linearLayout.addView(textView1);
                linearLayout.addView(textView2);
            }
        }
    }
    private void call(String a) {
        }
    private void load() {
        TastyToast.makeText(getApplicationContext(), phone+tableno+add+price+"", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
        class delete_itemsdb extends AsyncTask<String,Void,String> {
            Context context;
            AlertDialog alertDialog;
            delete_itemsdb(Context ctx) {
                context = ctx;
            }
            ProgressDialog loading;
            @Override
            protected String doInBackground(String... params) {
                try {
                    String login_url = "https://smartordering1.000webhostapp.com/order.php" ;

                    String p=phone;
                    String t=tableno;
                    String a=add;
                    String c=price;
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(p,"UTF-8")+"&"
                            +URLEncoder.encode("tid","UTF-8")+"="+URLEncoder.encode(t,"UTF-8")+"&"
                            +URLEncoder.encode("olist","UTF-8")+"="+URLEncoder.encode(a,"UTF-8")+"&"
                            +URLEncoder.encode("obill","UTF-8")+"="+URLEncoder.encode(c,"UTF-8")+"&";

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
        String result=null;
        delete_itemsdb db=new delete_itemsdb(this);
        try {
            result=db.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(result.equals("Inserted")){
            progressDialog.dismiss();
            final String str[] = s.split(";");
            for (int i = 0; i < str.length; i++) {
                String a=str[i];
                SharedPreferences sp4 = getSharedPreferences(a, MODE_PRIVATE);
                SharedPreferences.Editor editor = sp4.edit();
                editor.putInt(a, 0);
                editor.commit();
            }
            TastyToast.makeText(getApplicationContext(), "Order placed Successfully", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                Intent intent=new Intent(this,orderplaced.class);
            startActivity(intent);
        }
    }
    public void order(View view) {
        add="";
        SharedPreferences sp5=getSharedPreferences("tableno",MODE_PRIVATE);
        tableno=sp5.getString("tableno","");

        price=totalcost+"";
        fooditemsretrive sc = new fooditemsretrive(this);
        try {
            s = sc.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String str[] = s.split(";");
        for (int i = 0; i < str.length; i++) {
            SharedPreferences sp9=getSharedPreferences(str[i],MODE_PRIVATE);
            int temp=sp9.getInt(str[i],-1);

            if(temp>0){
                add+=temp+"."+str[i]+";";
            }
        }
        SharedPreferences sp6 = getSharedPreferences("name", MODE_PRIVATE);
        name = sp6.getString("name", "").toString();
        SharedPreferences sp7 = getSharedPreferences("phone", MODE_PRIVATE);
        phone = sp7.getString("phone", "").toString();
        SharedPreferences sp8 = getSharedPreferences("email", MODE_PRIVATE);
        email = sp8.getString("email", "").toString();
        progressDialog = new ProgressDialog(placeordercart.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("PLacing Your order... ");
        progressDialog.setMessage("PLease Wait");
        progressDialog.show();
        load();
    }
}

