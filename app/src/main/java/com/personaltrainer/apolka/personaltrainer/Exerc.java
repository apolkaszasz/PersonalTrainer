package com.personaltrainer.apolka.personaltrainer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Exerc extends AppCompatActivity {

    private static final String TAG = "ExercActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerc);

        AsyncTask.execute(new Runnable(){
            @Override
            public void run(){
                //networking code - logic

                //create url
                try {
                    URL wgerEndpoint = new URL("https://wger.de/api/v2/exercise/?language=2");

                    //create connection
                    HttpsURLConnection myConnection = (HttpsURLConnection) wgerEndpoint.openConnection();


                    myConnection.setRequestProperty("Accept","application/json");

                    if (myConnection.getResponseCode() == 200) {
                        // Success

                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader =
                                new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);

                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String key = jsonReader.nextName();
                            Log.d(TAG,".............................."+key);
                            if (key.equals("results")) {
                                jsonReader.beginArray();

                                while (jsonReader.hasNext()){
                                    jsonReader.beginObject();
                                    while(jsonReader.hasNext()){
                                        String id ;
                                        String name = jsonReader.nextName();
                                        if(name.equals("id")){
                                            id = jsonReader.nextString();
                                            Log.d(TAG,id);
                                        }
                                        else{
                                            jsonReader.skipValue();
                                        }
                                    }
                                    jsonReader.endObject();
                                }
                                jsonReader.endArray();
                                break;
                            } else {
                                jsonReader.skipValue();
                            }
                        }
                        jsonReader.close();
                        myConnection.disconnect();

                    } else Log.d(TAG, "Error response code: " + myConnection.getResponseCode());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }





            }
        });
    }
}
