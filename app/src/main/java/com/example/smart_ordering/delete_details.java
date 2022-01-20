package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class delete_details extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,Admin_menu.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletedetailsmain);
    }

    public void Delete_sub_Category(View view) {
        Intent intent=new Intent(this,delete_sub_category.class);
        startActivity(intent);
    }

    public void delete_food_items(View view) {
        Intent intent=new Intent(this,delete_fooditems.class);
        startActivity(intent);
    }

    public void delete_table(View view) {
        Intent intent=new Intent(this,delete_table.class);
        startActivity(intent);
    }
}
