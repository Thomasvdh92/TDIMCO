package TDIMCO.domain;

/**
 * Created by Thomas on 18-3-2018.
 */
public class Device {

    private String devId;

    private VehicleType vehicleType;

    public Device(String devId, String vehicleType) {
        this.devId = devId;
        switch (vehicleType) {
            case "C":
                this.vehicleType = VehicleType.C;
                break;
            case "U":
                this.vehicleType = VehicleType.U;
                break;
            case "T":
                this.vehicleType = VehicleType.T;
                break;
        }

    }

    public String getDevId() {
        return devId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
