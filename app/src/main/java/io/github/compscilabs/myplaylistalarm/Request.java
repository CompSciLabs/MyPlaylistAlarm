package io.github.compscilabs.myplaylistalarm;

import android.net.Uri;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by M on 26/07/2015.
 */
public class Request {

    static InputStream is = null;
    static JSONObject json = null;
    static String outPut = "";
    URL url = null;
    // constructor
    public Request() {

    }

    public JSONObject getJSONFromUrl(String requestURL, HashMap<String, Object> params) {
        // Making the HTTP request
        try {
            url = new URL(requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                is = new BufferedInputStream(urlConnection.getInputStream());
                /**
                 * Add parameters to the url from hashmap
                 */
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                Uri.Builder builder = new Uri.Builder();
                for(Map.Entry<String, Object> entry : params.entrySet()) {
                    /** POTENTIAL ISSUE: Casts parameter value to string **/
                    builder.appendQueryParameter(entry.getKey(), (String) entry.getValue());
                }
                String query = builder.build().getEncodedQuery();
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();
                Object content = urlConnection.getContent();
                /**
                 * TODO: Test what is returned from the connection upon connecting
                 */
                Log.v("Connection Content", content.toString());
                Log.v("Connection Response", urlConnection.getResponseMessage());
                // readStream(in);
                /**

                 try {
                 BufferedReader in = new BufferedReader(new InputStreamReader(
                 is, "iso-8859-1"), 8);
                 StringBuilder sb = new StringBuilder();
                 String line = null;
                 while ((line = in.readLine()) != null) {
                 sb.append(line + "\n");
                 }
                 is.close();
                 outPut = sb.toString();
                 Log.e("JSON", outPut);
                 } catch (Exception e) {
                 Log.e("Buffer Error", "Error converting result " + e.toString());
                 }

                 try {
                 json = new JSONObject(outPut);
                 } catch (JSONException e) {
                 Log.e("JSON Parser", "Error parsing data " + e.toString());
                 }

                 // return JSON String
                 return json;

                 **/
            }
            catch (IOException e) {
                urlConnection.getErrorStream();
            }
            finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {

        }

        return null;
    }
}
