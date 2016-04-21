package com.jeffrey.util;

import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jeffrey.activity.HsbcWebviewActivity;
import com.jeffrey.hsbcui.HsbcWebView;

public class HsbcWebViewClient extends WebViewClient {
	
	protected HsbcWebviewActivity activity;
	protected HsbcWebView webView;
	protected ProgressBar progressBar;
	
	public HsbcWebViewClient(final HsbcWebviewActivity activity, final HsbcWebView webView, final ProgressBar progressBar) {
		this.activity = activity;
		this.webView = webView;
		this.progressBar = progressBar;
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		Log.d(getClass().getName(), "onPageStarted(), url:" + url);
		webView.set("loadingStatus", "1");
		progressBar.setVisibility(View.VISIBLE);
    }
	
	@Override
	public boolean shouldOverrideUrlLoading(final WebView webView, final String url) {
		Log.d(getClass().getName(), "shouldOverrideUrlLoading(), url:" + url);
		
		if (url.toLowerCase().indexOf("hsbc://") >= 0) {
			try {
				Map <String, String> hookParams = HsbcUrlResolver.parseUrlString(url);
				HsbcUrlResolver.parseHookRequest(activity.getApplicationContext(), webView, hookParams);
				
			} catch (final Throwable throwable) {
				Log.e("shouldOverrideUrlLoading()", throwable.getMessage(), throwable);
			}
			return true;
			
		} else {
			// general URL link
			
			//with proxy server for internet connection
            /*
			UsernamePasswordCredentials creds= new UsernamePasswordCredentials("hbap\20710206", "Genius_15!");
            Header credHeader = BasicScheme.authenticate(creds, "UTF-8", true);
            Map<String, String> header = new HashMap<String, String>();
            header.put(credHeader.getName(), credHeader.getValue());
            
            LOG.debug("@@@ " + android.os.Build.VERSION.SDK_INT); 
            view.loadUrl(url, header); 
            */
			
            //no proxy server for internet connection
			webView.loadUrl(url); // don't forget to add the internet access permission in manifest
		}
		
		return true;
	}
	
    @Override
    public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
    	Log.d(getClass().getName(), "onReceivedError(), description:" + description);
    	((HsbcWebView)webView).set("loadingStatus", "0");
    	progressBar.setVisibility(View.INVISIBLE);
    	
        switch (errorCode) {
        	case ERROR_UNKNOWN:
                /* 
                 * when unknown error occur during loading local or remote webpage, 
                 * just show a blank screen to align iOS existing behavior, avoid 
                 * showing the URL path to user 
                 */
                //String summary = "<html><body></body></html>";
                //webView.loadData(summary, "text/html", null);
                break;
        }
    }
    
	@Override
	public void onPageFinished(final WebView webView, final String url) {
		Log.d(getClass().getName(), "onPageFinished(), url:" + url);
		((HsbcWebView)webView).set("loadingStatus", "0");
		progressBar.setVisibility(View.INVISIBLE);
	}
}
