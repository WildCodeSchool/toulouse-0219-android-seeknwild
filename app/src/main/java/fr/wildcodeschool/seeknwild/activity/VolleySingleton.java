package fr.wildcodeschool.seeknwild.activity;

import android.content.Context;
import android.os.Build;
import android.support.v4.util.Consumer;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.Treasure;

public class VolleySingleton {

    private final static String REQUEST_URL = "http://192.168.8.113:8080/";
    private static VolleySingleton instance;
    private static Context ctx;
    private RequestQueue requestQueue;

    private VolleySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    //TODO: autres appels API + ATTENTION AU CONSUMER
    public void getAdventures(final Consumer<List<Adventure>> listener) {

        String url = REQUEST_URL + "adventure";

        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("VOLLEY_SUCCESS", response.toString());
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();
                        List<Adventure> adventures = Arrays.asList(gson.fromJson(response.toString(), Adventure[].class));
                        listener.accept(adventures);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void createAdventure(Adventure adventure, final Consumer<Adventure> listener) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        String url = REQUEST_URL + "user/" + "4" + "/adventure";
        final String requestBody = gson.toJson(adventure);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("VOLLEY_SUCCESS", response.toString());
                Adventure adventure = gson.fromJson(response.toString(), Adventure.class);
                listener.accept(adventure);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
                }
                return null;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void createTreasure(Treasure treasure, Long idAdventure, final Consumer<Treasure> listener) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        String url = REQUEST_URL + "adventure/" + idAdventure + "/treasure";
        final String requestBody = gson.toJson(treasure);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("VOLLEY_SUCCESS", response.toString());
                Treasure treasure = gson.fromJson(response.toString(), Treasure.class);
                listener.accept(treasure);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
                }
                return null;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
