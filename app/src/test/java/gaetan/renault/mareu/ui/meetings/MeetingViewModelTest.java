package gaetan.renault.mareu.ui.meetings;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.Repository.RoomRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MeetingViewModelTest extends TestCase {

    @Mock
    private MeetingRepository mockMeetingRepository;

    private MeetingViewModel mMeetingViewModel;

    private List<Room> mRoomsList = RoomRepository.getInstance().getRooms();

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>();

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        Mockito.doReturn(meetingsLiveData).when(mockMeetingRepository).getMeetingsLiveData();

        mMeetingViewModel = new MeetingViewModel(mockMeetingRepository);
    }

    @Test
    public void verifyDeleteMeetingCallsMeetingRepositoryDelete() {
        //Given
        meetingsLiveData.setValue(utilsForTest.TEST_MEETINGS);
        //When
        mMeetingViewModel.onDeleteMeetingClicked(0);
        //then
        verify(mockMeetingRepository, times(1)).deleteMeeting(0);
    }

    @Test
    public void meeting_room_filtered() {
        meetingsLiveData.setValue(utilsForTest.TEST_MEETINGS);

        MeetingViewModel viewModel = new MeetingViewModel(mockMeetingRepository);
        viewModel.onRoomFilterSelected(0);

        viewModel.getMeetingsLiveData().observeForever(meetings -> assertEquals(2, meetings.size()));
    }

    @Test
    public void meeting_hour_filtered_16h() {
        meetingsLiveData.setValue(utilsForTest.TEST_MEETINGS);

        MeetingViewModel viewModel = new MeetingViewModel(mockMeetingRepository);

        viewModel.onHourFilterSelected(16);
        viewModel.getMeetingsLiveData().observeForever(meetings -> assertEquals(3, meetings.size()));
    }

    @Test
    public void meeting_hour_filtered_10h() {
        meetingsLiveData.setValue(utilsForTest.TEST_MEETINGS);

        MeetingViewModel viewModel = new MeetingViewModel(mockMeetingRepository);

        viewModel.onHourFilterSelected(10);
        viewModel.getMeetingsLiveData().observeForever(meetings -> assertEquals(2, meetings.size()));
    }

    @Test
    public void meeting_hour_filtered_9h() {
        meetingsLiveData.setValue(utilsForTest.TEST_MEETINGS);

        MeetingViewModel viewModel = new MeetingViewModel(mockMeetingRepository);

        viewModel.onHourFilterSelected(9);
        viewModel.getMeetingsLiveData().observeForever(meetings -> assertEquals(0, meetings.size()));
    }


}