package com.example.homelock;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.pio.PeripheralManager;

public class MainActivity extends Activity {

    private ServoMotor servoMotor;
    private LED greenLED;
    private LED redLED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeripheralManager pm = PeripheralManager.getInstance();

        servoMotor = new ServoMotor(pm);
        redLED = new LED(pm, "BCM21");
        greenLED = new LED(pm, "BCM20");

        Lock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Unlock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Lock();
    }

    private void Lock(){
        servoMotor.Swing90Degrees();
        greenLED.turnOff();
        redLED.turnOn();
    }

    private void Unlock(){
        servoMotor.Swing0Degrees();
        redLED.turnOff();
        greenLED.turnOn();
    }
}
