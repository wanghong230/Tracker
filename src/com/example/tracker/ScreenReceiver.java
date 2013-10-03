package com.example.tracker;

import java.util.ArrayList;
import Client.TestClient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings.Secure;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
	
	private String TAG = this.getClass().getSimpleName();
	
	private boolean isScreenOn = true;
	private boolean isUserPresent = true;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		
		// TODO Auto-generated method stub
		if(arg1.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			isScreenOn = true;
			AggregateMessages.cleanMessages();
		} else if (arg1.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			isScreenOn = false;
			isUserPresent = false;
			Log.w(TAG, AggregateMessages.getMessages());
			AggregateMessages.addMessages(null, true);
			/** Send aggregated messages to server here */	
		} else if(arg1.getAction().equals(Intent.ACTION_USER_PRESENT)) {
			isScreenOn = true;
			isUserPresent = true;
			/** Clean the messages */
			AggregateMessages.cleanMessages();
		}
		
		Intent trackerService = new Intent(arg0, TrackerService.class);
		trackerService.putExtra("isScreenOn", isScreenOn);
		trackerService.putExtra("isUserPresent", isUserPresent);
		arg0.startService(trackerService);
	}
}
