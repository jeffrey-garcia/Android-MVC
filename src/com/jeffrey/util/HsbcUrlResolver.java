package com.jeffrey.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

public class HsbcUrlResolver {

	protected final static String HSBC_HOOK_SCHEME = "hsbc://";
	
	public static void parseHookRequest(final Context context, final WebView webView, Map<String, String> hookParams) throws 
		ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
	{
		String hookFunction = hookParams.get("function");
		Log.d(HsbcUrlResolver.class.getName(), "parseHookRequest(), key:" + hookFunction);
		
		Class<?> theClass = Class.forName("com.jeffrey.util.hookapi." + hookFunction);
		Method theMethod = theClass.getMethod("execute", 
				Class.forName("android.content.Context"), 
				Class.forName("android.webkit.WebView"), 
				Class.forName("java.util.HashMap"));
		theMethod.invoke(theClass, hookParams);
	}
	
	public static Map<String, String> parseUrlString(String url) {
		Map<String, String> hookParams = new HashMap<String, String>();
		
		if (url.toLowerCase().indexOf(HSBC_HOOK_SCHEME) >= 0) {
			url = url.substring(url.toLowerCase().indexOf(HSBC_HOOK_SCHEME) + HSBC_HOOK_SCHEME.length(), url.length());
			
			StringTokenizer token = new StringTokenizer(url, "&");
			while (token.hasMoreTokens()) {
				String temp = token.nextToken();
				
				if (temp.indexOf("=") >= 0) {
					String key = temp.substring(0, temp.indexOf("="));
					String value = temp.substring(temp.indexOf("=")+1, temp.length());
					hookParams.put(key, value);
				}
			}
		}
		
		return hookParams;
	}
	
}
