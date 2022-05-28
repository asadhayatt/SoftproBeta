package com.hayat.stockprobeta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class expenseList extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener  {



    LinearLayout layout;
    Button datePicker , edit;
    EditText etdate;
    DBHandler dbhandler;
    TextView idtv,remstk;
    String procount,product="", date;
    AlertDialog dialog;
    EditText amount , Edate , newDes;
    String Examount="" , Exdate ="", ExDes = "";
    String id , amountTV , DateTV , DesTV; //for update expense on id base.

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        dbhandler= new DBHandler(this);
        layout      =        findViewById(R.id.containerlay);
        datePicker  =        findViewById(R.id.show_date);
        etdate=findViewById(R.id.ET_dt);


        String  Cdate   =   dbhandler.getDate();
        etdate.setText(Cdate);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadexpenseList();
            }
        });


        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowDatePicker();


            }
        });



        builddialog();
        loadexpenseList();

    }


    public void loadexpenseList()
    {

        date=etdate.getText().toString();
        layout.removeAllViews();


        try {
            ArrayList<String> st=new ArrayList<String>();
            st=dbhandler.getExpense_list(date);
            for( int i=0;i<st.size();i++)
            {
                final View view = getLayoutInflater().inflate(R.layout.expenselist, null);

                TextView expenseID      =   view.findViewById(R.id.expenseID);
                TextView Tamount      =   view.findViewById(R.id.Eamount);
                TextView Bdate        =   view.findViewById(R.id.Edate);
                TextView Des          =   view.findViewById(R.id.Des);
                Button edit           =   view.findViewById(R.id.edit);


                expenseID.setText(st.get(i));
                Tamount.setText(st.get(i+1));
                Bdate.setText(st.get(i+2));
                Des.setText(st.get(i+3));

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView expenseID = view.findViewById(R.id.expenseID);
                        TextView Tamount = view.findViewById(R.id.Eamount);
                        TextView Bdate = view.findViewById(R.id.Edate);
                        TextView BDes = view.findViewById(R.id.Des);

                         id             = expenseID.getText().toString();
                         amountTV             = Tamount.getText().toString();
                         DateTV             = Bdate.getText().toString();
                         DesTV             = BDes.getText().toString();

                        amount.setText(amountTV);
                        Edate.setText(DateTV);
                        newDes.setText(DesTV);

                        try {
                            dialog.show();
                        }
                        catch (NullPointerException e)
                        {
                            e.printStackTrace();
                        }

                    }
                });


                layout.addView(view);
                i=i+3;

            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
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

    private void builddialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.expense,null);

        amount=view.findViewById(R.id.amount);
        Edate=view.findViewById(R.id.Edate);
        newDes=view.findViewById(R.id.Edes);

        //Set String Data into EditText fields for update only



        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(amount.getText()))
                {
                    amount.setError("Enter Value");
                    Edate.requestFocus();
                    emptyToast();

                } else if (TextUtils.isEmpty(Edate.getText())) {
                    Edate.setError("Enter Value");
                    Edate.requestFocus();
                    emptyToast();

                }

                else if(TextUtils.isEmpty(newDes.getText())){
                    newDes.setError("Enter Value");
                    newDes.requestFocus();
                    emptyToast();

                }
                else {
                    Examount = amount.getText().toString().trim();
                    Exdate = Edate.getText().toString().trim();
                    ExDes = newDes.getText().toString().trim();


                    //update Expense Code
                    dbhandler.updateExpense(Examount,Exdate,ExDes,id);
                    amount.setText("");
                    Edate.setText("");
                    newDes.setText("");
                    loadexpenseList();
                    Toast.makeText(expenseList.this, "Product Code: "+id+" edited", Toast.LENGTH_SHORT).show();
                }




            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog=builder.create();
        loadexpenseList();
    }
    private void emptyToast() {
        Toast.makeText(expenseList.this,"Fields are empty..!", Toast.LENGTH_SHORT).show();

    }

}