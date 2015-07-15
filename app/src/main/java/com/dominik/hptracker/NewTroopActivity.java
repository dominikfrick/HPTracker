package com.dominik.hptracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.dominik.hptracker.modelhp.LinearHP;


public class NewTroopActivity extends ActionBarActivity
{
    EditText tempName;
    EditText hp;
    EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtroop);

        tempName = (EditText) findViewById(R.id.troop_name);
        hp = (EditText) findViewById(R.id.troop_hp);
        number = (EditText) findViewById(R.id.troop_number);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void createJSONInstance(View v)
    {
        if(tempName.getText().toString().equals("")|| hp.getText().toString().equals("") || number.getText().toString().equals(""))
        {
            showEmptyFieldsPopup();
        }
        else
        {
            for(int i = 0; i < Integer.parseInt(number.getText().toString()); i++)
            {
                String modelName = tempName.getText().toString();
                int HP = Integer.parseInt(hp.getText().toString());

                LinearHP troop = new LinearHP(modelName.concat(Integer.toString(i)), HP);
                troop.writeToJSON();
                troop.writeJSONToFile(getApplicationContext());
            }

        }
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
