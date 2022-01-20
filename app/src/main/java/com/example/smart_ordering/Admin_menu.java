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

import java.util.Timer;
import java.util.TimerTask;

public class Admin_menu extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminmenumain);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        TastyToast.makeText(getApplicationContext(), "You cannot goback", TastyToast.LENGTH_LONG, TastyToast.INFO).show();

    }
    public void change_password(View view) {
        Intent intent=new Intent(this,ChangePassword.class);
        startActivity(intent);
    }

    public void adddetails(View view) {
        Intent intent=new Intent(this,add_details.class);
        startActivity(intent);
    }

    public void deletedetails(View view) {
        Intent intent=new Intent(this,delete_details.class);
        startActivity(intent);
    }

    public void checkorders(View view) {
        progressDialog = new ProgressDialog(Admin_menu.this);
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
        Intent intent=new Intent(this,check_orders.class);
        startActivity(intent);
    }

    public void registration(View view) {
        Intent intent=new Intent(this,regstration.class);
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

    public void reports(View view) {
        progressDialog = new ProgressDialog(Admin_menu.this);
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
        Intent intent=new Intent(this,reports.class);
        startActivity(intent);
    }

    public void settable(View view) {
        progressDialog = new ProgressDialog(Admin_menu.this);
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
        Intent intent=new Intent(this,table_details.class);
        startActivity(intent);
    }
}
