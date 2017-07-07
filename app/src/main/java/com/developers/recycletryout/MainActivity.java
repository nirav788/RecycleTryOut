package com.developers.recycletryout;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.developers.recycletryout.Adapter.MyRecyclerAdapter;
import com.developers.recycletryout.model.Sathiya_Book;
import com.developers.recycletryout.network.NetworkController;
import com.liuguangqiang.cookie.CookieBar;
import com.liuguangqiang.cookie.OnActionClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    RecyclerView recyclerView;
    String Requesturl = "http://www.terapanthmmbg.org/json_sahitya.php";
    List<Sathiya_Book> feedsList = new ArrayList<Sathiya_Book>();
    MyRecyclerAdapter adapter;
    private MyRecyclerAdapter.ListItemClickListener onClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_beneificaries);
        adapter = new MyRecyclerAdapter(this, feedsList, onClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.alpha_gradient));
        //Getting Instance of Volley Request Queue
        queue = NetworkController.getInstance(this).getRequestQueue();
        //Volley's inbuilt class to make Json array request


        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...");
        loading.setCancelable(true);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, Requesturl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Dismissing progress dialog
                        loading.dismiss();


                        try {

                            response.getString("status");
                            Log.d("status", response.toString());
                            JSONArray jsonArray = response.getJSONArray("data");
                            Log.d("DEBUG", response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                int count = jsonArray.length();


                                Log.d("Array Length", String.valueOf(count));

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Sathiya_Book dataSet = new Sathiya_Book();


                                dataSet.setTitle(jsonObject.getString("title"));
                                dataSet.setName(jsonObject.getString("name"));
                                dataSet.setTips(jsonObject.getString("tips"));
                                dataSet.setStatis(jsonObject.getString("statis"));

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

                                    new CookieBar.Builder(MainActivity.this)
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

                                    new CookieBar.Builder(MainActivity.this)
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

                                    new CookieBar.Builder(MainActivity.this)
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

                                    new CookieBar.Builder(MainActivity.this)
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

                                    new CookieBar.Builder(MainActivity.this)
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
        queue.add(jsonRequest);
    }
}
