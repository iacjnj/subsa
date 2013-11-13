
package com.bnx.subsa;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bnx.subsa.common.Const;

import java.util.Calendar;

public class MainActivity extends Activity implements OnClickListener, OnCheckedChangeListener {

    private static final String TAG = "MainActivity";
    private static final boolean DBG = BuildConfig.DEBUG;

    private Context mContext = this;

    private String mType = Const.INTENT_AP;
    // private TimePicker mTimePickerStart;
    // private TimePicker mTimePickerEnd;
    private TextView mTVStart;
    private TextView mTVEnd;

    private CheckBox mCheckBoxSW;

    private TimePickerDialog mStartTPD;
    private TimePickerDialog mEndTPD;

    private AlarmManager mAM;
    private long sysTime = System.currentTimeMillis();

    private String mStartTime;
    private String mEndTime;
    private String mswName;
    private boolean mIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mType = getIntent().getAction();

        initViews();
        mAM = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    }

    private void initViews() {
        // mTimePickerStart = (TimePicker) findViewById(R.id.start_timepicker);
        // mTimePickerEnd = (TimePicker) findViewById(R.id.end_timepicker);
        mTVStart = (TextView) findViewById(R.id.start_time_tv);
        mTVEnd = (TextView) findViewById(R.id.end_time_tv);

        SharedPreferences timeInfo = getSharedPreferences(mType, 0);

        mStartTime = timeInfo.getString(Const.SP_KEY_START, Const.DEFAULT_START);

        mEndTime = timeInfo.getString(Const.SP_KEY_END, Const.DEFAULT_END);

        mswName = timeInfo.getString(Const.SP_KEY_NAME, mType);

        mIsChecked = timeInfo.getBoolean(Const.SP_KEY_CHECK, false);

        mTVStart.setText(mStartTime);
        mTVStart.setOnClickListener(this);

        mTVEnd.setText(mEndTime);
        mTVEnd.setOnClickListener(this);

        mCheckBoxSW = (CheckBox) findViewById(R.id.switch_checkbox);
        mCheckBoxSW.setText(mswName);
        mCheckBoxSW.setChecked(mIsChecked);
        mCheckBoxSW.setOnCheckedChangeListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private int getHour(String time) {
        return Integer.valueOf(time.substring(0, time.indexOf(Const.COLON)));
    }

    private int getMinute(String time) {
        return Integer.valueOf(time.substring(time.indexOf(Const.COLON) + 1));
    }

    private String getTime(int hour, int min) {
        return String.valueOf(hour) + Const.COLON
                + (min < 10 ? ("0" + min) : min);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        SharedPreferences timeInfo = getSharedPreferences(mType, 0);

        switch (v.getId()) {
            case R.id.start_time_tv: {
                String startTime = timeInfo.getString(Const.SP_KEY_START, Const.DEFAULT_START);

                int hour = getHour(startTime);
                int min = getMinute(startTime);

                mStartTPD = new TimePickerDialog(mContext,
                        new OnTimeSetListener() {
                            
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                String setTime = getTime(hourOfDay, minute);
                                mTVStart.setText(setTime);
                                SharedPreferences timeInfo = getSharedPreferences(mType, 0);
                                timeInfo.edit().putString(Const.SP_KEY_START, setTime).commit();
                            }
                        },
                        hour,
                        min,
                        true);
                mStartTPD.show();
                break;
            }
            case R.id.end_time_tv: {
                String endTime = timeInfo.getString(Const.SP_KEY_END, Const.DEFAULT_END);

                int hour = getHour(endTime);
                int min = getMinute(endTime);

                mEndTPD = new TimePickerDialog(mContext,
                        new OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                String setTime = getTime(hourOfDay, minute);
                                mTVEnd.setText(setTime);
                                SharedPreferences timeInfo = getSharedPreferences(mType, 0);
                                timeInfo.edit().putString(Const.SP_KEY_END, setTime).commit();
                            }
                        },
                        hour,
                        min,
                        true);
                mEndTPD.show();
                break;
            }
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        SharedPreferences timeInfo = getSharedPreferences(mType, 0);
        timeInfo.edit().putBoolean(Const.SP_KEY_CHECK, isChecked).commit();
        String startTime = timeInfo.getString(Const.SP_KEY_START, Const.DEFAULT_START);
        String endTime = timeInfo.getString(Const.SP_KEY_END, Const.DEFAULT_START);
        int hourOfDay = getHour(startTime);
        // Integer.valueOf(startTime.substring(0,
        // startTime.indexOf(Const.COLON)));
        int minute = getMinute(endTime);
        // Integer.valueOf(startTime.substring(startTime.indexOf(Const.COLON) +
        // 1));

        if (isChecked) {
            // on
            Calendar calendeStart = Calendar.getInstance();
            calendeStart.setTimeInMillis(System.currentTimeMillis());
            calendeStart.set(Calendar.HOUR, hourOfDay);
            calendeStart.set(Calendar.MINUTE, minute);
            calendeStart.set(Calendar.SECOND, 0);
            calendeStart.set(Calendar.MILLISECOND, 0);

            Intent intentStart = new Intent(MainActivity.this, AlarmService.class);
            intentStart.setAction(Const.INTENT_ACT_START);
            intentStart.putExtra(mType, true);

            PendingIntent startPi = PendingIntent
                    .getBroadcast(MainActivity.this, 0, intentStart, 0);
            mAM.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendeStart.getTimeInMillis(), Const.ONE_DAY, startPi);

            // off
            Calendar calendeEnd = Calendar.getInstance();
            calendeEnd.setTimeInMillis(System.currentTimeMillis());
            calendeEnd.set(Calendar.HOUR, getHour(endTime));
            calendeEnd.set(Calendar.MINUTE, getMinute(endTime));
            calendeEnd.set(Calendar.SECOND, 0);
            calendeEnd.set(Calendar.MILLISECOND, 0);

            Intent intentEnd = new Intent(MainActivity.this, AlarmService.class);
            intentEnd.setAction(Const.INTENT_ACT_END);
            intentEnd.putExtra(mType, false);

            PendingIntent endPi = PendingIntent
                    .getBroadcast(MainActivity.this, 0, intentEnd, 0);
            mAM.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendeEnd.getTimeInMillis(), Const.ONE_DAY, endPi);
        } else {

        }


    }

}
