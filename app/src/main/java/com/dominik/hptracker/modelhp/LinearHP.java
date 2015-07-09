package com.dominik.hptracker.modelhp;

import android.util.Log;

import com.dominik.hptracker.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dominik on 6/19/2015.
 */
public class LinearHP extends ModelHPTemplate
{
    public int maxHP;
    public int currentHP;

    public LinearHP(String name, int HP)
    {
        super(name);
        maxHP = HP;
    }

    public LinearHP(JSONObject jsonObject)
    {
        super(jsonObject);
        try
        {
            maxHP = jsonObject.getInt(Constants.HPNUMBER);
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
            jsonObject.put(Constants.HPNUMBER, maxHP);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Log.e("JSON Error", e.getLocalizedMessage());
        }
    }
}
