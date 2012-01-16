package org.codemash;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class SessionAdapter extends ArrayAdapter<Session> {
	private ArrayList<Session> items;
	private ArrayList<Session> all;
	private Context context;
	
	public SessionAdapter(Context context, int resId, ArrayList<Session> list) {
		super(context, resId, list);
		this.context = context;
		items = list;
		all = new ArrayList<Session>(items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, android.R.layout.two_line_list_item, null);
		Session item = items.get(position);
		TextView title = (TextView)convertView.findViewById(android.R.id.text1);
		TextView speaker = (TextView)convertView.findViewById(android.R.id.text2);
		title.setText(item.getTitle());
		speaker.setText(item.getSpeakerName());
		return convertView;
	}
	
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence search) {
				FilterResults results = new FilterResults();
				ArrayList<Session> filteredResults = new ArrayList<Session>();
				
				for(Session s : all) {
					if(s.getTitle().toLowerCase().contains(search.toString().toLowerCase())) {
						filteredResults.add(s);
					}
				}
				
				results.values = filteredResults;
				
				return results;
			}

			@Override
			protected void publishResults(CharSequence chars, FilterResults results) {
				items = (ArrayList<Session>)results.values;
				
				notifyDataSetChanged();
				clear();
				for(int i = 0; i < items.size(); i++) {
					add(items.get(i));
				}
			}
			
		};
	}
}
