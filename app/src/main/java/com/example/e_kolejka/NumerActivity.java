package com.example.e_kolejka;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_kolejka.R;
import com.example.e_kolejka.DjangoRest;
import com.example.e_kolejka.Net;

public class NumerActivity extends Activity {
    TextView  pokoj_nazwa;
    TextView kolejka;
    Button pobierz;
    Button stan;
    String pokoj_n;
    String login;
    DjangoRest ws1;
    DjangoRest ws2;
    ProgressDialog dialog;


    String url1 = "http://192.168.43.9:8080/api/kolejka/";
    String url2= "http://192.168.43.9:8080/api/stan/";
    Context contexttt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numer);
        contexttt = this;
        pokoj_nazwa = (TextView) findViewById(R.id.pokoj_nazwa);
        kolejka = (TextView) findViewById(R.id.kolejka);

        Intent inte = getIntent();
        Bundle b = inte.getExtras();

        if (b != null) {
            pokoj_n = (String) b.get("nazwa");
            login = (String) b.get("login");
            pokoj_nazwa.setText(pokoj_n);

        }
        pobierz = (Button) findViewById(R.id.pobierz_numerek);

        pobierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Net net = new Net();
                if ((net.netBoolean(contexttt)) != false) {
                    ws1 = new DjangoRest(NumerActivity.this, url1, pokoj_n, login);
                    ws1.execute();
                    ws1.sleep();

                    String tmp = ws1.getNumerek();
                    String tmp1 = ws1.getString();
                    kolejka.setText(tmp);

                    Toast.makeText(getApplicationContext(), tmp1,
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Brak połaczenia z siecia",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        stan = (Button) findViewById(R.id.stan);
        stan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Net net1 = new Net();
                if ((net1.netBoolean(contexttt)) != false) {
                    ws2 = new DjangoRest(NumerActivity.this, url2, pokoj_n, login);
                    ws2.execute();
                    ws2.sleep();


                    String pobrany_numer = ws2.getNumerek();
                    String komunikat = ws2.getString();

                    kolejka.setText(pobrany_numer );


                    Toast.makeText(getApplicationContext(), komunikat,
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Brak połaczenia z siecia",
                            Toast.LENGTH_LONG).show();
                }
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.numer, menu);
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
