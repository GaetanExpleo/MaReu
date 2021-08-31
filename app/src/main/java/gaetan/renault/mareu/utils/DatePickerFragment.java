package gaetan.renault.mareu.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
public class DatePickerFragment extends DialogFragment {

    private final Calendar mCalendar;
    private int year;
    private int month;
    private int day;

    private static Calendar c = Calendar.getInstance();

    public DatePickerFragment(Calendar calendar) {
        mCalendar = calendar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (mCalendar != null){
            year = mCalendar.get(Calendar.YEAR);
            month = mCalendar.get(Calendar.MONTH);
            day = mCalendar.get(Calendar.DAY_OF_MONTH);
        } else {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        CreateMeetingViewModel createMeetingViewModel = new ViewModelProvider(requireActivity(), ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), createMeetingViewModel, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        return datePickerDialog;
    }
}