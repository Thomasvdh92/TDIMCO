package TDIMCO.datareader;

import TDIMCO.domain.Detector;
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
 * Created by Thomas on 18-3-2018.
 */
@Data
public class XmlIterator {

    private TreeSet<Detector> allDetectors;
    private SpanCollection spanCollection;
    public int perc;

    public XmlIterator() {
        spanCollection = new SpanCollection();
        allDetectors = new TreeSet<>();
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
            iterateXmlNodelist(deviceNodeList, true);
            spanCollection.determineStandardDevation();

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
                NodeList detectionNodeList = element.getElementsByTagName("rdd");
                if (detectionNodeList.getLength() > 1) {
                    for (int j = 0; j < detectionNodeList.getLength()-1; j++) {
                        Node detectionNode = detectionNodeList.item(j);
                        Element detection1 = (Element) detectionNode;
                        detectionNode = detectionNodeList.item(j + 1);
                        Element detection2 = (Element) detectionNode;
                        if (Integer.parseInt(detection1.getAttribute("d")) != Integer.parseInt(detection2.getAttribute("d"))) {
                            spanCollection.addRouteDetection(detection1, detection2, secondIteration);
                        }
                    }
                }

            }
        }
    }

    public void iterateXmlFolder(String folderPath) {
        int prcnt = 10;
        int i=0;
        File[] files = new File(folderPath).listFiles();
        for (File file : files) {
            i++;
            if (((i * 100) / files.length) >= prcnt) {
                System.out.println(prcnt + "% of " + files.length +" files done");
                prcnt += 10;
            }
            iterateXmlFile(file.getAbsolutePath());
        }
    }

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

    public TreeSet<Detector> getAllDetectors() {
        return allDetectors;
    }
}
