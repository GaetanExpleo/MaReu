package gaetan.renault.mareu.ui.create;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.R;
import gaetan.renault.mareu.Repository.RoomRepository;
import gaetan.renault.mareu.ViewModelFactory;
import gaetan.renault.mareu.databinding.ActivityCreateMeetingBinding;
import gaetan.renault.mareu.utils.DurationDialog;

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

        mBinding.meetingParticipantsTiet.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (mViewModel.isEmailAddressValid(mBinding.meetingParticipantsTiet.getText().toString())){
                    mBinding.meetingParticipantsTil.setErrorEnabled(false);
                    mBinding.meetingParticipantsTiet.setError(null);
                } else {
                    mBinding.meetingParticipantsTil.setErrorEnabled(true);
                    mBinding.meetingParticipantsTil.setError("Au moins une addresse mail est au mauvais format");
                }
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
                if (s.toString().isEmpty()) mViewModel.isEmailAddressValid(s.toString());
            }
        });

        mBinding.dateTiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.onDateChanged(s.toString());
            }
        });

        mBinding.timeTiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.onTimeChanged(s.toString());
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
        mViewModel.getDateLiveData().observe(this, date -> mBinding.dateTiet.setText(date));
        mViewModel.getTimeLiveData().observe(this, time -> mBinding.timeTiet.setText(time));
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

    @Override
    public void onClick(View v) {
        mBinding.meetingTopicTiet.requestFocus();
        switch (v.getId()) {
            case R.id.create_button:
                mViewModel.onCreateButtonClicked();
                finish();
                break;
            case R.id.date_tiet:
                mViewModel.showDatePicker(this);
                break;
            case R.id.time_tiet:
                mViewModel.showTimePicker(this);
                break;
            case R.id.duration_tiet:
                DurationDialog durationDialog = new DurationDialog();
                durationDialog.show(getSupportFragmentManager(),"duration dialog");
                break;
            default:
                return;
        }
    }
}