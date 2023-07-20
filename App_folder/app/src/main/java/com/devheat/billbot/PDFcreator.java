package com.devheat.billbot;

import android.graphics.fonts.FontFamily;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PDFcreator {

    public static void createpdf()throws FileNotFoundException {

        String pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();         //Setting the path for the pdf
        File file = new File(pdfpath,User.GSTIN+User.invoice_num+".pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);                                                                             //Creating a writer object
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        float column_width[] = {40f,40f,40f,40f,40f,40f,40f,40f,40f,40f,40f,40f};                                            //adding 12 columns with same widths

        Table table = new Table(column_width);                                                                               //creating table


        table.addCell(new Cell(1,12).add(new Paragraph(new Text("Tax Invoice").setBold()).setTextAlignment(TextAlignment.CENTER)));

        table.addCell(new Cell(1,12).add(new Paragraph(new Text(User.name).setBold().setFontSize(32)).setTextAlignment(TextAlignment.CENTER)));

        table.addCell(new Cell(1,12).add(new Paragraph(new Text(User.address).setFontSize(20)).setTextAlignment(TextAlignment.CENTER)));

        table.addCell(new Cell(1,6).add(new Paragraph("Call at :"+User.phone).setTextAlignment(TextAlignment.RIGHT)));
        table.addCell(new Cell(1,6).add(new Paragraph("Mail at :"+User.email)));

        table.addCell(new Cell(2,4).add(new Paragraph("GSTIN No."+User.GSTIN)).setVerticalAlignment(VerticalAlignment.MIDDLE));
        table.addCell(new Cell(2,3).add(new Paragraph("HSN Code : 996331")).setVerticalAlignment(VerticalAlignment.MIDDLE));
        table.addCell(new Cell(1,2).add(new Paragraph("Invoice no.")));
        table.addCell(new Cell(1,3).add(new Paragraph("Dated")));

        table.addCell(new Cell(1,2).add(new Paragraph("INVN"+User.invoice_num)));
        table.addCell(new Cell(1,3).add(new Paragraph(new Text(Bill.date).setFontSize(11))).setVerticalAlignment(VerticalAlignment.MIDDLE));

        table.addCell(new Cell(1,1).add(new Paragraph("Sr. no.")));
        table.addCell(new Cell(1,4).add(new Paragraph("Item Name")));
        table.addCell(new Cell(1,2).add(new Paragraph("Price (Rs)")));
        table.addCell(new Cell(1,1).add(new Paragraph("Qty")));
        table.addCell(new Cell(1,2).add(new Paragraph("Units")));
        table.addCell(new Cell(1,2).add(new Paragraph("Amount (Rs)")));

        for(int i = 0; i < Bill.count; i++){
            table.addCell(new Cell(1,1).add(new Paragraph(Integer.toString(i+1))));
            table.addCell(new Cell(1,4).add(new Paragraph(Bill.data[i][0])));
            table.addCell(new Cell(1,2).add(new Paragraph(Bill.data[i][1])));
            table.addCell(new Cell(1,1).add(new Paragraph(Bill.data[i][2])));
            table.addCell(new Cell(1,2).add(new Paragraph("Servings")));
            table.addCell(new Cell(1,2).add(new Paragraph(Bill.data[i][3])));
        }

        table.addCell(new Cell(1,10).add(new Paragraph("Sub-total")));
        table.addCell(new Cell(1,2).add(new Paragraph(Bill.totol_amount)));

        double tax = Integer.parseInt(Bill.totol_amount)*0.05;

        table.addCell(new Cell(1,10).add(new Paragraph("GST @5% amount")));
        table.addCell(new Cell(1,2).add(new Paragraph(Double.toString(tax))));

        double amount_payable = Integer.parseInt(Bill.totol_amount) +tax;

        table.addCell(new Cell(1,10).add(new Paragraph(new Text("AMOUNT PAYABLE").setBold())));
        table.addCell(new Cell(1,2).add(new Paragraph(Double.toString(amount_payable))));

        document.add(table);
        int a = Integer.parseInt(Bill.split);
        if(a>1){
            Paragraph p = new Paragraph(new Text("After the split Each person is due Rs"+Double.toString(amount_payable/a)));           //Adding split statement if necessary
            document.add(p);
        }

        document.close();                                                                                                       //closing the file

    }
}
