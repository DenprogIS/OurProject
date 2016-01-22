package ru.owngame.vk2.timetable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by user on 16.01.2016.
 */
public class RepeatingAlarmService extends BroadcastReceiver{
    private Context context;
    //private static ArrayList<String> Data;
    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<String> key=intent.getStringArrayListExtra("time");
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        Toast.makeText(context, String.valueOf(key.size()), Toast.LENGTH_LONG).show();
        /*if (Data.size()>0) {
            Toast.makeText(context, Data.get(0), Toast.LENGTH_LONG).show();
            /*int hour = Integer.valueOf(Data.get(0).substring(0, Data.get(0).indexOf(":")));
            int minute = Integer.valueOf(Data.get(0).substring(Data.get(0).indexOf(":")));
            if ((hours == hour) && (minutes == minute))
                try {
                    play(context, R.raw.girlfart01);//vibrator.vibrate(2000);Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    Data.remove(0);
                    Data.remove(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }*/
        Log.v(this.getClass().getName(), "Timed alarm onReceive() started at time: " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
    }
    private void play(Context context, int resid) throws IOException {
        AssetFileDescriptor afd = context.getResources().openRawResourceFd(resid);
        if (afd == null)
            return;
        MediaPlayer player = new MediaPlayer();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        afd.close();
        player.prepareAsync();
    }
}
