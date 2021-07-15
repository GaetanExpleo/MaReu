package gaetan.renault.mareu.ui.meetings;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Repository.MeetingRepository;

public class MeetingViewModel extends ViewModel {

//    @NonNull
//    private final Application mApplication;
    @NonNull
    private final MeetingRepository mMeetingRepository = MeetingRepository.getInstance();
    private LiveData<List<Meeting>> mMeetings = new MutableLiveData<>();

    public MeetingViewModel() {
        mMeetings = mMeetingRepository.getMeetings();
    }

    public void onDeleteMeetingClicked(int meetingId){
        mMeetingRepository.deleteMeeting(meetingId);
    }

    public LiveData<List<Meeting>> getMeetings(){
        return mMeetings;
    }
}
