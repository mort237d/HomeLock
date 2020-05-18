package com.example.homelock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.things.pio.PeripheralManager;
import com.nilhcem.androidthings.driver.keypad.Keypad;
import com.nilhcem.androidthings.driver.keypad.KeypadInputDriver;

import java.io.IOException;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private ServoMotor servoMotor;
    private LED greenLED;
    private LED redLED;
    private LED blueLED;
    private PhysicalButton greenButton;
    private PhysicalButton redButton;
    private PhysicalButton blueButton;
    private LCD1602 screen;
    private KeyPad keyPad;

    private KeypadInputDriver mInputDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeripheralManager pm = PeripheralManager.getInstance();

        servoMotor = new ServoMotor(pm);
        redLED = new LED(pm, "BCM5");
        greenLED = new LED(pm, "BCM17");
        blueLED = new LED(pm, "BCM22");
        redButton = new PhysicalButton(pm, "BCM19");
        greenButton = new PhysicalButton(pm, "BCM26");
        blueButton = new PhysicalButton(pm, "BCM6");
        screen = new LCD1602();
        //keyPad = new KeyPad();

        Lock();

        String[] rowPins = new String[]{"BCM12", "BCM16", "BCM20", "BCM21"};
        String[] colPins = new String[]{"BCM25", "BCM24", "BCM23", "BCM27"};

        try {
            mInputDriver = new KeypadInputDriver(rowPins, colPins, Keypad.KEYS_4x4);
            mInputDriver.register();
        } catch (IOException e) {
            // error configuring keypad...
        }

        buttonThread.start();
    }

    private String input = "";

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyDown: " + event.getDisplayLabel());

        input += event.getDisplayLabel();
        screen.Print(input, "");
        return true;
    }

    Thread buttonThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while(true){
                    if (greenButton.Pushed()) Unlock();
                    if (redButton.Pushed()) Lock();
                    if (blueButton.Pushed()) Pairing();
                }
            }catch (Exception ex){

            }
        }
    });

    /*@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyUp: " + event.getDisplayLabel());
        return true;
    }*/

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
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        try {
            if (redLED.getState()) screen.Print("Locked", "");
            else if (greenLED.getState()) screen.Print("Unlocked", "");
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInputDriver.unregister();
        try {
            mInputDriver.close();
        } catch (IOException e) {
            // error closing input driver
        }
    }
}
