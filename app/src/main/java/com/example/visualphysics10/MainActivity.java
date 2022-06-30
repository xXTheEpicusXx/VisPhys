package com.example.visualphysics10;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.visualphysics10.ui.lesson.ItemFragment;
import com.example.visualphysics10.databinding.ActivityMainBinding;
import com.example.visualphysics10.ui.MainFlag;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    ItemFragment itemFragment = new ItemFragment();
    private ActivityMainBinding binding;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        if (itemFragment != null) {
            fragmentTransaction.add(R.id.container, itemFragment).commit();
        }
    }

    //redefine the method...
    @Override
    public void onBackPressed() {

        count = fragmentManager.getBackStackEntryCount();

        if (count == 0) {
            exitApp();
        } else {
            exitFragment();
            MainFlag.setNotLesson(false);
        }

    }

    private void exitFragment() {
        if (count > 1 || MainFlag.isNotLesson()) fragmentManager.popBackStack();
        else new MaterialAlertDialogBuilder(this,R.style.MaterialAlert)
                .setTitle(R.string.title_1)
                .setMessage(R.string.message_1)
                .setCancelable(false)
                .setPositiveButton("Завершить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        fragmentManager.popBackStack();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Отмена", null)
                .show().getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
    }

    private void exitApp() {
        new MaterialAlertDialogBuilder(this,R.style.MaterialAlert)
                .setTitle(R.string.title_1)

                .setMessage(R.string.message_2)
                .setCancelable(false)
                .setPositiveButton("Выйти", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Назад", null)

                .show().getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}