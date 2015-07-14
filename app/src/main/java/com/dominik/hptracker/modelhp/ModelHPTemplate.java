package com.dominik.hptracker.modelhp;

import android.util.Log;

import com.dominik.hptracker.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Dominik on 6/18/2015.
 */
abstract class ModelHPTemplate
{
    String name;
    String type;
    JSONObject jsonObject;

    public ModelHPTemplate(String name, String type)
    {
        this.name = name;
        this.type = type;
        jsonObject = new JSONObject();
    }

    public ModelHPTemplate(JSONObject jsonObject)
    {
        this.jsonObject = jsonObject;
        try
        {
            name = jsonObject.getString(Constants.NAME);
            type = jsonObject.getString(Constants.TYPE);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    void writeToJSON()
    {
        try
        {
            jsonObject.put(Constants.NAME, name);
            jsonObject.put(Constants.TYPE, type);
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
            } catch (IOException e)
            {
                e.printStackTrace();

            } finally
            {
                file.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
