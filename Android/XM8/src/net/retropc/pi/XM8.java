package net.retropc.pi;

import java.io.File;

import org.libsdl.app.SDLActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class XM8 extends SDLActivity {
    // directory and filename
    private static final String ROM_DIRECTORY = "/XM8/";
    private static final String PC88_FILENAME = "PC88.ROM";
    private static final String N80_FILENAME = "N80.ROM";
    private static final String N88_FILENAME = "N88.ROM";
    private static final String DISK_FILENAME = "DISK.ROM";
    private static final String N88EXT0_FILENAME = "N88_0.ROM";
    private static final String N88EXT1_FILENAME = "N88_1.ROM";
    private static final String N88EXT2_FILENAME = "N88_2.ROM";
    private static final String N88EXT3_FILENAME = "N88_3.ROM";
    private static final String KANJI1_FILENAME = "KANJI1.ROM";

    // message text
    private static final String ALERT_TITLE = "XM8 (based on ePC-8801MA)";
    private static final String ALERT_MESSAGE = "The ROM file is not found:\n";
    private static final String ALERT_BUTTON = "OK";

    // ROM error flag
    private static boolean mROMError;

    // JNI native routine
    public static native void nativeIntent(String name);

    // wrapper for API level 11
    @TargetApi(11)
    private void setUiVisibility(View view, int value) {
        view.setSystemUiVisibility(value);
    }

    // setup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get ROM directory in ExternalStorage
        String basepath = Environment.getExternalStorageDirectory() + ROM_DIRECTORY;

        // check mandatory ROMs
        mROMError = false;
        if (checkROM(basepath, PC88_FILENAME, false) == false) {
            // retry with M88 ROM sets
            mROMError = false;

            checkROM(basepath, N80_FILENAME, true);
            checkROM(basepath, N88_FILENAME, true);
            checkROM(basepath, DISK_FILENAME, true);
            checkROM(basepath, N88EXT0_FILENAME, true);
            checkROM(basepath, N88EXT1_FILENAME, true);
            checkROM(basepath, N88EXT2_FILENAME, true);
            checkROM(basepath, N88EXT3_FILENAME, true);
        }
        checkROM(basepath, KANJI1_FILENAME, true);

        // set ROM status
        mDisableThread = mROMError;

        // immersive full-screen mode or dim status bar / navigation icon
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 19) {
            // 0x1000 = SYSTEM_UI_FLAG_IMMERSIVE_STICKY (API level 19), 2 = SYSTEM_UI_FLAG_HIDE_NAVIGATION (API level 14)
            setUiVisibility(decorView, 0x1000 | 2);
        }
        else {
            if (Build.VERSION.SDK_INT >= 14) {
                // 1 = SYSTEM_UI_FLAG_LOW_PROFILE (API level 14)
                setUiVisibility(decorView, 1);
            }
        }

        // super class
        super.onCreate(savedInstanceState);

        // process intent
        if (Intent.ACTION_VIEW.equals(getIntent().getAction()) == true) {
            String fname[] = String.valueOf(getIntent().getData()).split("//");
            if (fname.length == 2) {
                nativeIntent(fname[1]);
            }
        }
        else {
            nativeIntent("");
        }
    }

    @Override
    protected void onResume() {
        // super class
        super.onResume();

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 19) {
            // 0x1000 = SYSTEM_UI_FLAG_IMMERSIVE_STICKY (API level 19), 2 = SYSTEM_UI_FLAG_HIDE_NAVIGATION (API level 14)
            setUiVisibility(decorView, 0x1000 | 2);
        }
        else {
            if (Build.VERSION.SDK_INT >= 14) {
                // 1 = SYSTEM_UI_FLAG_LOW_PROFILE (API level 14)
                setUiVisibility(decorView, 1);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.v("XM8", "onNewIntent()");
        if (Intent.ACTION_VIEW.equals(intent.getAction()) == true) {
            String fname[] = String.valueOf(intent.getData()).split("//");
            if (fname.length == 2) {
                nativeIntent(fname[1]);
            }
        }

        super.onNewIntent(intent);
    }

    // check ROM
    private boolean checkROM(String basepath, String filename, boolean alert) {
        if (mROMError == true) {
            return false;
        }

        // get result as whether the file exists or not
        File file = new File(basepath + filename);
        boolean result = file.exists();

        // save result
        if (result == false) {
            mROMError = true;
        }

        // check result
        if ((result == false) && (alert == true)) {
            // alert dialog
            new AlertDialog.Builder(this)
                    .setTitle(ALERT_TITLE)
                    .setMessage(ALERT_MESSAGE + basepath + filename)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(ALERT_BUTTON, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }

    return result;
    }
}
