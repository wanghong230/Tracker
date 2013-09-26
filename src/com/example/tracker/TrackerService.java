package com.example.tracker;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;

public class TrackerService extends Service {

	private String TAG = this.getClass().getSimpleName();
	private BroadcastReceiver receiver = null;
	private boolean isScreenOn = false;
	
	/** UserPresent is more important to flag to start or stop to track the user behavior */
	private boolean isUserPresent = false;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "Service onCreate: the number of processes is " + getTotalRunningApp());
		
		/** Create the filter to contain three Actions: ScreenOn, ScreenOff, UserPresent */
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		
		receiver = new ScreenReceiver();
		/** Register the Broadcast Receiver to make it work */
		registerReceiver(receiver, filter);
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		/** Phone has three state: 
		 * 		Screen Off: turn off the screen
		 * 	    Screen On:
		 * 			1.User not Present: before unlock the phone;
		 * 			2.User Present: phone unlocked.
		 */
		
		isScreenOn = intent.getBooleanExtra("isScreenOn", true);
		isUserPresent = intent.getBooleanExtra("isUserPresent", true);
		
		if(isScreenOn) {
			Log.i(TAG, "Screen is on!");
		} else {
			Log.i(TAG, "Screen is off!");
		}
		
		if(isUserPresent) {
			Log.i(TAG, "User is present!");
		} else {
			Log.i(TAG, "User not present!");
		}
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		/** Before destroy to unregister the Broadcast Receiver first(Avoid memory leak)*/
		unregisterReceiver(receiver);
		Log.i(TAG, "Boardcast Receiver Unregistered.");
		super.onDestroy();
		Log.i(TAG, "Service onDestroy.");
	}
	
	/** Return the number of running processes right now */
	public int getTotalRunningApp(){
	    ActivityManager actvityManager = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
	    List<RunningAppProcessInfo> procInfos = actvityManager.getRunningAppProcesses();
	    return procInfos.size();
	}
}
