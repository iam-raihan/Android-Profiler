package com.raihan.profiler;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private RadioGroup mRadioGroup;
	private SeekBar mSeekBar;
	private AudioManager mAudioManager;
	private BroadcastReceiver mReceiver;
	private IntentFilter mIntent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        mSeekBar = (SeekBar)findViewById(R.id.seekBar1);
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        
        mSeekBar.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {     
        	boolean tracking = true;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {     
            	if(tracking)
            		mAudioManager.setStreamVolume(AudioManager.STREAM_RING, progress, AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_VIBRATE);
            	tracking = false;
            }

			public void onStartTrackingTouch(SeekBar arg0) {
				tracking = true;
			}

			public void onStopTrackingTouch(SeekBar arg0) {
			}       
        });        
        
        mReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				setRadioButton();
			}
        };
        mIntent = new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION);
        registerReceiver(mReceiver, mIntent);
    }

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP){
        	mSeekBar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_RING));
        }
		
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setRadioButton();
		mSeekBar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_RING));
	}

	public void ringMode(View view) {
    	switch(mRadioGroup.getCheckedRadioButtonId()) {
    	case (R.id.radio0) :
    		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    		mSeekBar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_RING));
    		Toast.makeText(getApplicationContext(), "Normal Mode Activated", Toast.LENGTH_SHORT).show();
    		break;
    	case (R.id.radio1) :
    		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    		mSeekBar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_RING));
    		Toast.makeText(getApplicationContext(), "Silent Mode Activated", Toast.LENGTH_SHORT).show();
    		break;
    	case (R.id.radio2) :
    		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    		mSeekBar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_RING));
    		Toast.makeText(getApplicationContext(), "Vibrate Mode Activated", Toast.LENGTH_SHORT).show();
    		break;
    	}
    }
    
    private void setRadioButton() {
    	switch (mAudioManager.getRingerMode()) {
		case AudioManager.RINGER_MODE_NORMAL:
			mRadioGroup.check(R.id.radio0);
			break;
		case AudioManager.RINGER_MODE_SILENT:
			mRadioGroup.check(R.id.radio1);
			break;
		case AudioManager.RINGER_MODE_VIBRATE:
			mRadioGroup.check(R.id.radio2);
			break;
    	}
    }
}