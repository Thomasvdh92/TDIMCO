package TDIMCO.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HourTest {

    private Hour h;

    @Before
    public void setUp() {
        h = new Hour(8);
    }

    @Test
    public void testAddTimeToDrd() {
        Route r = new Route(new Detector(100), new Detector(101));
        double seconds = 100;
        assertNull(h.getHourCollection().get(r));
        h.addTimeToDrd(r, seconds, false);
        assertNotNull(h.getHourCollection().get(r));
        assertEquals(h.getHourCollection().get(r).getSum(), 100, 0);
    }

    @Test
    public void testSetTotals() {
        Route r = new Route(new Detector(100), new Detector(101));
        double seconds = 3600;
        h.addTimeToDrd(r, seconds, false);
        assertNotNull(h.getHourCollection().get(r));
        h.getHourCollection().get(r).setMaximumTime(4000);
        h.addTimeToDrd(r, seconds, true);
        h.setTotals();
        assertEquals(h.getTotalSumInHours(), 1.0, 0);
        assertEquals(h.getTotalSumSquaredInHours(), 3600, 0);
        assertEquals(h.getTotalAverageInMinutes(), 60, 0);
    }

}
