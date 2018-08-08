package TDIMCO.datareader;

import TDIMCO.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpanCollectionTest {

    SpanCollection sc;

    @Before
    public void setUp() {
        sc = new SpanCollection();
        Element e1 = mock(Element.class);
        Element e2 = mock(Element.class);
    }

    @Test
    public void testBuildWeekDays() {
        String[] weekdays = {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
        int i = 0;
        for(WeekDay wd : sc.getWeekDays()) {
            assertEquals(weekdays[i], wd.getDayOfWeek().toString());
            i++;
        }
    }

    @Test
    public void testGetDateFromDetection() {
        Element e = mock(Element.class);
        sc = mock(SpanCollection.class);
        String date = "2016-01-01 10:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formattedDate = LocalDateTime.parse(date, formatter);
        when(sc.getDateFromDetection(e)).thenReturn(formattedDate);
        String date2 = "2016-01-01 11:00";
        LocalDateTime testDate = LocalDateTime.parse(date2, formatter);
        assertTrue(sc.getDateFromDetection(e).isBefore(testDate) ||
            sc.getDateFromDetection(e).equals(testDate));
    }

}
