package com.nisum.android.secondhomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://rss.news.yahoo.com/rss/entertainment";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if(response.isSuccessful()) {
                        String xmlResponse = response.body().string();
                        JSONObject result = XML.toJSONObject(xmlResponse);
                        JSONObject rss = result.getJSONObject("rss");
                        JSONObject channel = rss.getJSONObject("channel");
                        JSONArray items = channel.getJSONArray("item");
                        Log.v(TAG, "items: " + items.toString());
                    }
                } catch (IOException e) {
                    Log.e(TAG,"Exception caught: ", e);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
