package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class add_table extends AppCompatActivity {
EditText table;
String t;
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,add_details.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);
        ActionBar ab=getSupportActionBar();
        ab.hide();
        table=(EditText) findViewById(R.id.add_table);
    }
    public void add(View view) {
        progressDialog = new ProgressDialog(add_table.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        t=table.getText().toString();
        database d=new database(this);
        d.execute("addtable",t);
        progressDialog.dismiss();
    }
}
