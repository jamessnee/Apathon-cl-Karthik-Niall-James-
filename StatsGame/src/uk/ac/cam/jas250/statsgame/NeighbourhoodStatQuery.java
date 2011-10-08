package uk.ac.cam.jas250.statsgame;

import java.io.IOException;
import java.net.URL;

import org.xml.sax.*;
import javax.xml.parsers.*;

import com.google.appengine.api.urlfetch.*;

public class NeighbourhoodStatQuery {
	
	//http://neighbourhood.statistics.gov.uk/NDE2/Disco/FindAreas?Postcode=PO155RR
	
	public static String getRegionFromPostcode(String postcode, int granularity){
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		
		try{
			String url_str = "http://neighbourhood.statistics.gov.uk/NDE2/Disco/FindAreas?Postcode="+postcode;
			URL url = new URL(url_str);
			HTTPResponse response = fetcher.fetch(url);
			String responseString = new String(response.getContent());
			
			String areaId = parseXMLResponsefromRegion(responseString);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static Stat getStat(Metric m, String region){
		return new Stat("",0.0);
	}
	
	
	private static String parseXMLResponsefromRegion(String responseString){
		return "";
	}

}
