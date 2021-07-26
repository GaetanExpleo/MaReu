package gaetan.renault.mareu.ui.meetings;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Repository.MeetingRepository;

public class MeetingViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository mMeetingRepository;

    private final MutableLiveData<List<Integer>> mListRoomIdMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Integer>> mListHourMutableLiveData = new MutableLiveData<>();


    public MeetingViewModel(@NonNull MeetingRepository meetingRepository) {
        mMeetingRepository = meetingRepository;
    }

    public void onDeleteMeetingClicked(int meetingId) {
        mMeetingRepository.deleteMeeting(meetingId);
    }

    public void onRoomFilterSelected(int roomId) {

        List<Integer> listRoomId = new ArrayList<>();
        if (mListRoomIdMutableLiveData.getValue() != null)
            listRoomId.addAll(mListRoomIdMutableLiveData.getValue());

        if (listRoomId.contains(roomId)) {
            listRoomId.remove(listRoomId.indexOf(roomId));
        } else {
            listRoomId.add(roomId);
        }

        mListRoomIdMutableLiveData.setValue(listRoomId);
    }

    public void onHourFilterSelected(int hourSelected) {
        List<Integer> listHour = new ArrayList<>();
        if (mListHourMutableLiveData.getValue() != null)
            listHour.addAll(mListHourMutableLiveData.getValue());

        if (listHour.contains(hourSelected)) {
            listHour.remove(listHour.indexOf(hourSelected));
        } else {
            listHour.add(hourSelected);
        }

        mListHourMutableLiveData.setValue(listHour);
    }


    public LiveData<List<Meeting>> getMeetingsLiveData() {

        MediatorLiveData<List<Meeting>> meetingMediatorLiveData = new MediatorLiveData<>();

        LiveData<List<Meeting>> meetingLiveData = mMeetingRepository.getMeetingsLiveData();

        meetingMediatorLiveData.addSource(meetingLiveData, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                meetingMediatorLiveData.setValue(combine(meetings, mListRoomIdMutableLiveData.getValue(), mListHourMutableLiveData.getValue()));
            }
        });

        meetingMediatorLiveData.addSource(mListRoomIdMutableLiveData, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> roomIds) {
                meetingMediatorLiveData.setValue(combine(meetingLiveData.getValue(), roomIds, mListHourMutableLiveData.getValue()));
            }
        });

        meetingMediatorLiveData.addSource(mListHourMutableLiveData, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> hours) {
                meetingMediatorLiveData.setValue(combine(meetingLiveData.getValue(), mListRoomIdMutableLiveData.getValue(), hours));
            }
        });

        return meetingMediatorLiveData;
    }

    private List<Meeting> combine(List<Meeting> meetings, List<Integer> roomIds, List<Integer> hours) {
        if (meetings == null)
            return new ArrayList<>();

        List<Meeting> meetingList = new ArrayList<>();
        meetingList.addAll(meetings);

        if (roomIds != null && !roomIds.isEmpty()) {
            meetingList = getMeetingsFiltered(meetingList, roomIds, "room");
        }

        if (hours != null && !hours.isEmpty()){
            meetingList = getMeetingsFiltered(meetingList, hours , "hours");
        }

        return meetingList;
    }

    private List<Meeting> getMeetingsFiltered(List<Meeting> meetings, List<Integer> filterValues, String filterType) {
        List<Meeting> meetingList = new ArrayList<>();

        if (filterType == "room") {
            for (Meeting meeting : meetings) {
                if (filterValues.contains(meeting.getRoom().getId())){
                    meetingList.add(meeting);
                }
            }
        } else {
            for (Meeting meeting : meetings) {
                Timestamp timestamp = new Timestamp(meeting.getStartMeeting());
                if (filterValues.contains(timestamp.getHours())){
                    meetingList.add(meeting);
                }
            }
        }
        return meetingList;
    }
}
