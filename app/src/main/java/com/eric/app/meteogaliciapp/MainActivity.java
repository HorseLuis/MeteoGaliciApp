package com.eric.app.meteogaliciapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.text.style.TtsSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.eric.app.meteogaliciapp.R.string.temperatura;

public class MainActivity extends AppCompatActivity {
    int aux = 0;
    String pag = "http://servizos.meteogalicia.gal/rss/observacion/observacionConcellos.action";
    String url2 ="";

    String estadoCielo;
    double sensTermica;
    double temperatura;
    String concello;
    String id;
    public String concelloBusca = "Ourense";
    public String idConcelloBusca = "32054";
    String[]concellos;

    ArrayList<String> Galicia = new ArrayList<>();
    ArrayList<String> Ids = new ArrayList<>();

    private static final String TAG_CIELO = "icoEstadoCeo";
    private static final String TAG_VAR = "sensacionTermica";
    private static final String TAG_TEMP = "temperatura";
    private static final String TAG_CITY = "nomeConcello";
    private static final String TAG_ID = "idConcello";

    JSONArray datos = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change:
                action(R.string.action_change);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void action(int resid) {
        Toast.makeText(this, getText(resid), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        url2 = "http://servizos.meteogalicia.gal/rss/predicion/rssLocalidades.action?idZona="+idConcelloBusca+"&dia=-1&request_locale=gl";
        new JSONParse().execute();
        new MostrarTiempoCortoPlazo().execute(url2);


    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Obteniendo Datos ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            return jParser.getJSONFromUrl(pag);
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array
                datos = json.getJSONArray("listaObservacionConcellos");
                for (int i=0;i<datos.length();i++) {
                    JSONObject c = datos.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String city = c.getString(TAG_CITY);
                    String cielo = c.getString(TAG_CIELO);
                    double var = c.getDouble(TAG_VAR);
                    double temp = c.getDouble(TAG_TEMP);
                    String idd = c.getString(TAG_ID);
                    concello = city;
                    estadoCielo = cielo;
                    sensTermica = var;
                    temperatura = temp;
                    id = idd;
                    if (aux==0){
                        Galicia.add(concello);
                        Ids.add(id);
                    }

                    if (city.equals(concelloBusca)){

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
                                imgres = R.drawable.dia_despejado;
                                datasky = "DESPEXADO";
                                break;
                            case "102":
                                imgres = R.drawable.dia_nubesaltas;
                                datasky = "NUBES ALTAS";
                                break;
                            case "103":
                                imgres = R.drawable.dia_nubesclaros;
                                datasky = "NUBES E CLAROS";
                                break;
                            case "104":
                                imgres = R.drawable.dia_nublado75;
                                datasky = "NUBES 75%";
                                break;
                            case "105":
                                imgres = R.drawable.nublado;
                                datasky = "CUBERTO";
                                break;
                            case "106":
                                imgres = R.drawable.niebla;
                                datasky = "NEBOA";
                                break;
                            case "107":
                                imgres = R.drawable.chuvasco;
                                datasky = "CHUVASCO";
                                break;
                            case "108":
                                imgres = R.drawable.chuvasco75;
                                datasky = "CHUVASCO 75%";
                                break;
                            case "109":
                                imgres = R.drawable.aguanieve;
                                datasky = "CHUVASCO NEVE";
                                break;
                            case "110":
                                imgres = R.drawable.orballo;
                                datasky = "ORBALLO";
                                break;
                            case "111":
                                imgres = R.drawable.lluvia;
                                datasky = "CHOIVA";
                                break;
                            case "112":
                                imgres = R.drawable.nieve;
                                datasky = "NEVE";
                                break;
                            case "113":
                                imgres = R.drawable.tormenta;
                                datasky = "TREBOADA";
                                break;
                            case "114":
                                imgres = R.drawable.neblina;
                                datasky = "BRETEMA";
                                break;
                            case "115":
                                imgres = R.drawable.niebla;
                                datasky = "NEBOA";
                                break;
                            case "116":
                                imgres = R.drawable.nubemedia;
                                datasky = "NUBES MEDIAS";
                                break;
                            case "117":
                                imgres = R.drawable.orballo;
                                datasky = "CHOIVA DEBIL";
                                break;
                            case "118":
                                imgres = R.drawable.chuvasco_debil;
                                datasky = "CHUVASCOS DEBILES";
                                break;
                            case "119":
                                imgres = R.drawable.tormenta;
                                datasky = "TREBOADA POUCAS NUBES";
                                break;
                            case "120":
                                imgres = R.drawable.aguanieve;
                                datasky = "AUGA NEVE";
                                break;
                            case "121":
                                imgres = R.drawable.ventisca;
                                datasky = "SARABIA";
                                break;


                            case "201":
                                imgres = R.drawable.noche_despejado;
                                datasky = "DESPEXADO";
                                break;
                            case "202":
                                imgres = R.drawable.noche_nubemedia;
                                datasky = "NUBES ALTAS";
                                break;
                            case "203":
                                imgres = R.drawable.noche_nubemedia;
                                datasky = "NUBES E CLAROS";
                                break;
                            case "204":
                                imgres = R.drawable.noche_nubemedia;
                                datasky = "NUBES 75%";
                                break;
                            case "205":
                                imgres = R.drawable.nublado;
                                datasky = "CUBERTO";
                                break;
                            case "206":
                                imgres = R.drawable.niebla;
                                datasky = "NEBOA";
                                break;
                            case "207":
                                imgres = R.drawable.chuvasco;
                                datasky = "CHUVASCO";
                                break;
                            case "208":
                                imgres = R.drawable.noche_lluvianubes;
                                datasky = "CHUVASCO 75%";
                                break;
                            case "209":
                                imgres = R.drawable.aguanieve;
                                datasky = "CHUVASCO NEVE";
                                break;
                            case "210":
                                imgres = R.drawable.orballo;
                                datasky = "ORBALLO";
                                break;
                            case "211":
                                imgres = R.drawable.noche_lluvia;
                                datasky = "CHOIVA";
                                break;
                            case "212":
                                imgres = R.drawable.noche_nieve;
                                datasky = "NEVE";
                                break;
                            case "213":
                                imgres = R.drawable.tormenta;
                                datasky = "TREBOADA";
                                break;
                            case "214":
                                imgres = R.drawable.neblina;
                                datasky = "BRETEMA";
                                break;
                            case "215":
                                imgres = R.drawable.niebla;
                                datasky = "NEBOA";
                                break;
                            case "216":
                                imgres = R.drawable.noche_nubemedia;
                                datasky = "NUBES MEDIAS";
                                break;
                            case "217":
                                imgres = R.drawable.noche_lluvianubes;
                                datasky = "CHOIVA DEBIL";
                                break;
                            case "218":
                                imgres = R.drawable.noche_lluvianubes;
                                datasky = "CHUVASCOS DEBILES";
                                break;
                            case "219":
                                imgres = R.drawable.tormenta;
                                datasky = "TREBOADA POUCAS NUBES";
                                break;
                            case "220":
                                imgres = R.drawable.aguanieve;
                                datasky = "AUGA NEVE";
                                break;
                            case "221":
                                imgres = R.drawable.ventisca;
                                datasky = "SARABIA";
                                break;


                            case "-9999":
                                imgres = R.drawable.icono_void;
                                datasky = "NO DISPONIBLE";
                                break;
                        }
                        view_estado.setImageResource(imgres);
                        dato_cielo.setText(datasky);

                        ImageView view_var = (ImageView) findViewById(R.id.tendencia_temperatura);
                        int imgres1;
                        if (sensTermica<temperatura){
                            imgres1 = R.drawable.icono_temp_baja;
                            //ICONO DE TERMOMETRO EN DESCENSO CUANDO LA SENSACION TERMICA ES MENOR QUE LA TEMPERATURA
                        }
                        else if (sensTermica>temperatura){
                            imgres1 = R.drawable.icono_temp_sube;
                            //ICONO DE TERMOMETRO EN DESCENSO CUANDO LA SENSACION TERMICA ES MAYOR QUE LA TEMPERATURA
                        }
                        else {
                            imgres1 = R.drawable.icono_temp_estable;
                            //ICONO DE TERMOMETRO ESTABLE CUANDO LA SENSACION TERMICA ES IGUAL QUE LA TEMPERATURA
                        }
                        view_var.setImageResource(imgres1);
                    }

                }
                if (aux==0){
                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                    concellos = new String[Galicia.size()];
                    for (int i=0;i<Galicia.size();i++){
                        concellos[i]=Galicia.get(i);
                    }
                    ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, concellos);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }

