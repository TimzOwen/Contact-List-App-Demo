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

public class ProffesionsActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;

    MediaPlayer.OnCompletionListener mOnCompletionListener = mp -> releaseMediaResources();

    AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener = focusChange -> {
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

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Contacts> proffesions = new ArrayList<>();

        proffesions.add(new Contacts("Daktari", "Doctor", R.raw.number_one));
        proffesions.add(new Contacts("Mhandisi", "Engineer", R.raw.number_two));
        proffesions.add(new Contacts("Nesi", "Nurse", R.raw.number_three));
        proffesions.add(new Contacts("Roboti", "Robot", R.raw.number_four));
        proffesions.add(new Contacts("Muimbi", "singer", R.raw.number_five));
        proffesions.add(new Contacts("Mkulima", "farmer", R.raw.number_six));
        proffesions.add(new Contacts("Kiongozi", "Leader", R.raw.number_seven));
        proffesions.add(new Contacts("Nafsi", "Nefsi", R.raw.number_eight));
        proffesions.add(new Contacts("Mguu", "Leg", R.raw.number_nine));
        proffesions.add(new Contacts("Mikono", "hands", R.raw.number_ten));
        proffesions.add(new Contacts("Muimbi", "singer", R.raw.number_one));
        proffesions.add(new Contacts("Daktari", "Doctor", R.raw.number_one));
        proffesions.add(new Contacts("Mhandisi", "Engineer", R.raw.number_two));
        proffesions.add(new Contacts("Nesi", "Nurse", R.raw.number_three));
        proffesions.add(new Contacts("Roboti", "Robot", R.raw.number_four));
        proffesions.add(new Contacts("Muimbi", "singer", R.raw.number_five));
        proffesions.add(new Contacts("Mkulima", "farmer", R.raw.number_six));
        proffesions.add(new Contacts("Kiongozi", "Leader", R.raw.number_seven));
        proffesions.add(new Contacts("Nafsi", "Nefsi", R.raw.number_eight));
        proffesions.add(new Contacts("Mguu", "Leg", R.raw.number_nine));
        proffesions.add(new Contacts("Mikono", "hands", R.raw.number_ten));
        proffesions.add(new Contacts("Muimbi", "singer", R.raw.number_one));


        ContactsAdapter contactsAdapter = new ContactsAdapter(this, proffesions, R.color.prof_bg);

        ListView listView = findViewById(R.id.list_view);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Contacts currentProfession = proffesions.get(position);

            releaseMediaResources();

            int result = mAudioManager.requestAudioFocus(afChangeListener,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                mMediaPlayer = MediaPlayer.create(this, currentProfession.getSoundResourceID());
                mMediaPlayer.start();

                mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            }
        });

        listView.setAdapter(contactsAdapter);
    }

    public void releaseMediaResources() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaResources();
    }
}