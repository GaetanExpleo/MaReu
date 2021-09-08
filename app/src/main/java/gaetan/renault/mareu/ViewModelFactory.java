package gaetan.renault.mareu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.Repository.RoomRepository;
import gaetan.renault.mareu.ui.create.CreateMeetingViewModel;
import gaetan.renault.mareu.ui.meetings.MeetingViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sFactory;

    private final MeetingRepository mMeetingRepository;
    private final RoomRepository mRoomRepository;

    public ViewModelFactory(MeetingRepository meetingRepository, RoomRepository roomRepository) {
        mMeetingRepository = meetingRepository;
        mRoomRepository = roomRepository;
    }

    public static ViewModelFactory getInstance() {
        if (sFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (sFactory == null) {
                    sFactory = new ViewModelFactory(
                            MeetingRepository.getInstance(),
                            RoomRepository.getInstance()
                    );
                }
            }
        }
        return sFactory;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeetingViewModel.class)) {
            return (T) new MeetingViewModel(mMeetingRepository);
        }
        if (modelClass.isAssignableFrom(CreateMeetingViewModel.class)) {
            return (T) new CreateMeetingViewModel(mMeetingRepository, mRoomRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}