                aux++;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    class MostrarTiempoCortoPlazo extends AsyncTask<String, Void, ArrayList<Tiempo>> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Obteniendo Datos ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected ArrayList<Tiempo> doInBackground(String... strings) {
            ArrayList<Tiempo> prediccion = new ArrayList<>();
            Tiempo t;
            Tiempo tiempo = null;
            InputStream is = null;

            try {
                int responseCode;

                URLConnection connection = null;
                connection = (new URL(strings[0])).openConnection();

                if (!(connection instanceof HttpURLConnection)) {
                    throw new IOException("Not HTTP connection");
                }

                HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
                httpURLConnection.setAllowUserInteraction(false);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    is = httpURLConnection.getInputStream();
                }
                XmlPullParserFactory factory;
                try {
                    factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(is, null);
                    int eventType = xpp.getEventType();
                    Tiempo tiempo1;
                    String data1 = "";
                    String tMax = "";
                    String tMin = "";
                    String ceoT = "";
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {

                            if (xpp.getName().equals("dataPredicion")) {
                                    data1 = xpp.nextText();
                                }
                                if (xpp.getName().equals("tMax")) {
                                    tMax = xpp.nextText();
                                }
                                if (xpp.getName().equals("tMin")) {
                                    tMin = xpp.nextText();
                                }
                                if (xpp.getName().equals("ceoT")) {
                                    ceoT = xpp.nextText();
                                    tiempo1 = new Tiempo(data1, tMax, tMin, ceoT);
                                    prediccion.add(tiempo1);
                                }




                        } else if (eventType == XmlPullParser.TEXT) {

                        }
                        eventType = xpp.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (is != null) {
                    is.close();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return prediccion;
        }

        //---when all the images have been downloaded---
        @Override
        protected void onPostExecute(ArrayList<Tiempo> tiemp) {
            pDialog.dismiss();

            ImageView imgdia1 = (ImageView) findViewById(R.id.imgdia1);
            TextView dia1 = (TextView) findViewById(R.id.dia1);
            TextView tempdia1 = (TextView) findViewById(R.id.tempdia1);

            ImageView imgdia2 = (ImageView) findViewById(R.id.imgdia2);
            TextView dia2 = (TextView) findViewById(R.id.dia2);
            TextView tempdia2 = (TextView) findViewById(R.id.tempdia2);

            ImageView imgdia3 = (ImageView) findViewById(R.id.imgdia3);
            TextView dia3 = (TextView) findViewById(R.id.dia3);
            TextView tempdia3 = (TextView) findViewById(R.id.tempdia3);

            ImageView imgdia4 = (ImageView) findViewById(R.id.imgdia4);
            TextView dia4 = (TextView) findViewById(R.id.dia4);
            TextView tempdia4 = (TextView) findViewById(R.id.tempdia4);

            ArrayList<Tiempo> tiempo = tiemp;

            dia1.setText(tiempo.get(0).getData());
            tempdia1.setText(tiempo.get(0).gettMin()+"º / "+tiempo.get(0).gettMax()+"º");

            dia2.setText(tiempo.get(1).getData());
            tempdia2.setText(tiempo.get(1).gettMin()+"º / "+tiempo.get(1).gettMax()+"º");

            dia3.setText(tiempo.get(2).getData());
            tempdia3.setText(tiempo.get(2).gettMin()+"º / "+tiempo.get(2).gettMax()+"º");

            dia4.setText(tiempo.get(3).getData());
            tempdia4.setText(tiempo.get(3).gettMin()+"º / "+tiempo.get(3).gettMax()+"º");

            int imgres = 0;

            switch (tiempo.get(0).getCeoT()) {
                case "101":
                    imgres = R.drawable.dia_despejado;
                    break;
                case "102":
                    imgres = R.drawable.dia_nubesaltas;
                    break;
                case "103":
                    imgres = R.drawable.dia_nubesclaros;
                    break;
                case "104":
                    imgres = R.drawable.dia_nublado75;
                    break;
                case "105":
                    imgres = R.drawable.nublado;
                    break;
                case "106":
                    imgres = R.drawable.niebla;
                    break;
                case "107":
                    imgres = R.drawable.chuvasco;
                    break;
                case "108":
                    imgres = R.drawable.chuvasco75;
                    break;
                case "109":
                    imgres = R.drawable.aguanieve;
                    break;
                case "110":
                    imgres = R.drawable.orballo;
                    break;
                case "111":
                    imgres = R.drawable.lluvia;
                    break;
                case "112":
                    imgres = R.drawable.nieve;
                    break;
                case "113":
                    imgres = R.drawable.tormenta;
                    break;
                case "114":
                    imgres = R.drawable.neblina;
                    break;
                case "115":
                    imgres = R.drawable.niebla;
                    break;
                case "116":
                    imgres = R.drawable.nubemedia;
                    break;
                case "117":
                    imgres = R.drawable.orballo;
                    break;
                case "118":
                    imgres = R.drawable.chuvasco_debil;
                    break;
                case "119":
                    imgres = R.drawable.tormenta;
                    break;
                case "120":
                    imgres = R.drawable.aguanieve;
                    break;
                case "121":
                    imgres = R.drawable.ventisca;
                    break;


                case "201":
                    imgres = R.drawable.noche_despejado;
                    break;
                case "202":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "203":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "204":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "205":
                    imgres = R.drawable.nublado;
                    break;
                case "206":
                    imgres = R.drawable.niebla;
                    break;
                case "207":
                    imgres = R.drawable.chuvasco;
                    break;
                case "208":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "209":
                    imgres = R.drawable.aguanieve;
                    break;
                case "210":
                    imgres = R.drawable.orballo;
                    break;
                case "211":
                    imgres = R.drawable.noche_lluvia;
                    break;
                case "212":
                    imgres = R.drawable.noche_nieve;
                    break;
                case "213":
                    imgres = R.drawable.tormenta;
                    break;
                case "214":
                    imgres = R.drawable.neblina;
                    break;
                case "215":
                    imgres = R.drawable.niebla;
                    break;
                case "216":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "217":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "218":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "219":
                    imgres = R.drawable.tormenta;
                    break;
                case "220":
                    imgres = R.drawable.aguanieve;
                    break;
                case "221":
                    imgres = R.drawable.ventisca;
                    break;


                case "-9999":
                    imgres = R.drawable.icono_void;
                    break;
            }
            imgdia1.setImageResource(imgres);
            switch (tiempo.get(1).getCeoT()) {
                case "101":
                    imgres = R.drawable.dia_despejado;
                    break;
                case "102":
                    imgres = R.drawable.dia_nubesaltas;
                    break;
                case "103":
                    imgres = R.drawable.dia_nubesclaros;
                    break;
                case "104":
                    imgres = R.drawable.dia_nublado75;
                    break;
                case "105":
                    imgres = R.drawable.nublado;
                    break;
                case "106":
                    imgres = R.drawable.niebla;
                    break;
                case "107":
                    imgres = R.drawable.chuvasco;
                    break;
                case "108":
                    imgres = R.drawable.chuvasco75;
                    break;
                case "109":
                    imgres = R.drawable.aguanieve;
                    break;
                case "110":
                    imgres = R.drawable.orballo;
                    break;
                case "111":
                    imgres = R.drawable.lluvia;
                    break;
                case "112":
                    imgres = R.drawable.nieve;
                    break;
                case "113":
                    imgres = R.drawable.tormenta;
                    break;
                case "114":
                    imgres = R.drawable.neblina;
                    break;
                case "115":
                    imgres = R.drawable.niebla;
                    break;
                case "116":
                    imgres = R.drawable.nubemedia;
                    break;
                case "117":
                    imgres = R.drawable.orballo;
                    break;
                case "118":
                    imgres = R.drawable.chuvasco_debil;
                    break;
                case "119":
                    imgres = R.drawable.tormenta;
                    break;
                case "120":
                    imgres = R.drawable.aguanieve;
                    break;
                case "121":
                    imgres = R.drawable.ventisca;
                    break;


                case "201":
                    imgres = R.drawable.noche_despejado;
                    break;
                case "202":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "203":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "204":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "205":
                    imgres = R.drawable.nublado;
                    break;
                case "206":
                    imgres = R.drawable.niebla;
                    break;
                case "207":
                    imgres = R.drawable.chuvasco;
                    break;
                case "208":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "209":
                    imgres = R.drawable.aguanieve;
                    break;
                case "210":
                    imgres = R.drawable.orballo;
                    break;
                case "211":
                    imgres = R.drawable.noche_lluvia;
                    break;
                case "212":
                    imgres = R.drawable.noche_nieve;
                    break;
                case "213":
                    imgres = R.drawable.tormenta;
                    break;
                case "214":
                    imgres = R.drawable.neblina;
                    break;
                case "215":
                    imgres = R.drawable.niebla;
                    break;
                case "216":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "217":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "218":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "219":
                    imgres = R.drawable.tormenta;
                    break;
                case "220":
                    imgres = R.drawable.aguanieve;
                    break;
                case "221":
                    imgres = R.drawable.ventisca;
                    break;


                case "-9999":
                    imgres = R.drawable.icono_void;
                    break;
            }
            imgdia2.setImageResource(imgres);

            switch (tiempo.get(2).getCeoT()) {
                case "101":
                    imgres = R.drawable.dia_despejado;
                    break;
                case "102":
                    imgres = R.drawable.dia_nubesaltas;
                    break;
                case "103":
                    imgres = R.drawable.dia_nubesclaros;
                    break;
                case "104":
                    imgres = R.drawable.dia_nublado75;
                    break;
                case "105":
                    imgres = R.drawable.nublado;
                    break;
                case "106":
                    imgres = R.drawable.niebla;
                    break;
                case "107":
                    imgres = R.drawable.chuvasco;
                    break;
                case "108":
                    imgres = R.drawable.chuvasco75;
                    break;
                case "109":
                    imgres = R.drawable.aguanieve;
                    break;
                case "110":
                    imgres = R.drawable.orballo;
                    break;
                case "111":
                    imgres = R.drawable.lluvia;
                    break;
                case "112":
                    imgres = R.drawable.nieve;
                    break;
                case "113":
                    imgres = R.drawable.tormenta;
                    break;
                case "114":
                    imgres = R.drawable.neblina;
                    break;
                case "115":
                    imgres = R.drawable.niebla;
                    break;
                case "116":
                    imgres = R.drawable.nubemedia;
                    break;
                case "117":
                    imgres = R.drawable.orballo;
                    break;
                case "118":
                    imgres = R.drawable.chuvasco_debil;
                    break;
                case "119":
                    imgres = R.drawable.tormenta;
                    break;
                case "120":
                    imgres = R.drawable.aguanieve;
                    break;
                case "121":
                    imgres = R.drawable.ventisca;
                    break;


                case "201":
                    imgres = R.drawable.noche_despejado;
                    break;
                case "202":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "203":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "204":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "205":
                    imgres = R.drawable.nublado;
                    break;
                case "206":
                    imgres = R.drawable.niebla;
                    break;
                case "207":
                    imgres = R.drawable.chuvasco;
                    break;
                case "208":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "209":
                    imgres = R.drawable.aguanieve;
                    break;
                case "210":
                    imgres = R.drawable.orballo;
                    break;
                case "211":
                    imgres = R.drawable.noche_lluvia;
                    break;
                case "212":
                    imgres = R.drawable.noche_nieve;
                    break;
                case "213":
                    imgres = R.drawable.tormenta;
                    break;
                case "214":
                    imgres = R.drawable.neblina;
                    break;
                case "215":
                    imgres = R.drawable.niebla;
                    break;
                case "216":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "217":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "218":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "219":
                    imgres = R.drawable.tormenta;
                    break;
                case "220":
                    imgres = R.drawable.aguanieve;
                    break;
                case "221":
                    imgres = R.drawable.ventisca;
                    break;


                case "-9999":
                    imgres = R.drawable.icono_void;
                    break;
            }
            imgdia3.setImageResource(imgres);

            switch (tiempo.get(3).getCeoT()) {
                case "101":
                    imgres = R.drawable.dia_despejado;
                    break;
                case "102":
                    imgres = R.drawable.dia_nubesaltas;
                    break;
                case "103":
                    imgres = R.drawable.dia_nubesclaros;
                    break;
                case "104":
                    imgres = R.drawable.dia_nublado75;
                    break;
                case "105":
                    imgres = R.drawable.nublado;
                    break;
                case "106":
                    imgres = R.drawable.niebla;
                    break;
                case "107":
                    imgres = R.drawable.chuvasco;
                    break;
                case "108":
                    imgres = R.drawable.chuvasco75;
                    break;
                case "109":
                    imgres = R.drawable.aguanieve;
                    break;
                case "110":
                    imgres = R.drawable.orballo;
                    break;
                case "111":
                    imgres = R.drawable.lluvia;
                    break;
                case "112":
                    imgres = R.drawable.nieve;
                    break;
                case "113":
                    imgres = R.drawable.tormenta;
                    break;
                case "114":
                    imgres = R.drawable.neblina;
                    break;
                case "115":
                    imgres = R.drawable.niebla;
                    break;
                case "116":
                    imgres = R.drawable.nubemedia;
                    break;
                case "117":
                    imgres = R.drawable.orballo;
                    break;
                case "118":
                    imgres = R.drawable.chuvasco_debil;
                    break;
                case "119":
                    imgres = R.drawable.tormenta;
                    break;
                case "120":
                    imgres = R.drawable.aguanieve;
                    break;
                case "121":
                    imgres = R.drawable.ventisca;
                    break;


                case "201":
                    imgres = R.drawable.noche_despejado;
                    break;
                case "202":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "203":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "204":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "205":
                    imgres = R.drawable.nublado;
                    break;
                case "206":
                    imgres = R.drawable.niebla;
                    break;
                case "207":
                    imgres = R.drawable.chuvasco;
                    break;
                case "208":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "209":
                    imgres = R.drawable.aguanieve;
                    break;
                case "210":
                    imgres = R.drawable.orballo;
                    break;
                case "211":
                    imgres = R.drawable.noche_lluvia;
                    break;
                case "212":
                    imgres = R.drawable.noche_nieve;
                    break;
                case "213":
                    imgres = R.drawable.tormenta;
                    break;
                case "214":
                    imgres = R.drawable.neblina;
                    break;
                case "215":
                    imgres = R.drawable.niebla;
                    break;
                case "216":
                    imgres = R.drawable.noche_nubemedia;
                    break;
                case "217":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "218":
                    imgres = R.drawable.noche_lluvianubes;
                    break;
                case "219":
                    imgres = R.drawable.tormenta;
                    break;
                case "220":
                    imgres = R.drawable.aguanieve;
                    break;
                case "221":
                    imgres = R.drawable.ventisca;
                    break;


                case "-9999":
                    imgres = R.drawable.icono_void;
                    break;
            }
            imgdia4.setImageResource(imgres);

        }

    }

    public void change_city(MenuItem item) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        concelloBusca = spinner.getSelectedItem().toString();
        idConcelloBusca = Ids.get(Galicia.indexOf(concelloBusca));
        new JSONParse().execute();
        url2 = "http://servizos.meteogalicia.gal/rss/predicion/rssLocalidades.action?idZona="+idConcelloBusca+"&dia=-1&request_locale=gl";
        new MostrarTiempoCortoPlazo().execute(url2);
    }


}