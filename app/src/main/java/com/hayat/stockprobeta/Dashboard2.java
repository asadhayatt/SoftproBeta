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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Dashboard2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    AlertDialog dialog , Edialog;
    DatePickerDialog datePickerDialog;





    ImageButton addproduct , addorder
                , addpurchase, addexpense;

    ImageButton productlist1 , orderlist1
            , purchaselist1 , reportlist
            , expenseList1;

    static TextView productlist , orderlist
                , purchaselist, expenseList;

    String procount,product="",sr="",stk="",des="",search="";
    DBHandler dbhandler;

    EditText prname,stock,srate,desc;
    EditText Eamount,Edes,date;
    Button Edateb;
    String   EAmount,EDate,EDes , date1;

    TextView name,org;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        //For Top Name and Organization name.

        name = findViewById(R.id.nameTV2);
        org = findViewById(R.id.orgTV2);


        //For linear Layouts.

        addproduct  = findViewById(R.id.addproduct);
        addorder    = findViewById(R.id.addOder);
        addpurchase =   findViewById(R.id.addPurchase);
        addexpense  =   findViewById(R.id.addExpense);

        //For Showlist ImageButton

        productlist1  = findViewById(R.id.productlist1);
        orderlist1    = findViewById(R.id.oderlist1);
        purchaselist1 =   findViewById(R.id.purchaselist1);
        expenseList1  =   findViewById(R.id.expenselist1);
        reportlist  =   findViewById(R.id.report);


        //For counting Textviews.

        productlist  = findViewById(R.id.productlist);
        orderlist    = findViewById(R.id.oderlist);
        purchaselist =   findViewById(R.id.purchaselist);
        expenseList  =   findViewById(R.id.expenselist);



        dbhandler= new DBHandler(this);
        name=findViewById(R.id.nameTV2);
        org=findViewById(R.id.orgTV2);



        builddialog();
        try {

            expense_dialog();
        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
        //Set Name and org name from mainActivity Textview which is String converted .
        name.setText("Owner Name: "+MainActivity.name);
        org.setText("Company Name: "+MainActivity.org);
//////////////////////////////////////////////////////////

        //Set OncLick Listener for 2 thing. 1 for Add(AddProduct) and 2 for show(productlist) list
        productlist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProductlistActivity.class));
            }
        });

        loadproduct();
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });


        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Addorder.class));

            }
        });

        orderlist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OrderlistActivity.class));
            }
        });


        //Set OncLick Listener for 2 thing. 1 for Add(AddPurchase) and 2 for show(Purchaselist) list
        purchaselist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PurchaselistActivity.class));
            }
        });

        loadstocklist();// to update the the counting of Purchase list(stocklist)
        addpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            startActivity(new Intent(getApplicationContext(),ADDpurchase.class));


            
            }
        });

        reportlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ReportActivity.class));
            }
        });
        loadExpense();
        addexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Edialog.show();

            }
        });
        expenseList1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard2.this,expenseList.class));

            }
        });





    }


    public void projectlist(View view)
    {
        Intent intent = new Intent(Dashboard2.this, ProductlistActivity.class);
        startActivity(intent);
    }



    private void builddialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.addproduct2,null);

        prname=view.findViewById(R.id.prET);
        stock=view.findViewById(R.id.instockET);
        srate=view.findViewById(R.id.srateET);
        desc=view.findViewById(R.id.desET);

        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(prname.getText()))
                {
                    prname.setError("Enter Value");
                    prname.requestFocus();
                }
                else
                {
                    product=prname.getText().toString().trim();
                    sr=srate.getText().toString().trim();
                    stk=stock.getText().toString().trim();
                    des=desc.getText().toString().trim();

                }

                int check = addnewprouct();
                if(check == 1 )
                {
                    prname.setText("");
                    srate.setText("");
                    stock.setText("");
                    desc.setText("");
                    loadproduct();
                    Toast.makeText(getApplicationContext(), "Product added successfully...", Toast.LENGTH_SHORT).show();
                }
                else if(check == 2)
                {
                    prname.setError("This name already exist");
                    prname.requestFocus();
                    Toast.makeText(getApplicationContext(), "Data exist", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    prname.setError("Enter value");
                    prname.requestFocus();
                    Toast.makeText(getApplicationContext(), "Data empty", Toast.LENGTH_SHORT).show();
                }
                

            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog=builder.create();
    }

    public void loadstocklist() {


        try {
            procount = dbhandler.countStock();

        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
        purchaselist.setText(procount);
    }
    private void loadproduct() {


        try {
            procount = dbhandler.countproduct();

        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
        productlist.setText(procount);
    }

    private void loadExpense() {


        try {
            procount = dbhandler.countExpense();

        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
        expenseList.setText(procount);
    }

    private int addnewprouct()
    {
        int check = 0;

        try{
            searchproduct();
            if(search.equals("0")) {
                dbhandler.createproduct(product,sr , stk, des);
                check = 1;
            }
            else{
                if(product.equals(""))
                {
                    check = 0;
                  //  Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(this, search+"Already available.", Toast.LENGTH_SHORT).show();
                    check = 2;
                }

            }

        } catch (Exception e) {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
        return check;
    }

    /////////////////////////////////////////////////////////////////////
    private int addNewStockList()
    {
        int check = 0;

        try{
            searchproduct();
            if(search.equals("0")) {
                dbhandler.createproduct(product,sr , stk, des);
                check = 1;
            }
            else{
                if(product.isEmpty())
                {
                    check = 0;
                    //  Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(this, search+"Already available.", Toast.LENGTH_SHORT).show();
                    check = 2;
                }

            }

        } catch (Exception e) {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
        return check;
    }


    public void searchproduct()
    {
        try{
            search=dbhandler.searchproduct(product);

        } catch (Exception e) {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void alert(String s)
    {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }

    private void expense_dialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.expense,null);

        Eamount=view.findViewById(R.id.amount);
        date=view.findViewById(R.id.Edate);
        Edes=view.findViewById(R.id.Edes);
        String Cdate =dbhandler.getDate();

        date.setText(Cdate);

        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(Eamount.getText()))
                {
                    Eamount.setError("Enter Amount");
                    Eamount.requestFocus();
                }
                else if(TextUtils.isEmpty(Edes.getText()))
                {
                    Edes.setError("Enter Description");
                    Edes.requestFocus();
                }
                else
                {
                        EAmount = Eamount.getText().toString().trim();

                         date1 =   date.getText().toString().trim();

                        //EDate=Edate.getText().toString().trim();
                        EDes = Edes.getText().toString().trim();



                }


               dbhandler.create_EXPENSE(EAmount, date1 ,EDes);


                    Eamount.setText("");
                    date.setText("");
                    Edes.setText("");
                    loadExpense(); //it count the expense date
                    Toast.makeText(getApplicationContext(), "Expense added successfully...", Toast.LENGTH_SHORT).show();

            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Edialog=builder.create();
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
        String date  = + month + "-" + dayOfMonth + "-" +year;
        Toast.makeText(Dashboard2.this, ""+date, Toast.LENGTH_SHORT).show();
        //Edate.setText(date);
    }
}