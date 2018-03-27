package TDIMCO.datawriter;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Interface used to write to excel files
 */
public interface ExcelWriter {

    /**
     * Method used to create an Excel file
     * @param destination Destination folder of the excel file
     * @param excelTitle Title of the excel file
     */
    void createExcelFile(String destination, String excelTitle);
}
