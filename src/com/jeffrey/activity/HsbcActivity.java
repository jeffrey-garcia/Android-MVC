package com.jeffrey.activity;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.jeffrey.hsbcui.HsbcView;
import com.jeffrey.hsbcui.HsbcViewInflater;

public class HsbcActivity extends Activity implements PropertyChangeListener {

	public final static String LAYOUT = "layout";
	public final static String URL = "URL";
	
	protected View layoutView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(getClass().getName(), "onCreate()");
		super.onCreate(savedInstanceState);
		
		// initialize the content view dynamically
		initContentView();
		
		// create the observer
		createObserver(layoutView);
	}
	
	@Override
	protected void onDestroy() {
		Log.d(getClass().getName(), "onDestroy()");
		super.onDestroy();
	}
	
	protected void initContentView() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		try {
			if (extras!=null && extras.getString(LAYOUT)!=null) {
				String layoutXml = extras.getString(LAYOUT);
				Log.d(getClass().getName(), "initContentView(), layout xml: " + layoutXml);
				
				// Inflate the layout XML passed in by the caller
				layoutView = HsbcViewInflater.getViewLayout(getApplicationContext(), layoutXml);
				
				// set the content view
				setContentView(layoutView);
			}
			
		} catch (final Throwable throwable) {
			Log.e("initContentView()", throwable.getMessage(), throwable);
		}
	}
	
	protected View findViewFromLayout(final View view, final String className) throws ClassNotFoundException {
		View targetView = null;
		
		if (view.getClass() == Class.forName(className)) {
			//Log.d(getClass().getName(), "findViewFromLayout(), targetView: " + view.getClass().getName());
			targetView = view;
			
		} else {
			if (view instanceof ViewGroup) {
				for (int i=0; i<((ViewGroup)view).getChildCount(); i++) {
					final View childView = ((ViewGroup)view).getChildAt(i);
					targetView = findViewFromLayout(childView, className);
					if (targetView != null) {
						//Log.d(getClass().getName(), "findViewFromLayout(), targetView: " + childView.getClass().getName());
						break;
					}
				}
			}
		}
		
		return targetView;
	}
	
	protected void createObserver(final View view) {
		Log.d(getClass().getName(), "createObserver(), view: " + view.getClass().getName());
		createObserverFromView(view);
	}
	
	protected void createObserverFromView(final View view) {
		if (view instanceof ViewGroup) {
			// iterate the child view
			for (int i=0; i<((ViewGroup)view).getChildCount(); i++) {
				final View childView = ((ViewGroup)view).getChildAt(i);
				createObserverFromView(childView);
			}
			
		} else {
			// create the observer
			createObserverFromHsbcUi(view);
		}
	}
	
	protected void createObserverFromHsbcUi(final View view) {
		if (view instanceof HsbcView) {
			Log.d(getClass().getName(), "createObserverFromHsbcUi(), addPropertyChangeListener for view: " + view.getClass().getName());
			((HsbcView) view).addPropertyChangeListener(this);
		}
	}
	
	public void propertyChange(final PropertyChangeEvent event) {
		View view = (View)event.getSource();
		String key = event.getPropertyName();
		Object value = event.getNewValue();
		Log.d(getClass().getName(), "propertyChange(), tag:" + view.getTag().toString());
		Log.d(getClass().getName(), "propertyChange(), key:" + key);
		Log.d(getClass().getName(), "propertyChange(), value:" + value.toString());
	}
	
}
