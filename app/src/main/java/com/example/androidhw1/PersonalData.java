package com.example.androidhw1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class PersonalData extends AppCompatActivity {

    private TextView eID, eName, eGender, eHeight, eWeight, eAge, eBMI, eBMR;
    private Button back, edit, delete;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personaldata);

        eID = findViewById(R.id.txt_id);
        eName = findViewById(R.id.txt_name);
        eGender = findViewById(R.id.txt_gender);
        eAge = findViewById(R.id.txt_age);
        eHeight = findViewById(R.id.txt_height);
        eWeight = findViewById(R.id.txt_weight);
        eBMI=findViewById(R.id.txt_bmi);
        eBMR = findViewById(R.id.txt_bmr);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(backOnClick);
        edit = findViewById(R.id.edit_button);
        edit.setOnClickListener(editOnClick);
        delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(deleteOnClick);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");

        try {
            json_string = getUserData(id);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject0 = null;
        try {
            jsonObject0 = jsonArray.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String name = jsonObject0.optString("name");
        String gender = jsonObject0.optString("gender");
        Log.e("gender",gender);
        Integer age = jsonObject0.optInt("age");
        String height = jsonObject0.optString("height");
        String weight = jsonObject0.optString("weight");
        String bmi = jsonObject0.optString("bmi");
        String bmr = jsonObject0.optString("bmr");

        eID.setText(""+id);
        eName.setText(""+name);
        eGender.setText(""+gender);
        eAge.setText(""+age);
        eHeight.setText(""+height);
        eWeight.setText(""+weight);
        eBMI.setText(""+bmi);
        eBMR.setText(""+bmr);
    }

    private String getUserData(String id) throws ExecutionException, InterruptedException {
        String get_data_url = "http://10.0.2.2/android_use/calculator/search_user_data.php";
        final String finalId = id;

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
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data = URLEncoder.encode("id","UTF-8") +"="+ URLEncoder.encode(finalId,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                    StringBuilder stringBuilder = new StringBuilder();
                    String data_str;
                    while((data_str = bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(data_str+"\n");
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
        Log.e("bug",result);
        return result;
    }

    Button.OnClickListener backOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent() ;
            it.setClass( PersonalData.this , MainActivity.class ) ;
            startActivity(it) ;
        }
    } ;
    Button.OnClickListener editOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent() ;
            it.setClass( PersonalData.this , EditData.class ) ;
            startActivity(it) ;
        }
    } ;
    Button.OnClickListener deleteOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent() ;
            it.setClass( PersonalData.this , DeleteData.class ) ;
            startActivity(it) ;
        }
    } ;
}
