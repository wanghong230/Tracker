package com.example.tracker;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;

public class TrackerService extends Service {

	private String TAG = this.getClass().getSimpleName();
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
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "Service onDestroy");
	}
	
	/** Return the number of running processes right now */
	public int getTotalRunningApp(){
	    ActivityManager actvityManager = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
	    List<RunningAppProcessInfo> procInfos = actvityManager.getRunningAppProcesses();
	    return procInfos.size();
	}
}
