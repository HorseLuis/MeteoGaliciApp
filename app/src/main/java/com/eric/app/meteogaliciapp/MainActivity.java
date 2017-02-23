package com.eric.app.meteogaliciapp;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.http.HttpResponseCache;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;

import static android.R.attr.category;

public class MainActivity extends AppCompatActivity {
    String pag = "http://servizos.meteogalicia.gal/rss/observacion/estadoEstacionsMeteo.action?idEst=10148";
    String estadoCielo;
    String varTemperatura;
    String temperatura;
    String concello;

    private static final String TAG_CIELO = "lnIconoCeo";
    private static final String TAG_VAR = "lnIconoTemperatura";
    private static final String TAG_TEMP = "valorTemperatura";
    private static final String TAG_CITY = "concello";

    JSONArray datos = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        new JSONParse().execute();

    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(pag);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array
                datos = json.getJSONArray("listEstadoActual");
                JSONObject c = datos.getJSONObject(0);

                // Storing  JSON item in a Variable
                String cielo = c.getString(TAG_CIELO);
                String var = c.getString(TAG_VAR);
                String temp = c.getString(TAG_TEMP);
                String city = c.getString(TAG_CITY);

                //Set JSON Data in TextView
                estadoCielo = cielo;
                varTemperatura = var;
                temperatura = temp;
                concello = city;

                TextView view_temp = (TextView) findViewById(R.id.text_temperatura);
                view_temp.setText(temperatura+"º");
                TextView view_city = (TextView) findViewById(R.id.text_ciudad);
                view_city.setText(concello);

                ImageView view_estado = (ImageView) findViewById(R.id.estado_cielo);
                TextView dato_cielo = (TextView) findViewById(R.id.dato_cielo);
                int imgres = 0;
                String datasky = "";
                switch (estadoCielo){
                    case "101":
                        imgres = R.drawable.icono_despejado;
                        datasky = "DESPEJADO";
                        break;
                    case "103":
                        imgres = R.drawable.icono_nubes_claros;
                        datasky = "NUBES Y CLAROS";
                        break;
                    case "105":
                        imgres = R.drawable.icono_nublado;
                        datasky = "NUBLADO";
                        break;
                    case "107":
                        imgres = R.drawable.icono_chubascos;
                        datasky = "CHUBASCOS";
                        break;
                    case "111":
                        imgres = R.drawable.icono_lluvia;
                        datasky = "LLUVIA";
                        break;
                    case "201":
                        imgres = R.drawable.icono_noche_despejada;
                        datasky = "DESPEJADO";
                        break;
                    case "211":
                        imgres = R.drawable.icono_noche_luvia;
                        datasky = "LLUVIA";
                        break;
                    case "-9999":
                        imgres = R.drawable.icono_void;
                        datasky = "SIN DATOS";
                        break;
                }
                view_estado.setImageResource(imgres);
                dato_cielo.setText(datasky);

                ImageView view_var = (ImageView) findViewById(R.id.tendencia_temperatura);
                TextView dato_temp = (TextView) findViewById(R.id.dato_temp);
                int imgres1 = 0;
                String datatemp = "";
                switch (varTemperatura){
                    case "400":
                        imgres1 = R.drawable.icono_temp_baja;
                        datatemp = "Temperaturas en descenso";
                        break;
                    case "401":
                        imgres1 = R.drawable.icono_temp_estable;
                        datatemp = "Temperaturas estables";
                        break;
                    case "402":
                        imgres1 = R.drawable.icono_temp_sube;
                        datatemp = "Temperaturas en aumento";
                        break;
                    case "-9999":
                        imgres1 = R.drawable.icono_void;
                        datatemp = "Variación de temperatura no disponible";
                        break;
                }
                view_var.setImageResource(imgres1);
                dato_temp.setText(datatemp);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}