package com.bnx.subsa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    private static final boolean DBG = BuildConfig.DEBUG;
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub
        String action = arg1.getAction();
        if (DBG)
            Log.e(TAG, "intent action: " + action);

    }

}
