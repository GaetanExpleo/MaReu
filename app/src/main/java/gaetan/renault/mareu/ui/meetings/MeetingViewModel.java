package gaetan.renault.mareu.ui.meetings;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.Repository.RoomRepository;

public class MeetingViewModel extends ViewModel {

    private final MeetingRepository mMeetingRepository;
    private final RoomRepository mRoomRepository;
    private final LiveData<List<Meeting>> mMeetings;

    public MeetingViewModel(@NonNull MeetingRepository meetingRepository, @NonNull RoomRepository roomRepository) {
        mMeetingRepository = meetingRepository;
        mRoomRepository = roomRepository;
        mMeetings = mMeetingRepository.getMeetings();
    }

    public void onDeleteMeetingClicked(int meetingId) {
        mMeetingRepository.deleteMeeting(meetingId);
    }

    public LiveData<List<Meeting>> getMeetings() {
        return mMeetings;
    }

    public List<Room> getRooms() {
        return mRoomRepository.getRooms();
    }
}
