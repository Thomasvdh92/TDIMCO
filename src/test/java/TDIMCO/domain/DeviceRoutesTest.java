package TDIMCO.domain;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeviceRoutesTest {

    private Device d;
    private DeviceRoutes dr;

    @Before
    public void setUp() {
        d = new Device("id", "C");
        dr = new DeviceRoutes(d);
    }

    @Test
    public void testConstructor() {
        assertEquals(dr.getDevice().getDevId(), "id");
        assertEquals(dr.getDevice().getVehicleType(), VehicleType.C);
        assertEquals(dr.getDevice(), d);
    }

    @Test
    public void testAddRoute() {
        LocalDateTime ldt = LocalDateTime.now();
        List<Detector> detectors = new ArrayList<>();
        for(int i=0;i<5;i++) {
            detectors.add(new Detector(i + 100));
        }
        double routeTime = 1000;
        assertNull(dr.getRoutes());
        dr.addRoute(ldt, detectors, routeTime);
        DeviceRoutes.DeviceRoute drdr = new DeviceRoutes.DeviceRoute(ldt, detectors, routeTime);
        assertEquals(dr.getRoutes().get(0), drdr);
        ldt = ldt.plusMinutes(5);
        detectors = new ArrayList<>();
        for(int i=10;i<15;i++) {
            detectors.add(new Detector(i + 100));
        }
        routeTime = 500;
        dr.addRoute(ldt, detectors, routeTime);
        drdr = new DeviceRoutes.DeviceRoute(ldt, detectors, routeTime);
        assertEquals(dr.getRoutes().size(), 2);
        assertEquals(dr.getRoutes().get(1), drdr);
    }

}
