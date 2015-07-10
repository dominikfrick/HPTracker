package com.dominik.hptracker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.dominik.hptracker.modelhp.LinearHP;


public class NewWarcasterActivity extends ActionBarActivity
{
    EditText tempName;
    EditText hp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        tempName = (EditText) findViewById(R.id.name1);
        hp = (EditText) findViewById(R.id.hp);

        setContentView(R.layout.activity_newwarcaster);
    }

    public void createJSONInstance(View v)
    {
        String modelName = tempName.getText().toString();
        int HP = Integer.valueOf(hp.getText().toString());

        LinearHP warcaster = new LinearHP(modelName, HP);
        warcaster.writeToJSON();
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
