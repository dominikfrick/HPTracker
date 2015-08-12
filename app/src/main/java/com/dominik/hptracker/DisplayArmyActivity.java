package com.dominik.hptracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dominik.hptracker.modelhp.LinearHP;
import com.dominik.hptracker.modelhp.ModelHPTemplate;
import com.dominik.hptracker.modelhp.WarjackHP;


public class DisplayArmyActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        ScrollView sv = new ScrollView(this);
        sv.addView(hsv);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        hsv.addView(ll);

        for (final ModelHPTemplate unit: CurrentArmyInfo.getInstance().units)
        {
            TextView textView = new TextView(getApplicationContext());
            textView.setText(unit.name);
            textView.setTextColor(Color.BLACK);
            ll.addView(textView);
            if (unit.type.equals(Constants.LINEAR))
            {
                LinearLayout healthBar = new LinearLayout(getApplicationContext());
                healthBar.setOrientation(LinearLayout.HORIZONTAL);
                if (((LinearHP)unit).maxHP > 5)
                {
                    EditText currentHealth = new EditText(this);
                    currentHealth.setInputType(InputType.TYPE_CLASS_NUMBER);
                    currentHealth.setText(((LinearHP) unit).currentHP);
                    currentHealth.setClickable(false);
                    healthBar.addView(currentHealth);
                    ProgressBar healthRange = new ProgressBar(this);
                    healthRange.setMax(((LinearHP) unit).maxHP);
                    healthRange.setProgress(((LinearHP) unit).currentHP);
                    Button add = new Button(this);
                    Button subtract = new Button(this);
                    add.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            ((LinearHP) unit).currentHP++;
                            currentHealth.setText(((LinearHP) unit).currentHP);
                            healthRange.setProgress(((LinearHP) unit).currentHP);
                            healthRange.invalidate();
                            currentHealth.invalidate();
                        }
                    });
                    subtract.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            ((LinearHP) unit).currentHP--;
                            currentHealth.setText(((LinearHP) unit).currentHP);
                            healthRange.setProgress(((LinearHP) unit).currentHP);
                            healthRange.invalidate();
                            currentHealth.invalidate();
                        }
                    });
                }
                else
                {
                    for (int i = 0; i < ((LinearHP) unit).maxHP; i++)
                    {
                        final ToggleButton tb = new ToggleButton(getApplicationContext());
                        tb.setText(" ");
                        tb.setTextOn(" ");
                        tb.setTextOff(" ");
                        if (i < ((LinearHP) unit).currentHP)
                        {
                            tb.setChecked(true);
                        }
                        tb.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                if (tb.isChecked())
                                {
                                    ((LinearHP) unit).currentHP++;
                                } else
                                {
                                    ((LinearHP) unit).currentHP--;
                                }
                            }
                        });
                        healthBar.addView(tb);
                    }
                }
                ll.addView(healthBar);
            }
            if (unit.type.equals(Constants.WARJACK))
            {
                LinearLayout healthRows = new LinearLayout(getApplicationContext());
                healthRows.setOrientation(LinearLayout.VERTICAL);
                for (int i = 0; i < ((WarjackHP) unit).HP.length; i++)
                {
                    LinearLayout healthRow = new LinearLayout(getApplicationContext());
                    healthRow.setOrientation(LinearLayout.HORIZONTAL);
                    for (int j = 0; j< ((WarjackHP) unit).HP[i].length; j++)
                    {
                        final int row = i;
                        final int col = j;
                        final ToggleButton tb = new ToggleButton(getApplicationContext());
                        tb.setText(" ");
                        tb.setTextOn(" ");
                        tb.setTextOff(" ");
                        tb.setTextColor(Color.BLACK);
                        if (!((((WarjackHP) unit).HP[row][col]) == null))
                        {
                            if ((((WarjackHP) unit).HP[row][col].damaged == true))
                            {
                                tb.setChecked(true);
                            }
                        }
                        tb.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                if (tb.isChecked())
                                {
                                    ((WarjackHP) unit).HP[row][col].damaged = true;
                                }
                                else
                                {
                                    ((WarjackHP) unit).HP[row][col].damaged = false;
                                }
                            }
                        });
                        if (((WarjackHP) unit).HP[i][j] == null)
                        {
                            tb.setBackgroundColor(Color.GRAY);
                            tb.setClickable(false);
                        }
                        if (((WarjackHP) unit).HP[i][j] != null && !(((WarjackHP) unit).HP[i][j].system.equals("")))
                        {
                            tb.setText(((WarjackHP)unit).HP[i][j].system);
                            tb.setTextOn(((WarjackHP)unit).HP[i][j].system);
                            tb.setTextOff(((WarjackHP)unit).HP[i][j].system);
                        }
                        healthRow.addView(tb);
                    }
                    healthRows.addView(healthRow);
                }
                ll.addView(healthRows);
            }
        }

        Button reset = new Button(this);
        reset.setText("Reset");
        ll.addView(reset);

        reset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createConfirmResetDialog();
            }
        });

        setContentView(sv);
    }

    private void createConfirmResetDialog()
    {
        AlertDialog.Builder emptyFieldsBuilder = new AlertDialog.Builder(this);
        emptyFieldsBuilder.setTitle("");
        emptyFieldsBuilder.setMessage("Are you sure that you want to reset this army?");
        emptyFieldsBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                CurrentArmyInfo.getInstance().units.clear();
                startActivity(new Intent(DisplayArmyActivity.this, MainActivity.class));
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

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {

    }
}
