package com.hayat.stockprobeta;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.hayat.stockprobeta.databinding.ActivityProductlistBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class PurchaselistActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {




    LinearLayout layout;
    Button datePicker;
    EditText etdate;
    DBHandler dbhandler;
    TextView idtv,remstk;
    String procount,product="", date;
    String IDD = "" ,  amountt ;
    EditText   Pramount , Prdate;  //EDittext which iniatialize and edit the order through alert dialog in orderlist.
    AlertDialog dialog;
    String PrID , PrAmount ;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchaselist);
        dbhandler= new DBHandler(this);
        layout      =        findViewById(R.id.containerlay);
        datePicker  =        findViewById(R.id.show_date);
        etdate=findViewById(R.id.ET_dt);
        String  Cdate   =   dbhandler.getDate();
        etdate.setText(Cdate);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadstocklist();
            }
        });


        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowDatePicker();


            }
        });


        builddialog();
        loadstocklist();


    }
    public void loadstocklist()
    {

        date=etdate.getText().toString();
        layout.removeAllViews();


        try {
            ArrayList<String> st=new ArrayList<String>();
            st=dbhandler.getstock_list(date);
          for( int i=0;i<st.size();i++)
          {
              final View view = getLayoutInflater().inflate(R.layout.custom_stocklist, null);

              TextView stockID      =   view.findViewById(R.id.stockID);
              TextView Tamount      =   view.findViewById(R.id.Tamount);
              TextView Bdate        =   view.findViewById(R.id.billdate);


              Button   editBTN     =   view.findViewById(R.id.editBTN);
              Button   viewBtn     =   view.findViewById(R.id.viewbtn);


              stockID.setText(st.get(i));
              Tamount.setText(st.get(i+1));
              Bdate.setText(st.get(i+2));
              //Convert TextView into String.
              String id , amount , date ;

              id=(stockID.getText().toString());
              amount=(Tamount.getText().toString());
              date=(Bdate.getText().toString());
              viewBtn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Bundle bundle = new Bundle();
                      bundle.putString("id",id);
                      bundle.putString("amount",amount);
                      bundle.putString("date",date);

                      Intent intent =new Intent(PurchaselistActivity.this,View_StockList.class);
                      intent.putExtras(bundle);
                      startActivity(intent);
                  }
              });
              editBTN.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      TextView PstockID = view.findViewById(R.id.stockID);
                      TextView Pamount = view.findViewById(R.id.Tamount);


                      IDD             = PstockID.getText().toString();
                      amountt            = Pamount.getText().toString();


                      Pramount.setText(amountt);

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
              i=i+2;

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
        View view=getLayoutInflater().inflate(R.layout.edit_stock,null);



        Pramount=view.findViewById(R.id.Pramount);






        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(Pramount.getText()))
                {
                    Pramount.setError("Enter Value");
                    Pramount.requestFocus();
                    emptyToast();

                }

                else {
                    //Storing the edittext data into string.


                    PrAmount = Pramount.getText().toString().trim();



                    //update Order Code
                    dbhandler.updateStock(PrAmount , IDD);


                    Pramount.setText("");


                    loadstocklist();
                    Toast.makeText(PurchaselistActivity.this, "Stock id : "+IDD+" edited", Toast.LENGTH_SHORT).show();
                }




            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog=builder.create();
        loadstocklist();
    }
    private void emptyToast() {
        Toast.makeText(PurchaselistActivity.this,"Fields are empty..!", Toast.LENGTH_SHORT).show();

    }

}