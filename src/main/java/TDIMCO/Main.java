package TDIMCO;


import TDIMCO.datareader.SpanCollection;
import TDIMCO.datareader.XmlIterator;
import TDIMCO.domain.*;

import java.time.DayOfWeek;

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


//        List<WeekDay> mappie = xmlIterator.getSpanCollection().getWeekDays();

        String[] headersDayRouteData = {"Route","totalhits","minimumtime","maximumtime", "sum", "sumSquared",
                            "secondTotalHits","secondSum","secondSquared","standardDevation","extremity"};
        String[] headersHour = {"Hour", "Total hits", "Sum in hours", "Sum squared in hours", "Average in minutes"};


//        ExcelWriter excelWriter;
//        for(WeekDay wd : mappie) {
//            hourCollections = wd.getHours();
//            excelWriter = new DayRouteExcelWriter(headersDayRouteData, hourCollections);
//            excelWriter.createExcelFile(excelDest, wd.getDayOfWeek().name() + " - " +"DayRouteData");
//        }
//        for(WeekDay wd : mappie) {
//            hourCollections = wd.getHours();
//            excelWriter = new HourExcelWriter(headersHour, hourCollections);
//            excelWriter.createExcelFile(excelDest, wd.getDayOfWeek().name() + " - " +"Hour Data");
//        }

    }
}
