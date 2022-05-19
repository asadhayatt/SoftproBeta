package com.hayat.stockprobeta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderlistActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {



    LinearLayout layout;
    Button datePicker;
    EditText etdate;
    DBHandler dbhandler;
    TextView idtv,remstk;
    String procount,product="", date;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        dbhandler= new DBHandler(this);
        layout      =        findViewById(R.id.containerlay);
        datePicker  =        findViewById(R.id.show_date);
        etdate=findViewById(R.id.ET_dt);

        String Cdate    =   dbhandler.getDate();
        etdate.setText(Cdate);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadorderlist();
            }
        });


        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowDatePicker();


            }
        });





    }
    public void loadorderlist() throws IndexOutOfBoundsException
    {

        date=etdate.getText().toString();
        layout.removeAllViews();


        try {
            ArrayList<String> st=new ArrayList<String>();
            st=dbhandler.getorder_list(date);
            for( int i=0;i<st.size();i++)
            {
                final View view = getLayoutInflater().inflate(R.layout.custom_orderlist, null);

                TextView orderID      =   view.findViewById(R.id.orderID);
                TextView orderTitile  =   view.findViewById(R.id.Otitle);
                TextView orderContact  =   view.findViewById(R.id.OContact);
                TextView Tamount      =   view.findViewById(R.id.Tamount);
                TextView Bdate        =   view.findViewById(R.id.orderDate);
                Button   viewBtn     =   view.findViewById(R.id.viewbtn);


                orderID.setText(st.get(i));
                orderTitile.setText(st.get(i+1));
                orderContact.setText(st.get(i+2));
                Tamount.setText(st.get(i+3));
                Bdate.setText(st.get(i+4));
                //Convert TextView into String.
                String id ,title , contact , amount , date ;

                id=(orderID.getText().toString());
                title=(orderTitile.getText().toString());
                contact=(orderContact.getText().toString());
                amount=(Tamount.getText().toString());
                date=(Bdate.getText().toString());
                viewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id",id);
                        bundle.putString("title",title);
                        bundle.putString("contact",contact);
                        bundle.putString("amount",amount);
                        bundle.putString("date",date);

                        Intent intent =new Intent(OrderlistActivity.this,View_OrderList.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });



                layout.addView(view);
                i=i+3;

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }



    public void alert(String s)
    {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }


    public void ShowDatePicker()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        );
        datePickerDialog.show();

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String m =String.format("%02d", (month+1));
        String date  = dayOfMonth+"-"+ m + "-" +year;
        etdate.setText(date);
    }
}