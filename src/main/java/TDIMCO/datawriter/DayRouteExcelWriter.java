package TDIMCO.datawriter;

import TDIMCO.domain.DayRouteData;
import TDIMCO.domain.Hour;
import TDIMCO.domain.Route;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Thomas on 19-3-2018.
 */
public class DayRouteExcelWriter implements ExcelWriter {

    private String[] columns;
    private List<Hour> hoursCollection;

    //TODO change hourscolleciton to weekdaycollection
    public DayRouteExcelWriter(String[] columns, List<Hour> hoursCollection) {
        this.columns = columns;
        this.hoursCollection = hoursCollection;
    }

    public void createExcelFile(String destination, String excelTitle) {

        Workbook workbook = new XSSFWorkbook();

        CreationHelper creationHelper = workbook.getCreationHelper();

        for(Hour h : hoursCollection) {
            Sheet sheet = workbook.createSheet("Hour - " + h.getHourNumber());
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
            for (Route r : h.getHourCollection().keySet()) {
                DayRouteData drd = h.getHourCollection().get(r);
                if (drd.getTotalHits() >= 1 && drd.getSecondTotalHits() >1) {
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
                            .setCellValue(drd.getSecondMaxTime());
                }
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }


        try {
            FileOutputStream fileOut = new FileOutputStream(destination + "\\" + excelTitle+ ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Route","totalhits","minimumtime","maximumtime", "sum", "sumSquared",
                "secondTotalHits","secondSum","secondSquared","standardDevation","extremity"};
    }
}
