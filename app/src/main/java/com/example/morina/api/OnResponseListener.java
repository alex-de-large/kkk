package com.example.morina.api;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface OnResponseListener {

    void onResponse(JSONObject json, int requestsCode);
    void onResponse(JSONArray json, int requestsCode);
    void onResponseError(VolleyError error, int requestsCode);

}
