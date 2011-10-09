package uk.ac.cam.jas250.main;

import uk.ac.cam.jas250.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Connect the buttons up
        Button btnNewGame = (Button) findViewById(R.id.btnNewGame);
        Button btnHighScores = (Button) findViewById(R.id.btnHighScores);
        btnNewGame.setOnClickListener(this);
        btnHighScores.setOnClickListener(this);
    }

	public void onClick(View argc) {
		Button caller = (Button) argc;
		if(caller.getId()==findViewById(R.id.btnNewGame).getId()){
			//Start the new game activity
			Intent myIntent = new Intent(argc.getContext(),NewGame.class);
			startActivity(myIntent);
		}else if(caller.getId()==findViewById(R.id.btnHighScores).getId()){
			//Start the high scores activity
			
		}
	}
}