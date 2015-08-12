package com.dominik.hptracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.dominik.hptracker.modelhp.HPBox;
import com.dominik.hptracker.modelhp.WarjackHP;


public class NewWarjackActivity extends ActionBarActivity
{
    private ToggleButton[][] toggleButtons;
    boolean settingHP = true;
    WarjackHP warjack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        toggleButtons = new ToggleButton[6][6];

        ScrollView sv1 = new ScrollView(this);
        HorizontalScrollView sv = new HorizontalScrollView(this);
        sv1.addView(sv);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        final TextView tv = new TextView(this);
        tv.setText("Warjack Name:");
        ll.addView(tv);

        final EditText nm = new EditText(this);
        nm.setText("");
        ll.addView(nm);

        final TextView tv2 = new TextView(this);
        tv2.setText("Select all applicable squares.");
        ll.addView(tv2);


        for (int i = 0; i < 6; i++)
        {
            LinearLayout ll2 = new LinearLayout(this);
            ll2.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j <6; j++)
            {
                ToggleButton tb = new ToggleButton(getApplicationContext());
                toggleButtons[i][j] = tb;
                tb.setText(" ");
                tb.setTextOn(" ");
                tb.setTextOff(" ");
                ll2.addView(tb);
            }
            ll.addView(ll2);
        }

        final Button nextStep = new Button(this);
        nextStep.setText("Continue to system layout");
        ll.addView(nextStep);

        final CheckBox ch = new CheckBox(getApplicationContext());
        ch.setText("Done with adding systems.");
        ch.setTextColor(Color.BLACK);

        nextStep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (settingHP)
                {
                    if (nm.getText().toString().equals("") || nm.getText().toString().contains("/") || nm.getText().toString().contains("\\"))
                    {
                        showEmptyFieldsPopup();
                    } else
                    {
                        warjack = new WarjackHP(nm.getText().toString(), 6, 6);
                        for (int i = 0; i < 6; i++)
                        {
                            for (int j = 0; j < 6; j++)
                            {
                                if (toggleButtons[i][j].isChecked())
                                {
                                    warjack.HP[i][j] = new HPBox("");
                                    toggleButtons[i][j].setChecked(false);
                                }
                            }
                        }
                        ll.addView(ch);
                        settingHP = false;
                        nextStep.setText("Continue");
                        nextStep.invalidate();
                        tv.setText("System Symbol:");
                        tv.invalidate();
                        nm.setText("");
                        nm.invalidate();
                    }
                } else
                {
                    if (nm.getText().length() > 1)
                    {
                        showMultipleCharacterPopup();
                    }
                    else
                    {
                        if (nm.getText().toString().equals(""))
                        {
                            showEmptyFieldsPopup();
                        } else
                        {
                            for (int i = 0; i < 6; i++)
                            {
                                for (int j = 0; j < 6; j++)
                                {
                                    if (toggleButtons[i][j].isChecked())
                                    {
                                        if (warjack.HP[i][j] != null)
                                        {
                                            warjack.HP[i][j].system = nm.getText().toString();
                                        }
                                    }
                                    toggleButtons[i][j].setChecked(false);
                                }
                            }
                        }
                        nm.setText("");
                        nm.invalidate();
                        if (ch.isChecked())
                        {
                            warjack.writeToJSON();
                            warjack.writeJSONToFile(getApplicationContext());
                            startActivity(new Intent(NewWarjackActivity.this, MainActivity.class));}
                        }
                }
                }
        });

        setContentView(sv1);
    }

    private void showEmptyFieldsPopup()
    {
        AlertDialog.Builder emptyFieldsBuilder = new AlertDialog.Builder(this);
        emptyFieldsBuilder.setTitle("");
        emptyFieldsBuilder.setMessage("Please fill all fields or remove slashes from the name.");
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

    private void showMultipleCharacterPopup()
    {
        AlertDialog.Builder emptyFieldsBuilder = new AlertDialog.Builder(this);
        emptyFieldsBuilder.setTitle("");
        emptyFieldsBuilder.setMessage("Make the symbol only one letter.");
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

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {

    }
}
