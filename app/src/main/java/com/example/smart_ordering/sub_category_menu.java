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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class sub_category_menu extends AppCompatActivity {

    private ImageView imageView;
    ProgressDialog progressDialog;
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,category_menu.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategorymain);
        searchView = (SearchView) findViewById(R.id.searchView2);
        listView = (ListView) findViewById(R.id.lv2);
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
        progressDialog = new ProgressDialog(sub_category_menu.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving...");
        progressDialog.show();
        SharedPreferences sp=getSharedPreferences("cat",MODE_PRIVATE);
        String category=sp.getString("cat","").toString();
        SharedPreferences sp5=getSharedPreferences(category,MODE_PRIVATE);
        String s=sp5.getString(category,"");
        final String str[] = s.split(";");

        for (int i = 0; i < str.length; i++) {
            final String a=str[i];
            final GridLayout gridLayout=(GridLayout) findViewById(R.id.g1);
            CardView cardView=new CardView(this);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
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

                    call(a);
                    progressDialog = new ProgressDialog(sub_category_menu.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Retriving Food Items...");
                    progressDialog.show();
                }
            });
            cardView.addView(linearLayout);

            imageView = new ImageView(this);
            Picasso.get().load("https://smartordering1.000webhostapp.com/upload/"+ str[i]+category).resize(460,345).into(imageView);

            imageView.setLayoutParams(new LinearLayout.LayoutParams(460, 345));
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView1.setTextSize(15f);
            textView1.setText(a);
            linearLayout.addView(imageView);
            linearLayout.addView(textView1);
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
                            gridLayout.setVisibility(View.GONE);
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
                    adapter = new ArrayAdapter<String>(sub_category_menu.this, android.R.layout.simple_list_item_1,list1);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            progressDialog = new ProgressDialog(sub_category_menu.this);
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
                            er1.putString("fooditemintent","subcategorymenu");
                            er1.commit();
                            TastyToast.makeText(getApplicationContext(), a, TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                            Intent intent=new Intent(sub_category_menu.this,fooditemdetails.class);
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

        }
        progressDialog.dismiss();
    }

    private void call(String a) {
        TastyToast.makeText(getApplicationContext(), a, TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
        SharedPreferences sp=getSharedPreferences("cat_sub",MODE_PRIVATE);
        SharedPreferences.Editor er=sp.edit();
        er.putString("cat_sub",a);
        er.commit();
        progressDialog.dismiss();
        Intent intent=new Intent(this,fooditem_menu.class);
        startActivity(intent);
    }
}
