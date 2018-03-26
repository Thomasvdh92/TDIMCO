package TDIMCO;


import TDIMCO.datareader.XmlIterator;
import TDIMCO.datawriter.DayRouteExcelWriter;
import TDIMCO.datawriter.ExcelWriter;
import TDIMCO.domain.DayRouteData;
import TDIMCO.domain.Route;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Thomas on 18-3-2018.
 */
public class Main {
    public static void main(String[] args) {

        String voorbeeldxml = "C:\\School\\TDIMCOapp\\voorbeeld.xml";
        String datahavenbedrijf = "E:\\Data Haven Bedrijf\\Originele Data\\2014 6\\01.xml";
        String xmlFolder = "E:\\Data Haven Bedrijf\\Originele Data\\2014 6";

        XmlIterator xmlIterator = new XmlIterator();
        xmlIterator.iterateXmlFile(voorbeeldxml);
//        xmlIterator.iterateXmlFolder(xmlFolder);

        String[] headers = {"Route","totalhits","minimumtime","maximumtime", "sum", "sumSquared",
                            "secondTotalHits","secondSum","secondSquared","standardDevation","extremity"};
        HashMap<Route, DayRouteData> dayCollections = xmlIterator.getSpanCollection().getDayCollections();


        ExcelWriter excelWriter = new DayRouteExcelWriter(headers, dayCollections);
        excelWriter.createWorkbook();

    }
}
