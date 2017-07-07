package com.developers.recycletryout.Adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developers.recycletryout.R;
import com.developers.recycletryout.util.CalendarCollection;


public class DailyDairyAdapter extends BaseAdapter{
	private Context context;
	int not_holiday=0;
	private Calendar month;
	public GregorianCalendar pmonth; 
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	int firstDay;
	int maxWeeknumber;
	int maxP;
	int calMaxP;
	int lastWeekDay;
	int leftDays;
	int mnthlength;
	String itemvalue, curentDateString;
	DateFormat df;
	String key;
	String formattedDate ;
	private ArrayList<String> items;
	public static List<String> day_string;
	private View previousView;
	
	ArrayList<String> holiday=new ArrayList<String>();
public ArrayList<CalendarCollection>  date_collection_arr;

	public DailyDairyAdapter(Context context, GregorianCalendar monthCalendar,ArrayList<CalendarCollection> date_collection_arr) {
		this.date_collection_arr=date_collection_arr;
		DailyDairyAdapter.day_string = new ArrayList<String>();
		Locale.setDefault(Locale.US);
		month = monthCalendar;
		selectedDate = (GregorianCalendar) monthCalendar.clone();
		this.context = context;
		month.set(GregorianCalendar.DAY_OF_MONTH, 1);
		
		this.items = new ArrayList<String>();
		df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		
		//Log.e("df", ""+df);
		curentDateString = df.format(selectedDate.getTime());
		//Log.e("current selection", ""+curentDateString);
		refreshDays();
		
	}

	public void setItems(ArrayList<String> items) {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}

	public int getCount() {
		return day_string.size();
	}

	public Object getItem(int position) {
		return day_string.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView dayView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.cal_item, null);
			v.setBackgroundColor(Color.parseColor("#ffffff"));
		}
			
		
		dayView = (TextView) v.findViewById(R.id.date);
		String[] separatedTime = day_string.get(position).split("-");
		
		
		String gridvalue = separatedTime[0].replaceFirst("^0*", "");
		
		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			dayView.setTextColor(Color.parseColor("#D3D3D3"));
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
			dayView.setTextColor(Color.parseColor("#D3D3D3"));
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else {
			// setting curent month's days in blue color.
			dayView.setTextColor(Color.BLACK);
		}

		//Log.e("", ""+day_string.get(position)+"=="+curentDateString);
		if (day_string.get(position).equals(curentDateString)) {
			
			v.setBackgroundColor(Color.parseColor("#D3D3D3"));
		
		} else {
			v.setBackgroundColor(Color.parseColor("#ffffff"));
			
		}
		
		
		dayView.setText(gridvalue);

		// create date string for comparison
		String date = day_string.get(position);

		if (date.length() == 1) {
			date = "0" + date;
		}
		String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}

		// show icon if date is not empty and it exists in the items array
		ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
		if (date.length() > 0 && items != null && items.contains(date)) {
			iw.setVisibility(View.VISIBLE);
		} else {
			iw.setVisibility(View.GONE);
		}
		if(position % 7==0)
		{
			v.setBackgroundColor(Color.parseColor("#03a9f4"));
			CalendarCollection.date_collection_arr.add(new CalendarCollection(day_string.get(position),"Sunday","H"));
			Log.e("Current day string ", "Current ---- "+day_string.get(position));
		}
		
		setEventView(v, position,dayView);
		
		return v;
	}

	public View setSelected(View view,int pos) {
		/*if (previousView != null) {
		previousView.setBackgroundColor(Color.parseColor("#ffffff"));
		}
		
		view.setBackgroundColor(Color.parseColor("#D3D3D3"));
		*/
		int len=day_string.size();
		if (len>pos) {
			if (day_string.get(pos).equals(curentDateString)) {
				
			}else{
				
				previousView = view;	
					
			}
				
		}
		
		return view;
	}

	public void refreshDays() {
		// clear items
		
		
		items.clear();
		
		day_string.clear();
		
		Locale.setDefault(Locale.US);
		
		pmonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
		
		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);
		

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {

			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			day_string.add(itemvalue);

		}
		
	}

	private int getMaxP() {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		return maxP;
	}

	
	
	
	public void setEventView(View v,int pos,TextView txt){
		
		
		int len=CalendarCollection.date_collection_arr.size();
		
		for (int i = 0; i < len; i++) {
			
			CalendarCollection cal_obj=CalendarCollection.date_collection_arr.get(i);
			String date=cal_obj.getDate();
			key=cal_obj.getKey();
			
			if(key.matches("H"))
			{

				int len1=day_string.size();
				holiday.add(date);
				if (len1>pos) {
				//Log.e("-------", ""+day_string.get(pos)+"="+date);
					
			//		Log.e("", ""+day_string.get(pos)+" = "+date);
					
				if (day_string.get(pos).equals(date)) {
					
					if(curentDateString.equals(date)){
						v.setBackgroundColor(Color.parseColor("#D3D3D3"));	
					}else{
						v.setBackgroundColor(Color.parseColor("#03a9f4"));
					//v.setBackgroundResource(R.drawable.calender);
					}
				//	v.setBackgroundResource(R.drawable.rounded_calender_item);
					
					txt.setTextColor(Color.WHITE);
				}
			}
			}
			
				if(key.matches("V"))
				{

					int len2=day_string.size();
					holiday.add(date);
					if (len2>pos) {
					//Log.e("-------", ""+day_string.get(pos)+"="+date);
						
					//	Log.e("", ""+day_string.get(pos)+" = "+date);
						
					if (day_string.get(pos).equals(date)) {
						
						v.setBackgroundColor(Color.parseColor("#03a9f4"));
					//	v.setBackgroundResource(R.drawable.rounded_calender_item);
						
						txt.setTextColor(Color.WHITE);
					}
				}
				}
					
					/*
			int len1=day_string.size();
			
			if (len1>pos) {
			//Log.e("-------", ""+day_string.get(pos)+"="+date);
				
				Log.e("", ""+day_string.get(pos)+" = "+date);
				
			if (day_string.get(pos).equals(date)) {
				
				v.setBackgroundColor(Color.parseColor("#ffffff"));
				v.setBackgroundResource(R.drawable.rounded_calender_item);
				
				txt.setTextColor(Color.GRAY);
			} 		*/
		}
		
		
	
	}
	
	
