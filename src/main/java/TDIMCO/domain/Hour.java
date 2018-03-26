package TDIMCO.domain;

import lombok.Data;

import java.util.HashMap;

/**
 * Created by Thomas on 26-3-2018.
 */
@Data
public class Hour {

    private int hourNumber;

    private HashMap<Route, DayRouteData> hourCollection;

    public Hour(int hourNumber) {
        this.hourNumber = hourNumber;
    }
}
