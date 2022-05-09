package com.hayat.stockprobeta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class View_StockList extends AppCompatActivity {
    TextView sid1 , date1 , Tstock;
    LinearLayout layout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stock_list);
        layout = findViewById(R.id.containerlay);
        DBHandler dbHandler= new DBHandler(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle != null)
        {
            String id , amount , date ;

            id = bundle.getString("id");
            amount = bundle.getString("amount");
            date = bundle.getString("date");

            sid1= findViewById(R.id.sid1);
            date1= findViewById(R.id.date1);
            Tstock= findViewById(R.id.Tstock);

            sid1.setText(id);
            date1.setText(date);
            Tstock.setText(amount);

            layout.removeAllViews();

            ArrayList<String> list = dbHandler.Viewstock_list(sid1.getText().toString());

            for (int i = 0; i < list.size(); i++)
            {
                final View view = getLayoutInflater().inflate(R.layout.viewstocklist, null);

                TextView pid=view.findViewById(R.id.pid);
                TextView prate=view.findViewById(R.id.prate);
                TextView qty=view.findViewById(R.id.qty);
                TextView total=view.findViewById(R.id.total);

                String Pname    =   dbHandler.getProduct_Name(list.get(i));

                pid.setText(Pname);
                prate.setText(list.get(i+1));
                qty.setText(list.get(i+2));
                total.setText(list.get(i+3));

                layout.addView(view);
                i=i+3;


            }


        }
    }
}