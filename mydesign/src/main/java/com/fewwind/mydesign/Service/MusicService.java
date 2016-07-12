package com.fewwind.mydesign.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.fewwind.mydesign.utils.Constants;

/**
 * Created by fewwind on 2015/11/3.
 */
public class MusicService extends Service implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private AudioManager mAm;

    private boolean isPlaymusic;
    private String url;
    private MediaPlayer mediaPlayer;

    MyUrlBraodCast myUrlBraodCast;
    Intent intentStatus;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAm = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(this);

        myUrlBraodCast = new MyUrlBraodCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BROAD_URL);
        filter.addAction(Constants.BROAD_Play_OR_PAUSE);
        registerReceiver(myUrlBraodCast, filter);

        intentStatus = new Intent(Constants.BROAD_PLAY_STATUS);
    }


    public class MyUrlBraodCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                if (action.endsWith(Constants.BROAD_Play_OR_PAUSE)) {
                    boolean play = intent.getBooleanExtra("play", true);
                    Log.v("tag","控制器动作"+play);
                    if (play) resume();
                        else pause();

                } else if (action.endsWith(Constants.BROAD_URL)) {

                        Bundle bundle = intent.getExtras();
                        if (bundle != null) {
                            url = bundle.getString("url");
                            Log.d("tag", "地址" + url + "是否播放" + isPlaymusic);
                                play(url);
                        }

                }


            }


        }
    }


    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // Pause playback
                pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
                resume();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // mAm.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
                mAm.abandonAudioFocus(afChangeListener);
                // Stop playback
                stop();
            }

        }
    };

    private boolean requestFocus() {
        // Request audio focus for playback
        int result = mAm.requestAudioFocus(afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }


    private void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    Handler mHanler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (mediaPlayer != null && mediaPlayer.isPlaying())
                intentStatus.putExtra("current", mediaPlayer.getCurrentPosition() / 1000);

            sendBroadcast(intentStatus);

            mHanler.sendEmptyMessageDelayed(0, 1000);
        }

    };

    private void play(String url) {
        Log.e("tag", requestFocus() + "");
        mHanler.removeMessages(0);
        if (requestFocus()) {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }

            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {

            mediaPlayer.release();
            mediaPlayer = null;
        }
        unregisterReceiver(myUrlBraodCast);
    }

    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {


    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (!mp.isLooping()) {
            mAm.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        Intent intent = new Intent(Constants.BROAD_TOTAL_DURATION);
        intent.putExtra("total", mediaPlayer.getDuration() / 1000);
        sendBroadcast(intent);

        mHanler.sendEmptyMessage(0);
    }
}
