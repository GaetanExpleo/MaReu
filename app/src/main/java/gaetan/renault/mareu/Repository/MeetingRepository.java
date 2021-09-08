package gaetan.renault.mareu.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;

public class MeetingRepository {

    private final MutableLiveData<List<Meeting>> mMeetingLiveData = new MutableLiveData<>();
    private int id = 0;

    private MeetingRepository() {
    }

    private static class MeetingRepositoryHolder {
        private final static MeetingRepository INSTANCE = new MeetingRepository();
    }

    public static synchronized MeetingRepository getInstance() {
        return MeetingRepositoryHolder.INSTANCE;
    }

    public void addMeeting(
            @NonNull String topic,
            @NonNull long startMeeting,
            @NonNull long endMeeting,
            @NonNull List<String> participants,
            @NonNull Room room
    ) {

        List<Meeting> currentList = new ArrayList<>();
        if (mMeetingLiveData.getValue() != null)
            currentList.addAll(mMeetingLiveData.getValue());

        currentList.add(new Meeting(id, topic, participants, startMeeting, endMeeting, room));
        id++;

        mMeetingLiveData.setValue(currentList);
    }

    public void deleteMeeting(int meetingId) {

        List<Meeting> currentList = new ArrayList<>();
        currentList.addAll(mMeetingLiveData.getValue());

        for (Iterator<Meeting> iterator = currentList.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getId() == meetingId) {
                iterator.remove();
                break;
            }
        }

        mMeetingLiveData.setValue(currentList);
    }

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return mMeetingLiveData;
    }
}