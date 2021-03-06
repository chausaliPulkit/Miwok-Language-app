package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FamilyMembersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyMembersFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    //    Handle audio focus while playing a sound
    private AudioManager mAudioManager;

    private final MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
//                        Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
//                        app gain its audio focus again and we
//                        resume playback
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
//                        app loses audio focus and we have to stop playback
//                        and release all media resources
                        releaseMediaPlayer();

                    }
                }
            };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FamilyMembersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FamilyMembersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FamilyMembersFragment newInstance(String param1, String param2) {
        FamilyMembersFragment fragment = new FamilyMembersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.words_list, container, false);
//          create and setup the audio manager to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> FamilyMembers = new ArrayList<>();
        FamilyMembers.add(new Word("father", "ede", R.drawable.family_father, R.raw.family_father));
        FamilyMembers.add(new Word("mother", "eta", R.drawable.family_mother, R.raw.family_mother));
        FamilyMembers.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        FamilyMembers.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        FamilyMembers.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        FamilyMembers.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        FamilyMembers.add(new Word("older sister", "tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        FamilyMembers.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        FamilyMembers.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        FamilyMembers.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter familyAdapter = new WordAdapter(getActivity(), FamilyMembers, R.color.category_family);
        ListView flistView = (ListView) rootView.findViewById(R.id.list);
        flistView.setAdapter(familyAdapter);


//        set item click listener on list view to play desired audio file
        flistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                release the mediaPlayer if it currently exists because we are going to play a
//                new audio file
                releaseMediaPlayer();
                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(onAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//                    we have audio focus now
                    mediaPlayer = MediaPlayer.create(getActivity(), FamilyMembers.get(position).getAudioResourcesId());
                    mediaPlayer.start();
//                setup a listener on MediaPlayer so that when it finished
//                we can stop it and release
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }

            }
        });



        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
//           When the activity is stopped, release the media player resources because we won't
//           be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer(){
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
//            setup a listener on MediaPlayer so that when it finished
//            we can stop it and release
            mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}