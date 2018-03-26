package TDIMCO.datawriter;

import TDIMCO.domain.DayRouteData;
import TDIMCO.domain.Route;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Thomas on 19-3-2018.
 */
public class DayRouteExcelWriter implements ExcelWriter {

    private String[] columns;
    private HashMap<Route, DayRouteData> dayCollections;

    public DayRouteExcelWriter(String[] columns, HashMap<Route, DayRouteData> dayCollections) {
        this.columns = columns;
        this.dayCollections = dayCollections;
    }

    public void createWorkbook() {
        Workbook workbook = new XSSFWorkbook();

        CreationHelper creationHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("DayRouteData");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for(Route r: dayCollections.keySet()) {
            DayRouteData drd = dayCollections.get(r);
            if (drd.getTotalHits() >=1) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(r.toString());

                row.createCell(1)
                        .setCellValue(drd.getTotalHits());

                row.createCell(2)
                        .setCellValue(drd.getMinimumTime());

                row.createCell(3)
                        .setCellValue(drd.getMaximumTime());
                row.createCell(4)
                        .setCellValue(drd.getSum());
                row.createCell(5)
                        .setCellValue(drd.getSumSquared());
                row.createCell(6)
                        .setCellValue(drd.getSecondTotalHits());
                row.createCell(7)
                        .setCellValue(drd.getSecondSum());
                row.createCell(8)
                        .setCellValue(drd.getSecondSquared());
                row.createCell(9)
                        .setCellValue(drd.getStandardDevation());
                row.createCell(10)
                        .setCellValue(drd.getExtremity());
            }
        }

        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }


        try {
            FileOutputStream fileOut = new FileOutputStream("poi-generated-file.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
