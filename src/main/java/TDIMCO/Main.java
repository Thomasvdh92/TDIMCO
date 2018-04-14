package TDIMCO;


import TDIMCO.DataAccess.DeviceRoutesDAO;
import TDIMCO.datareader.SpanCollection;
import TDIMCO.datareader.XmlIterator;
import TDIMCO.datawriter.DayRouteExcelWriter;
import TDIMCO.datawriter.DeviceRoutesExcelWriter;
import TDIMCO.datawriter.ExcelWriter;
import TDIMCO.datawriter.HourExcelWriter;
import TDIMCO.domain.*;

import java.time.DayOfWeek;
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
        runBothIterations(datahavenbedrijf, xmlIterator, false);
        xmlIterator.printVehicleHits();

        System.out.println(DeviceRoutesDAO.getErrors());


//        List<WeekDay> mappie = xmlIterator.getSpanCollection().getWeekDays();
//
//        String[] headersDayRouteData = {"Route","totalhits","minimumtime","maximumtime", "sum", "sumSquared",
//                            "secondTotalHits","secondSum","secondSquared","standardDevation","extremity"};
//        String[] headersHour = {"Hour", "Total hits", "Sum in hours", "Sum squared in hours", "Average in minutes"};
//
//        String[] headersDevices = {"ID", "VehicleType", "Routes"};
//
//        List<Hour> hourCollections;
//
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
//        List<Device> devices;

//        List<Device> devices = xmlIterator.getSpanCollection().getDevices();
//        excelWriter = new DeviceRoutesExcelWriter(headersDevices, devices);
//        excelWriter.createExcelFile(excelDest, "DeviceRoutes");

    }

    private static void runBothIterations(String voorbeeldxml, XmlIterator xmlIterator, boolean iterateFolder) {
        long startTime = System.currentTimeMillis();
        if(iterateFolder) {
            xmlIterator.iterateXmlFolder(voorbeeldxml, false);
            System.out.println("----------------SECOND----------------");
            xmlIterator.iterateXmlFolder(voorbeeldxml, true);
        }
        xmlIterator.iterateXmlFile(voorbeeldxml, false);
        System.out.println("----------------SECOND----------------");
        xmlIterator.iterateXmlFile(voorbeeldxml, true);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Elapsed time for method \"runBothIterations\": " + (elapsedTime / 1000)/60 + "min " +(elapsedTime/1000)%60+"sec");
    }
}
