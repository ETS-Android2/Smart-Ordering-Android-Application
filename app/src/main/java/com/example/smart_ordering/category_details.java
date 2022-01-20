package com.example.smart_ordering;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class category_details extends AppCompatActivity implements View.OnClickListener {
    EditText name,details;
    Button buttonChoose;
    public static final String UPLOAD_URL = "https://smartordering1.000webhostapp.com/upload.php";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;
    ImageView imageView;
    String n,d;
    String imgname;
    Bitmap bitmap;
    private Uri filePath;
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        Intent intent=new Intent(this,customerdetails.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        ActionBar ab=getSupportActionBar();
        ab.hide();
        name=(EditText) findViewById(R.id.c_name);
        details=(EditText) findViewById(R.id.c_details);
        buttonChoose = (Button)findViewById(R.id.imgbutton);
        buttonChoose.setOnClickListener(this);
        imageView=findViewById(R.id.imageView);
    }
    public void category(View view){
        if((name.getText().toString().length())>3) {
            n = name.getText().toString();
        }
        else{
            TastyToast.makeText(getApplicationContext(), "Please Enter Name", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        if((details.getText().toString().length())>3) {
            d = details.getText().toString();
        }
        else{
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            TastyToast.makeText(getApplicationContext(), "Please Enter Proper Details", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        if(n!=null && d!=null) {
            database db=new database(this);
            db.execute("category",n,d);
            TastyToast.makeText(getApplicationContext(), "Starting", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }
    }

    private void showFileChooser() {
        if(name.getText().toString().isEmpty()){
            TastyToast.makeText(getApplicationContext(), "please enter name first", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        else{

            imgname=name.getText().toString();
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
                loading = ProgressDialog.show(category_details.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                Toast.makeText(category_details.this, filePath.toString(), Toast.LENGTH_SHORT).show();
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
    @Override
    public void onClick(View v) {
            if (v == buttonChoose) {
                showFileChooser();
            }
    }
}
