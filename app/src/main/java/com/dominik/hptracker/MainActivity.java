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
        Button myButton = (Button) findViewById(R.id.button1);
        final Spinner dropdown = (Spinner) findViewById(R.id.dropdown1);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String page = dropdown.getSelectedItem().toString();
                if (page.equals("Warcaster"))
                {
                    startActivity (new Intent(MainActivity.this, NewWarcasterActivity.class));
                }
                else if (page.equals("Warjack"))
                {
                    startActivity (new Intent(MainActivity.this, NewWarjackActivity.class));
                }
                else if (page.equals("Solo"))
                {
                    startActivity (new Intent(MainActivity.this, NewSoloActivity.class));
                }
                else
                {
                    startActivity (new Intent(MainActivity.this, NewTroopActivity.class));
                }
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
