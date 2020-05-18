package com.example.homelock;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.nilhcem.androidthings.driver.keypad.Keypad;

import java.io.IOException;

public class KeyPad {
    private static final String TAG = "KEYPAD";

    String[] rowPins = new String[]{"BCM12", "BCM16", "BCM20", "BCM21"};
    String[] colPins = new String[]{"BCM25", "BCM24", "BCM23", "BCM27"};

    private Keypad keypad;

    public KeyPad() {
        try {
            keypad = new Keypad(rowPins, colPins, Keypad.KEYS_4x4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        keypad.register(new Keypad.OnKeyEventListener() {
            @Override
            public void onKeyEvent(KeyEvent keyEvent) {
                String action = keyEvent.getAction() == KeyEvent.ACTION_DOWN ? "ACTION_DOWN" : "ACTION_UP";
                Log.d(TAG, "onKeyEvent: (" + action + "): " + keyEvent.getDisplayLabel());
            }
        });
    }

    public void onDestroy(){
        // Don't forget to:
        keypad.unregister();
        try {
            keypad.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
