package gaetan.renault.mareu.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import gaetan.renault.mareu.R;
import gaetan.renault.mareu.ViewModelFactory;
import gaetan.renault.mareu.databinding.DurationDialogBinding;
import gaetan.renault.mareu.ui.create.CreateMeetingViewModel;

public class DurationDialog extends AppCompatDialogFragment {

    private final String[] MINUTE_VALUES = {"0", "15", "30", "45"};

    private NumberPicker mHourNumberPicker;
    private NumberPicker mMinuteNumberPicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.duration_dialog, null);

        CreateMeetingViewModel viewModel = new ViewModelProvider(requireActivity(),ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);

        builder.setView(view)
                .setNegativeButton("Annuler", (dialog, which) -> {})
                .setPositiveButton("Ok", (dialog, which) -> viewModel.onDurationOkClicked(mHourNumberPicker.getValue(),
                        mMinuteNumberPicker.getValue()));

        mHourNumberPicker = view.findViewById(R.id.dialog_hour_numberpicker);
        mMinuteNumberPicker = view.findViewById(R.id.dialog_minute_numberpicker);

        int hour = viewModel.getHour();
        int minute = viewModel.getMinute();

        initHourSpinner(hour);
        initMinuteSpinner(minute);

        return builder.create();
    }

    private void initMinuteSpinner(int minute) {
        mMinuteNumberPicker.setMinValue(0);
        mMinuteNumberPicker.setMaxValue(3);
        mMinuteNumberPicker.setDisplayedValues(MINUTE_VALUES);
        mMinuteNumberPicker.setValue(minute);
        mMinuteNumberPicker.setWrapSelectorWheel(false);
        mMinuteNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (newVal == 0 && mHourNumberPicker.getValue() == 0) {
                mHourNumberPicker.setValue(1);
            }
        });
    }

    private void initHourSpinner(int hour) {
        mHourNumberPicker.setMaxValue(10);
        mHourNumberPicker.setValue(hour);
        mHourNumberPicker.setWrapSelectorWheel(false);
        mHourNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (newVal == 0 && mMinuteNumberPicker.getValue() == 0) {
                mMinuteNumberPicker.setValue(1);
            }
        });
    }
}
