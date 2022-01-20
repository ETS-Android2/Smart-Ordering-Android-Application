package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.Timer;
import java.util.TimerTask;

public class add_details extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddetailsmain);
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,Admin_menu.class);
        startActivity(intent);

    }
    public void food_items(View view) {
        progressDialog = new ProgressDialog(add_details.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);
        Intent intent=new Intent(this, add_fooditem_details.class);
        startActivity(intent);
    }

    public void sub(View view) {
        progressDialog = new ProgressDialog(add_details.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);
        Intent intent=new Intent(this,sub_category.class);
        startActivity(intent);
    }

    public void table(View view) {
        progressDialog = new ProgressDialog(add_details.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                progressDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);
        Intent intent=new Intent(this,add_table.class);
        startActivity(intent);
    }
}
