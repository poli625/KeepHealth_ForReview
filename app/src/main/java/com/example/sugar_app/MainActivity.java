package com.example.sugar_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Member;


public class MainActivity extends AppCompatActivity {

    Button start_scan;
    Animation anim_open, anim_close, anim_rotate_clockwise, anim_rotate_anticlockwise;
    FloatingActionButton fab_open, fab_info, fab_help;

    private int CAMERA_PERMISSION_CODE = 1;

    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(MainActivity.this, "You have already granted this permission!", Toast.LENGTH_SHORT).show();
        } else {
            requestCameraPermission();
        }


        start_scan = findViewById(R.id.Button_Start_Scan);
        fab_open = findViewById(R.id.button_floating_main);
        fab_info = findViewById(R.id.floating_info);
        fab_help = findViewById(R.id.floating_help);

        anim_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        anim_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        anim_rotate_clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        anim_rotate_anticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        fab_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen){
                    fab_info.startAnimation(anim_close);
                    fab_help.startAnimation(anim_close);
                    fab_open.startAnimation(anim_rotate_clockwise);

                    fab_info.setClickable(false);
                    fab_help.setClickable(false);

                    isOpen = false;
                }

                else {

                    fab_info.startAnimation(anim_open);
                    fab_help.startAnimation(anim_open);
                    fab_open.startAnimation(anim_rotate_anticlockwise);

                    fab_info.setClickable(true);
                    fab_help.setClickable(true);

                    isOpen = true;

                }

            }
        });


        start_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanActivity.class));
            }
        });

        fab_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_help();
            }
        });

        fab_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_info();
            }
        });

    }

    public void show_help(){
        AlertDialog.Builder show_help_dialog = new AlertDialog.Builder(MainActivity.this);
        show_help_dialog.setTitle("Help");
        show_help_dialog.setMessage("Help instruction goes here!");
        show_help_dialog.setPositiveButton("OK", (dialog, which) -> {

        });
        show_help_dialog.show();
    }

    public void show_info(){
        AlertDialog.Builder show_info_dialog = new AlertDialog.Builder(MainActivity.this);
        show_info_dialog.setTitle("About");
        show_info_dialog.setMessage("About goes here!");
        show_info_dialog.setPositiveButton("OK", (dialog, which) -> {

        });
        show_info_dialog.show();
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            AlertDialog.Builder show_info_dialog = new AlertDialog.Builder(MainActivity.this);
            show_info_dialog.setTitle("Permission needed");
            show_info_dialog.setMessage("Czemu musi być pozwolenie!");
            show_info_dialog.setPositiveButton("OK", (dialog, which) -> {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            });
            show_info_dialog.setNegativeButton("CANCEL", (dialog, which) -> {
                dialog.dismiss();
            });
            show_info_dialog.show();

        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}