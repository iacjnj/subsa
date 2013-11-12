
package com.bnx.subsa;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.TimePicker;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final boolean DBG = BuildConfig.DEBUG;

    private TimePicker mTimePickerStart;
    private TimePicker mTimePickerEnd;

    private CheckBox mCheckBoxAirPlane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void initViews() {
        mTimePickerStart = (TimePicker) findViewById(R.id.start_timepicker);
        mTimePickerEnd = (TimePicker) findViewById(R.id.end_timepicker);
        mCheckBoxAirPlane = (CheckBox) findViewById(R.id.airplane_checkbox);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
