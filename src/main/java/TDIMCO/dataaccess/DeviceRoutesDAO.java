//package TDIMCO.dataaccess;
//
//import TDIMCO.domain.*;
//import com.mongodb.*;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import lombok.Getter;
//import org.bson.Document;
//
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//
///**
// * Created by Thomas on 9-4-2018.
// */
//public class DeviceRoutesDAO implements IDAO<DeviceRoutes> {
//
//    private MongoCollection<Document> collection;
//    @Getter
//    private static String errors = "";
//
//    public DeviceRoutesDAO() {
//        String uri = "mongodb+srv://admin:P%40ssw0rd@tdimco-wlvuy.mongodb.net/test";
//        MongoClientURI mongoClientURI = new MongoClientURI(uri, new MongoClientOptions.Builder().maxConnectionIdleTime(60000).connectTimeout(30000));
//        MongoClient mongoClient = new MongoClient(mongoClientURI);
//        MongoDatabase mongoDatabase = mongoClient.getDatabase("TDIMCO");
//        collection = mongoDatabase.getCollection("DeviceRoutes");
//
//        Logger mongoLogger = Logger.getLogger( "com.mongodb" );
//        mongoLogger.setLevel(Level.SEVERE);
//
//    }
//
//    @Override
//    public DeviceRoutes create(DeviceRoutes deviceRoutes) {
//        try {
//            Document document = new Document();
//            document.put("DeviceID",deviceRoutes.getDevice().getDevId());
//            document.put("VehicleType",deviceRoutes.getDevice().getVehicleType().toString());
//
//            document.put("Routes", deviceRoutes.getRoutes());
//            List<Object> list = new BasicDBList();
//            iterateAndPut(deviceRoutes, list);
//            document.put("Routes",list);
//
//            collection.insertOne(document);
//            return deviceRoutes;
//        } catch (MongoSocketException e) {
//            errors += e.toString() + "\n";
//        }
//        return null;
//    }
//
//    @Override
//    public DeviceRoutes read(DeviceRoutes deviceRoutes) {
//
//        try {
//            Document document = new Document("DeviceID", deviceRoutes.getDevice().getDevId());
//            FindIterable<Document> docs = collection.find(document);
//            if (docs.first() == null) return null;
//            Document rtrnDoc = docs.first();
//            String deviceID = rtrnDoc.get("DeviceID").toString();
//            String vehicleType = rtrnDoc.get("VehicleType").toString();
//            Device device = new Device(deviceID, vehicleType);
//            List<DeviceRoutes.DeviceRoute> dateroutes = (List<DeviceRoutes.DeviceRoute>) document.get("Routes");
//            DeviceRoutes rtrnDR = new DeviceRoutes(device);
//            rtrnDR.setRoutes(dateroutes);
//
//            return rtrnDR;
//        } catch (MongoSocketException e) {
//            errors += e.toString() + "\n";
//        }
//        return null;
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public DeviceRoutes update(DeviceRoutes deviceRoutes) {
//        try {
//            Document found = collection.find(new Document("DeviceID", deviceRoutes.getDevice().getDevId())).first();
//            Document updated = new Document("DeviceID", deviceRoutes.getDevice().getDevId());
//            updated.put("VehicleType", deviceRoutes.getDevice().getVehicleType().toString());
//            List<Object> list = new BasicDBList();
//            iterateAndPut(deviceRoutes, list);
//            List<Object> list2 = new BasicDBList();
//            list2.addAll((List<Object>) found.get("Routes"));
//            for(Object x : list2) {
//                if(!list.contains(x)) {
//                    list.add(x);
//                }
//            }
//            updated.put("Routes",list);
//            collection.findOneAndReplace(found, updated);
//            return this.read(deviceRoutes);
//        } catch (MongoSocketException e) {
//            errors += e.toString() + "\n";
//        }
//        return null;
//    }
//
//    private void iterateAndPut(DeviceRoutes deviceRoutes, List<Object> list) {
//        for(DeviceRoutes.DeviceRoute dr : deviceRoutes.getRoutes()) {
//            Document object = new Document();
//            object.put("Date",dr.getLdt().toLocalDate().toString());
//            object.put("Route",dr.getRoute().toString());
//            list.add(object);
//        }
//    }
//
//    @Override
//    public void delete(DeviceRoutes deviceRoutes) {
//        try {
//            Document document = new Document("DeviceID", deviceRoutes.getDevice().getDevId());
//            collection.deleteOne(document);
//        } catch (MongoSocketException e) {
//            errors += e.toString() + "\n";
//        }
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public List<DeviceRoutes> getAll() {
//        List<DeviceRoutes> list = new ArrayList<>();
//        FindIterable<Document> docs = collection.find();
//        for(Document d : docs) {
//            String devID = d.get("DeviceID").toString();
//            String vehicleType = d.get("VehicleType").toString();
//            Device device = new Device(devID, vehicleType);
//            List<DeviceRoutes.DeviceRoute> dateroutes =(List<DeviceRoutes.DeviceRoute>) d.get("Routes");
//            DeviceRoutes dr = new DeviceRoutes(device);
//            dr.setRoutes(dateroutes);
//            list.add(dr);
//        }
//        return list;
//    }
//
//    public void createOrUpdate(DeviceRoutes deviceRoutes) {
//        if(this.read(deviceRoutes) == null) {
//            this.create(deviceRoutes);
//        }
//        else {
//            this.update(deviceRoutes);
//        }
//    }
//}
