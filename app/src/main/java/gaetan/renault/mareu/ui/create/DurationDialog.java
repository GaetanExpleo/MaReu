package gaetan.renault.mareu.ui.create;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import gaetan.renault.mareu.databinding.DurationDialogBinding;

public class DurationDialog extends Dialog {

    private final String[] MINUTE_VALUES = {"0", "15", "30", "45"};

    private Context mContext;
    private DurationDialogBinding mBinding;
    private int mHour;
    private int mMinute;

    private NumberPicker mHourNumberPicker;
    private NumberPicker mMinuteNumberPicker;
    private Button mButton;

    private CreateMeetingViewModel.DurationListener mListener;

    public DurationDialog(@NonNull Context context, CreateMeetingViewModel.DurationListener listener,int hour, int minute) {
        super(context);
        mContext = context;
        mListener = listener;
        mHour = hour;
        mMinute = minute/15;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mBinding = DurationDialogBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mHourNumberPicker = mBinding.dialogHourNumberpicker;
        mMinuteNumberPicker = mBinding.dialogMinuteNumberpicker;
        mButton = mBinding.dialogOkButton;

        mHourNumberPicker.setMaxValue(10);
        mHourNumberPicker.setValue(mHour);
        mHourNumberPicker.setWrapSelectorWheel(false);
        mHourNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 0 && mMinuteNumberPicker.getValue() == 0) {
                    mMinuteNumberPicker.setValue(1);
                }
            }
        });

        mMinuteNumberPicker.setMinValue(0);
        mMinuteNumberPicker.setMaxValue(3);
        mMinuteNumberPicker.setDisplayedValues(MINUTE_VALUES);
        mMinuteNumberPicker.setValue(mMinute);
        mMinuteNumberPicker.setWrapSelectorWheel(false);

        mButton.setOnClickListener(v -> buttonOkClicked());
    }


    private void buttonOkClicked() {
        int hour = mHourNumberPicker.getValue();
        int minute = mMinuteNumberPicker.getValue()*15;

        if (hour == 0 && minute == 0) {
            Toast.makeText(mContext, "Veuillez remplir une durée de réunion supérieur à zéro.", Toast.LENGTH_SHORT).show();
            mMinuteNumberPicker.setValue(1);
            return;
        }
        this.dismiss();

        if (mListener != null) {
            String hourToDisplayed = "";
            if (hour > 0)
                hourToDisplayed = hour + " h ";
            if (minute > 0)
                hourToDisplayed = hourToDisplayed + minute + " min";
            mListener.durationEntered(hourToDisplayed);
        }
    }
}
