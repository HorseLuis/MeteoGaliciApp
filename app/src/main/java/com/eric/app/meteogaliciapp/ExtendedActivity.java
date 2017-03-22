package com.eric.app.meteogaliciapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Eric on 15/03/2017.
 */

public class ExtendedActivity extends AppCompatActivity {
    String idConcello;
    String Concello;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Concello = getIntent().getStringExtra("CONCELLO");
        idConcello = getIntent().getStringExtra("ID");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarE);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(Concello);
        }
        String url2 = "http://servizos.meteogalicia.gal/rss/predicion/rssLocalidades.action?idZona="+idConcello+"&dia=-1&request_locale=gl";
        String url3 = "http://servizos.meteogalicia.gal/rss/predicion/rssConcellosMPrazo.action?idZona="+idConcello+"&dia=-1&request_locale=gl";
        new MostrarPrediccion().execute(url2);
        new MostrarPrediccionLP().execute(url3);


    }


    class MostrarPrediccion extends AsyncTask<String, Void, ArrayList<Tiempo>> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ExtendedActivity.this);
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
            for (int i = 0; i< tiemp.size(); i++){
                ViewGroup layout = (ViewGroup) findViewById(R.id.content);
                LayoutInflater inflater = LayoutInflater.from(ExtendedActivity.this);
                int id = R.layout.layout_extend;

                //OBTENGO EL LINEAR LAYOUT DEL TIEMPO EXTENDIDO
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(id,null,false);

                //MODIFICO LOS ATRIBUTOS DE LAS VISTAS DE LA FILA PADRE
                TextView fecha = (TextView) linearLayout.findViewById(R.id.fecha);
                fecha.setText(tiemp.get(i).getData());
                ImageView ImgTMAX = (ImageView) linearLayout.findViewById(R.id.ImgTMAX);
                ImgTMAX.setImageResource(R.drawable.icono_temp_sube);
                TextView TMAX = (TextView) linearLayout.findViewById(R.id.TMAX);
                TMAX.setText(tiemp.get(i).gettMax()+"º");
                ImageView ImgTMIN = (ImageView) linearLayout.findViewById(R.id.ImgTMIN);
                ImgTMIN.setImageResource(R.drawable.icono_temp_baja);
                TextView TMIN = (TextView) linearLayout.findViewById(R.id.TMIN);
                TMIN.setText(tiemp.get(i).gettMin()+"º");

                //MODIFICO LOS ATRIBUTOS DE LA FILA CORRESPONDIENTE A LA MAÑANA
                ImageView IMGcieloM = (ImageView) linearLayout.findViewById(R.id.IMGcieloM);
                IMGcieloM.setImageResource(getCielo(tiemp.get(i).getCeoM()));
                ImageView imgRAINM = (ImageView) linearLayout.findViewById(R.id.imgRAINM);
                imgRAINM.setImageResource(R.drawable.lluvia);
                TextView RAINM = (TextView) linearLayout.findViewById(R.id.RAINM);
                if (tiemp.get(i).getpChoivaM().equals("-9999")) RAINM.setText("-");
                else RAINM.setText(tiemp.get(i).getpChoivaM()+"%");

                //MODIFICO LOS ATRIBUTOS DE LA FILA CORRESPONDIENTE A LA TARDE
                ImageView IMGcieloT = (ImageView) linearLayout.findViewById(R.id.IMGcieloT);
                IMGcieloT.setImageResource(getCielo(tiemp.get(i).getCeoT()));
                ImageView imgRAINT = (ImageView) linearLayout.findViewById(R.id.imgRAINT);
                imgRAINT.setImageResource(R.drawable.lluvia);
                TextView RAINT = (TextView) linearLayout.findViewById(R.id.RAINT);
                if (tiemp.get(i).getpChoivaT().equals("-9999")) RAINT.setText("-");
                else RAINT.setText(tiemp.get(i).getpChoivaT()+"%");

                //MODIFICO LOS ATRIBUTOS DE LA FILA CORRESPONDIENTE A LA NOCHE
                ImageView IMGcieloN = (ImageView) linearLayout.findViewById(R.id.IMGcieloN);
                IMGcieloN.setImageResource(getCielo(tiemp.get(i).getCeoN()));
                ImageView imgRAINN = (ImageView) linearLayout.findViewById(R.id.imgRAINN);
                imgRAINN.setImageResource(R.drawable.lluvia);
                TextView RAINN = (TextView) linearLayout.findViewById(R.id.RAINN);
                if (tiemp.get(i).getpChoivaN().equals("-9999")) RAINN.setText("-");
                else RAINN.setText(tiemp.get(i).getpChoivaN()+"%");



                layout.addView(linearLayout);
            }
            pDialog.dismiss();
        }
    }


    class MostrarPrediccionLP extends AsyncTask<String, Void, ArrayList<Tiempo>> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ExtendedActivity.this);
            pDialog.setMessage("Obteniendo Datos ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected ArrayList<Tiempo> doInBackground(String... strings) {
            return XMLParserLP.descargarTiempo(strings[0]);
        }


        //---when all the images have been downloaded---
        @Override
        protected void onPostExecute(ArrayList<Tiempo> tiemp) {
            for (int i = 0; i< tiemp.size(); i++){
                ViewGroup layout = (ViewGroup) findViewById(R.id.content);
                LayoutInflater inflater = LayoutInflater.from(ExtendedActivity.this);
                int id = R.layout.layout_extend_lp;

                //OBTENGO EL LINEAR LAYOUT DEL TIEMPO EXTENDIDO
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(id,null,false);

                //MODIFICO LOS ATRIBUTOS DE LAS VISTAS DE LA FILA PADRE
                TextView fecha = (TextView) linearLayout.findViewById(R.id.fecha);
                fecha.setText(tiemp.get(i).getData());
                ImageView ImgTMAX = (ImageView) linearLayout.findViewById(R.id.ImgTMAX);
                ImgTMAX.setImageResource(R.drawable.icono_temp_sube);
                TextView TMAX = (TextView) linearLayout.findViewById(R.id.TMAX);
                TMAX.setText(tiemp.get(i).gettMax()+"º");
                ImageView ImgTMIN = (ImageView) linearLayout.findViewById(R.id.ImgTMIN);
                ImgTMIN.setImageResource(R.drawable.icono_temp_baja);
                TextView TMIN = (TextView) linearLayout.findViewById(R.id.TMIN);
                TMIN.setText(tiemp.get(i).gettMin()+"º");

                //MODIFICO LOS ATRIBUTOS DE LA FILA CORRESPONDIENTE AL DIA
                ImageView IMGcielo = (ImageView) linearLayout.findViewById(R.id.IMGcielo);
                IMGcielo.setImageResource(getCielo(tiemp.get(i).getCeoT()));
                ImageView imgRAIN = (ImageView) linearLayout.findViewById(R.id.imgRAIN);
                imgRAIN.setImageResource(R.drawable.lluvia);
                TextView RAIN = (TextView) linearLayout.findViewById(R.id.RAIN);
                if (tiemp.get(i).getpChoivaT().equals("-9999")) RAIN.setText("-");
                else RAIN.setText(tiemp.get(i).getpChoivaT()+"%");
                layout.addView(linearLayout);
            }
            pDialog.dismiss();
        }
    }


    private int getCielo(String sky){
        int imgres = 0;
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
