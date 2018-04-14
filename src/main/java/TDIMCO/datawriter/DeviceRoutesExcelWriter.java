package TDIMCO.datawriter;

import TDIMCO.domain.Device;
import TDIMCO.domain.Hour;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Thomas on 1-4-2018.
 */
public class DeviceRoutesExcelWriter implements ExcelWriter {

    private String[] columns;
    private List<Device> deviceList;

    public DeviceRoutesExcelWriter(String[] columns, List<Device> deviceList) {
        this.columns = columns;
        this.deviceList = deviceList;
    }


    public void createExcelFile(String destination, String excelTitle) {

        Workbook workbook = new XSSFWorkbook();

        CreationHelper creationHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("Device Route collection");
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
        int amountOfColumns = columns.length;
        int extraColumns = 0;
        for (Device d : deviceList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(d.getDevId());

            row.createCell(1)
                    .setCellValue(d.getVehicleType().toString());

            for (int i = 0; i < d.getDeviceRoutes().size(); i++) {
                if(i > extraColumns) extraColumns=i;
                row.createCell(i+2)
                        .setCellValue(d.getDeviceRoutes().get(i).toString());
            }
        }

        amountOfColumns += extraColumns;
        for (int i = 0; i < amountOfColumns; i++) {
            sheet.autoSizeColumn(i);
        }


        try {
            FileOutputStream fileOut = new FileOutputStream(destination + "\\" + excelTitle + ".xlsx");
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
        return new String[0];
    }
}
