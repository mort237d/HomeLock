package com.example.homelock;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.nilhcem.androidthings.driver.keypad.Keypad;
import com.nilhcem.androidthings.driver.keypad.KeypadInputDriver;

import java.io.IOException;

class KeyPad {
    private static final String TAG = "KEYPAD";

    private String[] rowPins = new String[]{"BCM12", "BCM16", "BCM20", "BCM21"};
    private String[] colPins = new String[]{"BCM25", "BCM24", "BCM23", "BCM27"};

    private KeypadInputDriver mInputDriver;

    KeyPad() {

        try {
            mInputDriver = new KeypadInputDriver(rowPins, colPins, Keypad.KEYS_4x4);
            mInputDriver.register();
        } catch (IOException e) {
            Log.e(TAG, "KeyPad: ", e);
        }
    }
}
