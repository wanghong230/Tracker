package com.example.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReceiver extends BroadcastReceiver {
	
	private boolean screenOn = false;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		if(arg1.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			screenOn = true;
		} else if (arg1.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			screenOn = false;
		}

		Intent trackerService = new Intent(arg0, TrackerService.class);
		trackerService.putExtra("screen_state", screenOn);
		arg0.startService(trackerService);
	}
	
}
