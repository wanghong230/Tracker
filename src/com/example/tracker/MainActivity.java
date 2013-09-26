package com.example.tracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

	private String TAG = this.getClass().getSimpleName();
	private Intent trackerService = null;
	private Button startButton = null;
	private Button stopButton = null;
	private boolean serviceStatus = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.i(TAG, "onCreat() is called"); 
        super.onCreate(savedInstanceState);
        trackerService = new Intent(this, TrackerService.class);
        setContentView(R.layout.activity_main);
        
        /** Bind the button listener here */
        startButton = (Button)findViewById(R.id.startbutton);
        stopButton = (Button)findViewById(R.id.stopbutton);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(serviceStatus == true) {
			stopService(trackerService);
			serviceStatus = false;
		}
		super.onDestroy();
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()) {
			case R.id.startbutton:
				Log.i(TAG, "startbutton clicked.");
				if(serviceStatus == false) {
					startService(trackerService);
					serviceStatus = true;
				}
				break;
			case R.id.stopbutton:
				Log.i(TAG, "stopbuttion clicked.");
				if(serviceStatus == true) {
					stopService(trackerService);
					serviceStatus = false;
				}
				break;
			default:
					break;
		}		
	}
}
