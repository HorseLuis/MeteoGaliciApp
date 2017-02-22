package com.eric.app.meteogaliciapp;

import android.app.Application;
import android.net.http.HttpResponseCache;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String url = "http://servizos.meteogalicia.gal/rss/observacion/estadoEstacionsMeteo.action?idEst=10148";
    String concello;
    int  estadoCielo;
    double temperatura;
    int variacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            JSONObject init = new JSONObject(url);
            JSONObject object = init.optJSONObject("listEstadoActual");
            concello = object.getString("concello");
            estadoCielo = object.getInt("lnIconoCeo");
            temperatura = object.getDouble("valorTemperatura");
            variacion = object.getInt("lnIconoTemperatura");
            TextView tempview = (TextView) findViewById(R.id.text_temperatura);
            tempview.setText(String.valueOf(temperatura)+"C");
            TextView concview = (TextView) findViewById(R.id.text_ciudad);
            concview.setText(concello);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
