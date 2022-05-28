package com.hayat.stockprobeta;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class ProductlistActivity extends AppCompatActivity {

    LinearLayout layout;
    Button prbtn;
    DBHandler dbhandler;
    TextView idtv,remstk , nameTV , saleTV, stockTV , desTV;
    String procount;

    String product="",sr="",stk="",des="",search="";
    String STid , STname , STsale , STstock , STdes ; //id through which we edit or delete the data in sqllite database.
    EditText prname,stock,srate,desc;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        dbhandler= new DBHandler(this);
        layout=findViewById(R.id.containerlay);
        builddialog();
        loadproduct();
    }
    public void loadproduct()
    {
        layout.removeAllViews();


        try {
            procount = dbhandler.countproduct();
            ArrayList<String> products = dbhandler.getproducts();
            int c= Integer.parseInt(procount);
            int min=0;

            for(int i=1; i<=c; i++) {
                final View view = getLayoutInflater().inflate(R.layout.product, null);
                TextView idview =view.findViewById(R.id.TV_id_data);
                TextView lkview =view.findViewById(R.id.TV_lk);
                TextView bcview=view.findViewById(R.id.TV_bc);
                TextView dtview=view.findViewById(R.id.TV_udt_data);
                TextView stview=view.findViewById(R.id.TV_status);
                Button deleteBTN=view.findViewById(R.id.deleteBTN);
                Button editBTN=view.findViewById(R.id.editBTN);


                lkview.setText("Name : "+products.get(min));
                min++;
                bcview.setText("Sale Rate : "+products.get(min));
                min++;
                dtview.setText(products.get(min));
                min++;
                stview.setText("Description : "+products.get(min));
                min++;
                idview.setText(products.get(min));
                min++;

                deleteBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idtv= view.findViewById(R.id.TV_id_data);


                        remstk= view.findViewById(R.id.TV_udt_data);

                        STid=idtv.getText().toString();



                        String st=remstk.getText().toString();
                        int sti=Integer.parseInt(st);
                        if(sti>0)
                        {
                            alert("Stock is availble cannot delete.");

                        }
                        else
                        {
                            dbhandler.delproduct(STid);
                            alert("product deleted from list...");
                            loadproduct();
                        }




                        //layout.removeView(view);
                    }
                });

                editBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idtv= view.findViewById(R.id.TV_id_data);
                        nameTV= view.findViewById(R.id.TV_lk);
                        saleTV= view.findViewById(R.id.TV_bc);
                        stockTV= view.findViewById(R.id.TV_udt_data);
                        desTV= view.findViewById(R.id.TV_status);


//                        STid=idtv.getText().toString();
//                        STname=nameTV.getText().toString();
//                        STsale=saleTV.getText().toString();
//                        STstock=stockTV.getText().toString();
//                        STdes=desTV.getText().toString();
//
//                        prname.setText(STname);
//                        stock.setText(STstock);
//                        srate.setText(STsale);
//                        desc.setText(STdes);

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
                Dashboard2.productlist.setText(procount);
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

    private void builddialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.addproduct,null);

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
                    emptyToast();

                } else if (TextUtils.isEmpty(srate.getText())) {
                    srate.setError("Enter Value");
                    srate.requestFocus();
                    emptyToast();

                }
                else if(TextUtils.isEmpty(stock.getText())){
                    stock.setError("Enter Value");
                    stock.requestFocus();
                    emptyToast();

                }
                else if(TextUtils.isEmpty(desc.getText())){
                    desc.setError("Enter Value");
                    desc.requestFocus();
                    emptyToast();

                }
                else {
                    product = prname.getText().toString().trim();
                    sr = srate.getText().toString().trim();
                    stk = stock.getText().toString().trim();
                    des = desc.getText().toString().trim();


                    //update Product Code
                    dbhandler.updateProduct(product, sr, stk, des, STid);
                    prname.setText("");
                    srate.setText("");
                    stock.setText("");
                    desc.setText("");
                    loadproduct();
                    Toast.makeText(ProductlistActivity.this, "Product Code: "+STid+" edited", Toast.LENGTH_SHORT).show();
                }




            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog=builder.create();
        loadproduct();

    }

    private void emptyToast() {
        Toast.makeText(ProductlistActivity.this,"Fields are empty..!", Toast.LENGTH_SHORT).show();

    }

}