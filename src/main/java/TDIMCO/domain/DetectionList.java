package TDIMCO.domain;



import java.util.ArrayList;

import java.util.List;

/**
 * Created by Thomas on 18-3-2018.
 */
public class DetectionList {

    private Device device;

    private List<Detection> detections;


    public DetectionList(Device device) {
        this.device = device;
        detections = new ArrayList<Detection>();
    }

    public void addDetectionToList(Detection detection) {
        detections.add(detection);
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }


    @Override
    public String toString() {
        String s = "";
        s += "Device: "+ device.getDevId() + " of vehicletype "+device.getVehicleType().toString();
        s += "\n";
        for(Detection d : detections) {
            s += "Detector: "+d.getDetector()+ " on date: " +d.getDetectionDate()+"\n";
        }
        return s;
    }
}
