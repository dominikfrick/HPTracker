package com.dominik.hptracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dominik.hptracker.modelhp.Army;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Daniel on 7/24/2015.
 */
public class ShowArmyActivity extends Activity
{
    ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        File f = getDir(Constants.ARMYDIR, MODE_PRIVATE);
        File files[];
        FilenameFilter filter = new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String filename)
            {
                return  filename.length() >= 5 && filename.substring(filename.length()-5).toUpperCase().equals(".JSON");
            }
        };
        files = f.listFiles(filter);

        ScrollView sv = new ScrollView(this);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        TextView textView = new TextView(this);
        textView.setText("Choose an army");
        ll.addView(textView);

        for (int i = 0; i < files.length; i++)
        {
            CheckBox ch = new CheckBox(this);
            JSONObject obj;
            try
            {
                File file = new File(getDir(Constants.ARMYDIR, Context.MODE_PRIVATE), files[i].getName());
                FileInputStream fis = new FileInputStream(file);
                StringBuilder stringBuilder = new StringBuilder();
                int c;
                try
                {
                    while ((c = fis.read()) != -1)
                    {
                        stringBuilder.append(Character.toChars(c));
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    obj = new JSONObject(stringBuilder.toString());
                    if (obj.getString(Constants.TYPE).equals(Constants.ARMY))
                    {
                        ch.setText(obj.getString(Constants.NAME));
                        ch.setTextColor(Color.BLACK);
                        checkBoxes.add(ch);
                        ll.addView(ch);
                    }
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
        }

        Button create = new Button(this);
        create.setText("Create Army");
        ll.addView(create);

        create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean oneChecked = false;
                boolean moreChecked = false;
                for (CheckBox ch: checkBoxes)
                {
                    if (ch.isChecked())
                    {
                        if (!oneChecked)
                        {
                            oneChecked = true;
                        }
                        else
                        {
                            moreChecked = true;
                        }
                    }
                }
                if (!oneChecked || moreChecked) showEmptyFieldsPopup();
                else
                {
                    //ALL CODE HERE
                }
            }
        });

        setContentView(sv);
    }

    private void showEmptyFieldsPopup()
    {
        AlertDialog.Builder emptyFieldsBuilder = new AlertDialog.Builder(this);
        emptyFieldsBuilder.setTitle("");
        emptyFieldsBuilder.setMessage("Please choose one and only one army.");
        emptyFieldsBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                // Do nothing but close the dialog
            }
        });
        AlertDialog emptyFields = emptyFieldsBuilder.create();
        emptyFields.show();
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