public void getPositionList(String date,final Activity act){
	
	int len=CalendarCollection.date_collection_arr.size();
	
	
	
	
	for (int i = 0; i < len; i++) {
		CalendarCollection cal_collection=CalendarCollection.date_collection_arr.get(i);
		String event_date=cal_collection.getDate();
		
		String event_message=cal_collection.getName();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Log.e("1", event_date+" = "+i);
		
		
  try{
		 Date compare=sdf.parse(event_date);
		 Date date1=sdf.parse(date);
		 Log.e("2", date1+" = "+compare);
			Log.e("3", date+" = "+date1);
		if (date1.equals(compare)) {
			
				not_holiday=1;
				new AlertDialog.Builder(context)
		          .setIcon(R.mipmap.ic_launcher)
		          .setTitle("Date: "+event_date)
		           .setMessage("Event: "+"Holiday")
		            .setPositiveButton("OK",new DialogInterface.OnClickListener(){
		            public void onClick(DialogInterface dialog, int which) 
		            {
		            	//act.finish();	
		            	dialog.dismiss();
		            	not_holiday=0;
		            }
		            }).show();
				
			
			break;
	}	
  }
	catch(Exception e)
	{
		/*CustomToast("in currect"+e);*/
		Toast.makeText(context, ""+e, Toast.LENGTH_LONG).show();
	
	}
  
  }
	
	   try{
	    	
	    	Calendar c = Calendar.getInstance();
			System.out.println("Current time =&gt; "+c.getTime());
			 
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			 formattedDate = df.format(c.getTime());
			 
	    
	    
	    
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
           
           Date current=sdf.parse(formattedDate);
           Date compare=sdf.parse(date);
           if(compare.after(current))
           {
        	   
        	   
        	   
           }
           else{
        	   
        	   
        	  /* if(not_holiday == 0){
        			not_holiday=0;
        				Intent intent=new Intent(context,DailyDairyProfile_activity.class);
        				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        				intent.putExtra("date", date);
        				context.startActivity(intent);
        			}
        	   */
        	   
        	   
           }
           
	   }
		catch(Exception e)
		{
			/*CustomToast("in currect"+e);*/
			 Toast.makeText(context, ""+e, Toast.LENGTH_LONG).show();
		
		}

	


	
 }



}