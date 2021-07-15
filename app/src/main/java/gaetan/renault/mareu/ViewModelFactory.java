package gaetan.renault.mareu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.ui.meetings.MeetingViewModel;
import gaetan.renault.mareu.ui.meetings.MeetingsActivity;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sFactory;

    private final MeetingRepository mMeetingRepository;

    public ViewModelFactory(MeetingRepository meetingRepository) {
        mMeetingRepository = meetingRepository;
    }

    public static ViewModelFactory getInstance(){
        if (sFactory == null){
            synchronized (ViewModelFactory.class){
                if (sFactory == null){
                    sFactory = new ViewModelFactory(
                            MeetingRepository.getInstance()
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
            return (T) new MeetingViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
