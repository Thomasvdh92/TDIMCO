package TDIMCO.domain;

import TDIMCO.DataAccess.DeviceRoutesDAO;
import lombok.Data;

import java.util.*;

/**
 * Class used to split up data collection into hours
 */
@Data
public class Hour {

    private int hourNumber;
    private int hourTotalHits;
    private double totalSumInHours;
    private double totalSumSquaredInHours;
    private double totalAverageInMinutes;

    // Collection of data. A route is from 1 detector to a second detector
    private HashMap<Route, DayRouteData> hourCollection;
    private List<Device> devices;



    public Hour(int hourNumber) {
        this.hourNumber = hourNumber;
        hourCollection = new HashMap<>();
        devices = new ArrayList<>();
    }

    public void addRouteToDevice(Route r, Device device) {
        if(!devices.contains(device)) {
            devices.add(device);
        }
        int deviceIndex = devices.indexOf(device);
        devices.get(deviceIndex).getDeviceRoutes().add(r);
    }

    /**
     * Method to add data to the data collection
     * @param r The route for which the data is used
     * @param seconds The amount of seconds added
     * @param secondIteration Boolean to determine the iteration
     */
    public void addTimeToDrd(Route r, double seconds, boolean secondIteration) {
        if(!hourCollection.containsKey(r)){
            hourCollection.put(r, new DayRouteData());
        }
        hourCollection.get(r).addTimeToDayRouteData(seconds,secondIteration);
    }

    /**
     * Set the totals of an hour to a more readable format
     */
    public void setTotals() {
        double totalOfSums=0, totalOfSumSquared = 0;
        int secondToHours = 3600;
        for(Route r : hourCollection.keySet()) {
            DayRouteData drd = hourCollection.get(r);
            totalOfSums += drd.getSecondSum();
            hourTotalHits += drd.getSecondTotalHits();
            totalOfSumSquared += drd.getSecondSquared();
        }
        totalSumInHours = totalOfSums / secondToHours;
        totalSumSquaredInHours = totalOfSumSquared / secondToHours;
        totalAverageInMinutes = (totalSumInHours *60) / hourTotalHits;
    }


    @Override
    public String toString() {
        if(hourTotalHits < 1) return null;
        return "Hour: " + hourNumber + "\n" +
                "Total hits: " + hourTotalHits+ "\n" +
                "Total sum in hours: "+totalSumInHours + "\n" +
                "Total sum squared in hours: "+totalSumSquaredInHours + "\n" +
                "Total average in minutes: " + totalAverageInMinutes;
    }

    public DayRouteData getDrdFromRoute(Route r) {
        return hourCollection.get(r);
    }
}
