package com.example.tracker;

import java.util.ArrayList;
import Client.TestClient;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity implements View.OnClickListener{

	private String TAG = this.getClass().getSimpleName();
	private Intent trackerService = null;
	private Button startButton = null;
	private Button stopButton = null;
	private TextView status = null;
//	private LinearLayout fakeLayout = null;
//	private WindowManager mWindowManager = null;
//	private WindowManager.LayoutParams params = null;
	
	/** Flag to track the service status */
	private boolean serviceStatus = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.i(TAG, "onCreat() is called"); 
        super.onCreate(savedInstanceState);
        
        /** Create the Intent for trackerService */
        trackerService = new Intent(this, TrackerService.class);
        
        setContentView(R.layout.activity_main);
        
        /** Bind the button listener here */
        startButton = (Button)findViewById(R.id.startbutton);
        stopButton = (Button)findViewById(R.id.stopbutton);
        status = (TextView)findViewById(R.id.status);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        status.setText("Stopped!");
        
//		/** Create and configure the fake layout for service */
//		if(fakeLayout == null) {
//			fakeLayout = new LinearLayout(this);
//			LayoutParams layoutPrams = new LayoutParams(400, LayoutParams.MATCH_PARENT);
//			fakeLayout.setLayoutParams(layoutPrams);
//		}
//		
//		/** Fetch WindowManager and add fake layout to it */
//		if(mWindowManager == null) {
//			mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
//		}
//		
//		if(params == null) {
//			params = new WindowManager.LayoutParams(
//					400,
//					WindowManager.LayoutParams.MATCH_PARENT,
//					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
//	                PixelFormat.TRANSLUCENT);
//			params.gravity = Gravity.LEFT | Gravity.TOP;
//		}
   
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
		/** Ensure stop the trackerService */
//		mWindowManager.removeView(fakeLayout);
		stopService(trackerService);
		super.onDestroy();
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()) {
			case R.id.startbutton:
				Log.i(TAG, "startbutton clicked.");
				/** Start the trackerService */
				if(serviceStatus == false) {
					startService(trackerService);
					serviceStatus = true;
				}
				status.setText("Started!");
//				mWindowManager.addView(fakeLayout, params);			
				break;
			case R.id.stopbutton:
				Log.i(TAG, "stopbuttion clicked.");
				/** Stop the trackerService */
				if(serviceStatus == true) {
					stopService(trackerService);
					serviceStatus = false;
				}
				status.setText("Stopped!");
//				mWindowManager.removeView(fakeLayout);
				break;
			default:
					break;
		}	
	}
	
	private boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
}
