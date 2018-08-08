package TDIMCO.dataaccess;

import TDIMCO.domain.DayRouteData;
import TDIMCO.domain.Hour;
import TDIMCO.domain.Route;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HourDRDDaoMySQL implements IDAO<Hour> {

    @Override
    public Hour create(Hour hour)  {
        try {
            Connection conn = MySQLJDBCUtil.getConnection();
            String query = "INSERT INTO HourDayRouteData(HourNumber, Route, TotalHits, MinimumTime, MaximumTime, totalSum, " +
                    "sumSquared, secondTotalHits, secondSum, secondSquared, secondMaxTime) " +
                    "values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prpdstmt = conn.prepareStatement(query);
            for(Route r : hour.getHourCollection().keySet()) {
                DayRouteData drd = hour.getHourCollection().get(r);
                if(drd.getTotalHits() ==1 ) continue;
                prpdstmt.setInt(1, hour.getHourNumber());
                prpdstmt.setString(2, r.toString());
                prpdstmt.setInt(3, drd.getTotalHits());
                prpdstmt.setDouble(4, drd.getMinimumTime());
                prpdstmt.setDouble(5, drd.getMaximumTime());
                prpdstmt.setDouble(6, drd.getSum());
                prpdstmt.setDouble(7, drd.getSumSquared());
                prpdstmt.setInt(8, drd.getSecondTotalHits());
                prpdstmt.setDouble(9, drd.getSecondSum());
                prpdstmt.setDouble(10, drd.getSecondSquared());
                prpdstmt.setDouble(11, drd.getSecondMaxTime());
                prpdstmt.execute();
            }
            conn.close();
        } catch (SQLException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return hour;
    }

    @Override
    public Hour read(Hour hour) {
        return null;
    }

    @Override
    public Hour update(Hour hour) {
        return null;
    }

    @Override
    public void delete(Hour hour) {

    }

    @Override
    public List<Hour> getAll() {
        return null;
    }
}
