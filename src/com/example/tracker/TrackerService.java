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

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.graphics.PixelFormat;

import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;



public class TrackerService extends Service implements OnTouchListener{

	private String TAG = this.getClass().getSimpleName();
	private BroadcastReceiver receiver = null;
	private boolean isScreenOn = false;
	
	private LinearLayout fakeLayout;
	private WindowManager mWindowManager;
	
	/** UserPresent is more important to flag to start or stop to track the user behavior */
	private boolean isUserPresent = false;
	/** Keep the previous "RecentTaskList" to compare with latest one, 
	 * if not match, some app has been opened */
	private List<ActivityManager.RecentTaskInfo> recentTaskListPrevious = null;
	
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
		
		/** Create and configure the fake layout for service */
		fakeLayout = new LinearLayout(this);
		LayoutParams layoutPrams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
		fakeLayout.setLayoutParams(layoutPrams);
		fakeLayout.setOnTouchListener(this);
		
		/** Fetch WindowManager and add fake layout to it */
		mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				0,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.LEFT | Gravity.TOP;
		mWindowManager.addView(fakeLayout, params);
		
		/** Initialize the recentTaskListPrevious */
		ActivityManager actvityManager = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
		recentTaskListPrevious = actvityManager.getRecentTasks(10, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
		
		/** Create the filter to contain three Actions: ScreenOn, ScreenOff, UserPresent */
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		
		/** Register the Broadcast Receiver to make it work */
		receiver = new ScreenReceiver();
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
			/** Start the tracking */
			
			
		} else {
			Log.i(TAG, "User not present!");
			/** Stop the tracking */
			
			
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		/** Before destroy to unregister the Broadcast Receiver first(Avoid memory leak)*/
		unregisterReceiver(receiver);
		
		if(mWindowManager != null) {
			if(fakeLayout != null) {
				mWindowManager.removeView(fakeLayout);
			}
		}
		
		Log.i(TAG, "Boardcast Receiver Unregistered.");
		super.onDestroy();
		Log.i(TAG, "Service onDestroy.");
	}
	
	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if(arg1.getAction() ==  MotionEvent.ACTION_OUTSIDE) {
			Log.i(TAG, "Recorded Touch Outside the view.");
			Log.i(TAG, "App Status Changed:" + isAppStatusChanged());
		}
		return true;
	}

	/** Return the number of running processes right now */
	public int getTotalRunningApp() {
	    ActivityManager actvityManager = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
	    List<RunningAppProcessInfo> procInfos = actvityManager.getRunningAppProcesses();
	    return procInfos.size();
	}
	
	public boolean isAppStatusChanged() {
		/** Get latest recentTaskList */
		ActivityManager actvityManager = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
		List<ActivityManager.RecentTaskInfo> recentTaskList = actvityManager.getRecentTasks(10, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
		/** Compare the recentTaskList with previous */
		/** Need to be optimized in the future */
		for(int i = 0; i < recentTaskList.size(); i++) {
			ActivityManager.RecentTaskInfo recent = recentTaskList.get(i);
			/** Double check the list size, two list may not have the same size */
			if(i < recentTaskListPrevious.size()) {
				ActivityManager.RecentTaskInfo previous = recentTaskListPrevious.get(i);
				if(recent.persistentId != previous.persistentId) {
					recentTaskListPrevious = recentTaskList;
					return true;
				}
				Log.i(TAG, "Recent " + i + ":" + recent.persistentId);
				Log.i(TAG, "Previs " + i + ":" + previous.persistentId);
			} else {
				recentTaskListPrevious = recentTaskList;
				return true;
			}
		}	
		return false;	
	}
}
