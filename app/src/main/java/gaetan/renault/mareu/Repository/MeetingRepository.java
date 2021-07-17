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

    private final MutableLiveData<List<Meeting>> mMeetingsMutableLiveData = new MutableLiveData<>();
    private List<Meeting> mMeetings;
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
            long time,
            @NonNull List<String> participants,
            @NonNull Room room
    ) {
        if (mMeetings == null) mMeetings = new ArrayList<>();

        mMeetings.add(new Meeting(++id, topic, participants, time, room));

        mMeetingsMutableLiveData.postValue(mMeetings);
    }

    public void deleteMeeting(int meetingId) {
        for (Iterator<Meeting> iterator = mMeetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getId() == meetingId) {
                iterator.remove();
                break;
            }
        }

        mMeetingsMutableLiveData.setValue(mMeetings);
    }

    public LiveData<List<Meeting>> getMeetings() {
        return mMeetingsMutableLiveData;
    }
}