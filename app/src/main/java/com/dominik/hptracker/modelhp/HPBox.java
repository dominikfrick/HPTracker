package com.dominik.hptracker.modelhp;

import org.json.JSONObject;

/**
 * Created by Dominik on 6/18/2015.
 */
public class HPBox
{
    public String system = "";
    public boolean damaged;

    public  HPBox(String system)
    {
        this.system = system;
        damaged = false;
    }
}
