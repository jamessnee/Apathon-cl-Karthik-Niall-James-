package uk.ac.cam.jas250.statsgame;

import java.io.IOException;
import java.net.URL;

import com.google.appengine.api.urlfetch.*;

import java.util.List;

public class NeighbourhoodStatQuery {
	
	//http://neighbourhood.statistics.gov.uk/NDE2/Disco/FindAreas?Postcode=PO155RR
	
	public static String getRegionFromPostcode(String postcode, int granularity){
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		
		try{
			String url_str = "http://neighbourhood.statistics.gov.uk/NDE2/Disco/FindAreas?Postcode="+postcode;
			URL url = new URL(url_str);
			HTTPResponse response = fetcher.fetch(url);
			
			byte[] content = response.getContent();
			
			int responseCode = response.getResponseCode();
			System.out.println(responseCode);
			List<HTTPHeader> headers = response.getHeaders();
			
			for(HTTPHeader header: headers){
				String headerName = header.getName();
				String headerValue = header.getValue();
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static Stat getStat(Metric m, String region){
		return new Stat("",0.0);
	}

}
