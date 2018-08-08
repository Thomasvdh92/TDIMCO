package TDIMCO.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class DayRouteDataTest {

    private DayRouteData drd;

    @Before
    public void setUp() {
        drd = new DayRouteData();
    }

    @Test
    public void testSetDrdValuesToFirst() {
        drd.setTotalHits(5);
        drd.setMinimumTime(5);
        drd.setMaximumTime(15);
        drd.setSum(100);
        drd.setSumSquared(240);
        drd.setSecondTotalHits(4);
        drd.setSecondSum(80);
        drd.setSecondSquared(180);
        drd.setSecondMaxTime(14);
        drd.setDrdValuesToFirst();
        assertEquals(drd.getTotalHits(), 4);
        assertEquals(drd.getMaximumTime(), 14, 0);
        assertEquals(drd.getSum(), 80, 0);
        assertEquals(drd.getSumSquared(), 180, 0);
        assertEquals(drd.getSecondTotalHits(), 0, 0);
        assertEquals(drd.getSecondSum(), 0, 0);
        assertEquals(drd.getSecondMaxTime(), 0, 0);
        assertEquals(drd.getSecondSquared(), 0, 0);
    }

    @Test
    public void testIncrementAmountDeviation() {
        assertEquals(DayRouteData.getAmountNotDeviatingPastTenPrcnt(), 0);
        DayRouteData.incrementAmountDeviation();
        assertEquals(DayRouteData.getAmountNotDeviatingPastTenPrcnt(), 1);
    }

    @Test
    public void testAddTimeToDrd() {
        drd.addTimeToDayRouteData(100, false);
        assertEquals(drd.getTotalHits(), 1);
        assertEquals(drd.getSum(), 100, 0);
        assertEquals(drd.getSumSquared(), 10000,0 );
        assertEquals(drd.getMinimumTime(), 100, 0);
    }

    @Test
    public void testAddTimeToDrdSecondIteration() {
        drd.setMaximumTime(150);
        drd.addTimeToDayRouteData(100, true);
        assertEquals(drd.getSecondTotalHits(), 1);
        assertEquals(drd.getSecondSum(), 100, 0);
        assertEquals(drd.getSecondSquared(), 10000, 0);
    }

    @Test
    public void testAddTimeSecondIterationExceedingMaxTime() {
        drd.setMaximumTime(50);
        drd.addTimeToDayRouteData(100, true);
        assertEquals(drd.getSecondTotalHits(), 0);
    }

    @Test
    public void testCalculateMaxTime() {
        int totalhits = 5;
        double sum = 10;
        double sumsquared = 5000;
        drd.setMaximumTime(drd.calculateMaximumTime(totalhits, sum, sumsquared));
        assertEquals(drd.getMaximumTime(), 72.5691150575094, 0);
        assertEquals(drd.calculateMaximumTime(1, 10, 20), 0, 0);
    }

}
