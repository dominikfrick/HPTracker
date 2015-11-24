package com.dominik.hptracker;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


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
        Button sendEmailButton = (Button) findViewById(R.id.emailFilesButton);
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
                startActivity(new Intent(MainActivity.this, ChooseArmyActivity.class));
            }
        });
        sendEmailButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                emailIntent.setType("text/plain");
                //i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "HP Tracker JSON Files");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "The JSON Files are attached");

                ZipOutputStream zos;

                File f = getFilesDir();
                FilenameFilter filter = new FilenameFilter()
                {
                    @Override
                    public boolean accept(File dir, String filename)
                    {
                        return filename.length() >= 5 && filename.substring(filename.length() - 5).toUpperCase().equals(".JSON");
                    }
                };
                File[] files = f.listFiles(filter);
                try
                {
                    zos = new ZipOutputStream(new FileOutputStream("Units.zip"));
                    try
                    {
                        for (int i = 0; i < files.length; ++i)
                        {
                            ZipEntry entry = new ZipEntry(files[i].getName());
                            zos.putNextEntry(entry);
                            zos.write(files[i].toString().getBytes());//May not work
                            zos.closeEntry();
                        }
                    } finally
                    {
                        zos.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                f = getDir(Constants.ARMYDIR, MODE_PRIVATE);
                files = f.listFiles(filter);
                try
                {
                    zos = new ZipOutputStream(new FileOutputStream("Armies.zip"));
                    try
                    {
                        for (int i = 0; i < files.length; ++i)
                        {
                            ZipEntry entry = new ZipEntry(files[i].getName());
                            zos.putNextEntry(entry);
                            zos.write(files[i].toString().getBytes());//May not work
                            zos.closeEntry();
                        }
                    } finally
                    {
                        zos.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                ArrayList<Uri> uris = new ArrayList<Uri>();
                uris.add(Uri.fromFile(new File("Units.zip")));
                uris.add(Uri.fromFile(new File("Armies.zip")));

                emailIntent.putExtra(Intent.EXTRA_STREAM, uris);

                try
                {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {

    }
}
