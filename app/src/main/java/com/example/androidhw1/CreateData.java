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

public class CreateData extends AppCompatActivity {
    private EditText eName, eHeight, eWeight, eAge;
    private RadioButton eMale, eFemale;
    private Button clearbtn, submitbtn;
    String strName, strGender;
    int iAge;
    double iWeight, iHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createdata);
        eName = findViewById(R.id.name);
        eHeight = findViewById(R.id.height);
        eWeight = findViewById(R.id.weight);
        eAge = findViewById(R.id.age);
        eMale = (RadioButton)findViewById(R.id.male);
        eFemale = (RadioButton)findViewById(R.id.female);
        clearbtn = findViewById(R.id.go_clear);
        clearbtn.setOnClickListener(clearbtnOnClick);
        submitbtn = findViewById(R.id.go_click);
        submitbtn.setOnClickListener(submitbtnOnClick);

    }

    Button.OnClickListener clearbtnOnClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v){
            eName.setText("") ;
            eHeight.setText("") ;
            eWeight.setText("") ;
            eAge.setText("") ;
        }

    } ;
    public void showToast(String text)
    {
        Toast.makeText(CreateData.this,text,Toast.LENGTH_SHORT).show();
    }
    Button.OnClickListener submitbtnOnClick = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {

            iHeight = Double.valueOf(eHeight.getText().toString()).doubleValue() ;
            iWeight = Double.valueOf(eWeight.getText().toString()).doubleValue() ;
            iAge = Integer.parseInt(eAge.getText().toString()) ;
            if (TextUtils.isEmpty( eName.getText().toString())||TextUtils.isEmpty( eHeight.getText().toString())||TextUtils.isEmpty( eWeight.getText().toString())||TextUtils.isEmpty( eAge.getText().toString()))
            {
                showToast("Please fill in all of the blanks above.");
            }
            else if ((!eMale.isChecked())&&(!eFemale.isChecked()))
            {
                showToast("Please choose your gender.");
            }
            else
            {
                strName = eName.getText().toString();
                if (eMale.isChecked())
                    strGender = "Male";
                else
                    strGender = "Female";
                iHeight = Double.valueOf(eHeight.getText().toString());
                iWeight = Double.valueOf(eWeight.getText().toString());
                iAge = Integer.valueOf(eAge.getText().toString());
                Intent intent = new Intent() ;
                intent.setClass(CreateData.this , Output.class) ;

                Bundle bundle = new Bundle() ;
                bundle.putString("name" , strName) ;
                bundle.putString("gender" , strGender ); ;
                bundle.putDouble("height",iHeight) ;
                bundle.putDouble("weight",iWeight) ;
                bundle.putInt("age",iAge); ;
                intent.putExtras(bundle) ;

                startActivity(intent) ;
            }


        }
    };

}
