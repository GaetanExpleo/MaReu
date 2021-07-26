package gaetan.renault.mareu.ui.create;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.R;
import gaetan.renault.mareu.Repository.RoomRepository;
import gaetan.renault.mareu.ViewModelFactory;
import gaetan.renault.mareu.databinding.ActivityCreateMeetingBinding;
import gaetan.renault.mareu.utils.DatePickerFragment;
import gaetan.renault.mareu.utils.TimePickerFragment;
import gaetan.renault.mareu.utils.utility;

public class CreateMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "hh:mm";

    private ActivityCreateMeetingBinding mBinding;
    private CreateMeetingViewModel mViewModel;
    private RoomRepository mRoomRepository = RoomRepository.getInstance();
    private List<Room> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityCreateMeetingBinding.inflate(getLayoutInflater());

        setContentView(mBinding.getRoot());

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(CreateMeetingViewModel.class);
        rooms = mRoomRepository.getRooms();

        initToolbar();
        initDateAndTime();
        initRoomSpinner();
        initCreateButton();
        initObservers();
        initTextChangeListener();
    }

    private void initTextChangeListener() {
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

//        mBinding.dateTiet.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                try {
//                    mViewModel.onDateChanged(s.toString(), "date");
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        mBinding.timeTiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    mViewModel.onDateChanged(s.toString(), "time");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        mBinding.durationTiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.onDurationChanged(s.toString());
            }
        });
    }

    private void initObservers() {
        mViewModel.getDurationLiveData().observe(this, text -> mBinding.durationTiet.setText(text));
        mViewModel.isMeetingReadyToCreate().observe(this, isReady -> mBinding.createButton.setEnabled(isReady));
        mViewModel.getRoomsLiveData().observe(this, rooms -> {
            CreateMeetingSpinnerAdapter adapter = new CreateMeetingSpinnerAdapter(rooms);
            mBinding.roomSpinner.setAdapter(adapter);
        });
        mViewModel.isRoomReadyToBeSelected().observe(this, isReady -> mBinding.roomSpinner.setEnabled(isReady));
        mViewModel.getDateLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String date) {
                mBinding.dateTiet.setText(date);
            }
        });
        mViewModel.getTimeLiveData().observe(this, time -> mBinding.dateTiet.setText(time));
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Nouvelle Réunion");
    }

    private void initCreateButton() {
        mBinding.createButton.setEnabled(false);
        mBinding.createButton.setOnClickListener(this);
    }

    private void initDateAndTime() {
        mBinding.dateTiet.setOnClickListener(this);
        mBinding.timeTiet.setOnClickListener(this);
        mBinding.durationTiet.setOnClickListener(this);
    }

    private void initRoomSpinner() {
        mBinding.roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.onRoomChanged((Room) parent.getAdapter().getItem(position));
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

//    public void processDatePickerResult(int day, int month, int year) {
//        String day_string = utility.formatOneInTwoNumber(day);
//        String month_string = utility.formatOneInTwoNumber(month + 1);
//        String year_string = Integer.toString(year);
//        String dateMessage = (day_string + "/" + month_string + "/" + year_string);
//
//        mBinding.dateTiet.setText(dateMessage);
//    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_button:
                mViewModel.onCreateButtonClicked();
                finish();
                break;
            case R.id.date_tiet:
                showDatePicker();
                break;
            case R.id.time_tiet:
                showTimePicker();
                break;
            case R.id.duration_tiet:
                mViewModel.onDurationEntered(this, mBinding.durationTiet.getText().toString());
                break;
            default:
                return;
        }
    }
}