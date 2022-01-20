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

public class sub_category extends AppCompatActivity {
Spinner spinner;
EditText name,description;
String n,d,p;
    String result=null;
int c=0;
    ProgressDialog progressDialog;
String position;
    Button buttonChoose;
    public static final String UPLOAD_URL = "https://smartordering1.000webhostapp.com/upload.php";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;
    ImageView imageView;
    String imgname;
    Bitmap bitmap;
    private Uri filePath;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,Admin_menu.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        ActionBar ab=getSupportActionBar();
        ab.hide();
        spinner=(Spinner) findViewById(R.id.subspinner);
        name=(EditText) findViewById(R.id.sub_name);
        description=(EditText) findViewById(R.id.sub_des);
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
                int a=i;
                position= str[a];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void sub_cat(View view){
        progressDialog = new ProgressDialog(sub_category.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Adding Sub-Category...");
        progressDialog.show();
        if(name.getText().toString().length()>3){
            n=name.getText().toString();
        }
        else{
            progressDialog.dismiss();
            TastyToast.makeText(getApplicationContext(), "Please enter name!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        if(description.getText().toString().length()>5){
            d=description.getText().toString();
        }
        else{
            progressDialog.dismiss();
            TastyToast.makeText(getApplicationContext(), "Please enter description!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        if(!position.equals("Select Category")){
            p=position;
        }
        else{
            progressDialog.dismiss();
            TastyToast.makeText(getApplicationContext(), "Please Select Category!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        if(c==1) {
            if (p != null && d != null && n != null) {
                sub_categorydb sd = new sub_categorydb(this);
                try {
                    result=sd.execute(p, n, d).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(result.equals("Inserted")){
                    TastyToast.makeText(getApplicationContext(), "Sub-Category Added Successfully", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                    progressDialog.dismiss();
                    Intent intent=new Intent(this,add_details.class);
                    startActivity(intent);
                }
                else{
                    progressDialog.dismiss();
                    TastyToast.makeText(getApplicationContext(), "Error While Adding the Sub-category!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
                }
            }

        }
        else{
            progressDialog.dismiss();
            TastyToast.makeText(getApplicationContext(), "Please Select Image!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
    }
    private void showFileChooser() {
        if(name.getText().toString().isEmpty()){
            TastyToast.makeText(getApplicationContext(), "please enter name first!", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
        }
        else{

            imgname=name.getText()+position;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            c=1;
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
                loading = ProgressDialog.show(sub_category.this, "Uploading Image", "Please wait...",true,true);
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
class sub_categorydb extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    sub_categorydb (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://smartordering1.000webhostapp.com/subcategory.php" ;
        try {
            String c_name = params[0];
            String s_name = params[1];
            String s_des = params[2];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("cname","UTF-8")+"="+URLEncoder.encode(c_name,"UTF-8")+"&"
                    +URLEncoder.encode("sname","UTF-8")+"="+URLEncoder.encode(s_name,"UTF-8")+"&"
                    +URLEncoder.encode("sdes","UTF-8")+"="+URLEncoder.encode(s_des,"UTF-8")+"&";
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
