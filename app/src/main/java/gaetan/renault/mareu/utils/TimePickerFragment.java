package gaetan.renault.mareu.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import java.util.Calendar;

import gaetan.renault.mareu.R;
import gaetan.renault.mareu.ViewModelFactory;
import gaetan.renault.mareu.ui.create.CreateMeetingActivity;
import gaetan.renault.mareu.ui.create.CreateMeetingViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TimePickerFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        CreateMeetingViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);

        return new TimePickerDialog(getActivity(), viewModel ,hour,minute,false);
    }
//Todo : Supprimer si fonctionne avec le viewModel

//    @Override
//    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
//        CreateMeetingActivity activity = (CreateMeetingActivity) getActivity();
//        activity.processTimePickerResult(hour, minute);
//    }
}