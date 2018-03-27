package TDIMCO.datareader;

import TDIMCO.domain.DayRouteData;
import TDIMCO.domain.Detector;
import TDIMCO.domain.Hour;
import TDIMCO.domain.Route;
import lombok.Data;
import org.w3c.dom.Element;

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
    private List<Hour> hours;

    public SpanCollection() {
        buildHours();
    }

    private void buildHours() {
        hours = new ArrayList<>();
        for(int i=0;i<24;i++) {
            hours.add(new Hour(i));
        }
    }

    public void determineStandardDeviation() {
        for(Hour h : hours) {
            h.setTotals();
            for (DayRouteData drd : h.getHourCollection().values()) {
                drd.setDevationAndExtremity();
            }
        }
    }

    public void addRouteDetection(Element detection1, Element detection2, boolean secondIteration) {
        Detector d1 = new Detector(Integer.parseInt(detection1.getAttribute("d")));
        Detector d2 = new Detector(Integer.parseInt(detection2.getAttribute("d")));
        Route r = new Route(d1, d2);
        LocalDateTime date1 = getDateFromDetection(detection1);
        LocalDateTime date2 = getDateFromDetection(detection2);
        double seconds = date1.until(date2, ChronoUnit.SECONDS);
        hours.get(date1.getHour()).addTimeToDrd(r, seconds, secondIteration);
    }

    private LocalDateTime getDateFromDetection(Element detection1) {
        String s = detection1.getAttribute("t");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
        return dateTime;
    }
}
