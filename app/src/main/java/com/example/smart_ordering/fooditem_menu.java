package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class fooditem_menu extends AppCompatActivity {
    ProgressDialog progressDialog;
    private ImageView imageView;
    private ImageView imageView1,imageView2;
    String sub_category,category;
    int count=0;
    int totalcost=0;
    int cal=0;
    private String tableno;
    private String s2;
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,sub_category_menu.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooditemsmain);
        searchView = (SearchView) findViewById(R.id.searchView3);
        listView = (ListView) findViewById(R.id.lv3);
        searchfooditems db = new searchfooditems(this);
        String s1=null;
        try {
            s1 = db.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String temp[] = s1.split("/");
        final String s11[] = new String[temp.length];
        for(int i=0;i<temp.length;i++){
            final String temp2[] = temp[i].split(";");
            s11[i]=temp2[0];
        }

        SharedPreferences sp5=getSharedPreferences("categorynames",MODE_PRIVATE);
        String s2=sp5.getString("categorynames","");
        final String str2[] = s2.split(";");
        for (int i = 0; i < str2.length; i++) {
            GridLayout gridLayout2=(GridLayout) findViewById(R.id.g2);
            final String a=str2[i];
            CardView cardView=new CardView(this);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cardView.setCardElevation(20f);
            cardView.setRadius(20f);
            gridLayout2.addView(cardView);
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(0,0,0,50);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog = new ProgressDialog(fooditem_menu.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Retriving Sub-category...");
                    progressDialog.show();
                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            progressDialog.dismiss(); // when the task active then close the dialog
                            t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                        }
                    }, 2000);
                    call1(a);
                }
            });
            cardView.addView(linearLayout);

            imageView = new ImageView(this);
            Picasso.get().load("https://smartordering1.000webhostapp.com/upload/"+str2[i]).resize(460,345).
                    centerCrop().into(imageView);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(460, 345));
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView1.setTextSize(15f);
            textView1.setText(a);
            linearLayout.addView(imageView);
            linearLayout.addView(textView1);
        }
        progressDialog = new ProgressDialog(fooditem_menu.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving...");
        progressDialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<String> list1 = new ArrayList<>();
                String b=query;
                String[] str1=b.split("");
                final String[] result = new String[100];
                int[] number = new int[100];
                int count=0;
                final String[] fullname= new String[100];
                for(int i=0;i<s11.length;i++){
                    int c=0;
                    String a=(s11[i]);
                    String[] str=a.split("");
                    for(int j=0;j<str1.length;j++){
                        for(int k=0;k<str.length;k++){
                            if(str1[j].equals(str[k])){
                                c++;
                                break;
                            }
                        }
                    }
                    if(c>((str1.length/2)+2)){
                        listView.setVisibility(View.VISIBLE);
                        result[count]=a;
                        number[count]=c;
                        fullname[count]=temp[i];
                        count++;
                    }
                    else{
                    }
                }
                for (int i = 0; i < count; ++i)
                {
                    for (int j = i + 1; j < count; ++j)
                    {
                        if (number[i] > number[j])
                        {
                            String temp =  result[i];
                            result[i] = result[j];
                            result[j] = temp;

                            String temp1 =  fullname[i];
                            fullname[i] = fullname[j];
                            fullname[j] = temp1;
                        }
                    }
                }
                for(int i=0;i<count;i++){
                    list1.add(result[i]);
                }
                        /*
                        if(list.contains(query)){
                            listView.setVisibility(View.VISIBLE);
                            gridLayout.setVisibility(View.GONE);
                            Toast.makeText(category_menu.this, query, Toast.LENGTH_SHORT).show();
                            adapter.getFilter().filter(query);
                        }
                        else{
                            Toast.makeText(category_menu.this, query, Toast.LENGTH_SHORT).show();
                        }

 */
                adapter = new ArrayAdapter<String>(fooditem_menu.this, android.R.layout.simple_list_item_1,list1);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        progressDialog = new ProgressDialog(fooditem_menu.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Retriving "+result[position]+" Details");
                        progressDialog.show();
                        String a=fullname[position];
                        SharedPreferences sp3=getSharedPreferences("fooditemdetail",MODE_PRIVATE);
                        SharedPreferences.Editor er=sp3.edit();
                        er.putString("fooditemdetail",a);
                        er.commit();
                        SharedPreferences sp4=getSharedPreferences("fooditemintent",MODE_PRIVATE);
                        SharedPreferences.Editor er1=sp4.edit();
                        er1.putString("fooditemintent","fooditemmenu");
                        er1.commit();
                        TastyToast.makeText(getApplicationContext(), a, TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                        Intent intent=new Intent(fooditem_menu.this,fooditemdetails.class);
                        startActivity(intent);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }

        });

        fooditems();

    }

    private void call1(String a) {
        SharedPreferences sp=getSharedPreferences("cat",MODE_PRIVATE);
        SharedPreferences.Editor er=sp.edit();
        er.putString("cat",a);
        er.commit();
        progressDialog.dismiss();
        progressDialog = new ProgressDialog(fooditem_menu.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving...");
        progressDialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);
        subcatcall();
    }

    private void subcatcall() {
        SharedPreferences sp=getSharedPreferences("cat",MODE_PRIVATE);
        String category=sp.getString("cat","").toString();
        SharedPreferences sp5=getSharedPreferences(category,MODE_PRIVATE);
        String s=sp5.getString(category,"");
        final String str[] = s.split(";");

        for (int i = 0; i < str.length; i++) {
            final String a=str[i];
            GridLayout gridLayout=(GridLayout) findViewById(R.id.g3);
            CardView cardView=new CardView(this);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cardView.setCardElevation(20f);
            cardView.setRadius(20f);
            gridLayout.addView(cardView);
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(0,0,0,50);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sp=getSharedPreferences("cat_sub",MODE_PRIVATE);
                    SharedPreferences.Editor er=sp.edit();
                    er.putString("cat_sub",a);
                    er.commit();
                    progressDialog = new ProgressDialog(fooditem_menu.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Retriving...");
                    progressDialog.show();
                    fooditems();
                }
            });
            cardView.addView(linearLayout);

            imageView = new ImageView(this);
            Picasso.get().load("https://smartordering1.000webhostapp.com/upload/"+ str[i]+category).resize(460,345).
                    centerCrop().into(imageView);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(460, 345));
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView1.setTextSize(15f);
            textView1.setText(a);
            linearLayout.addView(imageView);
            linearLayout.addView(textView1);
            if(cal>0){
                gridLayout.removeAllViews();
                cal=0;
                subcatcall();
            }
        }
        cal++;
    }

    private void fooditems() {
        SharedPreferences sp=getSharedPreferences("cat",MODE_PRIVATE);
        category=sp.getString("cat","").toString();
        SharedPreferences sp1 = getSharedPreferences("cat_sub", MODE_PRIVATE);
        sub_category = sp1.getString("cat_sub", "").toString();
        SharedPreferences sp6=getSharedPreferences(sub_category,MODE_PRIVATE);
        String s=sp6.getString(sub_category,"");
        final String str[] = s.split(";");

        fooditems_price fip = new fooditems_price(this);
        String s1 = null;
        try {
            s1 = fip.execute(sub_category).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String str1[] = s1.split(";");
        int arr[]=null;
        for (int i = 0; i < str.length; i++) {
            final String a=str[i];
            String a1=str1[i];

            final int price=Integer.parseInt(a1);
            final LinearLayout linearLayout2=(LinearLayout) findViewById(R.id.placeorder);
            final TextView calprice = (TextView) findViewById(R.id.price);
            ImageView imageView4=(ImageView) findViewById(R.id.place);
            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    order();
                }
            });
            GridLayout gridLayout=(GridLayout) findViewById(R.id.g1);
            CardView cardView=new CardView(this);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cardView.setCardElevation(20f);
            cardView.setRadius(20f);
            gridLayout.addView(cardView);
            final LinearLayout linearLayout=new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(0,0,0,50);
            cardView.addView(linearLayout);

            imageView = new ImageView(this);
            Picasso.get().load("https://smartordering1.000webhostapp.com/upload/"+ str[i]+""+category+""+sub_category).resize(460,345).
                    centerCrop().into(imageView);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(460, 345));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog = new ProgressDialog(fooditem_menu.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Retriving Details...");
                    progressDialog.show();
                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            progressDialog.dismiss(); // when the task active then close the dialog
                            t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                        }
                    }, 2000);
                    call(a+";"+category+";"+sub_category);
                }
            });
            final TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView1.setTextSize(15f);
            textView1.setText(a);
            textView1.setGravity(Gravity.CENTER);
            final TextView textView2 = new TextView(this);
            textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView2.setTextSize(15f);
            textView2.setText("₹"+price);
            textView2.setGravity(Gravity.CENTER);
            LinearLayout linearLayout1=new LinearLayout(this);
            linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(65, 65);
            linearLayout1.setGravity(Gravity.CENTER);
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
            final TextView textView=new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(layoutParams);
            imageView1 = new ImageView(this);
            imageView1.setImageResource(R.drawable.add);
            imageView1.requestLayout();
            SharedPreferences sp2 = getSharedPreferences(a, MODE_PRIVATE);
            final int[] c = {sp2.getInt(a, 0)};
            textView.setText(c[0]+"");
            if(c[0]>0){
                linearLayout2.setVisibility(View.VISIBLE);
            }
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c[0] += 1;
                    count++;
                    totalcost+=price;
                    if(c[0]>10){
                        c[0]=10;
                        count--;
                        totalcost-=price;
                    }
                    textView.setText(c[0]+"");
                    calprice.setText("Total ₹"+totalcost);
                    SharedPreferences sp = getSharedPreferences(a, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(a, c[0]);
                    editor.commit();
                    SharedPreferences sp3=getSharedPreferences(a+"1",MODE_PRIVATE);
                    SharedPreferences.Editor er=sp3.edit();
                    er.putString(a+"1",a+""+category+""+sub_category);
                    er.commit();
                    if(c[0]>0){
                        linearLayout2.setVisibility(View.VISIBLE);
                    }
                }
            });
            imageView1.setLayoutParams(layoutParams);
            imageView2 = new ImageView(this);
            imageView2.setImageResource(R.drawable.subtract);
            imageView2.setLayoutParams(layoutParams);
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c[0] -= 1;
                    count--;
                    totalcost-=price;
                    if(c[0]<0){
                        c[0]=0;
                        count++;
                        totalcost+=price;
                    }
                    if(count==0){
                        totalcost=0;
                        linearLayout2.setVisibility(View.INVISIBLE);
                    }
                    textView.setText(c[0]+"");
                    calprice.setText("Total ₹"+totalcost);
                    SharedPreferences sp = getSharedPreferences(a, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(a, c[0]);
                    editor.commit();
                    SharedPreferences sp3=getSharedPreferences(a+"1",MODE_PRIVATE);
                    SharedPreferences.Editor er=sp3.edit();
                    er.putString(a+"1",a+""+category+""+sub_category);
                    er.commit();

                }
            });
            SharedPreferences sp5=getSharedPreferences("tableno",MODE_PRIVATE);
            tableno=sp.getString("tableno","").toString();
            linearLayout1.addView(imageView1);
            linearLayout1.addView(textView);
            linearLayout1.addView(imageView2);
            linearLayout.addView(imageView);
            linearLayout.addView(textView1);
            linearLayout.addView(textView2);
            linearLayout.addView(linearLayout1);
            if(cal>0){
                gridLayout.removeAllViews();
                cal=0;
                progressDialog = new ProgressDialog(fooditem_menu.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Retriving Food Items...");
                progressDialog.show();
                fooditems();
            }
        }
        cal++;
        progressDialog.dismiss();
    }

    private void call(String a) {
        SharedPreferences sp3=getSharedPreferences("fooditemdetail",MODE_PRIVATE);
        SharedPreferences.Editor er=sp3.edit();
        er.putString("fooditemdetail",a);
        er.commit();
        SharedPreferences sp4=getSharedPreferences("fooditemintent",MODE_PRIVATE);
        SharedPreferences.Editor er1=sp4.edit();
        er1.putString("fooditemintent","fooditemmenu");
        er1.commit();
        TastyToast.makeText(getApplicationContext(), a, TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
        Intent intent=new Intent(this,fooditemdetails.class);
        startActivity(intent);
    }

    public void order() {
        progressDialog = new ProgressDialog(fooditem_menu.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Redirecting you to the cart...");
        progressDialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);
        Intent intent=new Intent(this,placeordercart.class);
        startActivity(intent);
    }
}