package ru.owngame.vk2.alarm;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by user on 16.01.2016.
 */
public class RepeatingAlarmService extends BroadcastReceiver{
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if ((hour==20) && (minute==50))
            try {
                play(context,R.raw.girlfart01);//vibrator.vibrate(2000);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
