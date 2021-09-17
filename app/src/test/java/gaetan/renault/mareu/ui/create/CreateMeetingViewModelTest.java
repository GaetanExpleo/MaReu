package gaetan.renault.mareu.ui.create;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.Repository.RoomRepository;
import gaetan.renault.mareu.utilsForTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateMeetingViewModelTest {
    @Mock
    private MeetingRepository mockMeetingRepository;
    @Mock
    private RoomRepository mockRoomRepository;

    private CreateMeetingViewModel mViewModel;

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>();

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        mViewModel = new CreateMeetingViewModel(mockMeetingRepository,mockRoomRepository);
    }

    @Test
    public void verifyAddMeetingCallsMeetingRepositoryAdd() {

        //Given
        mViewModel.onTopicChanged(utilsForTest.TEST_MEETING1.getTopic());
        //When
        mViewModel.onCreateButtonClicked();
        //then
        verify(mockMeetingRepository, times(1)).addMeeting(utilsForTest.TEST_MEETING1.getTopic(),
                utilsForTest.TEST_MEETING1.getStartMeeting(),
                utilsForTest.TEST_MEETING1.getEndMeeting(),
                utilsForTest.TEST_MEETING1.getParticipants(),
                utilsForTest.TEST_MEETING1.getRoom());
    }
}