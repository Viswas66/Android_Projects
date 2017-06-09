package com.example.viswas.audioserver;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.viswas.KeyCommon.MusicPlayer;
import com.example.viswas.KeyCommon.R;

import java.io.IOException;


public class AudioServer extends Service {


    private int mCurrentSongNumber;
    static private MediaPlayer mp=new MediaPlayer();
    final static int[] songNumberList={R.raw.song1,R.raw.song2, R.raw.song3};
    private boolean isStarted = false;

    // Implement the Stub for this Object
    private final MusicPlayer.Stub mBinder = new MusicPlayer.Stub() {
        /*Implementation of playSong() defined in AIDL file
          It plays the specified song using the music player
          */
        public void playSong(int songNumber) {
            try {
                //Get the file descriptor for the currently requested songNumber and set the data source
                AssetFileDescriptor afd = getApplicationContext().getResources().openRawResourceFd(songNumberList[songNumber - 1]);
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepare();
                mp.start();
                afd.close();
            } catch (IOException e) {
                Log.i("vish", "Exception caused in playSong setDataSource ");
            }
            isStarted = true;
            mCurrentSongNumber = songNumber;
        }

        /*Implementation of resume_or_Play_Song() defined in AIDL file
          It resumes the old song or plays new song using the music player
          */
        public void resume_or_Play_Song(int songNumber) {
            // Resume a previously started song
            if (isStarted) {
                if (mCurrentSongNumber == songNumber) {
                    mp.start();
                    ContentValues values = new ContentValues();
                } else {
                    mp.stop();
                    mp.reset();
                    playSong(songNumber);
                }
            }
            // play the newly requested song
            else {
                playSong(songNumber);
            }
            mCurrentSongNumber = songNumber;
        }

		/*Implementation of playSong() defined in AIDL file
		It plays the specified song using the music player
		*/

        public void pauseSong(int songNumber) {
            //If player is playing a song then pause it
            if (mp.isPlaying()) {
                mp.pause();
                ContentValues values = new ContentValues();

            }
        }

		/*Implementation of stopSong() defined in AIDL file
		It stops the specified song using the music player */

        public void stopSong() {
            mp.stop();
            mp.reset();
            ContentValues values = new ContentValues();
            isStarted = false;
        }


    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //unbindService(mServiceConnection);
        Log.i("Viswas", "ondestroy called in Audioserver");
    }

    // Return the Stub defined above
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        Log.i("onStartCommand", "called");
        return START_NOT_STICKY;
    }
}
