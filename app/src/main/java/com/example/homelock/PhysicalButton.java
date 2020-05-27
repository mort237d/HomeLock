package com.example.homelock;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class PhysicalButton {
    private static final String TAG = "BUTTON";
    private Gpio button;

    public PhysicalButton(PeripheralManager pm, String GpioName) {
        try {
            button = pm.openGpio(GpioName);
            button.setDirection(Gpio.DIRECTION_IN);
        } catch (IOException e) {
            Log.e(TAG, "PhysicalButton: ", e);
        }
    }

    public boolean Pushed(){
        try {
            return button.getValue();
        } catch (IOException e) {
            Log.e(TAG, "Pushed: ", e);
        }
        return false;
    }

    public void onDestroy(){
        try {
            button.close();
        } catch (IOException e) {
            Log.e(TAG, "onDestroy: ", e);
        }
    }
}
