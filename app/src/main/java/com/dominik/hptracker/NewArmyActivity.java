package com.dominik.hptracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dominik.hptracker.modelhp.HPBox;
import com.dominik.hptracker.modelhp.WarjackHP;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;


public class NewArmyActivity extends ActionBarActivity
{
    ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        File f = getFilesDir();
        File files[];
        FilenameFilter filter = new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String filename)
            {
                if (filename.length() >= 5 && filename.substring(filename.length()-5).toUpperCase().equals(".JSON"))
                {
                    return true;
                }
                return false;
            }
        };
        files = f.listFiles();

        ScrollView sv = new ScrollView(this);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        CheckBox ch = new CheckBox(getApplicationContext());
        ch.setText(getFilesDir().toString());
        ll.addView(ch);

        for (int i = 0; i < files.length; i++)
        {
            CheckBox ch2 = new CheckBox(getApplicationContext());
            checkboxes.add(ch);
            JSONObject obj;
            try
            {
                FileInputStream fis = openFileInput(files[i].getName());
                StringBuilder stringBuilder = new StringBuilder();
                int c;
                try
                {
                    while ((c = fis.read()) != -1)
                    {
                        stringBuilder.append(c);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    obj = new JSONObject(stringBuilder.toString());
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }


            //try
            //{
            ch2.setText(files[i].getName());

                ch2.setTextColor(Color.BLACK);
            /*}
            catch (JSONException e)
            {
                e.printStackTrace();
            }*/
            ll.addView(ch2);
        }

        setContentView(sv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
