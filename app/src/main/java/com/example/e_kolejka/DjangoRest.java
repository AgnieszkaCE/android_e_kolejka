package com.example.e_kolejka;


import android.app.Activity;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DjangoRest extends AsyncTask<Void, Void, Void> {
    String FORMATURL = "/?format=json";
    ProgressDialog dialog;
    String url;
    String zawartosc;
    String  numer;
    Context context;
    public ArrayList<String> stan;
    public ArrayList<String> numerek;
    Activity activity;

    public DjangoRest(Activity activit, String dany_url_poczatek, String pokoj_nazwa, String uzytkownik){

        url =  dany_url_poczatek + pokoj_nazwa + "/" + uzytkownik
                + FORMATURL;
        stan = new ArrayList<String>();
        numerek = new ArrayList<String>();
        //context = con;
        activity = activit;
    }

    public String getString(){

        return zawartosc;
    }

    public String getNumerek(){

        return numer;
    }

    public void sleep(){
        try {
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = ProgressDialog.show(activity, "Loading",
                "Please wait...", true);



    }

    @Override
    public Void doInBackground(Void... params) {


        HttpClient client = new DefaultHttpClient();

        HttpGet getRequest = new HttpGet(url);

        try {

            HttpResponse response = client.execute(getRequest);
            StatusLine statusline = response.getStatusLine();
            int statusCode = statusline.getStatusCode();

            if (statusCode != 200) {

                Log.i("JasonDjango", "!= 200");
                zawartosc = "Problem";
                return null;
            }

            InputStream jsonStream = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(jsonStream));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                builder.append(line);
                Log.i("JasonDjango", "Builder++++++++++++");

            }

            String jsonData = builder.toString();
            Log.i("JasonDjango", jsonData);

            JSONObject json = new JSONObject(jsonData);
            stan.add(json.getString("stan"));
            numerek.add(json.getString("numer"));
            Log.i("Django", stan.get(0).toString());

            zawartosc =  stan.get(0).toString();
            numer = numerek.get(0).toString();



        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       return null;
    }




    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        dialog.dismiss();

    }



}
