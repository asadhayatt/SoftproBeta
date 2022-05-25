package com.hayat.stockprobeta;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Addorder extends AppCompatActivity {

    TextView textView, totalStock;
    Spinner spinner;
    Button add , save;
    ListView listView;
    LinearLayout layout;
    int c=0,res ,  counter=0 ;//cl counter
    int sRate , Qty;
    ArrayList cl=new ArrayList();//list which store indexes of all element.
    ArrayList dl= new ArrayList();//it stores deleted index.

    EditText customerName ,customerContact, quantity ;
    String DropdownPICKER;
    ArrayList<product_list> pl=new ArrayList<product_list>();
    String spinnerName , salerate, orderTitle , orderContact;
    int currentStockPrice = 0;
    int totalStockPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addorder);

        layout=findViewById(R.id.containerlay);
        spinner          = findViewById(R.id.nameET);
        customerName     = findViewById(R.id.customerName);
        customerContact  = findViewById(R.id.customerContact);
        quantity         = findViewById(R.id.quantityET);
        totalStock       = findViewById(R.id.totalStock);



        // int cal = Integer.parseInt(rate3)  *  Integer.parseInt(rate4);
        // String x = Integer.toString(cal);





        //linking Buttons
        add     =   findViewById(R.id.add);
        save    =   findViewById(R.id.save);

        DBHandler db = new DBHandler(this);

        //ArrayList<String> list = (ArrayList<String>) db.product_list();
        // List<String> list = (List<String>) db.product_list();
        //HashMap<Integer,String> hashMap = (HashMap<Integer, String>) db.product_list();
        List<String> overall_list =  db.getProduct_list(); //which contain both name and s rate.
        ArrayList<String>  nameList = new ArrayList<>();
        try {


            for (int i = 0; i <= overall_list.size(); i++) {
                if (i % 2 == 0) {
                    nameList.add(overall_list.get(i));
                }
            }
        }
        catch (Exception e)
        {
            //Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nameList);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //String spName = spinner.getId();
                //spinnerName = spinner.getSelectedItem().toString();
                spinnerName =   spinner.getSelectedItem().toString();

                int idName = (spinner.getSelectedItemPosition() * 2)+1; //sale rate from overall_list
                 salerate = overall_list.get(idName);
                    //// Remaining Code here




                //Toast.makeText(Addorder.this, "value is "+salerate, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        add.setOnClickListener(new View.OnClickListener()
        {
            private Object ArrayAdapter;
            @Override
            public void onClick(View v) {

                if(cl.contains(spinnerName))
                {
                    Toast.makeText(Addorder.this, "already exist", Toast.LENGTH_SHORT).show();
                }
                else {


                    if (customerName.getText().toString().equals("") || customerContact.getText().toString().equals("") || quantity.getText().toString().equals("")) {
                        Toast.makeText(Addorder.this, "invalid input", Toast.LENGTH_SHORT).show();
                    } else {
                        sRate = Integer.parseInt(salerate);
                        Qty   = Integer.parseInt(quantity.getText().toString().trim());
                        currentStockPrice = sRate * Qty;
                        totalStockPrice = totalStockPrice + currentStockPrice;


                        totalStock.setText(Integer.toString(totalStockPrice));
                        showlist(spinnerName, sRate, Qty, currentStockPrice, c);

                        cl.add(c);
                        cl.add(spinnerName);
                        cl.add(sRate);//sale rate from database
                        cl.add(Qty);//given quantity
                        cl.add(currentStockPrice);
                        customerName.setTag(String.valueOf(c));
                        c++;
                        //Toast.makeText(Addorder.this, "   " + cl.toString(), Toast.LENGTH_SHORT).show();
                       // Toast.makeText(Addorder.this, "Name is : "+customerName.getText().toString().trim(), Toast.LENGTH_SHORT).show();

                        quantity.setText("");
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               // Toast.makeText(Addorder.this, "cl="+cl.size()+"dl="+dl.size(), Toast.LENGTH_SHORT).show();
                try {
                    res         =   Integer.parseInt(totalStock.getText().toString());
                    orderTitle  =   customerName.getText().toString().trim();
                    orderContact  =   customerContact.getText().toString().trim();

                    String stockId   =    db.create_order(String.valueOf(res),orderTitle,orderContact);
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



                                String ProId = db.getProductID(cl.get(a + 1).toString());
                                Success_Toast(); //After click on save button it appears as a Toast.
                               // Toast.makeText(Addorder.this, "Record addeded successfully.", Toast.LENGTH_SHORT).show();
                                db.create_orderList(stockId, ProId, cl.get(a + 2).toString(), cl.get(a + 3).toString(), cl.get(a + 4).toString());
                                db.update_order(ProId, cl.get(a + 3).toString()); // Product id and qty passing.


                        }
                        a = a + 4;
                    }
                }
                catch (Exception e)
                {
                    //Toast.makeText(Addorder.this, ""+e, Toast.LENGTH_SHORT).show();
                }
                customerName.setText("");
                totalStock.setText("");
                customerContact.setText("");
                startActivity(new Intent(Addorder.this,Dashboard2.class));
                finish();

            }

        });







    }










    int serialNo = 0;

    public void showlist(String name1 , int sRate , int Qty , int Total,int tag)
    {
        View view = getLayoutInflater().inflate(R.layout.custom_listview_addpurchase_row, null);




        //Initializing views here...
        TextView name   = view.findViewById(R.id.customTV_name);
        TextView qty    = view.findViewById(R.id.customTV_qty);
        TextView rate   = view.findViewById(R.id.customTV_rate);
        TextView total  = view.findViewById(R.id.customTV_total);
        TextView TV_tag= view.findViewById(R.id.TV_tag);
        ImageView del   = view.findViewById(R.id.customImg_delete);
        serialNo++;

        if(TextUtils.isEmpty(rate.getText()))
        {
            Toast.makeText(Addorder.this, "rate is empty..", Toast.LENGTH_SHORT).show();
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
              //  Toast.makeText(Addorder.this, ""+dl, Toast.LENGTH_SHORT).show();
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
        rate.setText(String.valueOf(sRate));
        qty.setText(String.valueOf(Qty));
        TV_tag.setText(String.valueOf(tag));
        total.setText(String.valueOf(Total));




        layout.addView(view);
        layout.equals("");

    }



//    public void aletmsg(String str)
//    {
//        Toast.makeText(this,str+" deleted", Toast.LENGTH_SHORT).show();
//    }


    private void Success_Toast() {

        LayoutInflater inflater  = getLayoutInflater();
        View layout =   inflater.inflate(R.layout.toast_order,(ViewGroup) findViewById(R.id.OrderToast));

        Toast toast =new Toast(Addorder.this);
        toast.setView(layout);
        toast.setDuration(toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();

    }


}