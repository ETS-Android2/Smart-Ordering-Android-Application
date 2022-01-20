package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Timer;
import java.util.TimerTask;

public class staffmenu extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffmenumain);
    }
    @Override
    public void onBackPressed() {
        TastyToast.makeText(getApplicationContext(), "You Cannot Go Back!", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
    }
    public void orders(View view) {
        progressDialog = new ProgressDialog(staffmenu.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving Values...");
        progressDialog.show();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);
        Intent intent=new Intent(this,checkordersstaff.class);
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

    public void bills(View view) {
        progressDialog = new ProgressDialog(staffmenu.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving Values...");
        progressDialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);
        Intent intent=new Intent(this,check_bill.class);
        startActivity(intent);
    }
}
