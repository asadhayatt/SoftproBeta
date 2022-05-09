package com.hayat.stockprobeta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    AlertDialog dialog;

    static TextView pr;
    TextView name,org,idtv,remstk;
    EditText prname,stock,srate,desc;



    static String v,search;

    Button prbtn;
    DBHandler dbhandler;
    String procount,product="",sr="",stk="",des="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dbhandler= new DBHandler(this);
        setContentView(R.layout.activity_dashboard);
        name=findViewById(R.id.nameTV);
        org=findViewById(R.id.orgTV);
        pr=findViewById(R.id.replacelist);

        builddialog();

        name.setText("Owner Name: "+MainActivity.name);
        org.setText("Company Name: "+MainActivity.org);

        prbtn=findViewById(R.id.prbtn);
        loadproduct();
        prbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                 //  addnewprouct();
                 //  loadproduct();

            }
        });

    }
    public void projectlist(View view)
    {
        Intent intent = new Intent(DashboardActivity.this, ProductlistActivity.class);
        startActivity(intent);
    }

    private void builddialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.addproduct,null);

        prname=view.findViewById(R.id.prET);
        stock=view.findViewById(R.id.instockET);
        srate=view.findViewById(R.id.srateET);
        desc=view.findViewById(R.id.desET);

        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                product=prname.getText().toString().trim();
                sr=srate.getText().toString().trim();
                stk=stock.getText().toString().trim();
                des=desc.getText().toString().trim();
                addnewprouct();
                prname.setText("");
                srate.setText("");
                stock.setText("");
                desc.setText("");
                loadproduct();

            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog=builder.create();
    }


    public void loadproduct()
    {


        try {
            procount = dbhandler.countproduct();

        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
        pr.setText("Product: "+procount);
    }


    public  void addnewprouct()
    {
        try{
            dbhandler.createproduct(product,sr,stk,des);

        } catch (Exception e) {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void alert(String s)
    {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
}