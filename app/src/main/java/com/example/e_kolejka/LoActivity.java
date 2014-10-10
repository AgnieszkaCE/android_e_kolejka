package com.example.e_kolejka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_kolejka.Net;
import com.example.e_kolejka.DjangoRest;

public class LoActivity extends Activity {

    EditText nazwa;
    EditText haslo;
    Button login;
    String tmp;
    DjangoRest ws;
    Context contextt;
    int sukces = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lo);
        contextt = this;
        nazwa = (EditText) findViewById(R.id.name);
        haslo = (EditText) findViewById(R.id.password);


        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nazwaString = nazwa.getText().toString();
                String hasloString = haslo.getText().toString();
                String url= "http://192.168.43.9:8080/api/login/";
                Net net = new Net();
                if((net.netBoolean(contextt))!= false) {

                    ws = new DjangoRest(LoActivity.this, url, nazwaString, hasloString);

                    ws.execute();
                    ws.sleep();

                    tmp = ws.getString();

                    if (tmp.equals("Logged")) {
                        Intent intent = new Intent(LoActivity.this, PokojActivity.class);
                        intent.putExtra("login", nazwaString);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Błąd w loginie lub haśle",
                                Toast.LENGTH_LONG).show();
                        nazwa.getText().clear();
                        haslo.getText().clear();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "Brak połaczenia z siecia",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lo, menu);
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
