package com.codewithtimzowen.contactlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class MessageActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    //Declare the AudioManager
    private AudioManager mAudioManager;

    //Create a global variable member for releasing resources each time the sound finishes playing
    private final MediaPlayer.OnCompletionListener mOnCompletionListener = mp -> releaseMediaResources();

    //Implement callback for audio focus change listener
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = focusChange -> {

        if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            //Pause the audio and start playing from start if we loose the audio temporarily
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0);
        } else if (focusChange == AUDIOFOCUS_GAIN) {
            //we have gained back audio thus we need to continue playing audio, but we start from zero
            mMediaPlayer.start();
        } else if (focusChange == AUDIOFOCUS_LOSS) {
            //this means we have lost completely audio, so we release resources
            releaseMediaResources();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //Initialize the system service from audio Manager
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Contacts> message = new ArrayList<>();

        message.add(new Contacts("Inbox", "Read Message", R.drawable.number_one, R.raw.number_one));
        message.add(new Contacts("Outbox", "Read Message", R.drawable.number_two, R.raw.number_two));
        message.add(new Contacts("Sent", "Read Message", R.drawable.number_three, R.raw.number_three));
        message.add(new Contacts("Received", "Read Message", R.drawable.number_four, R.raw.number_four));
        message.add(new Contacts("Spam", "Read Message", R.drawable.number_five, R.raw.number_five));
        message.add(new Contacts("Blocked", "Read Message", R.drawable.number_six, R.raw.number_six));
        message.add(new Contacts("Read", "Read Message", R.drawable.number_seven, R.raw.number_seven));
        message.add(new Contacts("Inbox", "Read Message", R.drawable.number_eight, R.raw.number_eight));
        message.add(new Contacts("Outbox", "Read Message", R.drawable.number_nine, R.raw.number_nine));
        message.add(new Contacts("Sent", "Read Message", R.drawable.number_ten, R.raw.number_ten));
        message.add(new Contacts("Inbox", "Read Message", R.drawable.number_one, R.raw.number_one));
        message.add(new Contacts("Outbox", "Read Message", R.drawable.number_two, R.raw.number_two));
        message.add(new Contacts("Sent", "Read Message", R.drawable.number_three, R.raw.number_three));
        message.add(new Contacts("Received", "Read Message", R.drawable.number_four, R.raw.number_four));
        message.add(new Contacts("Spam", "Read Message", R.drawable.number_five, R.raw.number_five));
        message.add(new Contacts("Blocked", "Read Message", R.drawable.number_six, R.raw.number_six));
        message.add(new Contacts("Read", "Read Message", R.drawable.number_seven, R.raw.number_seven));
        message.add(new Contacts("Inbox", "Read Message", R.drawable.number_eight, R.raw.number_eight));
        message.add(new Contacts("Outbox", "Read Message", R.drawable.number_nine, R.raw.number_nine));
        message.add(new Contacts("Sent", "Read Message", R.drawable.number_ten, R.raw.number_ten));

        ContactsAdapter contactsAdapter = new ContactsAdapter(this, message, R.color.message_bg);

        ListView listView = findViewById(R.id.list_view);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Contacts currentMessage = message.get(position);

            //release media resources in case a user switches to another list before the media finishes playing
            releaseMediaResources();

            //Request AudioFocus here
            int results = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                    //use music stream
                    AudioManager.STREAM_MUSIC,
                    //For duration use GAIN_TRANSIENT
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            //check if we have been granted permission to play audio
            if (results == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                // now create and start playing audio

                mMediaPlayer = MediaPlayer.create(this, currentMessage.getSoundResourceID());
                mMediaPlayer.start();

                //release media resources one done playing the audio
                mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            }
        });

        listView.setAdapter(contactsAdapter);
    }

    //create a method to clear resources once done playing audio.
    public void releaseMediaResources() {
        if (mMediaPlayer != null) {
            // Release the audio where in use or not
            mMediaPlayer.release();
            mMediaPlayer = null;
            //abandon audio focus when done or when activity is interrupted regardless of when we were granted
            //or not granted audioFocus
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaResources();
    }
}