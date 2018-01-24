package jp.takenach.livedatasample;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jp.takenach.livedatasample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTextView;

//    private ClockLegacy.ClockListener mListener = new ClockLegacy.ClockListener() {
//        @Override
//        public void onReceive(Date date) {
//            Log.d(TAG, "onReceive() called with " + "date = [" + date + "]");
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            String dateString = String.format(Locale.getDefault(), "%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),
//                    calendar.get(Calendar.MINUTE));
//            mTextView.setText(dateString);
//        }
//    };
//    private ClockLegacy mClockLegacy;
    private ClockLiveData mClockLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        mTextView = findViewById(R.id.text_time);
//        mClockLegacy = new ClockLegacy(getApplicationContext());
//        mClockLiveData = new ClockLiveData(getApplicationContext());
//        mClockLiveData.observe(this, date -> setTime(date));
//        mClockLiveData.observeForever(date -> setTime(date));
        ClockViewModel clockViewModel = ViewModelProviders.of(this).get(ClockViewModel.class);
//        clockViewModel.getClock().observe(this, this::setTime);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setClockModel(clockViewModel);

        final LifecycleObserver lifecycleObserver = new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
            public void calledWhenOnAny(LifecycleOwner source, Lifecycle.Event event) {
                Log.d(TAG, "calledWhenOnAny " + event.name() + " : " + source.getLifecycle().getCurrentState().name());
            }

//            @OnLifecycleEvent(Lifecycle.Event.ON_START)
//            public void register() {
//                mClockLegacy.setClockListener(mListener);
//            }
//
//            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//            public void unregister() {
//                mClockLegacy.removeClockListener();
//            }
        };

        getLifecycle().addObserver(lifecycleObserver);

    }

    private void setTime(Date date) {
        Log.d(TAG, "onChanged called with " + "date = [" + date + "]");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String dateString = String.format(Locale.getDefault(), "%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
        mTextView.setText(dateString);
    }
}
