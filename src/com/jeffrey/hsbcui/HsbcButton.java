package com.jeffrey.hsbcui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class HsbcButton extends Button implements HsbcView {
	private PropertyChangeSupport changeSupport;
	private PropertyChangeListener changeListener;
	private OnClickListener clickListener;
	
	public HsbcButton(final Context context) {
		super(context);
		initUi();
	}
	
	public HsbcButton(final Context context, AttributeSet attrs) {
		super(context, attrs);
		initUi();
	}
	
	public HsbcButton(final Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initUi();
	}

	@Override
	public void initUi() {
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
		if (clickListener == null) {
			this.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					set("clicked", "1");
				}
			});
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
		
		if (key.equalsIgnoreCase("enabled")) {
			Object oldValue = get(key);
			if (Integer.parseInt(newValue.toString()) == 0) {
				this.setEnabled(false); // set to disable
			} else {
				this.setEnabled(true); // set to enable
			}
			changeSupport.firePropertyChange(key, oldValue, newValue);
			return;
		}
		
		if (key.equalsIgnoreCase("clicked")) {
			if (Integer.parseInt(newValue.toString()) == 1) {
				// only fire the change event if button is clicked
				changeSupport.firePropertyChange(key, "0", "1");
			}
			return;
		}
	}
	
	public Object get(final String key) {
		if (key.equalsIgnoreCase("hidden")) {
			return this.getVisibility()+"";
		}
		
		if (key.equalsIgnoreCase("enabled")) {
			if (this.isEnabled()) {
				return "1";
			} else {
				return "0";
			}
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
