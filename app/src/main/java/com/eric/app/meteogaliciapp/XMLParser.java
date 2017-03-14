package com.eric.app.meteogaliciapp;

import android.provider.DocumentsContract;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Eric on 13/03/2017.
 */

public class XMLParser {

    // constructor
    public XMLParser() {

    }
    public static InputStream openHttpConnection(String url) throws IOException {
        InputStream is = null;
        int responseCode;

        URLConnection connection = null;
        connection = (new URL(url)).openConnection();

        if(!(connection instanceof HttpURLConnection)) {
            throw new IOException("Not HTTP connection");
        }

        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        httpURLConnection.setAllowUserInteraction(false);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        responseCode = httpURLConnection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) {
            is = httpURLConnection.getInputStream();
        }

        return is;
    }
}