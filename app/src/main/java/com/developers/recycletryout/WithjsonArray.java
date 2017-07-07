package com.developers.recycletryout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.developers.recycletryout.Adapter.MyRecyclerAdapter;
import com.developers.recycletryout.Adapter.MyRecyclerAdapterone;
import com.developers.recycletryout.model.Beans_getcities;
import com.developers.recycletryout.model.Sathiya_Book;
import com.developers.recycletryout.network.NetworkController;
import com.liuguangqiang.cookie.CookieBar;
import com.liuguangqiang.cookie.OnActionClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developers on 30-06-2017.
 */

public class WithjsonArray extends AppCompatActivity {

    RequestQueue queue;
    RecyclerView recyclerView;
    MyRecyclerAdapterone adapter;
    private MyRecyclerAdapterone.ListItemClickListener onClickListener;
    List<Beans_getcities> feedsList = new ArrayList<Beans_getcities>();
    String Requesturl = "http://altynorda.kz/api/citiesapi/getcities";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_beneificaries);
        adapter = new MyRecyclerAdapterone(this, feedsList, onClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WithjsonArray.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.alpha_gradient));
        //Getting Instance of Volley Request Queue
        queue = NetworkController.getInstance(this).getRequestQueue();
        //Volley's inbuilt class to make Json array request


        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...");
        loading.setCancelable(true);


        JsonArrayRequest request = new JsonArrayRequest  (Request.Method.GET, Requesturl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Dismissing progress dialog
                loading.dismiss();


                try {

                    for (int i = 0; i < response.length(); i++) {

                        int count = response.length();


                        Log.d("Array Length", String.valueOf(count));

                        JSONObject jsonObject = response.getJSONObject(i);

                        Beans_getcities dataSet = new Beans_getcities();


                        dataSet.setName(jsonObject.getString("Name"));
                        dataSet.setLatitude(jsonObject.getString("Latitude"));
                        dataSet.setLongitude(jsonObject.getString("Longitude"));
                        dataSet.setEnabled(jsonObject.getString("Enabled"));
                        dataSet.setLastUpdate(jsonObject.getString("LastUpdate"));
                        dataSet.setState(jsonObject.getString("State"));
                        dataSet.setCreatedDate(jsonObject.getString("CreatedDate"));

                        feedsList.add(dataSet);

                        adapter.notifyItemChanged(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof NetworkError) {
                            loading.dismiss();

                            new CookieBar.Builder(WithjsonArray.this)
                                    .setTitle("Oops..!!")
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setMessage("Network Error!....Cannot connect to Internet...Please check your connection!")
                                    .setBackgroundColor(R.color.colorPrimary)
                                    .setActionColor(android.R.color.white)
                                    .setTitleColor(R.color.colorAccent)
                                    .setLayoutGravity(Gravity.BOTTOM)
                                    .setDuration(3000)
                                    .setAction("ACTION", new OnActionClickListener() {
                                        @Override
                                        public void onClick() {
                                        }
                                    })
                                    .show();
                            //Toast.makeText(getApplicationContext(), "Network Error!....Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            loading.dismiss();

                            new CookieBar.Builder(WithjsonArray.this)
                                    .setTitle("Oops..!!")
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setMessage("Server Side Error!...The server could not be found. Please try again after some time!!")
                                    .setBackgroundColor(R.color.colorPrimary)
                                    .setActionColor(android.R.color.white)
                                    .setTitleColor(R.color.colorAccent)
                                    .setLayoutGravity(Gravity.BOTTOM)
                                    .setDuration(3000)
                                    .setAction("ACTION", new OnActionClickListener() {
                                        @Override
                                        public void onClick() {
                                        }
                                    })
                                    .show();

                            // Toast.makeText(getApplicationContext(), "Server Side Error!...The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            loading.dismiss();

                            new CookieBar.Builder(WithjsonArray.this)
                                    .setTitle("Oops..!!")
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setMessage("Parsing error! Please try again after some time!!")
                                    .setBackgroundColor(R.color.colorPrimary)
                                    .setActionColor(android.R.color.white)
                                    .setTitleColor(R.color.colorAccent)
                                    .setLayoutGravity(Gravity.BOTTOM)
                                    .setDuration(3000)
                                    .setAction("ACTION", new OnActionClickListener() {
                                        @Override
                                        public void onClick() {
                                        }
                                    })
                                    .show();
                            // Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            loading.dismiss();

                            new CookieBar.Builder(WithjsonArray.this)
                                    .setTitle("Oops..!!")
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setMessage("Connection Error!.....Cannot connect to Internet...Please check your connection!")
                                    .setBackgroundColor(R.color.colorPrimary)
                                    .setActionColor(android.R.color.white)
                                    .setLayoutGravity(Gravity.BOTTOM)
                                    .setTitleColor(R.color.colorAccent)
                                    .setDuration(3000)
                                    .setAction("ACTION", new OnActionClickListener() {
                                        @Override
                                        public void onClick() {
                                        }
                                    })
                                    .show();
                            //  Toast.makeText(getApplicationContext(), "Connection Error!.....Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            loading.dismiss();

                            new CookieBar.Builder(WithjsonArray.this)
                                    .setTitle("Oops..!!")
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setMessage("Timeout Error!....Connection TimeOut! Please check your internet connection.")
                                    .setBackgroundColor(R.color.colorPrimary)
                                    .setActionColor(android.R.color.white)
                                    .setTitleColor(R.color.colorAccent)
                                    .setLayoutGravity(Gravity.BOTTOM)
                                    .setDuration(3000)
                                    .setAction("ACTION", new OnActionClickListener() {
                                        @Override
                                        public void onClick() {
                                        }
                                    })
                                    .show();
                            //Toast.makeText(getApplicationContext(), "Timeout Error!....Connection TimeOut! Please check your internet connect   ion.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        queue.add(request);
    }
}
