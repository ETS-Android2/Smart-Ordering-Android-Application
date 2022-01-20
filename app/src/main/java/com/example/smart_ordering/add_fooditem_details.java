package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.sdsmdg.tastytoast.TastyToast;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class add_fooditem_details extends AppCompatActivity {
Button save;
EditText name,incredents,cost,desc;
Spinner spinner,sub_category;
String n,i,cos,c,position,position1,d;
    ProgressDialog progressDialog;
    Button buttonChoose;
    public static final String UPLOAD_URL = "https://smartordering1.000webhostapp.com/upload.php";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;
    ImageView imageView;
    String imgname;
    Bitmap bitmap;
    private Uri filePath;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fooditem_details);
        ActionBar ab=getSupportActionBar();
        ab.hide();
        name=(EditText)findViewById(R.id.item_name);
        desc=(EditText)findViewById(R.id.item_desc);
        incredents=(EditText) findViewById(R.id.item_incredents);
        cost=(EditText)findViewById(R.id.item_cost);
        spinner=(Spinner) findViewById(R.id.item_cat);
        sub_category=(Spinner) findViewById(R.id.item_sub);
        buttonChoose = (Button)findViewById(R.id.imgbutton);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == buttonChoose) {
                    showFileChooser();
                }
            }
        });
        imageView=findViewById(R.id.imageView);
        spinnerdb sd=new spinnerdb(this);
        String s= "Select Category;";
        try {
            s += sd.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String str[] = s.split(";");
        List<String> al = new ArrayList<String>();
        al = Arrays.asList(str);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, al);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position=str[i];
                run(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void run(String pos) {
        if(pos.equals("Select Category")){
            TastyToast.makeText(getApplicationContext(), "Please select category !", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        else{
            sub_catspinnerdb scd=new sub_catspinnerdb(this);
            String s1= "Select sub-Category;";
            try {
                s1 += scd.execute(pos).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final String str1[] = s1.split(";");
            List<String> al1 = new ArrayList<String>();
            al1 = Arrays.asList(str1);
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, al1);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sub_category.setAdapter(dataAdapter1);
            sub_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    position1=str1[i];
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }

    public void savedetails(View view){
        progressDialog = new ProgressDialog(add_fooditem_details.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retriving...");
        progressDialog.show();
        if(name.getText().toString().length()>3){
            n=name.getText().toString();
        }
        else{
            TastyToast.makeText(getApplicationContext(), "Please enter name!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        if(incredents.getText().toString().length()>5){
            i=incredents.getText().toString();
        }
        else{
            TastyToast.makeText(getApplicationContext(), "Please enter Incredents!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        if(cost.getText().toString().length()>=2){
            cos=cost.getText().toString();
        }
        else{
            TastyToast.makeText(getApplicationContext(), "Please Enter the cost!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        if(desc.getText().toString().length()>3){
            d=desc.getText().toString();
        }
        else{
            TastyToast.makeText(getApplicationContext(), "Please Enter Description!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        if(d!=null && n!=null && cos!=null && i!=null ){
            Itemdetail c1=new Itemdetail(this);
            try {
                result=c1.execute(position,position1,n,cos,i,d).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(result.equals("Inserted")){
                TastyToast.makeText(getApplicationContext(), "Successfully Inserted", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                progressDialog.dismiss();
                Intent intent=new Intent(this,add_details.class);
                startActivity(intent);
            }
            else{
                progressDialog.dismiss();
                TastyToast.makeText(getApplicationContext(), "Error while inserting !", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            }
        }
        else
        {
            progressDialog.dismiss();
            TastyToast.makeText(getApplicationContext(), "Please Try Again!", TastyToast.LENGTH_LONG, TastyToast.CONFUSING).show();
        }
    }
    private void showFileChooser() {
        if(name.getText().toString().isEmpty()){
            FancyToast.makeText(this,"please enter name first!",FancyToast.LENGTH_LONG, FancyToast.WARNING,false).show();
        }
        else{

            imgname=name.getText()+position+position1;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        uploadImage();
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(add_fooditem_details.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("name",imgname);

                String result = rh.postRequest(UPLOAD_URL,data);
                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
}


