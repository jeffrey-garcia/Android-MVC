package com.jeffrey.hsbcui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class HsbcTextView extends TextView implements HsbcView {
	private PropertyChangeSupport changeSupport;
	private PropertyChangeListener changeListener;
	
	public HsbcTextView(final Context context) {
		super(context);
		initUi();
	}
	
	public HsbcTextView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		initUi();
	}
	
	public HsbcTextView(final Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initUi();
	}
	
	@Override
	public void initUi() {
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
	}
	
	public void set(final String key, final Object newValue) {
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
	
	public Object get(final String key) {
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
