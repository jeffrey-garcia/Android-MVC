package com.jeffrey.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jeffrey.hsbcui.HsbcButton;
import com.jeffrey.hsbcui.HsbcViewInflater;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		startActivity(HsbcWebviewActivity.class, "layout/t_c.xml", "file:///android_asset/web/tc.html", true);
		
//		try {
//			View view = HsbcViewInflater.getViewLayout(getApplicationContext(),"layout/activity_main.xml");
//			setContentView(view);
//			
//			final HsbcButton button1 = (HsbcButton) view.findViewWithTag("1");
//			//Log.d(getClass().getName(), " button1 ID: " + button1.getId());
//			//Log.d(getClass().getName(), " button1 TAG: " + button1.getTag());
//			button1.setOnClickListener(new Button.OnClickListener() {
//				public void onClick(View v) {
//					startActivity(HsbcActivity.class, "layout/activity_main.xml", "", false);
//				}
//			});
//			
//			HsbcButton button2 = (HsbcButton) view.findViewWithTag("2");
//			//Log.d(getClass().getName(), " button2 ID: " + button2.getId());
//			//Log.d(getClass().getName(), " button2 TAG: " + button2.getTag());
//			button2.setOnClickListener(new Button.OnClickListener() {
//				public void onClick(View v) {
//					startActivity(HsbcWebviewActivity.class, "layout/activity_webview.xml", "https://www.google.com", false);
//				}
//			});
//			
//		} catch (final Throwable throwable) {
//			Log.e("onCreate()", throwable.getMessage(), throwable);
//		}
	}

//	@Override
//	protected void onPause() {
//		Log.d(getClass().getName(), "onPause()");
//		super.onPause();
//	}
//	
//	@Override
//	protected void onStop() {
//		Log.d(getClass().getName(), "onStop()");
//		super.onStop();
//		
//		if (isFinishing()) {
//			Log.d(getClass().getName(), "activity is finishing...");
//		}
//	}
//	
//	@Override
//	protected void onDestroy() {
//		Log.d(getClass().getName(), "onDestroy()");
//		super.onDestroy();
//	}
	
	protected void startActivity(final Class<?> activityClass, final String layoutXml, final String url, boolean finishCurrentActivity) {
		Intent theIntent = new Intent(this, activityClass);
		// ensure only one instance of activity is invoked
		theIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		Bundle theBundle = new Bundle();
		theBundle.putString(HsbcActivity.LAYOUT, layoutXml);
		theBundle.putString(HsbcActivity.URL, url);
		
		theIntent.putExtras(theBundle);
		startActivity(theIntent);
		
		if (finishCurrentActivity) {
			finish();
		}
	}
}
