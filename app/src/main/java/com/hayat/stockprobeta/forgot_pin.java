package com.hayat.stockprobeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class forgot_pin extends AppCompatActivity {

    DBHandler db;
    EditText code , Pass , confirmP;
    Button verify , submit;
    int maxLength;
    ImageButton hint;
    TextView Pass_heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);

        //Disable EditText before Verify

        db = new DBHandler(this);


        //EditText linking
        code    =   findViewById(R.id.code);
        Pass    =   findViewById(R.id.password);
        confirmP    =   findViewById(R.id.confirmP);
        hint        =   findViewById(R.id.hintSize);
        Pass_heading    =   findViewById(R.id.Pass_heading);

        //Button linking
        verify      =   findViewById(R.id.verify);
        submit      =   findViewById(R.id.submit);

        ArrayList<String> security   =   db.backup_code(); // Code saved in Arraylist having secutiy.
        String sec = security.get(0); //it store Security code coming from sqllite database in string.
        maxLength = sec.length();
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(forgot_pin.this, "Hint : Your backup code is "+maxLength+"-digit long", Toast.LENGTH_SHORT).show();
            }
        });


        setEditTextMaxLength(code,maxLength); //for limiting backup code EditText length.


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(code.getText().toString().equals(sec))
                {
                    disableVerify_ET();
                    enablePass_FIELDS();


                }
                else
                {
                    Toast.makeText(forgot_pin.this, "Enter correct backup code.", Toast.LENGTH_SHORT).show();
                }
                //Enable EditText after Verify





            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass , confirm ;
                pass     =   Pass.getText().toString();
                confirm =   confirmP.getText().toString();

                if(TextUtils.isEmpty(Pass.getText()))
                {
                    Pass.setError("Enter new pin code");
                    Pass.requestFocus();
                }
                else if (TextUtils.isEmpty(confirmP.getText()))
                {
                    confirmP.setError("Enter confirm new pin code");
                    confirmP.requestFocus();
                }
                else
                {
                    if(pass.equals(confirm))
                    {
                        Toast.makeText(getApplicationContext(), "Your pin code changed successfully.", Toast.LENGTH_SHORT).show();
                        db.replace_pin(pass);
                        startActivity(new Intent(forgot_pin.this,Dashboard2.class));
                        finish();
//                      Toast.makeText(forgot_pin.this, "Your Pin Code changed successfully.", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Pin code do not match", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });


    }

    private void enablePass_FIELDS() {

        Pass_heading.setVisibility(View.VISIBLE);
        Pass.setVisibility(View.VISIBLE);
        confirmP.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);
    }

    private void disableVerify_ET() {

        code.setEnabled(false);
        code.setFocusable(false);
        Toast.makeText(forgot_pin.this, "Security Verified...", Toast.LENGTH_SHORT).show();

    }


    public void setEditTextMaxLength(final EditText editText, int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(FilterArray);
    }

}