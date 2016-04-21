/*
 * ***************************************************************
 * Copyright.  HSBC Holdings plc 2015 ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it
 * has been provided.  No part of it is to be reproduced,
 * disassembled, transmitted, stored in a retrieval system or
 * translated in any human or computer language in any way or
 * for any other purposes whatsoever without the prior written
 * consent of HSBC Holdings plc.
 * ***************************************************************
 *
 * Class Name			HsbcViewInflater
 *
 * Creation Date		Since May-2015
 *
 * Abstract				Platform 2.0
 * 						HSBC own's version of LayoutInflater to instantiate a view object
 * 						from pre-processed layout XML file 
 *
 * Amendment History   (In chronological sequence):
 *
 *    Amendment Date
 *    Programmer		Jeffrey
 *    Description
 */
package com.jeffrey.hsbcui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;

public class HsbcViewInflater {

	public static View getViewLayout(final Context context, final String filePathInAsset) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		/* 
		 * For performance reasons, view inflation relies heavily on pre-processing of XML files that is done at build time. 
		 * Therefore, it is not currently possible to use LayoutInflater with an XmlPullParser over a plain XML file at runtime; 
		 * it only works with an XmlPullParser returned from a compiled resource (R.something file.)
		 * 
		 * Thus we need to extract the layout XML from the APK before externalize it to the assets folder
		 * 
		 * It seems that LayoutInflater only accepts XmlBlock 
		 * (XmlBlock is a private class in Android that's why we need use java reflection to invoke it)
		 * (unzip the apk, get the XML files from res/layout/)
		 */
		
		// equivalent to XmlBlock block = new XmlBlock(data);
		Class<?> theClass = Class.forName("android.content.res.XmlBlock");
		Constructor<?> constructor = theClass.getDeclaredConstructor(byte[].class);
		constructor.setAccessible(true);
		Object xmlBlock = constructor.newInstance(getXmlDataFromAssets(context, filePathInAsset));
		
		// equivalent to XmlPullParser parser = block.newParser();
		Method method = theClass.getDeclaredMethod("newParser");
		method.setAccessible(true);
		XmlPullParser xmlParser = (XmlPullParser) method.invoke(xmlBlock);
		
		return inflater.inflate(xmlParser, null, true);
	}
	
	private static byte[] getXmlDataFromAssets(final Context context, final String filePathInAsset) throws IOException {
		AssetManager assetMgr = context.getAssets();
		
		// fetch the pre-processed resource XML here and convert to bytes array
		InputStream input = assetMgr.open(filePathInAsset); 
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int temp;
		while((temp = input.read())!= -1) {
			output.write(temp);
		}
		
		byte[] data = output.toByteArray();
		return data;
	}
	
	private static byte[] getXmlDataFromSandbox(final Context context, final String filePath) throws IOException {
		//TODO: to be implemented for core integration
		return null;
	}
	
	private static byte[] getXmlDataFromStorage(final Context context, final String filePath) throws IOException {
		//TODO: to be implemented for core integration
		return null;
	}
}