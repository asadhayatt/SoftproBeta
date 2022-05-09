package com.hayat.stockprobeta;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
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
    TextView idtv,remstk;
    String procount,product="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        dbhandler= new DBHandler(this);
        layout=findViewById(R.id.containerlay);
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
                Button btn=view.findViewById(R.id.btntest);


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
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idtv= view.findViewById(R.id.TV_id_data);
                        remstk= view.findViewById(R.id.TV_udt_data);

                        String id=idtv.getText().toString();
                        String st=remstk.getText().toString();
                        int sti=Integer.parseInt(st);
                        if(sti>0)
                        {
                            alert("Stock is availble cannot delete.");
                        }
                        else
                        {
                            dbhandler.delproduct(id);
                            alert("product deleted from list...");
                            loadproduct();
                        }




                        //layout.removeView(view);
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
}