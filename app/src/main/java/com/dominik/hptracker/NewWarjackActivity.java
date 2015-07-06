package com.dominik.hptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class NewWarjackActivity extends ActionBarActivity
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
        tv.setText("New Warjack:");
        ll.addView(tv);

        EditText nm = new EditText(this);
        nm.setText("NAME");
        ll.addView(nm);

        TextView tv2 = new TextView(this);
        tv2.setText("Select all applicable health squares.");
        ll.addView(tv2);

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

        Button nextStep = new Button(this);
        nextStep.setText("Continue to system layout");
        ll.addView(nextStep);

        nextStep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(NewWarjackActivity.this, NewWarjackSystemsActivity.class));
            }
        });


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
