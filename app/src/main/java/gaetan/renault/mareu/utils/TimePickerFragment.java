package gaetan.renault.mareu.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

import gaetan.renault.mareu.ViewModelFactory;
import gaetan.renault.mareu.ui.create.CreateMeetingViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TimePickerFragment extends DialogFragment {

    private final Calendar mCalendar;
    private int hour;
    private int minute;

    public TimePickerFragment(Calendar calendar) {
        mCalendar = calendar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (mCalendar != null) {
            hour = mCalendar.get(Calendar.HOUR_OF_DAY);
            minute = mCalendar.get(Calendar.MINUTE);
        } else {
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }

        CreateMeetingViewModel viewModel = new ViewModelProvider(requireActivity(), ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);

        return new TimePickerDialog(getActivity(), viewModel, hour, minute, false);
    }
}