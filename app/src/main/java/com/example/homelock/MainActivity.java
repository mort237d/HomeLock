package com.example.homelock;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.pio.PeripheralManager;

public class MainActivity extends Activity {

    private ServoMotor servoMotor;
    private LED greenLED;
    private LED redLED;
    private PhysicalButton greenButton;
    private PhysicalButton redButton;
    private LCD1602 screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeripheralManager pm = PeripheralManager.getInstance();

        servoMotor = new ServoMotor(pm);
        redLED = new LED(pm, "BCM21");
        greenLED = new LED(pm, "BCM20");
        redButton = new PhysicalButton(pm, "BCM19");
        greenButton = new PhysicalButton(pm, "BCM26");
        screen = new LCD1602();

        screen.Print("HomeLock", "");

        while(true){
            if (greenButton.Pushed()) Unlock();
            if (redButton.Pushed()) Lock();
        }
    }

    private void Lock(){
        servoMotor.Swing90Degrees();
        greenLED.turnOff();
        redLED.turnOn();
        screen.Print("Locked", "");
    }

    private void Unlock(){
        servoMotor.Swing0Degrees();
        redLED.turnOff();
        greenLED.turnOn();
        screen.Print("Unlocked", "");
    }
}
