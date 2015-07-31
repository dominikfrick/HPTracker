package com.dominik.hptracker.modelhp;

import android.content.Context;
import android.util.Log;

import com.dominik.hptracker.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dominik on 7/7/2015.
 */
public class Army
{
    String name;
    public ArrayList<String> units;
    JSONObject jsonObject;

    public Army(String name)
    {
        this.name = name;
        jsonObject = new JSONObject();
        units = new ArrayList<>();
    }

    public Army(JSONObject jsonObject)
    {
        this.jsonObject = jsonObject;
        units = new ArrayList<>();
        try
        {
            name = jsonObject.getString(Constants.NAME);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.ARMYARRAY);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                units.add(jsonArray.getString(i));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void writeToJSON()
    {
        try
        {
            jsonObject.put(Constants.NAME, name);
            jsonObject.put(Constants.TYPE, Constants.ARMY);
            JSONArray jsonArray = new JSONArray();
            for (String modelHPTemplate : units)
            {
                jsonArray.put(modelHPTemplate);
            }
            jsonObject.put(Constants.ARMYARRAY, jsonArray);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Log.e("JSON Error", e.getLocalizedMessage());
        }
    }

    public final void writeJSONToFile(Context context)
    {
        try
        {
            File file = new File(context.getDir(Constants.ARMYDIR, Context.MODE_PRIVATE), name + ".json");
            //file.createNewFile(); delete this if it is unnecessary

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(jsonObject.toString().getBytes());
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void addUnit(ModelHPTemplate modelHPTemplate, int quantity)
    {
        for (int i = 0; i < quantity; i++)
        {
            units.add(modelHPTemplate.name);
        }
    }
}
