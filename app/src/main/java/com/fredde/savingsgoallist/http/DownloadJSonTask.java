package com.fredde.savingsgoallist.http;

import android.os.AsyncTask;

import com.fredde.savingsgoallist.utils.DebugUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Downloads a jason string from the provided url.
 */
public class DownloadJSonTask extends AsyncTask<String, Void, String> {

    /**
     * Time out in milliseconds.
     */
    private final static int TIMEOUT = 10000;

    /**
     * Listener.
     */
    DownloadListener mListener;

    /**
     * Constructor.
     *
     * @param listener DownloadListener.
     */
    public DownloadJSonTask(DownloadListener listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... urls) {
        if (isCancelled()) {
            return null;
        }
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            DebugUtils.log("Unable to retrieve data from " + urls[0]);
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        mListener.onDownloadCanceled();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            mListener.onDownloadFinished(result);
        } else {
            mListener.onDownloadFailed();
        }
    }


    /**
     * Sets up an HTTPURLConnection and gets an input stream from the provided URL.
     *
     * @param urlString The url to download from.
     * @return String containing the content of the URL.
     * @throws IOException
     */
    private String downloadUrl(String urlString) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT);
            conn.setConnectTimeout(TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            is = conn.getInputStream();

            return readToString(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * Reads an InputStream to a String.
     *
     * @param stream The stream to read.
     * @return String containing data from stream.
     * @throws IOException
     */
    private String readToString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }
}