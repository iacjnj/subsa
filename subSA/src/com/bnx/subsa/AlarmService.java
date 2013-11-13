package com.bnx.subsa;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.bnx.subsa.common.Const;

public class AlarmService extends Service {

    private static final String TAG = "AlarmService";
    private static final boolean DBG = BuildConfig.DEBUG;
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        String action = arg0.getAction();
        if (DBG)
            Log.e(TAG, "intent action: " + action);
        if (action.equals(Const.INTENT_ACT_START)) {
            if (arg0.getBooleanExtra(Const.INTENT_AP, false)) {
                boolean isEnabled = Settings.System.getInt(getContentResolver(),
                        Settings.System.AIRPLANE_MODE_ON, 0) == 1;
                Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON,
                        isEnabled ? 0 : 1);
                Intent i = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                i.putExtra("state", !isEnabled);
                sendBroadcast(i);
            }
        } else if (action.equals(Const.INTENT_ACT_END)) {

        }
        return null;
    }


}
