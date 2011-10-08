package uk.ac.cam.jas250.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class NewGame extends Activity implements OnClickListener, OnCheckedChangeListener{
	
	EditText txtName;
	EditText txtPostCode; 
	
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
	}
	
	public void onStart(){
		super.onStart();
	}

	public void onClick(View arg0) {
		Intent myIntent = new Intent(arg0.getContext(), GameView.class);
		startActivity(myIntent);
	}

	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		txtPostCode.setEnabled(arg1);
	}

}
