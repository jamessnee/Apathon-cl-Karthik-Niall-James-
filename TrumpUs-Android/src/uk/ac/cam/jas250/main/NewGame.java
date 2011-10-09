package uk.ac.cam.jas250.main;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class NewGame extends Activity implements OnClickListener, OnCheckedChangeListener, LocationListener{
	
	private EditText txtName;
	private EditText txtPostCode;
	
	private LocationManager locMan;
	
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.newgame);
		
		//Attach stuff
		txtName = (EditText)findViewById(R.id.txtName);
		txtPostCode = (EditText)findViewById(R.id.txtPostcode);
		CheckBox chkbxManualPc = (CheckBox)findViewById(R.id.chkbxManualPc);
		Button btnFindGame = (Button)findViewById(R.id.findGame);
		chkbxManualPc.setOnCheckedChangeListener(this);
		btnFindGame.setOnClickListener(this);
		
		locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
		startListening();
	}
	
	@Override
    protected void onDestroy() {
        stopListening();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        stopListening();
        super.onPause();
    }

    @Override
    protected void onResume() {
        startListening();
        super.onResume();
    }

	public void onClick(View arg0) {
		//Get the usersname
		String username = getUsersName();
		
		//Get the postcode
		String postcode = getUsersPostcode();
		postcode = postcode.replaceAll(" ", "");
		
		Intent myIntent = new Intent(arg0.getContext(), GameView.class);
		myIntent.putExtra("username", username);
		myIntent.putExtra("postcode", postcode);
		startActivity(myIntent);
	}

	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		txtPostCode.setEnabled(arg1);
		if(arg1){
			stopListening();
			txtPostCode.setText("");
		}else{
			startListening();
			txtPostCode.setText("");
		}
	}
	
	public String getUsersName(){
		return txtName.getText().toString();
	}
	
	public String getUsersPostcode(){
		if(txtPostCode.toString().equals("")){
			sendToast("No Postcode");
		}
		System.out.println("Postcode "+txtPostCode.getText().toString());
		return txtPostCode.getText().toString();
	}

	
	//Location Stuff
	private void startListening() {
        locMan.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 
            0, 
            0, 
            this
        );
        System.out.println("Starting location stuff");
    }

    private void stopListening() {
        if (locMan != null)
        	locMan.removeUpdates(this);
    }

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		// we got new location info. lets display it in the textview
        String s = "";
        s += "Time: "        + location.getTime() + "\n";
        s += "\tLatitude:  " + location.getLatitude()  + "\n";
        s += "\tLongitude: " + location.getLongitude() + "\n";
        s += "\tAccuracy:  " + location.getAccuracy()  + "\n";
        System.out.println("GOT LOCATION");
        
        try{
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            txtPostCode.setText(addresses.get(0).getPostalCode());
            txtPostCode.setEnabled(true);
        }catch(IOException e){
        	this.sendToast("SHIT HAPPENED");
        }
        
	}

	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	private void sendToast(String message){
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(this, text, duration);
		toast.show();
	}

}
