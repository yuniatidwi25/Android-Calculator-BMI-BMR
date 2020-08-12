package com.example.androidhw1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class EditData extends AppCompatActivity {
    String name="",gender="", height="", weight="", age="", id="";
    private EditText mEditTextId, mEditTextName, mEditTextHeight, mEditTextWeight, mEditTextAge;
    private Button mBtnUpdate, mBtnBack;
    private RadioButton m_btnMale;
    private RadioButton m_btnFemale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdata);

        mEditTextId = (EditText) findViewById(R.id.editText_id);
        mEditTextName = (EditText) findViewById(R.id.editText_name);
        m_btnMale = (RadioButton) findViewById(R.id.radioButton_male);
        m_btnFemale = (RadioButton) findViewById(R.id.radioButton_female);
        mEditTextHeight = (EditText) findViewById(R.id.editText_height);
        mEditTextWeight = (EditText) findViewById(R.id.editText_weight);
        mEditTextAge = (EditText) findViewById(R.id.editText_age);
        mBtnUpdate = (Button) findViewById(R.id.btnUpdate);
        mBtnBack = (Button) findViewById(R.id.btn_Home);
        mBtnUpdate.setOnClickListener(mBtnUpdateOnClick);
        mBtnBack.setOnClickListener(mBtnBackOnClick);
    }
    Button.OnClickListener mBtnUpdateOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(mEditTextId.getText().toString()))
            {
                showToast("ID number is necessary.");
            }
            else if((!m_btnMale.isChecked())&&(!m_btnFemale.isChecked())) {
                showToast("Please choose your gender.");
            }
            else
            {
                id = mEditTextId.getText().toString();
                if (!TextUtils.isEmpty(mEditTextName.getText().toString()))
                {
                    name = mEditTextName.getText().toString();
                }
                if ((m_btnMale.isChecked()) || (m_btnFemale.isChecked()))
                {
                    if (m_btnMale.isChecked())
                        gender = "Male";
                    else
                        gender = "Female";
                }
                if (!TextUtils.isEmpty(mEditTextAge.getText().toString()))
                {
                    age = mEditTextAge.getText().toString();
                }
                if (!TextUtils.isEmpty(mEditTextHeight.getText().toString()))
                {
                    height = mEditTextHeight.getText().toString();
                }
                if (!TextUtils.isEmpty(mEditTextWeight.getText().toString()))
                {
                    weight = mEditTextWeight.getText().toString();
                }
                editData();
            }

        };
    };
    Button.OnClickListener mBtnBackOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Intent it = new Intent() ;
            it.setClass( EditData.this , MainActivity.class ) ;
            startActivity(it) ;
        };
    };

    private void editData() {

        Runnable myThread = new Runnable() {
            String str_response;

            @Override
            public void run() {
                String modify_url = "http://10.0.2.2/android_use/calculator/edit_data.php";

                try {
                    URL url = new URL(modify_url);
                    HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data = URLEncoder.encode("id","UTF-8") +"="+URLEncoder.encode(id,"UTF-8") +"&"+
                            URLEncoder.encode("name","UTF-8") +"="+URLEncoder.encode(name,"UTF-8") +"&"+
                            URLEncoder.encode("gender","UTF-8") +"="+URLEncoder.encode(gender,"UTF-8") +"&"+
                            URLEncoder.encode("height","UTF-8") +"="+URLEncoder.encode(height,"UTF-8") +"&"+
                            URLEncoder.encode("weight","UTF-8") +"="+URLEncoder.encode(weight,"UTF-8") +"&"+
                            URLEncoder.encode("age","UTF-8") +"="+URLEncoder.encode(age,"UTF-8");
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
                        Toast.makeText(EditData.this, str_response, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Thread t = new Thread(myThread) ;
        t.start() ;
    }
    public void showToast(String text)
    {
        Toast.makeText(EditData.this,text,Toast.LENGTH_SHORT).show();
    }
}
