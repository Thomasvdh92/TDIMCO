package TDIMCO.datawriter;

import TDIMCO.domain.DayRouteData;
import TDIMCO.domain.Hour;
import TDIMCO.domain.Route;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Thomas on 26-3-2018.
 */
public class HourExcelWriter implements ExcelWriter {
    private String[] columns;
    private List<Hour> hoursCollection;

    public HourExcelWriter(String[] columns, List<Hour> hoursCollection) {
        this.columns = columns;
        this.hoursCollection = hoursCollection;
    }

    @Override
    public void createExcelFile(String destination, String excelTitle) {

        Workbook workbook = new XSSFWorkbook();

        CreationHelper creationHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("Hour data collection");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for (Hour h : hoursCollection) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(h.getHourNumber());

                row.createCell(1)
                        .setCellValue(h.getHourTotalHits());

                row.createCell(2)
                        .setCellValue(h.getTotalSumInHours());

                row.createCell(3)
                        .setCellValue(h.getTotalSumSquaredInHours());
                row.createCell(4)
                        .setCellValue(h.getTotalAverageInMinutes());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }



        try {
            FileOutputStream fileOut = new FileOutputStream(destination + "\\" + excelTitle+ ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getHeaders() {
        return new String[] {"Hour", "Total hits", "Sum in hours", "Sum squared in hours", "Average in minutes"};
    }
}
