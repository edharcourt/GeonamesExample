package edu.cs364.webservicesocketexample;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by ehar on 4/17/2015.
 * http://api.geonames.org/postalCodeLookupJSON?postalcode=13625&country=US&username=edharcourt
 *
 * http://api.geonames.org/findNearByWeatherJSON?lat=44.5&lng=-74.9&username=edharcourt
 *
 */
public class HttpGetTask extends
        AsyncTask<String, Void, String> {

    MainActivity mact = null;

    public HttpGetTask(MainActivity mact) {
        this.mact = mact;
    }

    @Override
    protected String doInBackground(String... zips) {
        String zip = zips[0];
        String data = null;
        Socket socket = null;

        String HTTP_COMMAND =
            "GET /postalCodeLookupJSON?postalcode="
          + zip
          + "&country=US&username=edharcourt"
          + " HTTP/1.1"
          + "\n"
          + "Host: api.geonames.org"
          + "\n"
          + "Connection: close"
          + "\n\n";

        try {
            socket = new Socket("api.geonames.org", 80);
            PrintWriter pw = new PrintWriter(
              new OutputStreamWriter(socket.getOutputStream()),
              true
            );
            pw.println(HTTP_COMMAND);

            data = readStream(socket.getInputStream());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // function readStream
    String readStream(InputStream in) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in)
        );

        StringBuffer data = new StringBuffer();

        try {
            String line = reader.readLine();
            while (line != null) {
                data.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e("GEONAMES", "IOException");
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("GEONAMES", "Could not close inputstream");
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }

    @Override
    protected void onPostExecute(final String s) {
        super.onPostExecute(s);
        this.mact.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HttpGetTask.this.mact.output_area_v.setText(s);
            }
        });
        // write the output in to the output text area
    }
}
