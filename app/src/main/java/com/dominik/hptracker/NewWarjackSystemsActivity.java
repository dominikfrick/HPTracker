package com.dominik.hptracker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import java.util.ArrayList;


public class NewWarjackSystemsActivity extends ActionBarActivity
{
    private CheckBox[][] checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        checkBoxes = new CheckBox[6][6];

        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        TextView tv = new TextView(this);
        tv.setText("Write the letter representing the system you wish to add:");
        ll.addView(tv);

        EditText nm = new EditText(this);
        ll.addView(nm);

        for (int i = 0; i < 6; i++)
        {
            LinearLayout ll2 = new LinearLayout(this);
            ll2.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j <6; j++)
            {
                CheckBox ch = new CheckBox(getApplicationContext());
                checkBoxes[i][j] = ch;
                ch.setText("");
                ch.setBackgroundColor(0x00FF00);
                ch.setHighlightColor(0x00FF00);
                ll2.addView(ch);
            }
            ll.addView(ll2);
        }

        Button create = new Button(this);
        create.setText("Continue to system layout");
        ll.addView(create);

        setContentView(sv);
        //R.layout.activity_newwarjack
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
