package com.example.smart_ordering;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

class Itemdetail extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    Itemdetail (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://smartordering1.000webhostapp.com/fooditems.php" ;
        try {
            String c_id = params[0];
            String s_name = params[1];
            String f_name = params[2];
            String f_cost = params[3];
            String f_inc = params[4];
            String f_des = params[5];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(c_id,"UTF-8")+"&"
                    +URLEncoder.encode("fname","UTF-8")+"="+URLEncoder.encode(f_name,"UTF-8")+"&"
                    +URLEncoder.encode("sname","UTF-8")+"="+URLEncoder.encode(s_name,"UTF-8")+"&"
                    +URLEncoder.encode("fcost","UTF-8")+"="+URLEncoder.encode(f_cost,"UTF-8")+"&"
                    +URLEncoder.encode("finc","UTF-8")+"="+URLEncoder.encode(f_inc,"UTF-8")+"&"
                    +URLEncoder.encode("fdes","UTF-8")+"="+URLEncoder.encode(f_des,"UTF-8")+"&";
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
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
