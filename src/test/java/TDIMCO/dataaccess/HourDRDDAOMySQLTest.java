package TDIMCO.dataaccess;

import TDIMCO.domain.Hour;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class HourDRDDAOMySQLTest {
    HourDRDDaoMySQL dao;
    Hour h;

    @Before
    public void setup() {
        dao = mock(HourDRDDaoMySQL.class);
        h = new Hour(13);
    }

    @Test
    public void createTest() {
        when(dao.create(h)).thenReturn(h);
        assertEquals(dao.create(h), h);
    }
}
