package org.codemash;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.gravityworksdesign.AsyncHttpListener;
import com.gravityworksdesign.GWUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CodemashSessionListActivity extends Activity implements AsyncHttpListener {
	private GWUtil util;
	private Codemash codemash;
	private final String URL = "http://codemash.org/rest/sessions";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        util = new GWUtil(getApplicationContext());
        String sessionXML = util.getPreference(URL);
        if(sessionXML.equals("")) {
        		util.beginFetchFromUrl(URL, this, CodemashSessionListActivity.this);
        		
        }
        else {
        		parseSessions(sessionXML);
        }
    }

	@Override
	public void onAsyncComplete(String response) {
		util.storePreference(URL, response);
		parseSessions(response);
	}
	
	private void parseSessions(String xml) {
		Serializer serializer = new Persister();
		try {
			codemash = serializer.read(Codemash.class, xml);
			
			ListView list = (ListView)findViewById(R.id.sessionList);
			list.setTextFilterEnabled(true);
			list.setAdapter(new SessionAdapter(getApplicationContext(), android.R.id.text1, codemash.sessions));
			list.setOnItemClickListener(new OnItemClickListener() {
		
				
				
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					Intent detail = new Intent(getApplicationContext(), SessionDetails.class);
					detail.putExtra("URI", codemash.sessions.get(pos).getURI());
					startActivity(detail);
				}
				
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		parseSessions(util.getPreference(URL));
	}
}