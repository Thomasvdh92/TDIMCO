package TDIMCO.datareader;

import TDIMCO.domain.Detector;
import TDIMCO.domain.Device;
import TDIMCO.domain.VehicleType;
import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class used to iterate through xml files. Class can be used to iterate through a folder containing multiple xml
 * files. This class is limited to files that are similar to this example:
 * "E:\Data Haven Bedrijf\Originele Data\2014 5\01.xml
 */
@Data
public class XmlIterator {

    private SpanCollection spanCollection;
    public int perc;
    private final static HashMap<String, Integer> vehicleTypeHits = new HashMap<>();

    public XmlIterator() {
        spanCollection = new SpanCollection();
        vehicleTypeHits.put("C", 0);
        vehicleTypeHits.put("T", 0);
        vehicleTypeHits.put("U", 0);
    }

    public void iterateXmlFile(String xmlUrl) {
        File xmlFile = new File(xmlUrl);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList deviceNodeList = doc.getElementsByTagName("dev");
            System.out.println(getDateFromFilePath(xmlUrl) + " --> Iterating " + deviceNodeList.getLength() + " nodes");

            // Iterate through the list of nodes
            iterateXmlNodelist(deviceNodeList, false);
            spanCollection.determineStandardDeviation();
            iterateXmlNodelist(deviceNodeList, true);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void iterateXmlNodelist(NodeList deviceNodeList, boolean secondIteration) {

        perc = 10;
        for (int i = 0; i < deviceNodeList.getLength(); i++) {
            Node node = deviceNodeList.item(i);
            if(i==deviceNodeList.getLength()-2) System.out.println("100% completed");
            if (((i * 100) / deviceNodeList.getLength()) >= perc) {
                System.out.println(perc + "% done");
                perc += 10;
            }
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String vehicleType = String.valueOf(element.getAttribute("c"));
                incrementVehicleHits(vehicleType);
                String devId = String.valueOf(element.getAttribute("id"));
                NodeList detectionNodeList = element.getElementsByTagName("rdd");
                Device device = new Device(devId, vehicleType);
                spanCollection.addDevice(device);
                if (detectionNodeList.getLength() > 1) {
                    if(secondIteration) spanCollection.compileRoutes(device, detectionNodeList);
                    for (int j = 0; j < detectionNodeList.getLength()-1; j++) {
                        Node detectionNode = detectionNodeList.item(j);
                        Element detection1 = (Element) detectionNode;
                        for(int k= j+1; k<detectionNodeList.getLength(); k++) {
                            detectionNode = detectionNodeList.item(k);
                            Element detection2 = (Element) detectionNode;
                            if (Integer.parseInt(detection1.getAttribute("d")) != Integer.parseInt(detection2.getAttribute("d"))) {
                                spanCollection.addRouteDetection(detection1, detection2, secondIteration);
                            }
                        }
                    }
                }

            }
        }
    }

    private void incrementVehicleHits(String s) {
        switch(s) {
            case "U":
                vehicleTypeHits.put("U", vehicleTypeHits.get("U")+1);
                break;
            case "C":
                vehicleTypeHits.put("C", vehicleTypeHits.get("C")+1);
                break;
            case "T":
                vehicleTypeHits.put("T", vehicleTypeHits.get("T")+1);
                break;
        }
    }

    public void iterateXmlFolder(String folderPath) {
        int prcnt = 10;
        int i=0;
        File[] files = new File(folderPath).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                iterateXmlFolder(file.getPath());
            }
//            iterateXmlFile(file.getAbsolutePath());
            i++;
            System.out.println(i + "/" + files.length +" files done");
        }
    }

    /**
     * Method used to get the date from a file path.
     * @param filepath file to get the date from
     * @return Date of the file
     */
    private String getDateFromFilePath(String filepath) {
        File xmlFile = new File(filepath);
        String folderPath = xmlFile.getParent();
        String yearAndMonth = folderPath.substring(37);
        String year = yearAndMonth.substring(0, 4);
        String month = yearAndMonth.substring(5);
        String day = xmlFile.getName().substring(0, 2);
        Date date = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void printVehicleHits() {
        for(String s : vehicleTypeHits.keySet()) {
            System.out.println(s + " - " +vehicleTypeHits.get(s));
        }
    }

}
