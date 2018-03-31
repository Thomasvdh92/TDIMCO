package TDIMCO.datareader;

import TDIMCO.domain.*;
import lombok.Data;
import org.w3c.dom.Element;

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

    public SpanCollection() {
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
                    drd.setDevationAndExtremity();
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
        getDrdAndAddTime(secondIteration, r, seconds, date1);
    }

    private void getDrdAndAddTime(boolean secondIteration, Route r, double seconds, LocalDateTime date) {
        WeekDay wd = new WeekDay(date.getDayOfWeek());
        int hour =  date.getHour();
        int indexOfWeekday = weekDays.indexOf(wd);
        weekDays.get(indexOfWeekday).getHours().get(hour).addTimeToDrd(r,seconds,secondIteration);
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
}
