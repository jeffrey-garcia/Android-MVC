package com.jeffrey.hsbcui;

import java.beans.PropertyChangeListener;

public interface HsbcView {
	
	public void initUi();
	
	public void set(final String key, final Object newValue);
	
	public Object get(final String key);
	
	public void addPropertyChangeListener(final PropertyChangeListener listener);
	
	public void removePropertyChangeListener();
	
}
