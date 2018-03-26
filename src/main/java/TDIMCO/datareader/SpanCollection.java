package TDIMCO.datareader;

import TDIMCO.domain.DayRouteData;
import TDIMCO.domain.Detector;
import TDIMCO.domain.Hour;
import TDIMCO.domain.Route;
import lombok.Data;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by Thomas on 19-3-2018.
 */
@Data
public class SpanCollection {

//    private XmlIterator xmlIterator;
    private List<Route> allRoutes;
    private HashMap<Route, DayRouteData> dayCollections;
    private List<Hour> hours;

//    public SpanCollection(XmlIterator xmlIterator) {
//        this.xmlIterator = xmlIterator;
//        allRoutes = buildRoutes();
//        dayCollections= buildEmptyCollections();
//    }

    public SpanCollection() {
        allRoutes = buildRoutes();
        dayCollections = new HashMap<>();
        buildHours();
        buildEmptyCollections();
    }

    private void buildHours() {
        hours = new ArrayList<>();
        for(int i=0;i<24;i++) {
            hours.add(new Hour(i));
        }
    }

    public List<Route> buildRoutes() {
        List<Integer> detectors = new Detector().getAllDetectors();
        List<Route> tmpList = new ArrayList<>();
        for(Integer d1 : detectors) {
            for (Integer d2 : detectors) {
                if (!Objects.equals(d1, d2)) {
                    tmpList.add(new Route(new Detector(d1), new Detector(d2)));
                }
            }
        }
        return tmpList;
    }

    public void buildEmptyCollections() {
        for (Route r : allRoutes) {
            dayCollections.put(r, new DayRouteData());
        }
    }
//
//    public void iterateXmlNodelist(NodeList detectionNodeList) {
//
//        if(detectionNodeList.getLength() >1) {
//            for (int i = 0; i < detectionNodeList.getLength(); i++) {
//
//                Node node = detectionNodeList.item(i);
//                Element detection1 = (Element) node;
//
//
//                    node = detectionNodeList.item(i+1);
//                    Element detection2 = (Element) node;
//                    if (Integer.parseInt(detection1.getAttribute("d")) != Integer.parseInt(detection2.getAttribute("d"))) {
//                        addRouteDetection(detection1, detection2);
//                    }
//
//            }
//        }
//    }

    public void determineStandardDevation() {
        for(DayRouteData drd : dayCollections.values()) {
            drd.setDevationAndExtremity();
        }
    }

    public void addRouteDetection(Element detection1, Element detection2, boolean secondIteration) {
        Detector d1 = new Detector(Integer.parseInt(detection1.getAttribute("d")));
        Detector d2 = new Detector(Integer.parseInt(detection2.getAttribute("d")));
        Route r = new Route(d1, d2);
        LocalDateTime date1 = getDateFromDetection(detection1);
        LocalDateTime date2 = getDateFromDetection(detection2);
        double seconds = date1.until(date2, ChronoUnit.SECONDS);
        dayCollections.get(r).addTimeToDayRouteData(seconds, secondIteration);
    }

    private LocalDateTime getDateFromDetection(Element detection1) {
        String s = detection1.getAttribute("t");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
        return dateTime;
    }

    public void writeCollectionToFile() {
        try {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("collections.txt"), "utf-8"))) {
                for(Route r:dayCollections.keySet()) {
                    writer.write(r + " : " + dayCollections.get(r));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
