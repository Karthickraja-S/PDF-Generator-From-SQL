package com.pdfgenerator;

import com.database.DBUtil;
import com.entity.HeaderData;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class HeaderDataHandler {
    public static void addHeader(Document document) {
        PdfPTable header = new PdfPTable(2);
        try {
            HeaderData data = DBUtil.getHeaderData();
            // set defaults
            header.setWidths(new int[]{4, 24});
            header.setTotalWidth(document.getPageSize().getWidth()-50);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(45);
            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
            header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // add logo image
            Image logo = Image.getInstance(data.getLogo());
            header.addCell(logo);

            // add text
            PdfPCell text = new PdfPCell();
            text.setPaddingBottom(15);
            text.setPaddingLeft(10);
            text.setBorder(Rectangle.BOTTOM);
            text.setBorderColor(BaseColor.LIGHT_GRAY);
            text.addElement(new Phrase(Element.ALIGN_CENTER, data.getTitle(), new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD)));
            text.addElement(new Phrase(data.getAddress(), new Font(Font.FontFamily.HELVETICA, 15)));
            StringBuilder sb  = new StringBuilder()
                    .append("Contact No : ")
                    .append(data.getContactNo())
                    .append(" , ")
                    .append(" Email ID : ")
                    .append(data.getMailID());
            text.addElement(new Phrase(sb.toString(), new Font(Font.FontFamily.HELVETICA, 15)));
            header.addCell(text);

            header.setSpacingAfter(20F);
            // write content
            document.add(header);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addCopyRightFooter(Document document) {
        PdfPTable footer = new PdfPTable(1);
        try {
            footer.setWidthPercentage(100);
            Phrase copyright = new Phrase("\u00A9 All Rights Reserved.",new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL));
            PdfPCell t = new PdfPCell(copyright);t.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);t.setBorder(0);
            footer.addCell(t);
            footer.setSpacingBefore(20F);
            document.add(footer);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
//    public static void addFooter(PdfWriter writer,Document document) {
//        PdfPTable table = new PdfPTable(3);
//        try {
//            table.setWidths(new int[]{24, 2, 1});
//            table.setTotalWidth(527);
//            table.setLockedWidth(true);
//            table.getDefaultCell().setFixedHeight(20);
//            table.getDefaultCell().setBorder(Rectangle.TOP);
//            table.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
//
//            table.addCell(new Phrase("\u00A9 WWW.WEBSITE.COM", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
//
//            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//            table.addCell(new Phrase(String.format("Page : %d", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));
//
//            float tableWidth = table.getTotalWidth();
//            float xPosition = (document.right() - document.left() - tableWidth) / 2;
//            float yPosition = document.bottomMargin() - table.getDefaultCell().getFixedHeight();
//
//            // write page
//            PdfContentByte canvas = writer.getDirectContent();
//            table.writeSelectedRows(0, -1, 100, 100, canvas);
//
//        }
//        catch(DocumentException de) {
//            throw new ExceptionConverter(de);
//        }
//     }
}
