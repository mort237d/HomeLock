package com.example.homelock;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.android.things.update.StatusListener;
import com.google.android.things.update.UpdateManager;
import com.google.android.things.update.UpdateManagerStatus;
import com.google.android.things.update.UpdatePolicy;

import java.util.concurrent.TimeUnit;

public class UpdateService extends Service {
    UpdateManager updateManager;

    @Override
    public void onCreate() {
        super.onCreate();

        updateManager = UpdateManager.getInstance();
        UpdatePolicy policy = new UpdatePolicy.Builder()
                .setPolicy(UpdatePolicy.POLICY_APPLY_ONLY)
                .setApplyDeadline(7L, TimeUnit.DAYS)
                .build();
        updateManager.setPolicy(policy);
        updateManager.addStatusListener(updateStatusListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        updateManager.removeStatusListener(updateStatusListener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private StatusListener updateStatusListener = new StatusListener() {
        @Override
        public void onStatusUpdate(UpdateManagerStatus status) {
            switch (status.currentState) {
                case UpdateManagerStatus.STATE_DOWNLOADING_UPDATE:
                    /* Download in progress */
                    break;
                case UpdateManagerStatus.STATE_FINALIZING_UPDATE:
                    /* Download complete */
                    break;
                case UpdateManagerStatus.STATE_UPDATED_NEEDS_REBOOT:
                    /* Reboot device to apply update */
                    break;
            }
        }
    };
}
