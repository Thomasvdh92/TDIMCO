package TDIMCO.domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 18-3-2018.
 */
public class Detector implements Comparable<Detector>{

    public int getDetectorId() {
        return detectorId;
    }


    private Integer detectorId;
    private double x;
    private double y;

    public Detector(Integer detectorId) {
        this.detectorId = detectorId;
    }

    public String toString() {
        return String.valueOf(detectorId);
    }

    //TODO determine if this method is needed or no
    public List<Integer> getAllDetectors() {
        List<Integer> list = new ArrayList<Integer>();
        String filePath = "C:\\School\\TDIMCOapp\\src\\Detectors.txt";

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        try {
            assert bufferedReader != null;
            while((line =bufferedReader.readLine())!=null){

                stringBuffer.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = stringBuffer.toString();
        s = s.replaceAll("\\s+","");
        String[] arr = s.split(",");
        for(String st : arr) {
            list.add((Integer.parseInt(st)));
        }

        return list;
    }

    @Override
    public int compareTo(Detector o) {
        return Integer.compare(this.detectorId, o.detectorId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Detector detector = (Detector) o;

        if (Double.compare(detector.x, x) != 0) return false;
        if (Double.compare(detector.y, y) != 0) return false;
        return detectorId != null ? detectorId.equals(detector.detectorId) : detector.detectorId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = detectorId != null ? detectorId.hashCode() : 0;
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
