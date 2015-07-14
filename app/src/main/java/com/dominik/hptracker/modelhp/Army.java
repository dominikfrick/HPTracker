package com.dominik.hptracker.modelhp;

import android.util.Log;

import com.dominik.hptracker.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dominik on 7/7/2015.
 */
public class Army
{
    String name;
    ArrayList<String> units;
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
            jsonObject.put(Constants.ARMY, name);
            JSONArray jsonArray = new JSONArray(Constants.ARMYARRAY);
            for (String modelHPTemplate : units)
            {
                jsonArray.put(modelHPTemplate);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Log.e("JSON Error", e.getLocalizedMessage());
        }
    }

    final void writeJSONToFile()
    {
        try
        {
            FileWriter file = new FileWriter(name + ".json");
            try
            {
                file.write(jsonObject.toString());
            }
            catch (IOException e)
            {
                e.printStackTrace();

            }
            finally
            {
                file.close();
            }
        }
        catch (IOException e)
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
