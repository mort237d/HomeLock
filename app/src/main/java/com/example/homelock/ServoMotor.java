package com.example.homelock;

import android.util.Log;

import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.Pwm;

import java.io.IOException;

public class ServoMotor {
    private static final String TAG = "SERVO_MOTOR";
    private static final String PWM_PIN = "PWM1";
    private Pwm mPwm;

    public ServoMotor(PeripheralManager pm) {
        try {
            mPwm = pm.openPwm(PWM_PIN);
            mPwm.setPwmFrequencyHz(50);
        } catch (Exception e){
            Log.e(TAG, "ServoMotor: ");
        }
    }

    public void Swing0Degrees() {
        try {
            mPwm.setPwmDutyCycle(2.5);
            mPwm.setEnabled(true);
            Log.d(TAG,"Swing0");
        } catch (Exception ex) {

        }
    }

    public void Swing90Degrees() {
        try {
            mPwm.setPwmDutyCycle(7.5);
            Log.d(TAG,"Swing90");
        } catch (Exception ex) {

        }
    }

    public void onDestroy(){
        try {
            mPwm.close();
        } catch (IOException e) {
            Log.e(TAG, "onDestroy: ", e);
        }
    }
}
