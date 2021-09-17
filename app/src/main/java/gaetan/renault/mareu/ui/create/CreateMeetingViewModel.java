package gaetan.renault.mareu.ui.create;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Patterns;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.Repository.RoomRepository;
import gaetan.renault.mareu.utils.DatePickerFragment;
import gaetan.renault.mareu.utils.TimePickerFragment;
import gaetan.renault.mareu.utils.utility;

public class CreateMeetingViewModel extends ViewModel implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private final MeetingRepository mMeetingRepository;
    private final RoomRepository mRoomRepository;

    private static final long MINUTE_TO_MILLIS = 60000;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm";

    private String mTopic;
    private final List<String> mParticipants = new ArrayList<>();
    private Room mRoom;
    private long mStartMeeting;
    private long mDuration;

    private int mHour = 0, mMinute = 45;
    private Calendar mCalendar;

    private boolean isTopicOk = false;
    private boolean isParticipantsOk = false;
    private boolean isRoomOk = false;
    private boolean isDateOk = false;
    private boolean isTimeOk = false;
    private boolean isDurationOk = false;

    private final MutableLiveData<Boolean> mMeetingReadyToCreate = new MutableLiveData<>();
    private final MutableLiveData<String> mDurationMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mRoomReadyToBeSelected = new MutableLiveData<>();
    private final MutableLiveData<List<Room>> mRoomsListMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> mDateMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> mTimeMutableLiveData = new MutableLiveData<>();

    public CreateMeetingViewModel(MeetingRepository meetingRepository, RoomRepository roomRepository) {
        mMeetingRepository = meetingRepository;
        mRoomRepository = roomRepository;

        mMeetingReadyToCreate.setValue(false);
        mRoomReadyToBeSelected.setValue(false);
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);
    }

    public void onTopicChanged(@NonNull String topic) {
        this.mTopic = topic;

        isTopicOk = (!mTopic.isEmpty());
        verifiedInputs();
    }

    public boolean isEmailAddressValid(String emailAddress) {
        mParticipants.clear();

        String[] participantsList = emailAddress.replace(" ", "").split("[,; \n]");
        boolean validEmail = true;

        for (String participant : participantsList) {
            String participantCleaned = participant.trim();

            if (!participantCleaned.isEmpty() && isValidEmail(participantCleaned)) {
                mParticipants.add(participantCleaned);
            } else {
                validEmail = false;
            }
        }

        isParticipantsOk = validEmail && mParticipants.size() > 0;
        verifiedInputs();
        return validEmail;
    }

    private boolean isValidEmail(String emailAddress) {
        return emailAddress != null && Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    public void onDateChanged(@NonNull String date) {
        isDateOk = (!date.isEmpty());

        verifiedInputs();
        updateListOfRoomSelectable();
    }

    public void onTimeChanged(@NonNull String time) {
        isTimeOk = (!time.isEmpty());

        verifiedInputs();
        updateListOfRoomSelectable();
    }

    public void onDurationChanged(String s) {
        isDurationOk = !s.isEmpty();

        verifiedInputs();
        updateListOfRoomSelectable();
    }

    public void onRoomChanged(@NonNull Room room) {
        this.mRoom = room;

        isRoomOk = (mRoom != null);
        verifiedInputs();
    }

    public void onDurationOkClicked(int hour, int minute) {
        mHour = hour;
        mMinute = minute * 15;

        String strHour = "";
        String strMinute = "";

        if (mHour != 0) {
            strHour = String.valueOf(mHour) + " h";
        }

        if (mMinute != 0) {
            strMinute = String.valueOf(mMinute) + " min";
        }

        if (mHour != 0 && mMinute != 0) {
            mDurationMutableLiveData.setValue(strHour + " " + strMinute);
        } else {
            mDurationMutableLiveData.setValue(strHour + strMinute);
        }

        durationToLong();
        updateListOfRoomSelectable();
    }

    public void onCreateButtonClicked() {
        mStartMeeting = mCalendar.getTimeInMillis();
        mMeetingRepository.addMeeting(mTopic, mStartMeeting, endMeetingCalculation(mStartMeeting, mDuration), mParticipants, mRoom);
    }

    private void updateListOfRoomSelectable() {
        if (mRoomReadyToBeSelected.getValue()) {
            List<Meeting> meetingList = new ArrayList<>();
            List<Room> roomList = new ArrayList<>();
            roomList.addAll(mRoomRepository.getRooms());
            long startMeeting = mCalendar.getTimeInMillis();
            long endMeeting = endMeetingCalculation(startMeeting, mDuration);

            if (mMeetingRepository.getMeetingsLiveData().getValue() != null) {
                meetingList.addAll(mMeetingRepository.getMeetingsLiveData().getValue());

                for (Meeting meeting : meetingList) {
                    int roomMeetingId = meeting.getRoom().getId();

                    if (!isAvailableTime(startMeeting, endMeeting, meeting.getStartMeeting(), meeting.getEndMeeting())) {
                        for (Iterator<Room> iterator = roomList.iterator(); iterator.hasNext(); ) {
                            Room room = iterator.next();

                            int roomId = room.getId();

                            if (roomId == roomMeetingId) {
                                iterator.remove();
                            }
                        }
                    }
                }
            }
            mRoomsListMutableLiveData.setValue(roomList);
        }
    }

    private void verifiedInputs() {
        mRoomReadyToBeSelected.setValue(isDateOk && isTimeOk && isDurationOk);
        mMeetingReadyToCreate.setValue(isTopicOk && isParticipantsOk && isRoomOk && isTimeOk && isDateOk && isDurationOk);
    }

    private long endMeetingCalculation(long startMeeting, long duration) {
        return startMeeting + duration;
    }

    private void durationToLong() {
        mDuration = ((mHour * 60) + mMinute) * MINUTE_TO_MILLIS;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mDateMutableLiveData.setValue(utility.formatDate(mCalendar, DATE_FORMAT));
        updateListOfRoomSelectable();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        mTimeMutableLiveData.setValue(utility.formatDate(mCalendar, TIME_FORMAT));
    }

    private boolean isAvailableTime(
            long startDesiredMeeting,
            long endDesiredMeeting,
            long startCurrentMeeting,
            long endCurrentMeeting
    ) {
        return (startDesiredMeeting >= endCurrentMeeting || endDesiredMeeting <= startCurrentMeeting);
    }

    public LiveData<Boolean> isMeetingReadyToCreate() {
        return mMeetingReadyToCreate;
    }

    public LiveData<Boolean> isRoomReadyToBeSelected() {
        return mRoomReadyToBeSelected;
    }

    public LiveData<List<Room>> getRoomsLiveData() {
        return mRoomsListMutableLiveData;
    }

    public LiveData<String> getDateLiveData() {
        return mDateMutableLiveData;
    }

    public LiveData<String> getTimeLiveData() {
        return mTimeMutableLiveData;
    }

    public LiveData<String> getDurationLiveData() {
        return mDurationMutableLiveData;
    }

    public int getHour() {
        return mHour;
    }

    public int getMinute() {
        return mMinute / 15;
    }

    public void showTimePicker(Context context) {
        DialogFragment timeFragment = new TimePickerFragment(mCalendar);
        timeFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "timePicker");
    }

    public void showDatePicker(Context context) {
        DialogFragment dateFragment = new DatePickerFragment(mCalendar);
        dateFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "datePicker");
    }
}