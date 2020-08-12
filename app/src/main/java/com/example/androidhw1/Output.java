package com.example.androidhw1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class Output extends AppCompatActivity {
    private TextView out_name , out_bmi , out_bmr ;
    double bmr, bmi;
    private Button backmain ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output);

        out_name =(TextView) findViewById(R.id.outputname) ;
        out_bmi = (TextView)findViewById(R.id.outputbmi) ;
        out_bmr =(TextView) findViewById(R.id.outputbmr) ;
        backmain =(Button) findViewById(R.id.back_to_main);

        Bundle bundle = this.getIntent().getExtras() ;
        String name = bundle.getString("name") ;
        String gender = bundle.getString("gender");
        double height = bundle.getDouble("height");
        double weight = bundle.getDouble("weight");
        int age = bundle.getInt("age");
        out_name.setText(name);
        bmi = CalculateBMI(height,weight);
        bmi = Math.round((bmi*100.0))/100.0;
        out_bmi.setText(""+Double.toString(bmi));
        bmr = CalculateBMR(gender,height,weight,age);
        bmr = Math.round((bmr*100.0))/100.0;
        out_bmr.setText(""+Double.toString(bmr));

        insertData(name, gender, height, weight, age, bmi, bmr);
        backmain.setOnClickListener(backmainOnClick);
    }
    private double CalculateBMI(double height, double weight)
    {
        bmi = weight / ( height * height / 10000 );
        return bmi;
    }
    private double CalculateBMR(String gender, double height, double weight, int age)
    {
        if(gender.equals("Male"))
            bmr = 66 + (13.7 * weight) + (5.0 * height) - (6.8 * age);
        else
            bmr = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);

        return bmr;
    }
    public void insertData(String name, String gender, double height, double weight, int age, double bmi, double bmr)
    {
        final String str_name = name;
        final String str_gender = gender;
        final String str_height = Double.toString(height);
        final String str_weight = Double.toString(weight);
        final String str_age = Integer.toString(age);
        final String str_bmi = Double.toString(bmi);
        final String str_bmr = Double.toString(bmr);


        Runnable myThread = new Runnable() {
            String str_response;

            @Override
            public void run() {
                String insert_url = "http://10.0.2.2/android_use/alculator/insert_data.php";

                try {
                    URL url = new URL(insert_url);
                    HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");  //Using "POST" means we're going to encode first and then we send the message
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data = URLEncoder.encode("name","UTF-8") +"="+URLEncoder.encode(str_name,"UTF-8") +"&"+
                            URLEncoder.encode("gender","UTF-8") +"="+URLEncoder.encode(str_gender,"UTF-8") +"&"+
                            URLEncoder.encode("height","UTF-8") +"="+URLEncoder.encode(str_height,"UTF-8") +"&"+
                            URLEncoder.encode("weight","UTF-8") +"="+URLEncoder.encode(str_weight,"UTF-8") +"&"+
                            URLEncoder.encode("age","UTF-8") +"="+URLEncoder.encode(str_age,"UTF-8") +"&"+
                            URLEncoder.encode("bmi","UTF-8") +"="+URLEncoder.encode(str_bmi,"UTF-8") +"&"+
                            URLEncoder.encode("bmr","UTF-8") +"="+URLEncoder.encode(str_bmr,"UTF-8");
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
                        Toast.makeText(Output.this, str_response, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Thread t = new Thread(myThread) ;
        t.start() ;
    }
    Button.OnClickListener backmainOnClick = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent it = new Intent() ;
            it.setClass( Output.this , MainActivity.class) ;
            startActivity(it);
        }
    };
}
