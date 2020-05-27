package com.example.homelock;

import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class LED {
    private static final String TAG = "LED";
    private Gpio mLed;

    LED(PeripheralManager pm, String GpioName) {
        try {
            mLed = pm.openGpio(GpioName);
            mLed.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void turnOn(){
        try {
            mLed.setValue(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void turnOff(){
        try {
            mLed.setValue(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy(){
        try {
            mLed.close();
        } catch (IOException e) {
            Log.e(TAG, "onDestroy: ", e);
        }
    }
}
