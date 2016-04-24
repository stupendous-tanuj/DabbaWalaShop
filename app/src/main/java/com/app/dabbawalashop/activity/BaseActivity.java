package com.app.dabbawalashop.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dabbawalashop.R;
import com.app.dabbawalashop.listner.IRightListner;
import com.app.dabbawalashop.utils.PreferenceKeeper;

import java.util.Calendar;
import java.util.Locale;

public class BaseActivity extends FragmentActivity implements View.OnClickListener {


    public final String TAG = BaseActivity.class.getSimpleName();

    private ProgressBar progressBar;
    protected Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvCenter;
    private TextView tvRight;

    public void showTimePickerDialog(TimePickerDialog.OnTimeSetListener mTimeListner) {
        DialogFragment newFragment = new TimePickerFragment(mTimeListner);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /*

      time picker
    */
    public static class TimePickerFragment extends DialogFragment
           /* implements TimePickerDialog.OnTimeSetListener */ {
        private TimePickerDialog.OnTimeSetListener mTimeListner;

        public TimePickerFragment(TimePickerDialog.OnTimeSetListener mTimeListner) {
            this.mTimeListner = mTimeListner;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), mTimeListner, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }

    public void applyLocale()
    {
        String lang = PreferenceKeeper.getInstance().getLocale();
        if(lang==null) {
            lang = "en";
            PreferenceKeeper.getInstance().setLocale(lang);
        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public String getData(int i) {
        return String.valueOf((i > 9 ? i : "0" + i));
    }
    //TODO Fix it.
    public String getMonth(int i) {
        if(i == 1)
            return "Jan";
        if(i == 2)
            return "Feb";
        if(i == 3)
            return "Mar";
        if(i == 4)
            return "April";
        if(i == 5)
            return "May";
        if(i == 6)
            return "Jun";
        if(i == 7)
            return "Jul";
        if(i == 8)
            return "Aug";
        if(i == 9)
            return "Sep";
        if(i == 10)
            return "Oct";
        if(i == 11)
            return "Nov";
        if(i == 12)
            return "Dec";

        return "";
    }

    public void setHeader(String center, String right) {
        ivBack = (ImageView) findViewById(R.id.iv_main_back);
        tvCenter = (TextView) findViewById(R.id.tv_main_center);
        tvRight = (TextView) findViewById(R.id.tv_main_right);

        tvCenter.setText(center);
        if (right.equalsIgnoreCase("")) {
            tvRight.setVisibility(View.GONE);
        } else {
            tvRight.setText(right);
        }

        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }


    }

    public void setContentView(int layoutResID) {
        applyLocale();
        super.setContentView(layoutResID);

    }


    public void setHeader(String center, String right, final IRightListner listner) {
        setHeader(center, right);
        if (listner != null) {
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onRightClick();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void launchActivity(Class<? extends BaseActivity> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    public void launchActivity(Class<? extends BaseActivity> classType, Bundle bundle) {
        Intent intent = new Intent(this, classType);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void launchActivityMain(Class<? extends BaseActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void showProgressBar(View view) {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public void hideProgressBar(View view) {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void showProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
