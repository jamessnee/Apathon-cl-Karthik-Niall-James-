package uk.ac.cam.jas250.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class GameView extends Activity{
	
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.gameview);
		
		ImageView backgroundImageV = (ImageView)findViewById(R.id.imgBackground);
		backgroundImageV.setImageResource(R.drawable.cardbackground);
		
		//Setup the player - store: playerId
		Intent self = getIntent();
		String values = "rbtype=0&name="+self.getStringExtra("username")+"&postcode="+self.getStringExtra("postcode");
		JSONObject json = postRequest(values);
		System.out.println(json.toString());
		
		//Join Lobby - rqtype=2, playerId=... , store: lobby
		
		//Join Game - rqtype=3, playerId=..., store: gameId
		
		//LOOP Check status untill collect cards, store: number
		
		//Collect cards rqtype=4, gameId, playerId, store: card (says if it's my turn)
		
		//Wait until choose state (query status) rqtype=8
		
		//When choose - rqtype=5, playerId, gameId, metricId, direction
		
		//Check status for get result, who won
		
	}
	
	public JSONObject postRequest(String values){
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httppost = new HttpGet("http://www.trumpus-appathon.appspot.com/statsgame?"+values);

	    try {
	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	        //Get the json
	        JSONObject json = new JSONObject();


            HttpEntity entity = response.getEntity();

            if (entity != null) {

                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);

                json=new JSONObject(result);

                instream.close();
                
                return json;
            }
	    } catch (ClientProtocolException e) {
	        sendToast("Stuff went wrong");
	    } catch (IOException e) {
	    	sendToast("Stuff went wrong");
	    } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
	
	private void sendToast(String message){
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(this, text, duration);
		toast.show();
	}
	    /**
	     *
	     * @param is
	     * @return String
	     */
	    public static String convertStreamToString(InputStream is) {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();

	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	                System.out.println(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }

}