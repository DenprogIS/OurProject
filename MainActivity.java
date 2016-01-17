package ru.owngame.vk2.alarm;

import ru.owngame.vk2.alarm.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Button buttonStart, buttonStop;

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonStart:
                Log.v(this.getClass().getName(), "onClick: Starting service.");
                startService(new Intent(this, ServiceExample.class));
                break;
            case R.id.buttonStop:
                Log.v(this.getClass().getName(), "onClick: Stopping service.");
                stopService(new Intent(this, ServiceExample.class));
                break;
        }
    }
}
