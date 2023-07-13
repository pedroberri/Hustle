package com.example.appstyle.api;
import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExerciseApiService {

    private static final String API_BASE_URL = "https://exercisedb.p.rapidapi.com/";

    private static final String API_HOST = "exercisedb.p.rapidapi.com";
    private static final String API_KEY = "2d8c47cb4fmsh7b70804dfc31b6bp1363c4jsnbdc68ce3fdb3";

    @SuppressLint("StaticFieldLeak")
    public void getExercises(StringCallback callback){
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://exercisedb.p.rapidapi.com/exercises")
                        .get()
                        .addHeader("X-RapidAPI-Key", "2d8c47cb4fmsh7b70804dfc31b6bp1363c4jsnbdc68ce3fdb3")
                        .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseBody = response.body().string();
                    response.close();
                    return responseBody;
                } catch (IOException e) {
                    System.out.println(e.getMessage());
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
