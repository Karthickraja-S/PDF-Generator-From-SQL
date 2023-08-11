package com.pdfgenerator;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;

public class SizeHandler {
    public static void setSectionWidth(float[] columnWidths, int tableSize) {
        float width =  100.0F / (float) tableSize;
        for (int cs = 0; cs < tableSize; cs++) {
            columnWidths[cs] = width;
        }
    }
    public static Document getPageSize(long colCount) {
        if (colCount > 50) {
            return new Document(PageSize.A0);
        } else if (colCount > 30) {
            return new Document(PageSize.A1);
        }else if (colCount > 20) {
            return new Document(PageSize.A2);
        }else if (colCount > 10) {
            return new Document(PageSize.A3);
        } else {
            return new Document(PageSize.A4);
        }
    }
}
