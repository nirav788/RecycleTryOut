package com.developers.recycletryout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.developers.recycletryout.Adapter.CalendarAdapter;
import com.developers.recycletryout.model.CalendarCollection;

public class CalenderActivity extends AppCompatActivity {
	public GregorianCalendar cal_month, cal_month_copy;
	private CalendarAdapter cal_adapter;
	private TextView tv_month;
	Button graph;
	
	String first_date__of_month;
	String last_date__of_month;
	ProgressDialog loadingView;
	String total_day;
	TextView tv_present,tv_holiday,tv_absent;
	int month;
	GridView gridview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.calender);
		
		setActionBar();
		 tv_holiday = (TextView)findViewById(R.id.tv_holiday_day);
		 tv_present = (TextView)findViewById(R.id.tv_present_day);
		 tv_absent = (TextView)findViewById(R.id.tv_absent_day);
		 
		CalendarCollection.date_collection_arr =new ArrayList<CalendarCollection>();
		cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
		
		
		cal_month_copy = (GregorianCalendar) cal_month.clone();
		//Log.e("calender activuty", ""+cal_month);
		

		month = cal_month.get(GregorianCalendar.MONTH)+1;
		first_date__of_month = "01"+"-"+month+"-"+cal_month.get(GregorianCalendar.YEAR);
		last_date__of_month = cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+"-"+month+"-"+cal_month.get(GregorianCalendar.YEAR);
		total_day = ""+cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		
		tv_month = (TextView) findViewById(R.id.tv_month);
		tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
		Log.e("mmm yyyy", ""+android.text.format.DateFormat.format("MMMM yyyy", cal_month));
		
		
		ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				Log.e("mmm yyyy", ""+android.text.format.DateFormat.format("MMMM yyyy", cal_month));
				
				refreshCalendar();
				Log.e("size", ""+CalendarCollection.date_collection_arr.size());
				
			}
		});

		ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				Log.e("mmm yyyy", ""+android.text.format.DateFormat.format("MMMM yyyy", cal_month));
				
				refreshCalendar();
				Log.e("size", ""+CalendarCollection.date_collection_arr.size());
				

			}
		});
		
		
		gridview = (GridView) findViewById(R.id.gv_calendar);
		
		gridview.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
				String selectedGridDate = CalendarAdapter.day_string
						.get(position);
				
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[0].replaceFirst("^0*","");
				Log.e("1-", "1->"+gridvalueString);
				int gridvalue = Integer.parseInt(gridvalueString);

				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v,position);


				((CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, CalenderActivity.this);
			}
			
		});
		
	}
	
	
	protected void setNextMonth() {
		if (cal_month.get(GregorianCalendar.MONTH) == cal_month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
					cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
			Toast.makeText(getApplicationContext(), "max:"+cal_month
					.getActualMaximum(GregorianCalendar.MONTH),Toast.LENGTH_LONG).show();
			Toast.makeText(getApplicationContext(), "min:"+cal_month
					.getActualMinimum(GregorianCalendar.MONTH),Toast.LENGTH_LONG).show();
			month = cal_month.get(GregorianCalendar.MONTH)+1;
			first_date__of_month = "01"+"-"+month+"-"+cal_month.get(GregorianCalendar.YEAR);
			last_date__of_month = cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+"-"+month+"-"+cal_month.get(GregorianCalendar.YEAR);
			total_day = ""+cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			
		} else {
			cal_month.set(GregorianCalendar.MONTH,
					cal_month.get(GregorianCalendar.MONTH) + 1);
			month = cal_month.get(GregorianCalendar.MONTH)+1;
			first_date__of_month = "01"+"-"+month+"-"+cal_month.get(GregorianCalendar.YEAR);
			last_date__of_month = cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+"-"+month+"-"+cal_month.get(GregorianCalendar.YEAR);
			total_day = ""+cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			
		}
	}

	protected void setPreviousMonth() {
		if (cal_month.get(GregorianCalendar.MONTH) == cal_month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
					cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
			
			month = cal_month.get(GregorianCalendar.MONTH)+1;
			first_date__of_month = "01"+"-"+month+"-"+cal_month.get(GregorianCalendar.YEAR);
			last_date__of_month = cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+"-"+month+"-"+cal_month.get(GregorianCalendar.YEAR);
			total_day = ""+cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		} else {
			cal_month.set(GregorianCalendar.MONTH,
					cal_month.get(GregorianCalendar.MONTH) - 1);
			month = cal_month.get(GregorianCalendar.MONTH)+1;
			first_date__of_month = "01"+"-"+month+"-"+cal_month.get(GregorianCalendar.YEAR);
			last_date__of_month = cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+"-"+month+"-"+cal_month.get(GregorianCalendar.YEAR);
			total_day = ""+cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		}
	}

	public void refreshCalendar() {
		cal_adapter.refreshDays();
		cal_adapter.notifyDataSetChanged();
		tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
	}



	private void setActionBar() {
		// TODO Auto-generated method stub
		this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		LayoutInflater inflator = LayoutInflater.from(this);
		View v = inflator.inflate(R.layout.custom_attendace, null);
		ImageView im_back=(ImageView)v.findViewById(R.id.image_back);
		ImageView im_graph=(ImageView)v.findViewById(R.id.img_icon1);
		TextView tv_title=(TextView)v.findViewById(R.id.txt_title);
		tv_title.setText("Attendance");
		im_back.setVisibility(View.VISIBLE);
		this.getSupportActionBar().setCustomView(v);
	}


}
