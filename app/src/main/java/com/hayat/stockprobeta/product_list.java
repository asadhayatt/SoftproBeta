package com.hayat.stockprobeta;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class product_list {

    String productName,qty,rate,total;

    public void product_list(String productName, String rate, String qty, String total) {
        this.productName = productName;
        this.qty = qty;
        this.rate = rate;
        this.total = total;

        ArrayList<String> al = new ArrayList<String>();
        al.add(productName);
        al.add(rate);
        al.add(qty);
        al.add(total);

        ArrayList<ArrayList> al2 = new ArrayList<>();
        al2.add(al);

       // String[] strArray = (String[]) al2.toArray();

//        Log.d(TAG, "product_list: "+strArray);
                for (int i = 0; i < al.size(); i++)
        {

            Log.d(TAG, "product_list: " + al2.get(i));
            //Log.d(TAG, "product_list: " + al.get(i));
        }
//        Log.d(TAG, "log D end here... ");
    }




}
