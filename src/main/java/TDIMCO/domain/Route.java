package TDIMCO.domain;

/**
 * Created by Thomas on 19-3-2018.
 */
public class Route {
    private Detector detectorOne;
    private Detector detectorTwo;

    public Route(Detector detectorOne, Detector detectorTwo) {
        this.detectorOne = detectorOne;
        this.detectorTwo = detectorTwo;
    }

    public String toString() {
        return detectorOne.toString() + " --> " + detectorTwo.toString();
    }

    public Detector getDetectorOne() {
        return detectorOne;
    }

    public Detector getDetectorTwo() {
        return detectorTwo;
    }

    @Override
    public int hashCode() {
        int result = detectorOne != null ? detectorOne.hashCode() : 0;
        result = 31 * result + (detectorTwo != null ? detectorTwo.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;
        if (detectorOne != null ? !detectorOne.equals(route.detectorOne) : route.detectorOne != null) return false;
        return detectorTwo != null ? detectorTwo.equals(route.detectorTwo) : route.detectorTwo == null;
    }

//    @Override
//    public boolean equals(Object o) {
//        if(this == o){
//            System.out.println("equals is true");
//            return true;
//        }
//        if(!(o instanceof Route)) {
//            System.out.println("equals is false");
//            return false;
//        }
//        Route oc = (Route) o;
//        System.out.println(this.detectorOne.toString() + this.detectorTwo.toString() + " compare to " + ((Route) o).detectorOne.toString() + ((Route) o).detectorTwo.toString());
//        System.out.println(this.detectorOne.equals(oc.detectorOne) && this.detectorTwo.equals(((Route) o).detectorTwo));
//        return this.detectorOne == (oc.detectorOne) && this.detectorTwo ==(((Route) o).detectorTwo);
//    }
}
