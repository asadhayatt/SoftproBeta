package com.hayat.stockprobeta;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DBHandler  extends SQLiteOpenHelper {
    private static final String DB_NAME = "softpro_sb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String ACCOUNT_TABLE = "account";
    private static final String PRODUCT_TABLE = "products";
    private static final String STOCK_TABLE = "stocks";
    private static final String STOCKLIST_TABLE = "stocklist";
    private static final String ORDER_TABLE = "orders";
    private static final String ORDERLIST_TABLE = "orderlist";
    private static final String EXPENSE_TABLE = "expenses";
    private static final String EXPENSELIST_TABLE = "expenslist";
    // common varibale for field
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String DATE_COL = "date";
    // account table
    private static final String ORG_COL = "org";
    private static final String STATUS_COL = "status";
    // product table

    private static final String SRATE_COL = "srate";
    private static final String STOCK_COL = "stock";
    private static final String DES_COL = "des";
    // stock table
    private static final String BILL_COL = "billno";
    private static final String BILLDATE_COL = "billdate";
    private static final String BILLAMOUNT_COL = "billamount";
    // stocklist table
    private static final String STOCKID_COL = "stockid";
    private static final String PRODUCTID_COL = "productid";
    private static final String QTY_COL = "qty";
    private static final String PRATE_COL = "prate";
    private static final String TOTAL_COL = "total";
    // order table
    private static final String ORDER_COL = "ordertitle";
    private static final String ORDERID_COL = "orderid";
    private static final String ORDERCONTACT_COL = "contact";

    // Expenses table
    private static final String ExpenseAMOUNT_COL = "billamount";
    private static final String ExpenseDesc_COL = "des";
    private static final String ExpenseDate_COL = "ExpDate";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String query = "";
        query = "CREATE TABLE " + ACCOUNT_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + ORG_COL + " TEXT,"
                + DATE_COL + " TEXT,"
                + STATUS_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);

        query = "CREATE TABLE " + PRODUCT_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + SRATE_COL + " TEXT,"
                + STOCK_COL + " TEXT,"
                + DES_COL + " TEXT)";
        db.execSQL(query);

        query = "CREATE TABLE " + STOCK_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BILL_COL + " TEXT,"
                + BILLDATE_COL + " TEXT,"
                + BILLAMOUNT_COL + " TEXT,"
                + DATE_COL + " TEXT)";
        db.execSQL(query);

        query = "CREATE TABLE " + STOCKLIST_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STOCKID_COL + " TEXT,"
                + PRODUCTID_COL + " TEXT,"
                + PRATE_COL + " TEXT,"
                + QTY_COL + " TEXT,"
                + TOTAL_COL + " TEXT)";
        db.execSQL(query);

        query = "CREATE TABLE " + ORDER_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_COL + " TEXT,"
                + ORDERCONTACT_COL + " TEXT,"
                + BILLAMOUNT_COL + " TEXT,"
                + DATE_COL + " TEXT)";
        db.execSQL(query);

        query = "CREATE TABLE " + ORDERLIST_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDERID_COL + " TEXT,"
                + PRODUCTID_COL + " TEXT,"
                + SRATE_COL + " TEXT,"
                + QTY_COL + " TEXT,"
                + TOTAL_COL + " TEXT)";
        db.execSQL(query);

        query = "CREATE TABLE " + EXPENSE_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ExpenseAMOUNT_COL + " TEXT,"
                + ExpenseDate_COL + " TEXT,"
                + ExpenseDesc_COL + " TEXT,"
                + DATE_COL + " TEXT)";
        db.execSQL(query);


    }

    public ArrayList<String> getData() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + ACCOUNT_TABLE, null);
        ((Cursor) res).moveToFirst();

        while (((Cursor) res).isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(NAME_COL)));
            array_list.add(res.getString(res.getColumnIndex(ORG_COL)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getStocks() {
        ArrayList<String> array_list = new ArrayList<String>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + STOCKLIST_TABLE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            array_list.add(res.getString(res.getColumnIndex(PRODUCTID_COL)));
            array_list.add(res.getString(res.getColumnIndex(PRATE_COL)));
            array_list.add(res.getString(res.getColumnIndex(QTY_COL)));
            array_list.add(res.getString(res.getColumnIndex(TOTAL_COL)));
            array_list.add(res.getString(res.getColumnIndex(ID_COL)));

            res.moveToNext();
        }
        return array_list;

    }

    public ArrayList<String> getproducts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + PRODUCT_TABLE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            array_list.add(res.getString(res.getColumnIndex(NAME_COL)));
            array_list.add(res.getString(res.getColumnIndex(SRATE_COL)));
            array_list.add(res.getString(res.getColumnIndex(STOCK_COL)));
            array_list.add(res.getString(res.getColumnIndex(DES_COL)));
            array_list.add(res.getString(res.getColumnIndex(ID_COL)));
            res.moveToNext();
        }
        return array_list;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSELIST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ORDERLIST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STOCK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STOCKLIST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE);
        onCreate(db);

    }

    public void create(String name, String date, String org, String status) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, name);
        values.put(DATE_COL, date);
        values.put(STATUS_COL, status);
        values.put(ORG_COL, org);

        // after adding all values we are passing
        // content values to our table.
        db.insert(ACCOUNT_TABLE, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }
    ////////////////////////////////////////////////////////////////////////

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss a", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public String getProductID(String name) {


        SQLiteDatabase db = this.getReadableDatabase();
        String id = "0";
        Cursor res = db.rawQuery("select id from products where " + NAME_COL + "='" + name + "'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            id = res.getString(res.getColumnIndex("id"));
            res.moveToNext();
        }
        return id;

    }

    public String getpro_stock(String pid) {


        SQLiteDatabase db = this.getReadableDatabase();
        String stock = "0";
        Cursor res = db.rawQuery("select stock from products where id='" + pid + "'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            stock = res.getString(res.getColumnIndex("stock"));
            res.moveToNext();
        }
        return stock;

    }


    public String getOrderID() {


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select id from orders ORDER BY id   DESC LIMIT 1", null);
        String id = "0";
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            id = res.getString(res.getColumnIndex("id"));
            res.moveToNext();
        }
        return id;

    }

    public String getStockID() {


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select id from stocks ORDER BY id   DESC LIMIT 1", null);
        String id = "0";
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            id = res.getString(res.getColumnIndex("id"));
            res.moveToNext();
        }
        return id;

    }


    public String create_stock(String total) {


        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BILLAMOUNT_COL, total);
        values.put(DATE_COL, getDate());

        // Cursor getTotalId = db.rawQuery("select id from STOCK_TABLE where BILLAMOUNT_COL = '"+total+"'",null);

        // after adding all values we are passing
        // content values to our table.
        db.insert(STOCK_TABLE, null, values);
        // at last we are closing our
        // database after adding database.
        db.close();
        String id = getStockID();
        // return getTotalId.getColumnIndex("id");
        return id;
    }

    public String create_order(String total, String orderTitle, String contact) {


        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(BILLAMOUNT_COL, total);
        values.put(DATE_COL, getDate());
        values.put(ORDER_COL, orderTitle);
        values.put(ORDERCONTACT_COL, contact);

        // Cursor getTotalId = db.rawQuery("select id from STOCK_TABLE where BILLAMOUNT_COL = '"+total+"'",null);

        // after adding all values we are passing
        // content values to our table.
        db.insert(ORDER_TABLE, null, values);
        // at last we are closing our
        // database after adding database.
        db.close();
        String id = getOrderID();
        // return getTotalId.getColumnIndex("id");
        return id;
    }

    ////////////////////////////////////////////////////////////////////////

    public void update_order(String pid, String qty) {
        String s = getpro_stock(pid);
        String t = String.valueOf(Integer.parseInt(s) - Integer.parseInt(qty));
        SQLiteDatabase db = this.getReadableDatabase();

        String strSQL = "UPDATE products SET stock='" + t + "' WHERE id='" + pid + "'";
        db.execSQL(strSQL);


    }

    public void update_product(String pid, String qty) {
        String s = getpro_stock(pid);
        String t = String.valueOf(Integer.parseInt(s) + Integer.parseInt(qty));
        SQLiteDatabase db = this.getReadableDatabase();

        String strSQL = "UPDATE products SET stock='" + t + "' WHERE id='" + pid + "'";
        db.execSQL(strSQL);


    }


    public void create_orderList(String orderid, String orderName, String rate, String qty, String total) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(ORDERID_COL, orderid);
        values.put(PRODUCTID_COL, orderName);
        values.put(SRATE_COL, rate);
        values.put(QTY_COL, qty);
        values.put(TOTAL_COL, total);

        // after adding all values we are passing
        // content values to our table.
        db.insert(ORDERLIST_TABLE, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void create_stockList(String stockid, String stockName, String rate, String qty, String total) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(STOCKID_COL, stockid);
        values.put(PRODUCTID_COL, stockName);
        values.put(PRATE_COL, rate);
        values.put(QTY_COL, qty);
        values.put(TOTAL_COL, total);

        // after adding all values we are passing
        // content values to our table.
        db.insert(STOCKLIST_TABLE, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void createproduct(String name, String salerate, String stock, String des) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, name);
        values.put(SRATE_COL, salerate);
        values.put(STOCK_COL, stock);
        values.put(DES_COL, des);

        // after adding all values we are passing
        // content values to our table.

        db.insert(PRODUCT_TABLE, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public String countStock() {
        String count = "";


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select count(*) AS pro from " + STOCKLIST_TABLE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            count = res.getString(res.getColumnIndex("pro"));
            res.moveToNext();
        }
        return count;

    }

    public String countproduct() {
        String count = "";


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select count(*) AS pro from " + PRODUCT_TABLE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            count = res.getString(res.getColumnIndex("pro"));
            res.moveToNext();
        }
        return count;

    }

    public String countExpense() {
        String count = "";


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select count(*) AS pro from " + EXPENSE_TABLE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            count = res.getString(res.getColumnIndex("pro"));
            res.moveToNext();
        }
        return count;

    }


    ///////////////////////////////////////////////////////////////////
