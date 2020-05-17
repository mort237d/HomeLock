package com.example.homelock;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class LED {
    private static final String TAG = "LED";
    private Gpio mLed;

    public LED(PeripheralManager pm, String GpioName) {
        try {
            mLed = pm.openGpio(GpioName);
            mLed.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void turnOn(){
        try {
            mLed.setValue(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void turnOff(){
        try {
            mLed.setValue(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
