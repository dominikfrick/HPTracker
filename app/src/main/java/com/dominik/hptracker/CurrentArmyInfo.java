package com.dominik.hptracker;

import com.dominik.hptracker.modelhp.ModelHPTemplate;

import java.util.ArrayList;

/**
 * Created by Dominik on 8/7/2015.
 */
public class CurrentArmyInfo
{
    private static CurrentArmyInfo Instance;
    public ArrayList<ModelHPTemplate> units;

    private  CurrentArmyInfo()
    {
        units = new ArrayList<>();
    }

    public static CurrentArmyInfo getInstance()
    {
        if (Instance == null)
        {
            Instance = new CurrentArmyInfo();
        }
        return Instance;
    }
}
