package com.example.e_kolejka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.e_kolejka.R;
import com.example.e_kolejka.DjangoRestPokoje;
import com.example.e_kolejka.Net;
import java.util.ArrayList;

public class PokojActivity extends Activity {

    Context contextt;
    DjangoRestPokoje wsp;
    String url = "http://192.168.43.9:8080/api/?format=json";
    String login;
    ListView pokojList;
    public ArrayList<String> pokojArrayList= new ArrayList<String>();
    ArrayAdapter<String> pokojAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokoj);


        Intent inte = getIntent();
        Bundle b = inte.getExtras();

        if (b != null) {
            login = (String) b.get("login");

        }
        contextt = this;

        Net net = new Net();
        if((net.netBoolean(contextt))!= false) {
            wsp = new DjangoRestPokoje(contextt,url);

            wsp.execute();

            wsp.sleep();

            pokojArrayList.addAll(wsp.getList());

            pokojList = (ListView) findViewById(R.id.pokojList);
            pokojAdapter = new ArrayAdapter<String>(this, R.layout.pokoj_list_items, pokojArrayList);
            pokojList.setAdapter(pokojAdapter);

            pokojList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int index,
                                        long arg3) {

                    //Toast.makeText(getApplicationContext(), pokojArrayList.get(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PokojActivity.this, NumerActivity.class);
                    intent.putExtra("nazwa", pokojArrayList.get(index));
                    intent.putExtra("login", login);
                    startActivity(intent);

                }

            });
        }else{
            Toast.makeText(getApplicationContext(), "Brak połaczenia z siecia",
                    Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pokoj, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
