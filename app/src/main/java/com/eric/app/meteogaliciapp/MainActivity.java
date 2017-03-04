package com.eric.app.meteogaliciapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    String estadoCielo;
    double sensTermica;
    double temperatura;
    String concello;
    public String concelloBusca = "A Baña";
    String[]concellos;

    ArrayList<String> Galicia = new ArrayList<>();

    private static final String TAG_CIELO = "icoEstadoCeo";
    private static final String TAG_VAR = "sensacionTermica";
    private static final String TAG_TEMP = "temperatura";
    private static final String TAG_CITY = "nomeConcello";

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
                    concello = city;
                    estadoCielo = cielo;
                    sensTermica = var;
                    temperatura = temp;
                    if (aux==0){
                        Galicia.add(concello);
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

    public void change_city(MenuItem item) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        concelloBusca = spinner.getSelectedItem().toString();

        new JSONParse().execute();
    }

}