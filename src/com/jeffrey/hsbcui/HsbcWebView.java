package com.jeffrey.hsbcui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class HsbcWebView extends WebView implements HsbcView {
	private PropertyChangeSupport changeSupport;
	private PropertyChangeListener changeListener;
	
	protected int LOADING_STATUS = 0;
	
	public HsbcWebView(Context context) {
		super(context);
		initUi();
	}

    public HsbcWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUi();
    }
	
    public HsbcWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		initUi();
    }

	@Override
	public void initUi() {
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
	}
    
	@Override
	public void set(String key, Object newValue) {
		if (key.equalsIgnoreCase("loadingStatus")) {
			Object oldValue = get(key);
			if (Integer.parseInt(newValue.toString()) == 0) {
				LOADING_STATUS = 0;
			} else {
				LOADING_STATUS = 1;
			}
			changeSupport.firePropertyChange(key, oldValue, newValue);
			return;
		}
		
		if (key.equalsIgnoreCase("hidden")) {
			Object oldValue = get(key);
			if (Integer.parseInt(newValue.toString()) == 0) {
				this.setVisibility(VISIBLE);// set to visible
			} else {
				this.setVisibility(INVISIBLE);// set to invisible
			}
			changeSupport.firePropertyChange(key, oldValue, newValue);
			return;
		}
	}

	@Override
	public Object get(String key) {
		if (key.equalsIgnoreCase("loadingStatus")) {
			return LOADING_STATUS + "";
		}
		
		if (key.equalsIgnoreCase("hidden")) {
			return this.getVisibility()+"";
		}		
		
		return null;
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		if (changeListener == null) { // avoid adding multiple listener
			changeListener = listener;
			changeSupport.addPropertyChangeListener(changeListener);
		} else {
			throw new RuntimeException("Invalid Operation, at least one Property Change Listener is already added.");
		}
	}
	
	public void removePropertyChangeListener() {
		if (changeListener != null) { // avoid removing when no listener been added
			changeSupport.removePropertyChangeListener(changeListener);
			changeListener = null;
		}
	}
}
