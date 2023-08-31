package com.example.appstyle.api;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.example.appstyle.Exercise;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExerciseApiService {

    private static final String API_BASE_URL = "https://exercisedb.p.rapidapi.com/";

    private static final String API_HOST = "exercisedb.p.rapidapi.com";
    private static final String API_KEY = "2d8c47cb4fmsh7b70804dfc31b6bp1363c4jsnbdc68ce3fdb3";

    @SuppressLint("StaticFieldLeak")
    public void getListOfExercises(String bodyPart, StringCallback callback) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(API_BASE_URL + "exercises/bodyPart/" + bodyPart)
                        .get()
                        .addHeader("X-RapidAPI-Key", API_KEY)
                        .addHeader("X-RapidAPI-Host", API_HOST)
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
