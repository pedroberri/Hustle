package com.example.appstyle.api;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class QuoteApiService {


    private static final String API_BASE_URL = "https://api.quotable.io/quotes/random?maxLength=130";

    @SuppressLint("StaticFieldLeak")
    public void getRandomQuote(StringCallback callback) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(API_BASE_URL)
                        .get()
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseBody = Objects.requireNonNull(response.body()).string();
                    Log.e("api",responseBody);
                    response.close();
                    return responseBody;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                callback.callbacK(result);
            }
        }.execute();
    }
}
