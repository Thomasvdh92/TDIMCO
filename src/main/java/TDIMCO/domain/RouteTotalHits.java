package TDIMCO.domain;

import lombok.Setter;

/**
 * Created by Thomas on 28-3-2018.
 */
public class RouteTotalHits {

    private Route route;
    @Setter
    private double firstIterationHits;
    @Setter
    private double secondIterationHits;

    public RouteTotalHits(Route route) {
        this.route = route;
        this.firstIterationHits = 0;
        this.secondIterationHits = 0;
    }

    public void increment(double intlist) {
        if(intlist == 1) firstIterationHits++;
        if(intlist == 0) secondIterationHits++;
    }

    @Override
    public String toString() {
        return route.toString() + "|1st: " + firstIterationHits + "|2nd: " + secondIterationHits+"|";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteTotalHits that = (RouteTotalHits) o;

        return route.equals(that.route);
    }

    @Override
    public int hashCode() {
        return route.hashCode();
    }
}
