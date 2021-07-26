package gaetan.renault.mareu.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

import gaetan.renault.mareu.R;
import gaetan.renault.mareu.ViewModelFactory;
import gaetan.renault.mareu.ui.create.CreateMeetingActivity;
import gaetan.renault.mareu.ui.create.CreateMeetingViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DatePickerFragment extends DialogFragment {

    private static Calendar c = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        CreateMeetingViewModel createMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), createMeetingViewModel, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        return datePickerDialog;
    }
//Todo : Supprimer si fonctionne avec le ViewModel

//    @Override
//    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//        c.set(Calendar.YEAR,year);
//        c.set(Calendar.MONTH,month);
//        c.set(Calendar.DAY_OF_MONTH,day);
//        CreateMeetingActivity activity = (CreateMeetingActivity) getActivity();
//        activity.processDatePickerResult(day, month, year);
//    }
}