package ru.owngame.vk2.timetable;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int globalId=0;

     ArrayList<String> Data = new ArrayList<String>();

          //{"Time", "Event"};
    GridView gvMain;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button speakButton = (Button) findViewById(R.id.button);
        speakButton.setOnClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //my code

        adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, Data);
        gvMain = (GridView) findViewById(R.id.gridView);
        gvMain.setAdapter(adapter);
        gvMain.setNumColumns(2);
        Button btnStart = (Button) findViewById(R.id.button2);
        Button btnStop = (Button) findViewById(R.id.button3);
        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startServise();
            }
        };

        View.OnClickListener onClick2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopServise();
            }
        };

        btnStart.setOnClickListener(onClick);
        btnStop.setOnClickListener(onClick2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            SpeechRecognition.run(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String  results = matches.get(0);
            if ((results.indexOf(" ")!=-1)&&(results.lastIndexOf(" ")==results.indexOf(" "))) { //correctly or uncorrectly
                String time = results.substring(0, results.indexOf(" "));
                if (time.indexOf(":") == -1)
                    time = insertForString(time, time.length() - 2, ':'); //addition ':' for results (translate to time format)
                ///////REPOSITORY OF CODE /////     (str.substring(0, str.indexOf(" ")));       (str.substring(str.indexOf(" ")));
                Data.add(time);
                Data.add(results.substring(results.indexOf(" ")));
                update();
            }else
            {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Пожалуйста, повторите запрос!");
                dlgAlert.create().show();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void update()
    {
        gvMain.setAdapter(adapter);
        gvMain.setNumColumns(2);
    }

    private void startServise()
    {
        Log.v(this.getClass().getName(), "onClick: Starting service.");
        startService(new Intent(this, ServiceExample.class));
    }

    private void stopServise()
    {
        Log.v(this.getClass().getName(), "onClick: Stopping service.");
        stopService(new Intent(this, ServiceExample.class));
    }

    private String insertForString(String str,int index, char symbol)
    {
        StringBuffer s_buffer = new StringBuffer(str.subSequence(0, str.length()));
        s_buffer=s_buffer.insert(index, symbol);
        str=s_buffer.toString();
        return str;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
