package com.eric.app.meteogaliciapp;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Eric on 20/03/2017.
 */

public class XMLParserLP {

    public static InputStream openHttpConnection(String url) throws IOException {
        InputStream is = null;
        int responseCode;

        URLConnection connection = null;
        connection = (new URL(url)).openConnection();

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
        return is;
    }

    public static ArrayList<Tiempo> descargarTiempo(String url) {
        ArrayList<Tiempo> tiempo = null;
        InputStream is = null;

        try {
            is = openHttpConnection(url);
            tiempo=leerXML(is);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return tiempo;
    }

    public static ArrayList<Tiempo> leerXML(InputStream inputStream) {
        XmlPullParserFactory factory;
        ArrayList<Tiempo> prediccion = new ArrayList<>();
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream, null);
            int eventType = xpp.getEventType();
            Tiempo tiempo1;
            String data1 = "";
            String tMax = "";
            String tMin = "";
            String ceo = "";
            String pChoiva = "";
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
                    if (xpp.getName().equals("ceo")) {
                        ceo = xpp.nextText();
                    }
                    if (xpp.getName().equals("pChoiva")) {
                        pChoiva = xpp.nextText();
                        tiempo1 = new Tiempo(data1, tMax, tMin, ceo, pChoiva);
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
        return prediccion;
    }

}
