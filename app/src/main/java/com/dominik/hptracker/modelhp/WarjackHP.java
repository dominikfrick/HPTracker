package com.dominik.hptracker.modelhp;

import com.dominik.hptracker.Contants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dominik on 6/19/2015.
 */
public class WarjackHP extends ModelHPTemplate
{
    public HPBox[][] HP;

    public WarjackHP(String name, int rows, int columns)
    {
        super(name);

        HP = new HPBox[rows][columns];
    }

    public WarjackHP(JSONObject jsonObject)
    {
        super(jsonObject);

        try
        {
            JSONArray hpArray = jsonObject.getJSONArray(Contants.HPARRAY);

            HP = new HPBox[hpArray.length()][hpArray.getJSONArray(0).length()];

            for (int i = 0; i < hpArray.length(); i++)
            {
                JSONArray hpRow = hpArray.getJSONArray(i);
                for (int j = 0; j < hpRow.length(); j++)
                {
                    String system = hpRow.getString(j);
                    if (!system.equals(""))
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
    void writeToJSON()
    {
        super.writeToJSON();

        JSONArray rows = new JSONArray();

        for (int i = 0; i < HP.length; i++)
        {
            JSONArray row = new JSONArray();
            for (int j = 0; j < HP[i].length; j++)
            {
                if(HP[i][j] != null)
                {
                    try
                    {
                        row.put(j, HP[i][j].system);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            try
            {
                rows.put(i, row);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            jsonObject.put(Contants.HPARRAY, rows);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
