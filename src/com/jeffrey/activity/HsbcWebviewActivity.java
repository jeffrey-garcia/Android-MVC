package com.jeffrey.activity;

import java.beans.PropertyChangeEvent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.ProgressBar;

import com.jeffrey.hsbcui.HsbcButton;
import com.jeffrey.hsbcui.HsbcWebView;
import com.jeffrey.util.HsbcWebViewClient;

public class HsbcWebviewActivity extends HsbcActivity {

	protected HsbcWebView webView;
	protected ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(getClass().getName(), "onCreate()");
		super.onCreate(savedInstanceState);
		
		// instantiate the progress bar before webview
		initProgressBar();
		
		// instantiate the URL and webview once only when the view is loaded
		initWebview();
	}
	
	protected void initProgressBar() {
		try {
			View view = findViewFromLayout(layoutView, "android.widget.ProgressBar");
			if (view == null) {
				Log.d(getClass().getName(), "No progress bar found from layout ");
				return; 
			}
			
			// cast the View into ProgressBar
			progressBar = (ProgressBar) view;
			
			// default is invisible
			progressBar.setVisibility(View.INVISIBLE);
			
		} catch (final Throwable throwable) {
			Log.e("initWebview()", throwable.getMessage(), throwable);
		}
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	protected void initWebview() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		try {
			if (extras!=null && extras.getString(URL)!=null) {
				String url = extras.getString(URL);
				Log.d(getClass().getName(), "initWebview(), url: " + url);
				
				webView = (HsbcWebView) findViewFromLayout(layoutView, "com.jeffrey.hsbcui.HsbcWebView");
				Log.d(getClass().getName(), "findWebview() - webView: " + webView);
				
				if (webView != null) {
					webView.setWebChromeClient(new WebChromeClient());
					webView.setWebViewClient(new HsbcWebViewClient(this, webView, progressBar));
					webView.requestFocus(View.FOCUS_DOWN);
					webView.getSettings().setJavaScriptEnabled(true);
					webView.getSettings().setSaveFormData(true);
					webView.loadUrl(url); 
				}
			}
			
		} catch (final Throwable throwable) {
			Log.e("initWebview()", throwable.getMessage(), throwable);
		}
	}
	
//	protected WebView findWebview(final View view) {
//		//Log.d("*** view:", view.getClass().getName());
//		WebView theWebView = null;
//		
//		if (view instanceof WebView) {
//			//Log.d("*** instance is webview", view.getClass().getName());
//			theWebView = (WebView) view;
//			
//		} else {
//			if (view instanceof ViewGroup) {
//				for (int i=0; i<((ViewGroup)view).getChildCount(); i++) {
//					final View childView = ((ViewGroup)view).getChildAt(i);
//					//Log.d("*** child view:", childView.getClass().getName());
//					theWebView = findWebview(childView);
//					if (theWebView != null) {
//						break;
//					}
//				}
//			}
//		}
//		
//		return theWebView;
//	}
	
	public void propertyChange(final PropertyChangeEvent event) {
		View view = (View)event.getSource();
		String key = event.getPropertyName();
		Object value = event.getNewValue();
		Log.d(getClass().getName(), "propertyChange(), tag:" + view.getTag().toString());
		Log.d(getClass().getName(), "propertyChange(), key:" + key);
		Log.d(getClass().getName(), "propertyChange(), value:" + value.toString());
	}
}
