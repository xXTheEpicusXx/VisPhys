package com.example.visualphysics10.ui.item;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.visualphysics10.MainActivity;
import com.example.visualphysics10.R;
import com.example.visualphysics10.database.LessonData;
import com.example.visualphysics10.database.LessonViewModel;
import com.example.visualphysics10.databinding.FragmentSettingsBinding;

import java.util.Objects;

public class SettingsFragment1 extends Fragment {
    private FragmentSettingsBinding binding;
    public static String string="VisPhysUser";
    private static String nameHint;
    public static boolean isFABClicked = false;
    private LessonViewModel viewModel;
    public LessonData lessonDataList = new LessonData();

    public void setStr(String hint) {
        this.nameHint = hint;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.editProfileText.setText(nameHint);

        binding.editProfile.setOnClickListener(v -> {
            changeProfile();
        });
        addToolbar();

    }



    private void changeProfile() {
        if (!isFABClicked) {
            binding.editProfileText.setText(nameHint);
            binding.editName.setEnabled(true);
            isFABClicked = true;
            binding.editProfile.setImageResource(R.drawable.check_24);
        } else {
            binding.editName.setEnabled(false);
            isFABClicked = false;
            binding.editProfile.setImageResource(R.drawable.edit_name);
            saveData();
        }

    }

    //the method inserts the User-name from the edittext into the database
    private void saveData() {
        string = String.valueOf(binding.editProfileText.getText());
        //
        // using ViewModel in new thread
        // so as not to collapse the main thread - detailed description in the ViewModel and Repository classes
        // the value in the database is saved even when the application is closed
        //
        viewModel = ViewModelProviders.of(requireActivity()).get(LessonViewModel.class);
        lessonDataList.name = string;
        viewModel.insert(lessonDataList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void addToolbar() {
        Toolbar toolbar = binding.toolbar;
        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }
}
