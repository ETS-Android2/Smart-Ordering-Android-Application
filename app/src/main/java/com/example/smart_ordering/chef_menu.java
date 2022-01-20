package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.sdsmdg.tastytoast.TastyToast;


public class chef_menu extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chefmenumain);
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        TastyToast.makeText(getApplicationContext(), "You Cannot Go Back!", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
    }
    public void Checkorders(View view) {
        progressDialog = new ProgressDialog(chef_menu.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving Values...");
        progressDialog.show();
        Intent intent=new Intent(this,check_orders.class);
        startActivity(intent);
    }

    public void report(View view) {
        progressDialog = new ProgressDialog(chef_menu.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving Values...");
        progressDialog.show();
        Intent intent=new Intent(this,reports.class);
        startActivity(intent);
    }

    public void logout(View view) {
        SharedPreferences sp=getSharedPreferences("email",MODE_PRIVATE);
        SharedPreferences.Editor er=sp.edit();
        er.putString("email"," ");
        er.commit();
        Intent intent=new Intent(this,customerdetails.class);
        startActivity(intent);
    }
}
