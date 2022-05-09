package com.hayat.stockprobeta;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ADDpurchase extends AppCompatActivity {

    TextView textView, totalStock;
    Spinner spinner;
    Button add , save;
    ListView listView;
    LinearLayout layout;
    int c=0,res ,  counter=0 ;//cl counter
    int rate3 , rate4;
    ArrayList cl=new ArrayList();//list which store indexes of all element.
    ArrayList dl= new ArrayList();//it stores deleted index.

    EditText rate , quantity ;
    String DropdownPICKER;
    ArrayList<product_list> pl=new ArrayList<product_list>();
    String spinnerName;
    int currentStockPrice = 0;
    int totalStockPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpurchase);

        layout=findViewById(R.id.containerlay);
        spinner          = findViewById(R.id.nameET);
        rate             = findViewById(R.id.prateET);
        quantity         = findViewById(R.id.quantityET);
        totalStock       = findViewById(R.id.totalStock);
        //listView         = findViewById(R.id.listView);




       // int cal = Integer.parseInt(rate3)  *  Integer.parseInt(rate4);
       // String x = Integer.toString(cal);





        //linking Buttons
        add     =   findViewById(R.id.add);
        save    =   findViewById(R.id.save);

        DBHandler db = new DBHandler(this);

        //ArrayList<String> list = (ArrayList<String>) db.product_list();
       // List<String> list = (List<String>) db.product_list();
        //HashMap<Integer,String> hashMap = (HashMap<Integer, String>) db.product_list();
        List<String> list = (List<String>) db.product_list();
        ArrayList<String>  arrayList = new ArrayList<>(list);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerName = spinner.getSelectedItem().toString();

                 }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        //linking listview
        //textView = findViewById(R.id.textView);



//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                 DropdownPICKER = parent.getItemAtPosition(position).toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        //final List<String> itemList = new ArrayList<>();


            add.setOnClickListener(new View.OnClickListener()
            {
                private Object ArrayAdapter;
                @Override
                public void onClick(View v) {

                    if(cl.contains(spinnerName))
                    {
                        Toast.makeText(ADDpurchase.this, "already exist", Toast.LENGTH_SHORT).show();
                    }
                else {


                        if (rate.getText().toString().equals("") || quantity.getText().toString().equals("")) {
                            Toast.makeText(ADDpurchase.this, "invalid input", Toast.LENGTH_SHORT).show();
                        } else {
                            rate3 = Integer.parseInt(rate.getText().toString().trim());
                            rate4 = Integer.parseInt(quantity.getText().toString().trim());
                            currentStockPrice = rate3 * rate4;
                            totalStockPrice = totalStockPrice + currentStockPrice;


                            totalStock.setText(Integer.toString(totalStockPrice));
                            showlist(spinnerName, rate3, rate4, currentStockPrice, c);
                            rate.setText("");
                            quantity.setText("");
                            cl.add(c);
                            cl.add(spinnerName);
                            cl.add(rate3);//rate
                            cl.add(rate4);//qty
                            cl.add(currentStockPrice);
                            rate.setTag(String.valueOf(c));
                            c++;
                            //Toast.makeText(ADDpurchase.this, "   " + cl.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }


/*

                 //   ArrayAdapter<String> adp = new ArrayAdapter<String>(ADDpurchase.this, android.R.layout.simple_list_item_1, itemList);

                     //   itemList.add(count,CombinedString);
                     //   count++ ;//increment value.

                    //Dynamically list view Adapter

                    //adapter method Call..
                    my_listView_adapter myADP = new my_listView_adapter(ADDpurchase.this,
                            get_nameArray(spinnerName),
                            get_rateArray(rate3),
                            get_qtyArray(rate4),
                            get_totalArray(currentStockPrice),
                            get_ImgArray(R.drawable.delete));


                         listView.setAdapter(myADP);

 */


                }
            });

            save.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Toast.makeText(ADDpurchase.this, "cl="+cl.size()+"dl="+dl.size(), Toast.LENGTH_SHORT).show();
                    try {
                        res =Integer.parseInt(totalStock.getText().toString());
                        String stockId   =    db.create_stock(String.valueOf(res));
                        for (int a = 0; a < cl.size(); a++)
                        {
                            int matched = 0;
                            for (int j = 0; j < dl.size(); j++)
                            {

                                if (Integer.parseInt(cl.get(a).toString()) == Integer.parseInt(dl.get(j).toString()))
                                {
                                    matched = 1;
                                }
                            }
                            if (matched == 0)
                            {


                                String ProId   =    db.getProductID(cl.get(a+1).toString());
                                Toast.makeText(ADDpurchase.this, "Recod addeded successfully.", Toast.LENGTH_SHORT).show();
                                db.create_stockList(stockId,ProId,cl.get(a+2).toString(),cl.get(a+3).toString(),cl.get(a+4).toString());
                                db.update_product(ProId,cl.get(a+3).toString());

                              /*  for (int k = 1; k < cl.size(); k++)
                                {
                                    String productId =    db.getProductID(String.valueOf(cl.get(k)));  //convert into string and call.
                                     k=k+4;
                                    Log.d(TAG, "Product id"+productId);
                                }

                               */

                            }
                            a = a + 4;
                        }
                    }
                    catch (Exception e)
                    {
                        //Toast.makeText(ADDpurchase.this, ""+e, Toast.LENGTH_SHORT).show();
                    }


                    totalStock.setText("");


                }


            });







    }




    //Set data dynamically in listView.
    //Multiple methods here...





    int serialNo = 0;

    public void showlist(String name1 , int newRate , int newQty , int newTotal,int tag)
    {
        View view = getLayoutInflater().inflate(R.layout.custom_listview_addpurchase_row, null);




        //Initializing views here...
        TextView name   = view.findViewById(R.id.customTV_name);
        TextView qty    = view.findViewById(R.id.customTV_qty);
        TextView rate   = view.findViewById(R.id.customTV_rate);
        TextView total  = view.findViewById(R.id.customTV_total);
        TextView TV_tag = view.findViewById(R.id.TV_tag);
        ImageView del   = view.findViewById(R.id.customImg_delete);
        serialNo++;

        if(TextUtils.isEmpty(rate.getText()))
        {
            Toast.makeText(ADDpurchase.this, "rate is empty..", Toast.LENGTH_SHORT).show();
        }



        // btn.setOnClickListener(new View.OnClickListener() {}
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //aletmsg(name.getText().toString());

                int tv=Integer.parseInt(totalStock.getText().toString());

                int ts=Integer.parseInt(total.getText().toString());

                res=tv-ts;
                String tst= TV_tag.getText().toString();

                dl.add(tst);
                //To debug that data is processing or not.
                //Toast.makeText(ADDpurchase.this, ""+dl, Toast.LENGTH_SHORT).show();
                layout.removeView(view);
                totalStock.setText(String.valueOf(res));
            }
        });

//        if(1==1)
//        {
//            Toast.makeText(ADDpurchase.this, "Empty...", Toast.LENGTH_SHORT).show();
////            rate.setError("Enter Value");
////            rate.requestFocus();
//        }


            name.setText(name1);
            rate.setText(String.valueOf(newRate));
            qty.setText(String.valueOf(newQty));
            TV_tag.setText(String.valueOf(tag));
            total.setText(String.valueOf(newTotal));




        layout.addView(view);

    }



//    public void aletmsg(String str)
//    {
//        Toast.makeText(this,str+" deleted", Toast.LENGTH_SHORT).show();
//    }




}