package com.dominik.hptracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newCardButton = (Button) findViewById(R.id.button1);
        Button newArmyButton = (Button) findViewById(R.id.newArmyButton);
        Button loadArmyButton = (Button) findViewById(R.id.loadArmyButton);
        final Spinner dropdown = (Spinner) findViewById(R.id.dropdown1);
        newCardButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String page = dropdown.getSelectedItem().toString();
                if (page.equals("Linear"))
                {
                    startActivity(new Intent(MainActivity.this, NewLinearActivity.class));
                }
                else if (page.equals("Warjack"))
                {
                    startActivity(new Intent(MainActivity.this, NewWarjackActivity.class));
                }
                else
                {
                    startActivity(new Intent(MainActivity.this, NewColossalActivity.class));
                }
            }
        });
        newArmyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, NewArmyActivity.class));
            }
        });
        loadArmyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, ShowArmyActivity.class));
            }
        });
    }

    public void buttonOnClick(View v)
    {
        Button button = (Button) v;
        button.setText("clicked");
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
