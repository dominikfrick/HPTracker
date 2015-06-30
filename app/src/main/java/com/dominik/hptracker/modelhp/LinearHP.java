package com.dominik.hptracker.modelhp;

import android.util.Log;

import com.dominik.hptracker.Contants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dominik on 6/19/2015.
 */
public class LinearHP extends ModelHPTemplate
{
    public int maxHP;
    public int currentHP;

    public LinearHP(String name)
    {
        super(name);
    }

    public LinearHP(JSONObject jsonObject)
    {
        super(jsonObject);
        try
        {
            maxHP = jsonObject.getInt(Contants.HPNUMBER);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    void writeToJSON()
    {
        super.writeToJSON();

        try
        {
            jsonObject.put(Contants.HPNUMBER, maxHP);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Log.e("JSON Error", e.getLocalizedMessage());
        }
    }
}
