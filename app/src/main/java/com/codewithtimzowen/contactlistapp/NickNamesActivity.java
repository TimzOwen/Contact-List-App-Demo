package com.codewithtimzowen.contactlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class NickNamesActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;

    MediaPlayer.OnCompletionListener mOnCompletionListener = mp -> releaseMediaResource();

    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = focusChange -> {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0);
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            mMediaPlayer.start();
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            releaseMediaResource();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Contacts> nickNames = new ArrayList<>();

        nickNames.add(new Contacts("Kabdaa", "Timz", R.raw.color_black));
        nickNames.add(new Contacts("Tula", "Chemwoget", R.raw.color_white));
        nickNames.add(new Contacts("Tarushot", "Tarusho", R.raw.color_red));
        nickNames.add(new Contacts("Bazuu", "Buda", R.raw.color_white));
        nickNames.add(new Contacts("Mayengz", "Ladies", R.raw.color_black));
        nickNames.add(new Contacts("Ninja", "Senior", R.raw.color_dusty_yellow));
        nickNames.add(new Contacts("Kabdaa", "Timz", R.raw.color_mustard_yellow));
        nickNames.add(new Contacts("Tula", "Chemwoget", R.raw.color_green));
        nickNames.add(new Contacts("Tarushot", "Tarusho", R.raw.color_gray));
        nickNames.add(new Contacts("Bazuu", "Buda", R.raw.color_brown));
        nickNames.add(new Contacts("Mayengz", "Ladies", R.raw.color_white));
        nickNames.add(new Contacts("Kabdaa", "Timz", R.raw.color_black));
        nickNames.add(new Contacts("Tula", "Chemwoget", R.raw.color_white));
        nickNames.add(new Contacts("Tarushot", "Tarusho", R.raw.color_red));
        nickNames.add(new Contacts("Bazuu", "Buda", R.raw.color_white));
        nickNames.add(new Contacts("Mayengz", "Ladies", R.raw.color_black));
        nickNames.add(new Contacts("Ninja", "Senior", R.raw.color_dusty_yellow));
        nickNames.add(new Contacts("Kabdaa", "Timz", R.raw.color_mustard_yellow));
        nickNames.add(new Contacts("Tula", "Chemwoget", R.raw.color_green));
        nickNames.add(new Contacts("Tarushot", "Tarusho", R.raw.color_gray));
        nickNames.add(new Contacts("Bazuu", "Buda", R.raw.color_brown));
        nickNames.add(new Contacts("Mayengz", "Ladies", R.raw.color_white));


        ContactsAdapter contactsAdapter = new ContactsAdapter(this, nickNames, R.color.nick_bg);

        ListView listView = findViewById(R.id.list_view);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Contacts currentNickName = nickNames.get(position);

            releaseMediaResource();

            int results = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (results == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                mMediaPlayer = MediaPlayer.create(this, currentNickName.getSoundResourceID());
                mMediaPlayer.start();

                mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            }
        });

        listView.setAdapter(contactsAdapter);
    }

    public void releaseMediaResource() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaResource();
    }
}