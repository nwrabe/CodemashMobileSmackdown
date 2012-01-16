package org.codemash;

import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.gravityworksdesign.GWUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class SessionDetails extends Activity {
	Codemash codemash;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
    
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
        		String uri = extras.getString("URI");
        		GWUtil util = new GWUtil(getApplicationContext());
        		String sessionXML = util.getPreference("http://codemash.org/rest/sessions");
        		Serializer serializer = new Persister();
        		try {
        			codemash = serializer.read(Codemash.class, sessionXML);
        			
        			for(Session s : codemash.sessions){
        				if(s.getURI().equals(uri)) {
        					TextView title = (TextView)findViewById(R.id.txtTitle);
        					TextView speaker = (TextView)findViewById(R.id.txtSpeaker);
        					TextView abstractText = (TextView)findViewById(R.id.txtAbstract);
        					TextView room = (TextView)findViewById(R.id.txtRoom);
        					TextView startTime = (TextView)findViewById(R.id.txtStartTime);
        					TextView difficulty = (TextView)findViewById(R.id.txtDifficulty);
        					TextView technology = (TextView)findViewById(R.id.txtTechnology);
        					CheckBox interested = (CheckBox)findViewById(R.id.interested);
        					
        					title.setText(s.getTitle());
        					speaker.setText(s.getSpeakerName());
        					abstractText.setText(s.getAbstract());
        					room.setText(s.getRoom());
        					startTime.setText(s.getStart());
        					difficulty.setText(s.getDifficulty());
        					technology.setText(s.getTechnology());
        					interested.setChecked(s.getInterested());
        					
        					final Session foo = s;
        					interested.setOnCheckedChangeListener(new OnCheckedChangeListener() {
								
								@Override
								public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
									foo.setInterested(isChecked);
								}
							});
        				}
        			}
        		}
        		catch(Exception e) {
        			
        		}
        }
    }
    

	@Override
	protected void onPause() {
		super.onPause();
		GWUtil util = new GWUtil(getApplicationContext());
		String sessionXML = util.getPreference("http://codemash.org/rest/sessions");
		Serializer serializer = new Persister();
		StringWriter sw = new StringWriter();
		try {
			serializer.write(codemash, sw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
