package com.dominik.hptracker.modelhp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.dominik.hptracker.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Dominik on 6/18/2015.
 */
public abstract class ModelHPTemplate
{
    public String name;
    public String type;
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

    public final void writeJSONToFile(Context context)
    {
        try
        {
            FileOutputStream fos = context.openFileOutput(name + ".json", Context.MODE_PRIVATE);
            fos.write(jsonObject.toString().getBytes());
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
