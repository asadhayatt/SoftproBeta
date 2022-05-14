package com.hayat.stockprobeta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Spinner spinner;
    TextView rTitle;
    EditText frDate,toDate;
    Button searchBtn;
    String frdate, todate;
    int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //Initialize views with java file.

        frDate  =   findViewById(R.id.frDate);
        toDate  =   findViewById(R.id.toDate);
        searchBtn=  findViewById(R.id.searchBtn);
        spinner  =  findViewById(R.id.nameET);


        List<String>    list =  new ArrayList<>();
        list.add("Select an item ▼");
        list.add("Purchase");
        list.add("Sale");
        list.add("Expense");
        list.add("Stock");
        list.add("Compiled");


        ArrayAdapter    adp =new ArrayAdapter(this, android.R.layout.simple_spinner_item,list){


            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };

        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);
        spinner.setDropDownWidth(330);


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String Title = spinner.getSelectedItem().toString().trim();

                        if(position > 0){
                            // Notify the selected item text
                            Toast.makeText
                                    (getApplicationContext(), "Selected : " + Title, Toast.LENGTH_SHORT)
                                    .show();
                        }
//
                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });




        frDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePicker();
                check=1;

            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePicker();
                check=2;

            }
        });


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
         frdate  = dayOfMonth+"-"+ m + "-" +year;
         if(check==1)
         {
             frDate.setText(frdate);
         }
         else if(check==2)
         {
             toDate.setText(frdate);
         }




    }

}