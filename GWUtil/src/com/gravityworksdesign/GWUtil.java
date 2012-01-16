package com.gravityworksdesign;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;


public class GWUtil {
	private Context context;
	private static final String GWUTIL_PREF = "GWUTIL_PREF";
	
	public GWUtil(Context c) {
		context = c;
	}
	
	public void beginFetchFromUrl(String url, AsyncHttpListener delegate, Context progressContext) {
		Log.i("GWUtil", "Beging Fetch from " + url);
		HttpFetchTask task = new HttpFetchTask(delegate, progressContext, "Fetching data from "+url);
		task.execute(url);
	}
	
	public void storePreference(String prefKey, String prefValue) {
		SharedPreferences prefrence = context.getSharedPreferences(GWUTIL_PREF, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor= prefrence.edit();
		
		editor.putString(prefKey,prefValue);
		editor.commit();	
	}
	
	public String getPreference(String prefKey) {
		SharedPreferences preference = context.getSharedPreferences(GWUTIL_PREF, Context.MODE_PRIVATE);
		return preference.getString(prefKey, "");
	}
	
	public boolean networkIsConnected() {
		 ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 return cm.getActiveNetworkInfo().isConnected();
	}
	
	
    private class HttpFetchTask extends AsyncTask<String, Void, String> {
    	private ProgressDialog spinner;
    	private AsyncHttpListener delegate;
    	private Context windowContext;
    	private String message;
    	
    	public HttpFetchTask(AsyncHttpListener del, Context c, String m) {
    		delegate = del;
    		windowContext = c;
    		message = m;
    	}
    	
    	protected void onPreExecute() {
    		spinner = ProgressDialog.show(windowContext, "", message);
    	}
    	
    	protected String doInBackground(String...urls) {
    		String tmpRtn = "";
    	    HttpClient httpclient = new DefaultHttpClient();
    	    HttpGet httpget = new HttpGet(urls[0]); 
    	    HttpResponse response;
    	    
    	    try {
    	        response = httpclient.execute(httpget);
    	        HttpEntity entity = response.getEntity();
    	        
    	        if (entity != null) {
    	            InputStream instream = entity.getContent();
    	    	    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
    	    	    StringBuilder sb = new StringBuilder();
    	    	    String line = null;
        	        while ((line = reader.readLine()) != null) {
        	            sb.append(line);
        	        }
    	    	    tmpRtn = sb.toString();

    	            instream.close();
    	        }
    	    } 
    	    catch (Exception e) {
    	    	Log.e("GWUtil", "Get Failed: "+e.getMessage());
    	    }
    	    
    		return tmpRtn;
    	}
    	
    	protected void onPostExecute(String response) {
    		spinner.dismiss();
    		delegate.onAsyncComplete(response);
    	}
    }    
}
