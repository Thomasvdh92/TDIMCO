package TDIMCO.dataaccess;

import TDIMCO.datawriter.MillisConverter;
import TDIMCO.domain.Detector;
import TDIMCO.domain.Device;
import TDIMCO.domain.DeviceRoutes;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeviceRoutesDaoMYSQL implements IDAO<DeviceRoutes> {

    @Override
    public DeviceRoutes create(DeviceRoutes deviceRoutes) {
        try {
            Connection conn = MySQLJDBCUtil.getConnection();
            String query = "INSERT INTO DeviceRoute(DeviceId, Date_Time, Detectors, routeTimeInMillis)"
                    + " values(?, ?, ?, ?)";
            PreparedStatement prpdstmt = conn.prepareStatement(query);
            for(int i=0;i<deviceRoutes.getRoutes().size();i++) {
                prpdstmt.setString(1, deviceRoutes.getDevice().getDevId());
                prpdstmt.setString(2, deviceRoutes.getRoutes().get(i).getLdt().toString());
                prpdstmt.setString(3, deviceRoutes.getRoutes().get(i).getDetectors().toString());
                prpdstmt.setString(4, MillisConverter.convertMillis((long)deviceRoutes.getRoutes().get(i).getRouteTimeInMillis()));
                prpdstmt.execute();
            }
            conn.close();
        } catch (IOException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return deviceRoutes;
    }

    @Override
    public DeviceRoutes read(DeviceRoutes deviceRoutes) {
        return null;
    }

    @Override
    public DeviceRoutes update(DeviceRoutes deviceRoutes) {
        return null;
    }

    @Override
    public void delete(DeviceRoutes deviceRoutes) {

    }

    @Override
    public List<DeviceRoutes> getAll() {
        return null;
    }

    public List<DeviceRoutes> getAllById(String id) {
        List<DeviceRoutes> list = new ArrayList<>();
        try {
            Connection conn = MySQLJDBCUtil.getConnection();
            String query = "SELECT * FROM DeviceRoute WHERE DeviceId = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                String devId = rs.getString("DeviceId");
                LocalDateTime ldt = LocalDateTime.parse(rs.getString("Date_Time"));
                StringBuilder sb = new StringBuilder(rs.getString("Detectors"));
                sb.deleteCharAt(0);
                sb.deleteCharAt(rs.getString("Detectors").length()-2);
                String[] detectors = sb.toString().split(", ");
                List<Detector> properList = new ArrayList<>();
                for(String s : detectors) {
                    properList.add(new Detector(Integer.valueOf(s)));
                }
                double routeTime = rs.getInt("routeTimeInMillis");
                DeviceRoutes dr = new DeviceRoutes(new Device(devId));
                dr.addRoute(ldt, properList, routeTime);
                list.add(dr);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
