package com.example.viswas.ClientPlayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.viswas.KeyCommon.MusicPlayer;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    protected static final String TAG = "PlayerServiceUser";
    private MusicPlayer mMusicplayerService;
    private boolean mIsBound = false;
    public ServiceConnection mServiceConnection;
    private MediaPlayer player = null;
    private ImageButton playButton = null;
    private ImageButton pauseButton = null;
    private ImageButton stopButton = null;
    private Button getTransactButton=null;
    private EditText songNoEditText=null;
    private int songNumber ;
    private  List<String> transactionsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        		/*Find the view elements */
        songNoEditText=(EditText)findViewById(R.id.songNumber_et);
        playButton = (ImageButton) findViewById(R.id.play);
        pauseButton = (ImageButton) findViewById(R.id.pause);
        stopButton = (ImageButton) findViewById(R.id.stop);
        getTransactButton=(Button)findViewById(R.id.trans_button_id);

		/*Set onClick Listeners*/
        playButton.setOnClickListener(onButtonClick);
        stopButton.setOnClickListener(onButtonClick);
        pauseButton.setOnClickListener(onButtonClick);
        getTransactButton.setOnClickListener(onButtonClick);

        mServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder iservice) {
                mMusicplayerService = MusicPlayer.Stub.asInterface(iservice);
                mIsBound = true;
            }
            public void onServiceDisconnected(ComponentName className) {
                mMusicplayerService = null;
                mIsBound = false;
            }
        };
		/*Bind to the service using the service connection*/
        if (!mIsBound) {
            boolean b = false;
            Intent it = new Intent(MusicPlayer.class.getName());
            it.setAction("com.example.viswas.PlayMusic");
            ResolveInfo info = getPackageManager().resolveService(it, Context.BIND_AUTO_CREATE);
            it.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
            startService(it);
            b=bindService(it, mServiceConnection, Context.BIND_AUTO_CREATE);
            if (b) {
                Log.i(TAG, "BindService() succeeded!");
            } else {
                Log.i(TAG, "BindService() failed!");
            }
        }
    }

    private View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Perform input validation of the edit text
            if(v.getId()!=R.id.trans_button_id) {
                String toast_msg=null;
                toast_msg = "Enter a value from 1-3";
                String et_string=songNoEditText.getText().toString();
                String pattern= "^[0-3]*$";

                if(et_string.length()==0) {
                    Toast.makeText(getApplicationContext(), (String) toast_msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_string.matches(pattern)){
                    songNumber = Integer.parseInt(songNoEditText.getText().toString());
                    if(songNumber<=0 || songNumber>3){
                        Toast.makeText(getApplicationContext(), (String) toast_msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), (String) toast_msg, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
			/*If the input is correct based on the button clicked call the aidl interface functions */
            switch (v.getId()) {
                case R.id.play: {
                    try {
                        if (mIsBound) {
                            //call resume_or_Play_Song() implemented at the server side to play or resume a song
                            transactionsList.add("User played song " + songNumber);
                            mMusicplayerService.resume_or_Play_Song(songNumber);
                        }
                        else {
                            Log.i(TAG, "Service was not bound!");
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, e.toString());
                    }
                    break;
                }
                case R.id.stop: {

                    try {
                        if (mIsBound) {
                            //call StopSong() implemented at the server side to stop a song
                            transactionsList.add("User stopped song " + songNumber);
                            mMusicplayerService.StopSong();
                        }
                        else {
                            Log.i(TAG, "Service was not bound!");
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, e.toString());
                    }
                    break;
                }
                case R.id.pause: {
                    try {
                        // Call KeyGenerator and pause the song
                        if (mIsBound) {
                            //call pauseSong() implemented at the server side to pause a song
                            transactionsList.add("User paused song " + songNumber);
                            mMusicplayerService.pauseSong(songNumber);
                        }
                        else {
                            Log.i(TAG, "Service was not bound!");
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, e.toString());
                    }
                    break;
                }
                case R.id.trans_button_id:{



                        if (mIsBound) {
                            //Start a new activity(showTransactions) and pass the list of transactions obtained to it
                            Intent i = new Intent(MainActivity.this, getTransactions.class);
                            i.putExtra("mylist", (ArrayList)transactionsList);
                            startActivity(i);
                        }
                        else {
                            Log.i(TAG, "Service was not bound!");
                        }

                }
            }
        }
    };

    protected void onDestroy() {
        super.onDestroy();
        try{
        mMusicplayerService.StopSong();
            unbindService(mServiceConnection);
        }
        catch (RemoteException e) {
            Log.e(TAG, e.toString());
        }


    }
}
