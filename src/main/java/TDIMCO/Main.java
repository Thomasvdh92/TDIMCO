package TDIMCO;


import TDIMCO.datareader.XmlIterator;
import TDIMCO.datawriter.DayRouteExcelWriter;
import TDIMCO.datawriter.ExcelWriter;
import TDIMCO.datawriter.HourExcelWriter;
import TDIMCO.domain.Hour;

import java.util.List;

/**
 * Created by Thomas on 18-3-2018.
 */
public class Main {
    public static void main(String[] args) {

        String voorbeeldxml = "C:\\School\\TDIMCOapp\\voorbeeld.xml";
        String datahavenbedrijf = "E:\\Data Haven Bedrijf\\Originele Data\\2014 6\\01.xml";
        String xmlFolder = "E:\\Data Haven Bedrijf\\Originele Data\\2014 6";
        String excelDest = "F:\\Generated Excel Files";

        XmlIterator xmlIterator = new XmlIterator();
        xmlIterator.iterateXmlFile(datahavenbedrijf);
//        xmlIterator.iterateXmlFolder(xmlFolder);

        String[] headersDayRouteData = {"Route","totalhits","minimumtime","maximumtime", "sum", "sumSquared",
                            "secondTotalHits","secondSum","secondSquared","standardDevation","extremity"};
        String[] headersHour = {"Hour", "Total hits", "Sum in hours", "Sum squared in hours", "Average in minutes"};
        List<Hour> hourCollections = xmlIterator.getSpanCollection().getHours();


        ExcelWriter excelWriter = new DayRouteExcelWriter(headersDayRouteData, hourCollections);

        excelWriter.createExcelFile(excelDest, "DayRouteData");
        excelWriter = new HourExcelWriter(headersHour, hourCollections);
        excelWriter.createExcelFile(excelDest, "Hour Data");

    }
}
