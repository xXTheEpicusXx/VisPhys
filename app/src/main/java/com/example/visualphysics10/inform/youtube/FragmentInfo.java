package com.example.visualphysics10.inform.youtube;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visualphysics10.MainActivity;
import com.example.visualphysics10.R;
import com.example.visualphysics10.databinding.FragmentInfoBinding;
import com.example.visualphysics10.ui.MainFlag;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Objects;

public class FragmentInfo extends Fragment {
    private FragmentInfoBinding binding;
    private MaterialTextView textView;
    YouTubePlayerView player;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //methods initialize data depending on the lesson (fragment)
        addToolbar();
        createYoutubePlayer();
        createDescription();
    }

    private void createDescription() {
        textView = binding.info;

        //thread loading text, avoid crashing the main thread
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                textView.setText(selectDescription());
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    //select desired text (lecture of lesson)
    private int selectDescription() {
        switch (MainFlag.getPosition()) {
            case 0:
                return R.string.lesson1_inform;
            case 1:
                return R.string.lesson2_inform;
            case 2:
                return R.string.lesson3_inform;
            case 3:
                return R.string.lesson4_inform;
            case 4:
                return R.string.lesson5_inform;
            default:
                return 0;
        }
    }

    // we have written a fully working code with the logic of working with the official YouTube library (profs in package libs)
    // but due to reasons beyond our control, we completely removed it
    // Google hasn't refactored youtube api library to androidx yet
    // and use a third-party library to play videos from YouTube

    private void createYoutubePlayer() {
        player = binding.containerYouTube;
        getLifecycle().addObserver(player);
        player.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                assert selectVideoId() != null;
                youTubePlayer.loadVideo(selectVideoId(), 0);
            }
        });
    }

    private String selectVideoId() {
        switch (MainFlag.getPosition()) {
            case 0:
                return getString(R.string.video1);
            case 1:
                return getString(R.string.video2);
            case 2:
                return getString(R.string.video3);
            case 3:
                return getString(R.string.video4);
            case 4:
                return getString(R.string.video5);
            default:
                return null;
        }
    }


    private void addToolbar() {
        MaterialToolbar toolbar = binding.toolbar;
        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.close);
        toolbar.setTitle(selectTitle());
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });
    }

    private int selectTitle() {
        switch (MainFlag.getPosition()) {
            case 0:
                return R.string.title_info_1;
            case 1:
                return R.string.title_info_2;
            case 2:
                return R.string.title_info_3;
            case 3:
                return R.string.title_info_4;
            case 4:
                return R.string.title_info_5;
            default:
                return 0;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.release();
        binding = null;
    }
}
