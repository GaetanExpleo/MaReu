package gaetan.renault.mareu.ui.create;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.Repository.RoomRepository;

public class CreateMeetingViewModel extends ViewModel {

    private final MeetingRepository mMeetingRepository;
    private final RoomRepository mRoomRepository;

    private String mTopic;
    private List<String> mParticipants = new ArrayList<>();
    private Room mRoom;
    private long mTime;

    private boolean isTopicOk = false;
    private boolean isParticipantsOk = false;
    private boolean isRoomOk = false;
//    private boolean isTimeOk = false;

    private final List<Room> mRooms;
    private final MutableLiveData<Boolean> meetingReadyToCreate = new MutableLiveData<>();

    public CreateMeetingViewModel(MeetingRepository meetingRepository, RoomRepository roomRepository) {
        mMeetingRepository = meetingRepository;
        mRoomRepository = roomRepository;
        mRooms = mRoomRepository.getRooms();
        meetingReadyToCreate.setValue(false);
    }

    public LiveData<Boolean> isMeetingReadyToCreate() {
        return meetingReadyToCreate;
    }

    public void onTopicChanged(@NonNull String topic) {
        this.mTopic = topic;

        isTopicOk = (!mTopic.isEmpty());
        verifiedInputs();
    }

    public void onParticipantsChanged(@NonNull String participants) {
        //todo : emailaddress to verify
        mParticipants.clear();

        String[] participantsList = participants.split("[,; \n]");

        for (String participant : participantsList) {
            String participantCleaned = participant.trim();

            if (!participantCleaned.isEmpty()) {
                mParticipants.add(participantCleaned);
            }
        }

        isParticipantsOk = (mParticipants.size() > 0);
        verifiedInputs();
    }

    public void onRoomChanged(@NonNull Room room) {
        this.mRoom = room;

        isRoomOk = (mRoom != null);
        verifiedInputs();
    }

    public void onDateChanged(String time) {
        //Todo : gestion du changement de la date et de l'heure
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
//        Timestamp timestamp = new Timestamp(year,month+1,day,hour,minute,0,0);
//        long mTime = timestamp.getTime();
        try {
            mTime = format.parse(time).getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        verifiedInputs();
    }

    public void onCreateButtonClicked() {
        mMeetingRepository.addMeeting(mTopic, mTime, mParticipants, mRoom);

    }

    private void verifiedInputs() {
        meetingReadyToCreate.setValue(isTopicOk && isParticipantsOk);//&& isRoomOk && isTimeOk
    }

    public List<Room> getRooms() {
        return mRooms;
    }
}
