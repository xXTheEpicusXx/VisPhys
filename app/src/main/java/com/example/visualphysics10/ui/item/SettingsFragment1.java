package com.example.visualphysics10.ui.item;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.visualphysics10.MainActivity;
import com.example.visualphysics10.R;
import com.example.visualphysics10.database.LessonData;
import com.example.visualphysics10.database.LessonViewModel;
import com.example.visualphysics10.databinding.FragmentSettingsBinding;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

public class SettingsFragment1 extends Fragment {
    private FragmentSettingsBinding binding;
    public static String string = "VisPhysUser";
    public static Bitmap image = null;
    private static String nameHint;
    private static Bitmap userProfileImage;
    public static boolean isFABClicked = false;
    private LessonViewModel viewModel;
    public LessonData lessonDataList = new LessonData();
    int SELECT_IMAGE_CODE = 1;


    public void setStr(String hint) {
        this.nameHint = hint;
    }

    public void setBmp(Bitmap bitmap) {
        this.userProfileImage = bitmap;
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
        Log.d("TAG", "h", new Throwable(string));
        binding.editProfileText.setText(nameHint);
        binding.profileImage.setImageBitmap(userProfileImage);
        binding.editProfile.setOnClickListener(v -> {
            changeProfile();

        });
        binding.profileImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "title"), SELECT_IMAGE_CODE);
        });
        addToolbar();

    }


    private void changeProfile() {
        if (!isFABClicked) {
            binding.editProfileText.setText(nameHint);
            binding.profileImage.setImageBitmap(userProfileImage);
            binding.editName.setEnabled(true);
            binding.profileImage.setClickable(true);

            isFABClicked = true;

            binding.editProfile.setImageResource(R.drawable.check_24);
        } else {
            binding.editName.setEnabled(false);
            binding.profileImage.setClickable(false);
            isFABClicked = false;
            binding.editProfile.setImageResource(R.drawable.edit_name);
            saveData();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Uri uri = data.getData();
            binding.profileImage.setImageURI(uri);
        }
    }

    //the method inserts the User-name from the edittext into the database
    private void saveData() {
        string = String.valueOf(binding.editProfileText.getText());
        image = ((BitmapDrawable) binding.profileImage.getDrawable()).getBitmap();
        //
        // using ViewModel in new thread
        // so as not to collapse the main thread - detailed description in the ViewModel and Repository classes
        // the value in the database is saved even when the application is closed
        //
        viewModel = ViewModelProviders.of(requireActivity()).get(LessonViewModel.class);

        viewModel.getLessonLiveData().observe(getViewLifecycleOwner(), new Observer<List<LessonData>>() {
            @Override
            public void onChanged(List<LessonData> lessonData) {
                lessonDataList.name = string;
                lessonDataList.userImage = BitmapToBytes(image);
                lessonData.set(0, lessonDataList);

            }
        });

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

    public static byte[] BitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);

        return baos.toByteArray();
    }

    public static Bitmap BytesToBimap(byte[] b) {
        if (b.length == 0) {
            return null;
        }

        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }
}
