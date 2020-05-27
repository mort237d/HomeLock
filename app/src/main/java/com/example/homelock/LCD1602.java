package com.example.homelock;

import android.util.Log;

import com.nilhcem.androidthings.driver.lcdpcf8574.LcdPcf8574;

import java.io.IOException;

public class LCD1602 {
    private static final String TAG = "LCD1602";
    private LcdPcf8574 lcd;

    public LCD1602() {
        try {
            lcd = new LcdPcf8574("I2C1", 0x3f);
            lcd.begin(16, 2);
            lcd.setBacklight(true);

            int[] heart = {0b00000, 0b01010, 0b11111, 0b11111, 0b11111, 0b01110, 0b00100, 0b00000};
            lcd.createChar(0, heart);

            int[] arrow = {0b00100, 0b01110, 0b11111, 0b00100, 0b00100, 0b00100, 0b00100, 0b00000};
            lcd.createChar(1, arrow);

            int[] android = {0b00100, 0b01110, 0b11111, 0b00000, 0b11111, 0b11111, 0b11111, 0b11111};
            lcd.createChar(2, android);
        } catch (IOException e) {
            Log.e(TAG, "LCD1602: ", e);
        }
    }

    public void Print(String text1, String text2){
        try {
            lcd.clear();
            lcd.setCursor(0, 0);
            lcd.print(text1);
            lcd.setCursor(0, 1);
            lcd.print(text2);
        } catch (IOException e) {
            Log.e(TAG, "Print: ", e);
        }
    }
}
