package com.dominik.hptracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
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

import com.dominik.hptracker.modelhp.Army;
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
    ArrayList<TextView> textViews = new ArrayList<TextView>();
    ArrayList<EditText> editTexts = new ArrayList<EditText>();
    Army army;
    File files[];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        File f = getFilesDir();
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

        TextView tv = new TextView(this);
        tv.setText("Army Name:");
        ll.addView(tv);

        final EditText nm = new EditText(this);
        nm.setText("");
        ll.addView(nm);

        for (int i = 0; i < files.length; i++)
        {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            final EditText num = new EditText(this);
            num.setInputType(InputType.TYPE_CLASS_NUMBER);
            num.setText("");


            TextView textView = new TextView(getApplicationContext());
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
                    if (obj.getString(Constants.TYPE).equals(Constants.LINEAR))
                    {
                        textView.setText(obj.getString(Constants.NAME) + " - " + obj.getString(Constants.HPNUMBER));
                        textView.setTextColor(Color.BLACK);
                        editTexts.add(num);
                        textViews.add(textView);
                        linearLayout.addView(num);
                        linearLayout.addView(textView);
                    }
                    else if (obj.getString(Constants.TYPE).equals(Constants.WARJACK))
                    {
                        textView.setText(obj.getString(Constants.NAME));
                        textView.setTextColor(Color.BLACK);
                        editTexts.add(num);
                        textViews.add(textView);
                        linearLayout.addView(num);
                        linearLayout.addView(textView);
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
            ll.addView(linearLayout);
        }
        Button create = new Button(this);
        create.setText("Create Army");
        ll.addView(create);

        Button delete = new Button(this);
        delete.setText("Delete Selected Units");
        ll.addView(delete);

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createConfirmDeleteDialog();
            }
        });

        create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (nm.getText().toString().equals(""))
                {
                    showEmptyFieldsPopup();
                }
                else
                {
                    army = new Army(nm.getText().toString());
                    for (TextView text : textViews)
                    {
                        int count = 0;
                        if (!editTexts.get(textViews.indexOf(text)).getText().toString().equals(""))
                        {
                            count = Integer.parseInt(editTexts.get(textViews.indexOf(text)).getText().toString());
                        }
                        for (int i = 0; i < count; i++)
                        {
                            army.units.add(text.getText().toString());
                        }
                    }
                    army.writeToJSON();
                    army.writeJSONToFile(getApplicationContext());
                    startActivity(new Intent(NewArmyActivity.this, MainActivity.class));
                }
            }
        });

        for (final TextView text: textViews)
        {
            text.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    editTexts.get(textViews.indexOf(text)).setText("1");
                }
            });
        }

        setContentView(sv);
    }

    private void showEmptyFieldsPopup()
    {
        AlertDialog.Builder emptyFieldsBuilder = new AlertDialog.Builder(this);
        emptyFieldsBuilder.setTitle("");
        emptyFieldsBuilder.setMessage("Please fill all fields.");
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

    private void createConfirmDeleteDialog()
    {
        AlertDialog.Builder emptyFieldsBuilder = new AlertDialog.Builder(this);
        emptyFieldsBuilder.setTitle("");
        emptyFieldsBuilder.setMessage("Are you sure that you want to delete?");
        emptyFieldsBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                for (TextView text: textViews)
                {
                    if (!editTexts.get(textViews.indexOf(text)).getText().toString().equals(""))
                    {
                        files[textViews.indexOf(text)].delete();
                    }
                }
                startActivity(new Intent(NewArmyActivity.this, MainActivity.class));
            }
        });
        emptyFieldsBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {

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
