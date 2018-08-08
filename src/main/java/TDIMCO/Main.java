package TDIMCO;


import TDIMCO.dataaccess.DeviceRoutesDaoMYSQL;
import TDIMCO.dataaccess.HourDRDDaoMySQL;
import TDIMCO.datareader.XmlIterator;
import TDIMCO.domain.*;

import java.sql.SQLException;
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
        iterateXml(xmlIterator, datahavenbedrijf, false);

        for (WeekDay wd : xmlIterator.getSpanCollection().getWeekDays()) {
            for (Hour h : wd.getHours()) {
                new HourDRDDaoMySQL().create(h);
            }
        }

        for (DeviceRoutes dr : xmlIterator.getSpanCollection().getDeviceRoutesCollection()) {
            DeviceRoutesDaoMYSQL deviceRoutesDaoMYSQL = new DeviceRoutesDaoMYSQL();
            deviceRoutesDaoMYSQL.create(dr);

        }


//        System.out.println(DeviceRoutesDAO.getErrors());


        List<WeekDay> mappie = xmlIterator.getSpanCollection().getWeekDays();

        String[] headersDayRouteData = {"Route", "totalhits", "minimumtime", "maximumtime", "sum", "sumSquared",
                "secondTotalHits", "secondSum", "secondSquared", "secondMaxTime", "extremity"};
        String[] headersHour = {"Hour", "Total hits", "Sum in hours", "Sum squared in hours", "Average in minutes"};

        String[] headersDevices = {"ID", "VehicleType", "Routes"};

        List<Hour> hourCollections;

//        ExcelWriter excelWriter;
//        for(WeekDay wd : xmlIterator.getSpanCollection().getWeekDays()) {
//            hourCollections = wd.getHours();
//            excelWriter = new DayRouteExcelWriter(headersDayRouteData, hourCollections);
//            excelWriter.createExcelFile(excelDest, wd.getDayOfWeek().name() + " - " +"DayRouteData");
//        }
//        for(WeekDay wd : mappie) {
//            hourCollections = wd.getHours();
//            excelWriter = new HourExcelWriter(headersHour, hourCollections);
//            excelWriter.createExcelFile(excelDest, wd.getDayOfWeek().name() + " - " +"Hour Data");
//        }

//        String[] headersDevices = {"ID", "VehicleType","Routes"};
//        List<DeviceRoutes> devices = xmlIterator.getSpanCollection().getDeviceRoutesCollection();
//        excelWriter = new DeviceRoutesExcelWriter(headersDevices, devices);
//        excelWriter.createExcelFile(excelDest, "DeviceRoutes");

    }

    private static void iterateXml(XmlIterator xmlIterator, String xml, boolean iterateFolder) {
        runBothIterations(xml, xmlIterator, iterateFolder);
        xmlIterator.compileRoutesFromDeviceNodeList(xml);
    }

    private static void runBothIterations(String xmlUrl, XmlIterator xmlIterator, boolean iterateFolder) {
        long startTime = System.currentTimeMillis();
        if (iterateFolder) {
            xmlIterator.iterateXmlFolder(xmlUrl, false);
            xmlIterator.iterateXmlFolder(xmlUrl, true);
        } else {
            xmlIterator.iterateXmlFile(xmlUrl, false);
            xmlIterator.iterateXmlFile(xmlUrl, true);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Elapsed time for method \"runBothIterations\": " + (elapsedTime / 1000) / 60 + "min " + (elapsedTime / 1000) % 60 + "sec");
    }
}
