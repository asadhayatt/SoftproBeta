package com.hayat.stockprobeta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHandler dbhandler;
    EditText nameET, orgET;
    static String name, org;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhandler = new DBHandler(this);
        btn = findViewById(R.id.startbtn);
        nameET = findViewById(R.id.nameET);
        orgET = findViewById(R.id.orgET);

        ArrayList array_list = dbhandler.getData();

        if (array_list.isEmpty()) {

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nameET.getText().toString().equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Enter your Name", Toast.LENGTH_SHORT).show();

                    } else if (orgET.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this, "Enter organization", Toast.LENGTH_SHORT).show();

                    } else {
                        name = nameET.getText().toString().trim();
                        org = orgET.getText().toString().trim();
                        start(name, org);
                        Intent intent = new Intent(MainActivity.this, Dashboard2.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });
        } else {
            name = array_list.get(0).toString();
            org = array_list.get(1).toString();

            Intent intent = new Intent(MainActivity.this, Dashboard2    .class);
            startActivity(intent);
            finish();
        }


    }

    public void start(String name, String org) {
        try {
            dbhandler.create(name, "20-12-2022", org, "0");


        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "" + e.getCause().toString(), Toast.LENGTH_SHORT).show();
        }
    }


}
