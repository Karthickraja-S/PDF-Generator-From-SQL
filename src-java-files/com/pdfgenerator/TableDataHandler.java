package com.pdfgenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;


import java.util.List;

public class TableDataHandler {
    private static final Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
    public static Font black_text_small =new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    public static void addReportSummaryTable(Document document) {
        PdfPTable summary = new PdfPTable(1);
        PdfPTable heading = new PdfPTable(1);
        try {
            summary.setWidthPercentage(100);
            Phrase phrase = new Phrase("Report Generated On - "+HelperFunctions.getDateFormat());
            Phrase head = new Phrase("Report Summary",new Font(Font.FontFamily.HELVETICA, 13, Font.UNDERLINE));

            PdfPCell t = new PdfPCell(phrase);t.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);t.setBorder(0);
            summary.addCell(t);
            summary.setSpacingAfter(20F);

            PdfPCell t2 = new PdfPCell(head);t2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);t2.setBorder(0);
            heading.addCell(t2);
            heading.setSpacingAfter(25F);

            document.add(summary);document.add(heading);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void writeTableHeader(List columnValues, PdfPTable pTable){
        try{
            for (int w = 0; w < columnValues.size(); w++) {
                String cellVal = "";
                if(columnValues.get(w) != null)
                {
                    cellVal = columnValues.get(w).toString().toUpperCase();
                    System.out.println("v : "+cellVal);
                }
                PdfPCell cell = new PdfPCell(new Phrase(cellVal,headerFont));
                cell.setBackgroundColor(BaseColor.ORANGE);
                cell.setPadding(5);
                pTable.addCell(cell);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }
    }
    public static void writeTableData(List values , PdfPTable pTable) {
        int rowCount = 0;
        for(Object value : values) {
            for(Object dataInside : (List)value) {
                Phrase para =new Phrase(dataInside.toString(),black_text_small);
                PdfPCell cell =new PdfPCell(para);
                if (rowCount % 2 != 0)
                {
                    cell.setBackgroundColor(BaseColor.YELLOW);
                }
                cell.setBorder(Rectangle.BOX);
                cell.setBorderColor(BaseColor.BLACK);
                cell.setPadding(5);
                pTable.addCell(cell);
            }
            rowCount++;
        }
    }
}
