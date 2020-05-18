package com.example.homelock;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.pio.PeripheralManager;

public class MainActivity extends Activity {

    private ServoMotor servoMotor;
    private LED greenLED;
    private LED redLED;
    private LED blueLED;
    private PhysicalButton greenButton;
    private PhysicalButton redButton;
    private PhysicalButton blueButton;
    private LCD1602 screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeripheralManager pm = PeripheralManager.getInstance();

        servoMotor = new ServoMotor(pm);
        redLED = new LED(pm, "BCM21");
        greenLED = new LED(pm, "BCM20");
        blueLED = new LED(pm, "BCM12");
        redButton = new PhysicalButton(pm, "BCM19");
        greenButton = new PhysicalButton(pm, "BCM26");
        blueButton = new PhysicalButton(pm, "BCM6");
        screen = new LCD1602();

        Lock();

        while(true){
            if (greenButton.Pushed()) Unlock();
            if (redButton.Pushed()) Lock();
            if (blueButton.Pushed()) Pairing();
        }
    }

    private int timer = 0;
    private int maxTime = 10;

    private void Pairing() {

        screen.Print("Pairing", "Waiting for device");
        while (timer < maxTime){
            blueLED.turnOn();
            try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
            blueLED.turnOff();
            try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
            timer += 1;
        }
        timer = 0;
        screen.Print("Bluetooth", "No devices attached");
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
