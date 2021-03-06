package com.dominik.hptracker.modelhp;

import com.dominik.hptracker.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ContentHandler;

/**
 * Created by Dominik on 6/19/2015.
 */
public class WarjackHP extends ModelHPTemplate
{
    public HPBox[][] HP;

    public WarjackHP(String name, int rows, int columns)
    {
        super(name, Constants.WARJACK);

        HP = new HPBox[rows][columns];
    }

    public WarjackHP(JSONObject jsonObject)
    {
        super(jsonObject);

        try
        {
            JSONArray hpArray = jsonObject.getJSONArray(Constants.HPARRAY);

            HP = new HPBox[hpArray.length()][hpArray.getJSONArray(0).length()];

            for (int i = 0; i < hpArray.length(); i++)
            {
                JSONArray hpRow = hpArray.getJSONArray(i);
                for (int j = 0; j < hpRow.length(); j++)
                {
                    String system = hpRow.getString(j);
                    if(system.equals(Constants.NULL))
                    {
                        HP[i][j] = null;
                    }
                    else
                    {
                        HP[i][j] = new HPBox(system);
                    }
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToJSON()
    {
        super.writeToJSON();

        try
        {
            JSONArray rows = new JSONArray();

            for (int i = 0; i < HP.length; i++)
            {
                JSONArray row = new JSONArray();
                for (int j = 0; j < HP[i].length; j++)
                {
                    if(HP[i][j] != null)
                    {
                        row.put(j, HP[i][j].system);
                    }
                    else
                    {
                        row.put(j, Constants.NULL);
                    }
                }
                rows.put(i, row);
            }
            jsonObject.put(Constants.HPARRAY, rows);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
