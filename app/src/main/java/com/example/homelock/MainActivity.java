package com.example.homelock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.things.pio.PeripheralManager;

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

    private String passwordInput = "";
    private boolean locked = true;

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
        keyPad = new KeyPad();

        Lock();

        buttonThread.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyDown: " + event.getDisplayLabel());

        switch (event.getDisplayLabel()) {
            case '#':
                String passwordToUnlock = "13";
                if (passwordInput.equals(passwordToUnlock)) {
                    if (locked) {
                        passwordInput = "";
                        Unlock();
                    } else {
                        passwordInput = "";
                        Lock();
                    }
                } else {
                    passwordInput = "";
                    screen.Print("Wrong password", "Try again");
                }
                break;
            case 'D':
                if (passwordInput.length() > 0) {
                    passwordInput = passwordInput.substring(0, passwordInput.length() - 1);
                    screen.Print(passwordInput, "DELETE");
                }
                break;
            case 'C':
                if (passwordInput.length() > 0) {
                    passwordInput = "";
                    screen.Print(passwordInput, "CLEAR");
                }
                break;
            default:
                passwordInput += event.getDisplayLabel();
                screen.Print(passwordInput, "");
                break;
        }

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
                Log.e(TAG, "run: ", ex);
            }
        }
    });

    private int pairingTimer = 0;
    private int pairingMaxTime = 10;

    private void Pairing() {

        screen.Print("Pairing", "Waiting for device");
        redLED.turnOff();
        greenLED.turnOff();
        while (pairingTimer < pairingMaxTime){
            blueLED.turnOn();
            try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
            blueLED.turnOff();
            try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
            pairingTimer += 1;
        }
        pairingTimer = 0;
        screen.Print("Bluetooth", "No devices attached");
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        if (locked){
            screen.Print("Locked", "");
            redLED.turnOn();
        }
        else{
            screen.Print("Unlocked", "");
            greenLED.turnOn();
        }
    }

    private void Lock(){
        locked = true;
        servoMotor.Swing90Degrees();
        greenLED.turnOff();
        redLED.turnOn();
        screen.Print("Locked", "");
    }

    private void Unlock(){
        locked = false;
        servoMotor.Swing0Degrees();
        redLED.turnOff();
        greenLED.turnOn();
        screen.Print("Unlocked", "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        greenLED.onDestroy();
        redLED.onDestroy();
        blueLED.onDestroy();
    }
}