//Will change its parameters.
    public String searchByStockId(String product) {
        String count = "";


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select count(*) AS pro from " + PRODUCT_TABLE + " where " + NAME_COL + "='" + product + "'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            count = res.getString(res.getColumnIndex("pro"));
            res.moveToNext();
        }
        return count;

    }

    ///////////////////////////////////////////////////////////////////
    public String searchproduct(String product) {
        String count = "";


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select count(*) AS pro from " + PRODUCT_TABLE + " where " + NAME_COL + "='" + product + "'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            count = res.getString(res.getColumnIndex("pro"));
            res.moveToNext();
        }
        return count;

    }

    public void productlist() {
        SQLiteDatabase db = this.getReadableDatabase();
    }

    public void delproduct(String id) {

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + PRODUCT_TABLE + " WHERE " + ID_COL + "= '" + id + "'");
        db.close();


    }


    public void delstocks(String id) {

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + PRODUCT_TABLE + " WHERE " + ID_COL + "= '" + id + "'");
        db.close();


    }

    @SuppressLint("Range")
    public final List<String> getProduct_list() {
        ArrayList<String> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PRODUCT_TABLE, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            list.add(cursor.getString(cursor.getColumnIndex(NAME_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(SRATE_COL)));
            cursor.moveToNext();

        }
        return list;
    }

    public ArrayList<String> Vieworder_list(String orderID) {
        ArrayList<String> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ORDERLIST_TABLE + " WHERE " + ORDERID_COL + "='" + orderID + "'", null);
        cursor.moveToFirst();


        while (cursor.isAfterLast() == false) {
            list.add(cursor.getString(cursor.getColumnIndex(PRODUCTID_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(SRATE_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(QTY_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(TOTAL_COL)));
            cursor.moveToNext();

        }
        return list;
    }

    public ArrayList<String> Viewstock_list(String stockId) {
        ArrayList<String> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + STOCKLIST_TABLE + " WHERE " + STOCKID_COL + "='" + stockId + "'", null);
        cursor.moveToFirst();


        while (cursor.isAfterLast() == false) {
            list.add(cursor.getString(cursor.getColumnIndex(PRODUCTID_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(PRATE_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(QTY_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(TOTAL_COL)));
            cursor.moveToNext();

        }
        return list;
    }

    public ArrayList<String> getorder_list(String date) {
        ArrayList<String> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ORDER_TABLE + " WHERE " + DATE_COL + " like '%" + date + "%'", null);
        cursor.moveToFirst();


        while (cursor.isAfterLast() == false) {
            list.add(cursor.getString(cursor.getColumnIndex(ID_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(ORDER_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(ORDERCONTACT_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(BILLAMOUNT_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(DATE_COL)));
            cursor.moveToNext();

        }
        return list;
    }

    public ArrayList<String> getExpense_list(String date) {
        ArrayList<String> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + EXPENSE_TABLE + " WHERE " + ExpenseDate_COL + " like '%" + date + "%'", null);
        cursor.moveToFirst();


        while (cursor.isAfterLast() == false) {
            list.add(cursor.getString(cursor.getColumnIndex(ID_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(ExpenseAMOUNT_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(ExpenseDate_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(ExpenseDesc_COL)));
            cursor.moveToNext();

        }
        return list;
    }

    public ArrayList<String> getstock_list(String date) {
        ArrayList<String> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + STOCK_TABLE + " WHERE " + DATE_COL + " like '%" + date + "%'", null);
        cursor.moveToFirst();


        while (cursor.isAfterLast() == false) {
            list.add(cursor.getString(cursor.getColumnIndex(ID_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(BILLAMOUNT_COL)));
            list.add(cursor.getString(cursor.getColumnIndex(DATE_COL)));
            cursor.moveToNext();

        }
        return list;
    }


    @SuppressLint("Range")
    public final List<String> product_list() {
        Map<Integer, String> hashMap = new HashMap<>();
        ArrayList<String> list = new ArrayList<String>();
        //ArrayList<String> idList = new ArrayList<String>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PRODUCT_TABLE, null);

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {


            list.add(cursor.getString(cursor.getColumnIndex(NAME_COL)));
            //list.add(cursor.getString(cursor.getColumnIndex(ID_COL)));
            cursor.moveToNext();

        }
        return list;

    }


    public void create_EXPENSE(String Amount, String ExDate, String ExDes) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(ExpenseAMOUNT_COL, Amount);
        values.put(ExpenseDate_COL, ExDate);
        values.put(ExpenseDesc_COL, ExDes);
        values.put(DATE_COL, getDateTime());

        // after adding all values we are passing
        // content values to our table.

        db.insert(EXPENSE_TABLE, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    @SuppressLint("Range")
    public String getProduct_Name(String s) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PRODUCT_TABLE + " WHERE " + ID_COL + "='" + s + "'", null);
        cursor.moveToFirst();


        while (cursor.isAfterLast() == false) {
            s = (cursor.getString(cursor.getColumnIndex(NAME_COL)));

            cursor.moveToNext();

        }

        return s;
    }

    public ArrayList<String> get_Report(String category) {

        if (category == "products") {
            //To Do Code here

            ArrayList<String> list = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + PRODUCT_TABLE, null);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                list.add(cursor.getString(cursor.getColumnIndex(ID_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(NAME_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(SRATE_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(STOCK_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(DES_COL)));
                cursor.moveToNext();

            }


            return list;
        }
        else if (category == "orders") {
            //To Do Code here


            ArrayList<String> list = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + ORDER_TABLE, null);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                list.add(cursor.getString(cursor.getColumnIndex(ID_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(ORDER_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(ORDERCONTACT_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(BILLAMOUNT_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(DATE_COL)));
                cursor.moveToNext();

            }
            return list;
        }
        else if (category == "expenses") {
            //To Do Code here
            ArrayList<String> list = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + EXPENSE_TABLE, null);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                list.add(cursor.getString(cursor.getColumnIndex(ID_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(BILLAMOUNT_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(ExpenseDesc_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(ExpenseDate_COL)));
                cursor.moveToNext();
            }
            return list;
        }
        else if (category == "stocks") {
            //To Do Code here

            ArrayList<String> list = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + STOCK_TABLE, null);
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                list.add(cursor.getString(cursor.getColumnIndex(ID_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(BILLAMOUNT_COL)));
                list.add(cursor.getString(cursor.getColumnIndex(DATE_COL)));
                cursor.moveToNext();
            }

            return list;
        }
        else if (category == "compiled") {
            //To Do Code here
            return null;
        }
        else {
            return null;
        }
    }
}
