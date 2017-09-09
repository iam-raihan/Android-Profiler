package com.raihan.profiler;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
	private RadioGroup mRadioGroup;
	private AudioManager mAudioManager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		setRadioButton();
	}

	public void ringMode(View view) {
    	switch(mRadioGroup.getCheckedRadioButtonId()) {
    	case (R.id.radio0) :
    		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    		Toast.makeText(getApplicationContext(), "Normal Mode Activated", Toast.LENGTH_SHORT).show();
    		break;
    	case (R.id.radio1) :
    		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    		Toast.makeText(getApplicationContext(), "Silent Mode Activated", Toast.LENGTH_SHORT).show();
    		break;
    	case (R.id.radio2) :
    		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
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