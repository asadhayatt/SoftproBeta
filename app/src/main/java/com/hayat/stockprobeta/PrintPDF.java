package com.hayat.stockprobeta;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class PrintPDF {

    String Date , Name , Total , PaidAmount , DueAmount;

    SimpleDateFormat dateFormat =   new SimpleDateFormat();

    public PrintPDF(String date, String name, String total) {

        Date = date;
        Name = name;
        Total = total;
//        PaidAmount = paidAmount;
//        DueAmount = dueAmount;


    }

    public void getPDF() throws FileNotFoundException
    {
        //String pdfPath  = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
       // File file   =   new File(pdfPath , "StockReport.pdf");
        File file = new File(Environment.getExternalStorageDirectory(), "StockReport.pdf");

        OutputStream outputStream   =   new FileOutputStream(file);

        PdfWriter writer            =   new PdfWriter(outputStream);

        PdfDocument pdfDocument     =   new PdfDocument(writer);

        Document document           =   new Document(pdfDocument);

        float columnWidth1[]    =   {120,220,120,100};

        Table   table   =   new Table(columnWidth1);

        table.addCell(new Cell().add(new Paragraph("Date").setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(dateFormat.format(Date)).setFontSize(14)).setBorder(Border.NO_BORDER));

        table.addCell(new Cell().add(new Paragraph("Name").setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(Name+"").setFontSize(14)).setBorder(Border.NO_BORDER));

        table.addCell(new Cell().add(new Paragraph("Total").setFontSize(14)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(Total+"").setFontSize(14)).setBorder(Border.NO_BORDER));


        document.add(table);
        document.close();

    }
}
