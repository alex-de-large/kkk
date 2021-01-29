package com.example.morina.api;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Requests {

    private OnResponseListener mListener;
    private  static RequestQueue sRequestQueue;

    public Requests(OnResponseListener listener) {
        mListener = listener;
    }

    static {
        Cache cache = new DiskBasedCache(App.getContext().getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        sRequestQueue = new RequestQueue(cache, network);
        sRequestQueue.start();
    }

    public void get(String url, int requestCode) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Object json = new JSONTokener(response).nextValue();

                            if (json instanceof JSONObject) {
                                mListener.onResponse(new JSONObject(response), requestCode);
                            } else {
                                mListener.onResponse(new JSONArray(response), requestCode);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mListener.onResponseError(error, requestCode);
                    }
                });
        sRequestQueue.add(stringRequest);
    }

    public void post(String url, JSONObject data, int requestCode) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String temp = response.toString();
                            Object json = new JSONTokener(temp).nextValue();

                            if (json instanceof JSONObject) {
                                mListener.onResponse(new JSONObject(temp), requestCode);
                            } else {
                                mListener.onResponse(new JSONArray(temp), requestCode);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mListener.onResponseError(error, requestCode);
                    }
                });
        sRequestQueue.add(jsonObjectRequest);
    }

    public void setListener(OnResponseListener listener) {
        mListener = listener;
    }
}
