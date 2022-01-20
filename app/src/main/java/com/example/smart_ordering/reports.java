package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class reports extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,Admin_menu.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportsmain);
    }

    public void overall(View view) {
        SharedPreferences sp = getSharedPreferences("reports", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("reports", "overall");
        editor.commit();
        Intent intent=new Intent(this,reportdetails.class);
        startActivity(intent);
    }

    public void todayreport(View view) {
        SharedPreferences sp = getSharedPreferences("reports", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("reports", "today");
        editor.commit();
        Intent intent=new Intent(this,reportdetails.class);
        startActivity(intent);
    }
}
