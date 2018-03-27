package TDIMCO.domain;

import lombok.Data;

/**
 * Collection of data. This class is also responsible for the regulation the iterations.
 */

@Data
public class DayRouteData {


    private int totalHits;
    private double minimumTime;
    private double maximumTime;
    private double sum;
    private double sumSquared;
    public int secondTotalHits;
    public double secondSum;
    public double secondSquared;
    public double standardDevation;
    public double extremity;

    public DayRouteData() {
        this.totalHits= 0;
        this.minimumTime= 0;
        this.maximumTime = 0;
        this.sum = 0;
        this.sumSquared = 0;
        this.secondTotalHits=0;
        this.secondSum=0;
        this.secondSquared=0;
        this.standardDevation=0;
        this.extremity=0;
    }

    public void setDevationAndExtremity() {
        calculateAndSetStandardDevation();
        calculateAndSetExtremity();
    }

    /**
     * Method to add collected data to the collection.
     * @param seconds Amount of time(in seconds) to be added
     * @param secondIteration Used to determine whether its the first or second iteration
     */
    public void addTimeToDayRouteData(double seconds, boolean secondIteration) {
        if(secondIteration) {
            if(seconds > maximumTime) return;
            secondTotalHits++;
            secondSum+= seconds;
            secondSquared += (Math.pow(seconds,2));
            return;
        }
        totalHits++;
        sumSquared += Math.pow(seconds, 2);
        this.sum += seconds;
        if(minimumTime == 0.0 || minimumTime > seconds) {
            minimumTime = seconds;
            maximumTime = seconds *3;
        }
    }

    public void calculateAndSetStandardDevation() {
        if(secondTotalHits<1) {
            //maybe throw error
            return;
        }
        double averageSquared = Math.pow(secondSum/secondTotalHits, 2);
        double sumTimesAverageSquared = secondTotalHits * averageSquared;
        double sumSquaredMinusSumTimesAverageSquared = secondSquared - sumTimesAverageSquared;
        double postFinalNumber = sumSquaredMinusSumTimesAverageSquared/(secondTotalHits - 1);
        this.standardDevation =Math.sqrt(postFinalNumber);
    }

    public void calculateAndSetExtremity() {
        if(standardDevation == 0) calculateAndSetStandardDevation();
        this.extremity = (secondSum/secondTotalHits) + standardDevation * 2;
    }

    public String toString() {
        return "Totalhits: "+ totalHits + " minimumtime: " +minimumTime + " total sum: "+ sum + " maximum time: "+ maximumTime + " sumsquared:"+sumSquared;
    }
}
