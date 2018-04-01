package TDIMCO.datareader;

import TDIMCO.domain.*;
import lombok.Data;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Class that represents a collection of {@link Hour} hours
 */
@Data
public class SpanCollection {

    /**
     * List of hours containing all the routes and data collections. The list is always 24 indexes big
     */
    private List<WeekDay> weekDays;
    public final static List<RouteTotalHits> hitList = new ArrayList<>();
    private List<Device> devices;

    public SpanCollection() {
        devices = new ArrayList<>();
        buildWeekDays();
    }

    private void buildWeekDays() {
        weekDays = new ArrayList<>();
        for(DayOfWeek d : DayOfWeek.values()) {
            weekDays.add(new WeekDay(d));
        }
    }

    public void determineStandardDeviation() {
        for(WeekDay wd:weekDays) {
            for (Hour h : wd.getHours()) {
                h.setTotals();
                for (DayRouteData drd : h.getHourCollection().values()) {
                    drd.calculateAndSetStandardDevation();
                }
            }
        }
    }

    public void addRouteDetection(Element detection1, Element detection2, boolean secondIteration) {
        Detector d1 = new Detector(Integer.parseInt(detection1.getAttribute("d")));
        Detector d2 = new Detector(Integer.parseInt(detection2.getAttribute("d")));
        Route r = new Route(d1, d2);
        if(!hitList.contains(new RouteTotalHits(r))) {
            RouteTotalHits rth = new RouteTotalHits(r);
            hitList.add(rth);
        }
        hitList.get(hitList.indexOf(new RouteTotalHits(r))).increment((secondIteration) ? 1 : 0);
        LocalDateTime date1 = getDateFromDetection(detection1);
        LocalDateTime date2 = getDateFromDetection(detection2);
        double seconds = date1.until(date2, ChronoUnit.SECONDS);
        getHourAndAddTime(secondIteration, r, seconds, date1);
    }

    private void getHourAndAddTime(boolean secondIteration, Route r, double seconds, LocalDateTime date) {
        WeekDay wd = new WeekDay(date.getDayOfWeek());
        int hour =  date.getHour();
        int indexOfWeekday = weekDays.indexOf(wd);
        weekDays.get(indexOfWeekday).getHours().get(hour).addTimeToDrd(r,seconds,secondIteration);
    }

    private DayRouteData getDrd(Route r, LocalDateTime date) {
        WeekDay wd = new WeekDay(date.getDayOfWeek());
        int hour =  date.getHour();
        int indexOfWeekday = weekDays.indexOf(wd);
        return weekDays.get(indexOfWeekday).getHours().get(hour).getHourCollection().get(r);
    }

    private LocalDateTime getDateFromDetection(Element detection1) {
        String s = detection1.getAttribute("t");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss");
        return LocalDateTime.parse(s, formatter);
    }

    public static void printTotalHits() {
        System.out.println("Route: " + "    |  1st   |  2nd   |");
        for(RouteTotalHits rth : hitList) {
            System.out.println("_______________________________");
            System.out.println(rth.toString());
            System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        }
    }

    public void addDevice(Device device) {
        if(!devices.contains(device)) devices.add(device);
    }

    public void compileRoutes(Device device, NodeList detectionNodeList) {
        int startDetec = 0;
        for(int i=1;i<detectionNodeList.getLength();i++) {
            if(!isViableRouteTime(detectionNodeList, i-1, i) && i - startDetec != 1) {
                compileRoute(device, detectionNodeList, startDetec, i-1);
                startDetec = i;
            }
        }
    }

    private void compileRoute(Device device, NodeList detectionNodeList, int first, int last) {
        Detector startDetec = getDetectorFromNodeList(detectionNodeList, first);
        Detector endDetec = getDetectorFromNodeList(detectionNodeList, last);
        Route r = new Route(startDetec, endDetec);
        devices.get(devices.indexOf(device)).getDeviceRoutes().add(r);
    }

    private boolean isViableRouteTime(NodeList detectionNodeList, int i, int i1) {
        Element detection1 = (Element) detectionNodeList.item(i);
        Element detection2 = (Element) detectionNodeList.item(i1);
        LocalDateTime date1 = getDateFromDetection(detection1);
        LocalDateTime date2 = getDateFromDetection(detection2);
        double seconds = date1.until(date2, ChronoUnit.SECONDS);
        Detector d1= getDetectorFromNodeList(detectionNodeList, i);
        Detector d2 = getDetectorFromNodeList(detectionNodeList, i1);
        Route r = new Route(d1, d2);
        return seconds < getDrd(r, date1).getMaximumTime();

    }

    private Detector getDetectorFromNodeList(NodeList detectionNodeList, int i) {
        Node node = detectionNodeList.item(i);
        Element element = (Element) node;
        return new Detector(Integer.parseInt(element.getAttribute("d")));
    }

}
