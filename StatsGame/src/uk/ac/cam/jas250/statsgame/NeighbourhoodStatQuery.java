package uk.ac.cam.jas250.statsgame;

import java.io.IOException;
import java.net.URL;
import java.util.SortedMap;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

import javax.xml.parsers.*;

import com.google.appengine.api.urlfetch.*;

public class NeighbourhoodStatQuery {

	// http://neighbourhood.statistics.gov.uk/NDE2/Disco/FindAreas?Postcode=PO155RR

	public static String[] getAreasFromPostcode(String postcode) {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// Using factory get an instance of document builder
		DocumentBuilder db;
		String[] areas = new String[0];
		try {
			db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			Document dom = db
					.parse("http://neighbourhood.statistics.gov.uk/NDE2/Disco/FindAreas?Postcode="
							+ postcode);
			
			NodeList nl = dom.getElementsByTagName("Area");
			SortedMap<String, String> sm = new TreeMap<String, String>();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				sm.put(n.getChildNodes().item(0).getTextContent(), n.getChildNodes().item(2).getTextContent());
			}
			
			areas = sm.values().toArray(areas);
			return areas;
				
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Stat getStat(Metric m, String region) {
		return new Stat("", 0.0);
	}

	private static String parseXMLResponsefromRegion(String responseString) {
		return "";
	}

}
