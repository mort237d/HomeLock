package com.example.homelock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.things.update.StatusListener;
import com.google.android.things.update.UpdateManager;
import com.google.android.things.update.UpdateManagerStatus;
import com.google.android.things.update.UpdatePolicy;

import static android.content.ContentValues.TAG;

public class SetupActivity extends Activity {

    private UpdateManager updateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        updateManager = UpdateManager.getInstance();
        updateManager.addStatusListener(new StatusListener() {
            @Override
            public void onStatusUpdate(UpdateManagerStatus status) {
                switch (status.currentState) {
                    case UpdateManagerStatus.STATE_UPDATE_AVAILABLE:
                        /* Notify user of the update */
                        Toast.makeText(SetupActivity.this,"STATE_UPDATE_AVAILABLE", Toast.LENGTH_LONG).show();
                        break;
                    case UpdateManagerStatus.STATE_DOWNLOADING_UPDATE:
                        /* Update UI to show progress */
                        Toast.makeText(SetupActivity.this,"STATE_DOWNLOADING_UPDATE", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        // Trigger an update check immediately
        updateManager.performUpdateNow(UpdatePolicy.POLICY_CHECKS_ONLY);
    }

    public void FinishButton(View view) {
        finish();
    }
}