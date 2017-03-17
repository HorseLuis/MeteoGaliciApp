package com.eric.app.meteogaliciapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        url2 = "http://servizos.meteogalicia.gal/rss/predicion/rssLocalidades.action?idZona="+idConcelloBusca+"&dia=-1&request_locale=gl";

        new JSONParse().execute();
        new MostrarPrediccion().execute(url2);
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
                    concellos = new String[Galicia.size()];
                    for (int i=0;i<Galicia.size();i++){
                        concellos[i]=Galicia.get(i);
                    }
                    Spinner spinner=(Spinner)findViewById(R.id.spinner);
                    ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, concellos);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);



                }

                aux++;

                ViewGroup layout = (ViewGroup) findViewById(R.id.activity_main);
                Toolbar toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
                if (Double.valueOf(estadoCielo)/2.0<100) {
                    layout.setBackgroundResource(R.color.dayPrimary);
                    toolbar.setBackgroundResource(R.color.dayPrimaryDark);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    class MostrarPrediccion extends AsyncTask<String, Void, ArrayList<Tiempo>> {
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
            return XMLParser.descargarTiempo(strings[0]);
        }

        //---when all the images have been downloaded---
        @Override
        protected void onPostExecute(ArrayList<Tiempo> tiemp) {
            pDialog.dismiss();
            ViewGroup layout1 = (ViewGroup) findViewById(R.id.predict);
            layout1.removeAllViews();
            ArrayList<Tiempo> tiempo = tiemp;

            for (int i=0;i<tiempo.size();i++){
                ViewGroup layout = (ViewGroup) findViewById(R.id.predict);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                int id = R.layout.prediction;
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(id,null,false);

                ImageView imgdia = (ImageView) linearLayout.findViewById(R.id.imgdia);
                TextView dia = (TextView) linearLayout.findViewById(R.id.dia);
                TextView tempdia = (TextView) linearLayout.findViewById(R.id.tempdia);

                dia.setText(tiempo.get(i).getData());
                tempdia.setText(tiempo.get(i).gettMin()+"º / "+tiempo.get(i).gettMax()+"º");
                imgdia.setImageResource(getCielo(tiempo.get(i).getCeoT()));

                layout.addView(linearLayout);
            }

        }

    }


    public void change_city(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        concelloBusca = spinner.getSelectedItem().toString();
        idConcelloBusca = Ids.get(Galicia.indexOf(concelloBusca));
        new JSONParse().execute();
        url2 = "http://servizos.meteogalicia.gal/rss/predicion/rssLocalidades.action?idZona="+idConcelloBusca+"&dia=-1&request_locale=gl";
        new MostrarPrediccion().execute(url2);
    }

    public void extend_prediction(View view) {
        Intent intent = new Intent(this, ExtendedActivity.class);
        intent.putExtra("CONCELLO",concelloBusca);
        intent.putExtra("ID",idConcelloBusca);
        startActivity(intent);
    }

    private int getCielo(String sky){
        int imgres = 0;
        String cielo = sky;
        switch(sky){
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

        return imgres;
    }


}