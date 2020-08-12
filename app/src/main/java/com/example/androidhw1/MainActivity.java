package com.example.androidhw1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ListViewAdapter listViewAdapter;
    ListView m_listView;
    private Button createbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createbtn = findViewById(R.id.createbutton);
        createbtn.setOnClickListener(createbtnOnClick);
        try {
            json_string = getUserData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {

            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");

            listViewAdapter = new ListViewAdapter(this, jsonArray);
            m_listView = (ListView) findViewById(R.id.listview);
            m_listView.setAdapter(listViewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String getUserData() throws ExecutionException, InterruptedException {
        String get_data_url = "http://10.0.2.2/android_use/calculator/login_activity.php";

        @SuppressLint("StaticFieldLeak") String result = new AsyncTask<String, Void, String>()
        {
            @Override
            protected String doInBackground(String... params)
            {
                String result = null;
                String get_data_url = params[0];
                try {
                    URL url = new URL(get_data_url);
                    HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));

                    StringBuilder stringBuilder = new StringBuilder();
                    String data;
                    while((data = bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(data+"\n");
                    }
                    result = stringBuilder.toString();
                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }.execute(get_data_url).get();

        return result;

    }
    Button.OnClickListener createbtnOnClick = new Button.OnClickListener() {
        public void onClick(View v){
            Intent toBMR = new Intent();
            toBMR.setClass(MainActivity.this, CreateData.class);
            startActivity(toBMR);
        }
    };
}
