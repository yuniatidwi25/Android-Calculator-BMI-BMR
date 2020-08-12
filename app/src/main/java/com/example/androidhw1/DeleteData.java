package com.example.androidhw1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class DeleteData extends AppCompatActivity {
    private EditText eName, eAge;
    private Button bBtnDelete, bBtnBack;

    private String name,age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletedata);
        eName = (EditText)findViewById(R.id.text_nama);
        eAge = (EditText)findViewById(R.id.text_umur);
        bBtnDelete = (Button)findViewById(R.id.btnDelete);
        bBtnBack = (Button)findViewById(R.id.btnBack);

        bBtnDelete.setOnClickListener(bBtnDeleteOnClick);
        bBtnBack.setOnClickListener(bBtbnBackOnClick);
    }
    Button.OnClickListener bBtnDeleteOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(TextUtils.isEmpty(eName.getText().toString() )  || TextUtils.isEmpty(eAge.getText().toString()))
            {
                showToast("Please fill in all of the blanks above.");
            }
            else
            {
                name = eName.getText().toString();
                age = eAge.getText().toString();
                deleteData(name, age);
            }
        };
    };
    private void deleteData(String name, String age)
    {
        final String str_name = name;
        final String str_age = age;

        Runnable myThread = new Runnable() {
            String str_response;

            @Override
            public void run() {
                String delete_url = "http://10.0.2.2/android_use/calculator/delete_data.php";

                try {
                    URL url = new URL(delete_url);
                    HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data = URLEncoder.encode("name","UTF-8") +"="+URLEncoder.encode(str_name,"UTF-8") +"&"+
                            URLEncoder.encode("age","UTF-8") +"="+URLEncoder.encode(str_age,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));

                    StringBuilder stringBuilder = new StringBuilder();
                    String response;
                    while((response = bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(response+"\n");

                    }
                    str_response = stringBuilder.toString().trim();
                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DeleteData.this, str_response, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Thread t = new Thread(myThread) ;
        t.start() ;
    }

    Button.OnClickListener bBtbnBackOnClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent it = new Intent() ;
            it.setClass( DeleteData.this , MainActivity.class ) ;
            startActivity(it) ;
        };
    };

    public void showToast(String text)
    {
        Toast.makeText(DeleteData.this,text,Toast.LENGTH_SHORT).show();
    }
}
