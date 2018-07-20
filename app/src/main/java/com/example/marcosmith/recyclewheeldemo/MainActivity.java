package com.example.marcosmith.recyclewheeldemo;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private static final String URL_DATA = "https://simplifiedcoding.net/demos/marvel/";
    private MediaPlayer mPlayer;

    public List<ListItems> listItemsarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItemsarray = new ArrayList<>();

        loadRecyclerViewData();

        final  int MAX_VOLUME = 100;
        final float volume = (float) (1 - (Math.log(MAX_VOLUME - 50) / Math.log(MAX_VOLUME)));
        mPlayer = MediaPlayer.create(this, R.raw.bgmusic);
        mPlayer.setVolume(volume, volume);
        mPlayer.start();
        mPlayer.setLooping(true);

        }


    @Override
    protected void onPause() {
        super.onPause();{
            mPlayer.pause();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();{
            mPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();{
            mPlayer.release();
        }
    }

        private void loadRecyclerViewData () {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();


            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        //JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i<array.length() ; i++) {
                            JSONObject o = array.getJSONObject(i);
                            ListItems item = new ListItems(
                                    o.getString("name"),
                                    o.getString("bio"),
                                    o.getString("imageurl")
                            );
                            listItemsarray.add(item);
                        }

                        adapter = new MyAdapter(listItemsarray, getApplicationContext());
                        recyclerView.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            );

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }

    }
