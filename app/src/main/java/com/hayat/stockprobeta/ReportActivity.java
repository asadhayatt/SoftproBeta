package com.hayat.stockprobeta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.os.Environment;
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

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.property.HorizontalAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Spinner spinner;
    TextView rTitle;
    EditText frDate,toDate;
    Button downloadBtn;
    String frdate, todate;
    String Title;   // Title pdf Report
    int check = 0;
    ArrayList<String> report_list;//It stores all cursor elements in DBHandler/get_report from sql.
    File file , pdfpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        //Initialize views with java file.

        frDate  =   findViewById(R.id.frDate);
        toDate  =   findViewById(R.id.toDate);
        downloadBtn=  findViewById(R.id.downloadBtn);
        spinner  =  findViewById(R.id.nameET);


        List<String>    list =  new ArrayList<>();
        list.add("Select an item ▼");
        list.add("products");
        list.add("orders");
        list.add("expenses");
        list.add("stocks");
        list.add("compiled");


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

                         Title = spinner.getSelectedItem().toString().trim();

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

        DBHandler db = new DBHandler(this);

       downloadBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

              report_list =   db.get_Report(Title);

               try {
                   createPDF(Title);
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               }
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


    private void createPDF(String fileName) throws FileNotFoundException {

        //String pdfPath  = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        pdfpath = new File(Environment.getExternalStorageDirectory(),"StockProBeta");
        file   =   new File(pdfpath,fileName+".pdf");

        if(!pdfpath.exists())
        {
            pdfpath.mkdir();


            if(!file.exists())
            {
                if (Title == "products")
                {
                    //To Do Code here

                    createPRODUCT();
                }
                else if (Title == "orders")
                {
                    //To Do Code here

                    createORDER();
                }
                else if (Title == "expenses")
                {
                    //To Do Code here

                    createEXPENSES();
                } 
                else if (Title == "stocks")
                {
                    //To Do Code here

                    createSTOCKS();
                }
                else if (Title == "compiled")
                {
                    //To Do Code here

                    createCOMPILED();
                }

            }

        }//end of IF StockProBeta Directory.
        if(Title == "products")
        {
            createPRODUCT();
        }
        else if(Title == "orders")
        {
            createORDER();
        }
        else if(Title == "expenses")
        {
            createEXPENSES();
        }
        else if(Title == "stocks")
        {
            createSTOCKS();
        } else if(Title == "compiled")
        {
            createCOMPILED();
        }






//
//        String path1    =   file.toString();
//        //OutputStream outputStream     =  new FileOutputStream(file);
//
//        if (Title == "products") {
//            //To Do Code here
//
//            PdfWriter pdfWriter = new PdfWriter(file);
//            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//
//            Document document = new Document(pdfDocument);
//
//
//            float columnwidth[] = {200f, 200f, 200f, 200f, 200f};
//
//            com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnwidth);
//
//            table.addCell("ID");
//            table.addCell("NAME");
//            table.addCell("SALE RATE");
//            table.addCell("STOCK");
//            table.addCell("DESCRIPTION");
//
//            for (int i = 0; i < report_list.size(); i++) {
//
//                table.addCell(report_list.get(i));
//                table.addCell(report_list.get(i + 1));
//                table.addCell(report_list.get(i + 2));
//                table.addCell(report_list.get(i + 3));
//                table.addCell(report_list.get(i + 4));
//
//                i = i + 4;
//
//            }
//
//            document.add(table);
//            document.close();
//            Toast.makeText(getApplicationContext(), path1+ " created.", Toast.LENGTH_SHORT).show();
//        }
//        else if (Title == "orders") {
//            //To Do Code here
//
//            PdfWriter pdfWriter = new PdfWriter(file);
//            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//
//            Document document = new Document(pdfDocument);
//
//
//            float columnwidth[] = {200f, 200f, 200f, 200f, 200f};
//
//            com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnwidth);
//
//            table.addCell("ID");
//            table.addCell("ORDER TITLE");
//            table.addCell("CONTACT#");
//            table.addCell("BILL AMOUNT");
//            table.addCell("DATE");
//
//            for (int i = 0; i < report_list.size(); i++) {
//
//                table.addCell(report_list.get(i));
//                table.addCell(report_list.get(i + 1));
//                table.addCell(report_list.get(i + 2));
//                table.addCell(report_list.get(i + 3));
//                table.addCell(report_list.get(i + 4));
//
//                i = i + 4;
//
//            }
//
//            document.add(table);
//            document.close();
//            Toast.makeText(getApplicationContext(), path1+ " created.", Toast.LENGTH_SHORT).show();
//        }
//        else if (Title == "expenses") {
//            //To Do Code here
//
//            PdfWriter pdfWriter = new PdfWriter(file);
//            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//
//            Document document = new Document(pdfDocument);
//
//
//            float columnwidth[] = {200f, 200f, 200f, 200f};
//
//            com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnwidth);
//
//            table.addCell("ID");
//            table.addCell("Expense Amount");
//            table.addCell("Description");
//            table.addCell("Date");
//
//            for (int i = 0; i < report_list.size(); i++) {
//
//                table.addCell(report_list.get(i));
//                table.addCell(report_list.get(i + 1));
//                table.addCell(report_list.get(i + 2));
//                table.addCell(report_list.get(i + 3));
//
//                i = i + 3;
//
//            }
//
//            document.add(table);
//            document.close();
//            Toast.makeText(getApplicationContext(), path1+ " created.", Toast.LENGTH_SHORT).show();
//        }
//        else if (Title == "stocks") {
//            //To Do Code here
//
//            PdfWriter pdfWriter = new PdfWriter(file);
//            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//
//            Document document = new Document(pdfDocument);
//
//
//            float columnwidth[] = {200f, 200f, 200f};
//
//            com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnwidth);
//
//            table.addCell("ID");
//            table.addCell("Bill Amount");
//            table.addCell("Date");
//
//            for (int i = 0; i < report_list.size(); i++) {
//
//                table.addCell(report_list.get(i));
//                table.addCell(report_list.get(i + 1));
//                table.addCell(report_list.get(i + 2));
//
//                i = i + 2;
//
//            }
//
//            document.add(table);
//            document.close();
//            Toast.makeText(getApplicationContext(), path1+ " created.", Toast.LENGTH_SHORT).show();
//        }
//        else if (Title == "compiled"){
//            //To Do Code here
//        }


//        Drawable    d        =   getDrawable(R.drawable.report);
//        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
//        ByteArrayOutputStream   stream  =   new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG   ,   100 ,   stream);
//        byte[]  bitmapData  =   stream.toByteArray();
//
//        ImageData imageData = ImageDataFactory.create(bitmapData);
//        Image   image   =   new Image(imageData);
//        image.setHeight(100);
//        image.setWidth(100);
////        image.setHorizontalAlignment(HorizontalAlignment.CENTER);
//
//        document.add(image);


    }

    private void createPRODUCT() {

        PdfWriter pdfWriter = null;
        try {
            pdfWriter = new PdfWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Document document = new Document(pdfDocument);


        float columnwidth[] = {200f, 200f, 200f, 200f, 200f};

        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnwidth);

        table.addCell("ID");
        table.addCell("NAME");
        table.addCell("SALE RATE");
        table.addCell("STOCK");
        table.addCell("DESCRIPTION");

        for (int i = 0; i < report_list.size(); i++) {

            table.addCell(report_list.get(i));
            table.addCell(report_list.get(i + 1));
            table.addCell(report_list.get(i + 2));
            table.addCell(report_list.get(i + 3));
            table.addCell(report_list.get(i + 4));

            i = i + 4;

        }

        document.add(table);
        document.close();
        Toast.makeText(getApplicationContext(), file.toString()+ " created.", Toast.LENGTH_SHORT).show();


    }

    private void createORDER() {
        PdfWriter pdfWriter = null;
        try {
            pdfWriter = new PdfWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Document document = new Document(pdfDocument);


        float columnwidth[] = {200f, 200f, 200f, 200f, 200f};

        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnwidth);

        table.addCell("ID");
        table.addCell("ORDER TITLE");
        table.addCell("CONTACT#");
        table.addCell("BILL AMOUNT");
        table.addCell("DATE");

        for (int i = 0; i < report_list.size(); i++) {

            table.addCell(report_list.get(i));
            table.addCell(report_list.get(i + 1));
            table.addCell(report_list.get(i + 2));
            table.addCell(report_list.get(i + 3));
            table.addCell(report_list.get(i + 4));

            i = i + 4;

        }

        document.add(table);
        document.close();
        Toast.makeText(getApplicationContext(), file.toString()+ " created.", Toast.LENGTH_SHORT).show();
    }

    private void createEXPENSES() {

        PdfWriter pdfWriter = null;
        try {
            pdfWriter = new PdfWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Document document = new Document(pdfDocument);


        float columnwidth[] = {200f, 200f, 200f, 200f};

        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnwidth);

        table.addCell("ID");
        table.addCell("Expense Amount");
        table.addCell("Description");
        table.addCell("Date");

        for (int i = 0; i < report_list.size(); i++) {

            table.addCell(report_list.get(i));
            table.addCell(report_list.get(i + 1));
            table.addCell(report_list.get(i + 2));
            table.addCell(report_list.get(i + 3));

            i = i + 3;

        }

        document.add(table);
        document.close();
        Toast.makeText(getApplicationContext(), file.toString()+ " created.", Toast.LENGTH_SHORT).show();
        
    }


    private void createSTOCKS() {

        PdfWriter pdfWriter = null;
        try {
            pdfWriter = new PdfWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Document document = new Document(pdfDocument);


        float columnwidth[] = {200f, 200f, 200f};

        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnwidth);

        table.addCell("ID");
        table.addCell("Bill Amount");
        table.addCell("Date");

        for (int i = 0; i < report_list.size(); i++) {

            table.addCell(report_list.get(i));
            table.addCell(report_list.get(i + 1));
            table.addCell(report_list.get(i + 2));

            i = i + 2;

        }

        document.add(table);
        document.close();
        Toast.makeText(getApplicationContext(), file.toString()+ " created.", Toast.LENGTH_SHORT).show();
        
    }

    private void createCOMPILED() {

        Toast.makeText(this, "In the Compiled", Toast.LENGTH_SHORT).show();
        
    }
}