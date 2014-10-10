package com.example.e_kolejka;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DjangoRestPokoje extends AsyncTask<Void, Void, Void> {

    String FORMATURL = "/?format=json";

    String url;
    Context context;
    public ArrayList<String> pokojArrayList;
    ProgressDialog dialog;


    public DjangoRestPokoje(Context con, String dany_url_poczatek, String pokoj_nazwa, String uzytkownik){

        url =  dany_url_poczatek + pokoj_nazwa + "/" + uzytkownik
                + FORMATURL;
        pokojArrayList = new ArrayList<String>();
        context = con;

    }
    public DjangoRestPokoje(Context con, String dany_url){
        url =  dany_url;
        pokojArrayList = new ArrayList<String>();
        context = con;
    }

    public ArrayList<String> getList(){

        return pokojArrayList;
    }
    public void sleep(){
        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "",
                "Doing stuff. Please wait...", true);

    }

    @Override
    protected Void doInBackground(Void... params) {
        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);

        try {

            HttpResponse response =  client.execute(getRequest);
            StatusLine statusline = response.getStatusLine();
            int statusCode = statusline.getStatusCode();

            if(statusCode != 200){

                Log.i("JasonDjango", "!= 200");
                return null;
            }

            InputStream jsonStream = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonStream));
            StringBuilder builder = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null){

                builder.append(line);
                Log.i("JasonDjango", "Builder++++++++++++");

            }

            String jsonData = builder.toString();

            JSONArray arr = new JSONArray(jsonData);

            for(int i =0; i<arr.length(); i++){
                JSONObject jObj = arr.getJSONObject(i);
                pokojArrayList.add(jObj.getString("nazwa"));
            }
        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {String pokoj_n;

            e.printStackTrace();
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        dialog.dismiss();
        super.onPostExecute(result);
    }
}
