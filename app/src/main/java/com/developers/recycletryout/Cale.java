package com.developers.recycletryout;

import android.support.v7.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.developers.recycletryout.Adapter.DailyDairyAdapter;
import com.developers.recycletryout.model.Bean_holiday;
import com.developers.recycletryout.util.CalendarCollection;
import com.developers.recycletryout.util.JsonParse;

/**
 * Created by Developers on 07-06-2017.
 */

public class Cale extends AppCompatActivity {

    public GregorianCalendar cal_month, cal_month_copy;
    private DailyDairyAdapter cal_adapter;
    private TextView tv_month;
    private ArrayList<NameValuePair> param;
    public ArrayList<Bean_holiday> array_holiday = new ArrayList<Bean_holiday>();
    String url ="http://mypanel.cleverinternationalschool.com/webservice/WebServiceAndroid.asmx/GetCalender_Events";
    int month;
    String first_date__of_month;
    String last_date__of_month;


    String total_day;

    ProgressDialog loadingView;
    String selected_date = new String();

    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_dairy);
        setActionBar();
        CalendarCollection.date_collection_arr = new ArrayList<CalendarCollection>();


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        Log.e("calender activuty", "" + cal_month);


        month = cal_month.get(GregorianCalendar.MONTH) + 1;
        first_date__of_month = "01" + "-" + month + "-" + cal_month.get(GregorianCalendar.YEAR);
        last_date__of_month = cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) + "-" + month + "-" + cal_month.get(GregorianCalendar.YEAR);
        total_day = "" + cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);


        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
        //Log.e("mmm yyyy", ""+android.text.format.DateFormat.format("MMMM yyyy", cal_month));
        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });


        gridview = (GridView) findViewById(R.id.gv_calendar);

        gridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                ((DailyDairyAdapter) parent.getAdapter()).setSelected(v, position);
                String selectedGridDate = DailyDairyAdapter.day_string
                        .get(position);

                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[0].replaceFirst("^0*", "");
                Log.e("1-", "1->" + gridvalueString);
                int gridvalue = Integer.parseInt(gridvalueString);

                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                ((DailyDairyAdapter) parent.getAdapter()).setSelected(v, position);


                ((DailyDairyAdapter) parent.getAdapter()).getPositionList(selectedGridDate, Cale.this);
            }

        });
         get_attendance_list();


    }

    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                    cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
            month = cal_month.get(GregorianCalendar.MONTH) + 1;
            first_date__of_month = "01" + "-" + month + "-" + cal_month.get(GregorianCalendar.YEAR);
            last_date__of_month = cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) + "-" + month + "-" + cal_month.get(GregorianCalendar.YEAR);
            total_day = "" + cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
            month = cal_month.get(GregorianCalendar.MONTH) + 1;
            first_date__of_month = "01" + "-" + month + "-" + cal_month.get(GregorianCalendar.YEAR);
            last_date__of_month = cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) + "-" + month + "-" + cal_month.get(GregorianCalendar.YEAR);
            total_day = "" + cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        }
        get_attendance_list();;
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
                    cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
            month = cal_month.get(GregorianCalendar.MONTH) + 1;
            first_date__of_month = "01" + "-" + month + "-" + cal_month.get(GregorianCalendar.YEAR);
            last_date__of_month = cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) + "-" + month + "-" + cal_month.get(GregorianCalendar.YEAR);
            total_day = "" + cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) - 1);
            month = cal_month.get(GregorianCalendar.MONTH) + 1;
            first_date__of_month = "01" + "-" + month + "-" + cal_month.get(GregorianCalendar.YEAR);
            last_date__of_month = cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) + "-" + month + "-" + cal_month.get(GregorianCalendar.YEAR);
            total_day = "" + cal_month.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        }

    }

    public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }


    private void setActionBar() {
        // TODO Auto-generated method stub
		/*getActionBar().setLogo(R.drawable.ic_launcher);
		getActionBar().setTitle("EDU MASTER");*/

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_attendace, null);
        ImageView im_back = (ImageView) v.findViewById(R.id.image_back);
        TextView tv_title = (TextView) v.findViewById(R.id.txt_title);
        tv_title.setText("Daily Dairy");
        this.getSupportActionBar().setCustomView(v);
    }

    private void get_attendance_list() {
        loadingView = ProgressDialog.show(Cale.this,
                "Please Wait", "Loading data...");
        loadingView.setCancelable(false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONArray jsonObject = response.getJSONArray("ContactList");
                            for (int i = 0; i < jsonObject.length(); i++) {
                                JSONObject c = jsonObject.getJSONObject(i);

                                Bean_holiday bean_absent = new Bean_holiday();

                                bean_absent.setDate(c.getString("Column1"));

                                Log.e("", "" + c.getString("Column1"));
                                bean_absent.setContent(c.getString("Event_Name"));
                                bean_absent.setKey(c.getString("Description"));
                                Log.e("", "" + c.getString("Description"));

                                array_holiday.add(bean_absent);
                                CalendarCollection.date_collection_arr.add(new CalendarCollection(array_holiday.get(i).getDate(), array_holiday.get(i).getContent(), array_holiday.get(i).getKey()));
                                loadingView.hide();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Not available", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            loadingView.hide();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingView.hide();
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();

            }

        });
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

}

   /* protected void CustomToast(String st) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(st);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }*/