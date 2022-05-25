package com.hayat.stockprobeta;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHandler dbhandler;
    EditText nameET, orgET , securityQ , pinCode;
    static String name, org , securityQ1 , pinCode1;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhandler = new DBHandler(this);
        btn = findViewById(R.id.startbtn);
        nameET = findViewById(R.id.nameET);
        orgET = findViewById(R.id.orgET);
        securityQ   =   findViewById(R.id.securityQ);
        pinCode   =   findViewById(R.id.pinCode);

        ArrayList array_list = dbhandler.getData();

        if (array_list.isEmpty()) {

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(nameET.getText()))
                    {
                        nameET.setError("Enter your name");
                        nameET.requestFocus();
                    }
                    else if (TextUtils.isEmpty(orgET.getText())) {
                        orgET.setError("Enter your organization");
                        orgET.requestFocus(); }
                    else if (TextUtils.isEmpty(securityQ.getText())) {
                        securityQ.setError("Create your backup security");
                        securityQ.requestFocus();  }
                    else if(TextUtils.isEmpty(pinCode.getText()))
                    {
                        pinCode.setError("Create your pin code");
                        pinCode.requestFocus();
                    }
                    else {
                        name = nameET.getText().toString().trim();
                        org = orgET.getText().toString().trim();
                        securityQ1 = securityQ.getText().toString().trim();
                        pinCode1 = pinCode.getText().toString().trim();
                        start(name, org ,securityQ1 , pinCode1);
                        create_Toast();
                        Intent intent = new Intent(MainActivity.this, Dashboard2.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });
        } else {
            name = array_list.get(0).toString();
            org = array_list.get(1).toString();

            Intent intent = new Intent(MainActivity.this, Dashboard2.class);
            startActivity(intent);
            finish();
        }


    }

    private void create_Toast() {

        LayoutInflater inflater  = getLayoutInflater();
        View layout =   inflater.inflate(R.layout.toast_create_account,(ViewGroup) findViewById(R.id.ConditionalToast1));

        Toast toast =new Toast(MainActivity.this);
        toast.setView(layout);
        toast.setDuration(toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();

    }

    public void start(String name, String org , String securityQ, String pinCode) {
        try {
            dbhandler.create(name,org,"0" , securityQ , pinCode);


        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "" + e.getCause().toString(), Toast.LENGTH_SHORT).show();
        }
    }


}
