package com.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdfgenerator.HeaderDataHandler;
import com.pdfgenerator.SizeHandler;
import com.pdfgenerator.TableDataHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PDFGenerationService {
    public void writeQueryResultToPDF(String fileName , Object dataToWrite , HttpServletResponse response)throws Exception {

        Document document = null;
        PdfWriter writer = null;

        System.out.println("Data to write => "+dataToWrite);


        JSONObject object = new JSONObject(dataToWrite.toString());

        List<Object> columnName = ((JSONArray) object.get("columnnames")).toList();
        List<List<Object>> values = new ArrayList<>();
        JSONArray array = ((JSONArray) object.get("values"));
        for(Object arr : array) {
            if(arr instanceof JSONArray) {
                values.add(((JSONArray)arr).toList());
            }
        }
        response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
        response.setHeader("X-FileName",fileName);

        float[] columnWidth = new float[columnName.size()];
        SizeHandler.setSectionWidth(columnWidth,columnName.size());
        System.out.println(Arrays.toString(columnWidth));

        document = SizeHandler.getPageSize(columnName.size());
        writer = PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        HeaderDataHandler.addHeader(document);
        TableDataHandler.addReportSummaryTable(document);

        PdfPTable tableData = new PdfPTable(columnName.size());
        tableData.setWidths(columnWidth);
        tableData.setTotalWidth(document.getPageSize().getWidth()-50);
        tableData.setLockedWidth(true);

        TableDataHandler.writeTableHeader(columnName,tableData);
        TableDataHandler.writeTableData(values,tableData);

        document.add(tableData);

        HeaderDataHandler.addCopyRightFooter(document);

        document.close();

    }
}
