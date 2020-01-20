package com.yaroslav.chucknorristest.jokes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJsonAsync extends AsyncTask<String, Void, ArrayList<String>> {
    private static final String TOP_KEY = "value";
    private static final String TYPE_KEY = "type";
    private static final String ITEM_KEY = "joke";
    private static final String SUCCESS = "success";

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> listOfJokes = new ArrayList<>();
        try {
            JSONObject topLevel = new JSONObject(strings[0]);
            String typeOfJsonResponse = topLevel.getString(TYPE_KEY);
            if (typeOfJsonResponse != null && typeOfJsonResponse.equals(SUCCESS)) {
                JSONArray jokesArray = topLevel.getJSONArray(TOP_KEY);

                for (int i = 0; i < jokesArray.length(); i++) {
                    JSONObject nestedObject = jokesArray.getJSONObject(i);
                    String jokeItem = nestedObject.getString(ITEM_KEY);
                    listOfJokes.add(jokeItem);
                }
            }
            return listOfJokes;
        } catch (JSONException e) {
            Log.e("PARSE_JSON", "JSON Exception: " + "(" + e.getClass() + "): " + e.getMessage());
        }

        return null;
    }
}
