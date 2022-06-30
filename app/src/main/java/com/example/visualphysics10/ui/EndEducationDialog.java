package com.example.visualphysics10.ui;

import static com.example.visualphysics10.R.color.white;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.visualphysics10.MainActivity;
import com.example.visualphysics10.R;
import com.example.visualphysics10.databinding.EndEducationDialogBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class EndEducationDialog extends DialogFragment {
    private EndEducationDialogBinding binding;
    public static DialogFragment newInstance() {
        return new EndEducationDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        assert dialog != null;
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EndEducationDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //methods initialize data depending on the lesson (fragment)
        if(MainFlag.notLesson){
            binding.text.setText("Вы решили все задачи по теме Ускорение !");
        }
        addToolbar();
    }

    private void addToolbar() {
        MaterialToolbar toolbar = binding.toolbar;
        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.close);
        toolbar.setNavigationIconTint(Color.WHITE);
        toolbar.setTitle("Обучение");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationOnClickListener(v -> {
            if(MainFlag.notLesson) getActivity().onBackPressed();
            dismiss();
        });
    }
}
