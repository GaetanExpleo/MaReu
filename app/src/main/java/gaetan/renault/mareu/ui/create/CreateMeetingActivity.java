package gaetan.renault.mareu.ui.create;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;

import java.util.Calendar;
import java.util.List;

import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.Repository.RoomRepository;
import gaetan.renault.mareu.databinding.ActivityCreateMeetingBinding;
import gaetan.renault.mareu.utils.DatePickerFragment;
import gaetan.renault.mareu.utils.TimePickerFragment;
import gaetan.renault.mareu.utils.utility;

public class CreateMeetingActivity extends AppCompatActivity {

    private ActivityCreateMeetingBinding mBinding;
    private CreateMeetingViewModel mViewModel;
    private RoomRepository mRoomRepository = RoomRepository.getInstance();
    private List<Room> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityCreateMeetingBinding.inflate(getLayoutInflater());

        setContentView(mBinding.getRoot());

        mViewModel = new ViewModelProvider(this).get(CreateMeetingViewModel.class);
        rooms = mRoomRepository.getRooms();

        mBinding.meetingTopicTiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.onTopicChanged(s.toString());
            }
        });

        mBinding.meetingParticipantsTiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.onParticipantsChanged(s.toString());
            }
        });

        initToolbar();
        initDateAndTime();
        initCreateButton();
        initRoomSpinner();
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("New Meeting");
    }

    private void initCreateButton() {
        mBinding.createButton.setEnabled(false);
        mViewModel.isMeetingReadyToCreate().observe(this, isReady -> mBinding.createButton.setEnabled(isReady));
        mBinding.createButton.setOnClickListener(v -> {
            mViewModel.onCreateButtonClicked();
            finish();
        });
    }

    private void initDateAndTime() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int month1 = month+1;
        processDatePickerResult(day, month, year);
        processTimePickerResult(hour, minute);
//        mViewModel.onDateChanged(year,month,day,hour,minute);
        mViewModel.onDateChanged(year + "/" + month1 + "/" + day +  " " + hour + ":" + minute);
        mBinding.dateTiet.setOnClickListener(v -> showDatePicker());
        mBinding.dateTiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.onDateChanged(s + " " + mBinding.timeTiet.getText().toString());

            }
        });
        mBinding.timeTiet.setOnClickListener(v -> showTimePicker());
        mBinding.timeTiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.onDateChanged(mBinding.dateTiet.getText().toString() + " " + s);
            }
        });

    }

    private void initRoomSpinner() {
        CreateMeetingSpinnerAdapter adapter = new CreateMeetingSpinnerAdapter(rooms);
        mBinding.roomSpinner.setAdapter((SpinnerAdapter) adapter);
        mBinding.roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.onRoomChanged(rooms.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showDatePicker() {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void processDatePickerResult(int day, int month, int year) {
        String day_string = utility.formatOneInTwoNumber(day);
        String month_string = utility.formatOneInTwoNumber(month + 1);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string + "/" + month_string + "/" + year_string);

        mBinding.dateTiet.setText(dateMessage);
    }

    private void showTimePicker() {
        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void processTimePickerResult(int hour, int minute) {
        String hour_string = utility.formatOneInTwoNumber(hour);
        String minute_string = utility.formatOneInTwoNumber(minute);
        String timeMessage = (hour_string + ":" + minute_string);

        mBinding.timeTiet.setText(timeMessage);
    }
}