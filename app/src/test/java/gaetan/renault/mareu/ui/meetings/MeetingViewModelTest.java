package gaetan.renault.mareu.ui.meetings;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.Repository.RoomRepository;
import gaetan.renault.mareu.ui.create.CreateMeetingViewModel;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MeetingViewModelTest extends TestCase {

    @Mock
    private MeetingRepository mockMeetingRepository;
    @Mock
    private RoomRepository mockRoomRepository;

    private MeetingViewModel mMeetingViewModel;

    private List<Room> mRoomsList = RoomRepository.getInstance().getRooms();

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Room>> roomsLiveData = new MutableLiveData<>();
    private final Calendar c1 = Calendar.getInstance();
    private final long startMeeting = c1.getTimeInMillis();
    private final long endMeeting = c1.getTimeInMillis() + utilsForTest.durationToLong(1, 0);

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        Mockito.doReturn(meetingsLiveData).when(mockMeetingRepository).getMeetingsLiveData();
        Mockito.doReturn(roomsLiveData).when(mockRoomRepository).getRooms();

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
    public void add_meeting() {
        //Given
        CreateMeetingViewModel createMeetingViewModel = new CreateMeetingViewModel(mockMeetingRepository,mockRoomRepository);

        createMeetingViewModel.onCreateButtonClicked();
        //When
        mMeetingViewModel.getMeetingsLiveData().observeForever(meetings1 -> {
            //Then
            assertEquals(5, meetings1.size());
        });
    }

    @Test
    public void delete_meeting() throws InterruptedException {
        //Given
//        List<Meeting> meetings = utilsForTest.TEST_MEETINGS;
        int meetingsSize = utilsForTest.TEST_MEETINGS.size();

        meetingsLiveData.setValue(utilsForTest.TEST_MEETINGS);

        mMeetingViewModel.onDeleteMeetingClicked(0);
        List<Meeting> result = LiveDataTestUtils.getOrAwaitValue(mMeetingViewModel.getMeetingsLiveData());
        assertEquals(meetingsSize, result.size());
//        viewModel.getMeetingsLiveData().observeForever(new Observer<List<Meeting>>() {
//            @Override
//            public void onChanged(List<Meeting> meetings1) {
//                assertEquals(meetingsSize-1,meetings1.size());
//            }
//        });

    }

    @Test
    public void meeting_room_filtered() {
        List<Meeting> meetings = utilsForTest.TEST_MEETINGS;

        MeetingViewModel viewModel = new MeetingViewModel(mockMeetingRepository);
        viewModel.onRoomFilterSelected(0);

        viewModel.getMeetingsLiveData().observeForever(new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                assertEquals(2, meetings.size());
            }
        });
    }

    @Test
    public void meeting_hour_filtered() {
        List<Meeting> meetings = utilsForTest.TEST_MEETINGS;

        MeetingViewModel viewModel = new MeetingViewModel(mockMeetingRepository);
        viewModel.onHourFilterSelected(3);

        viewModel.getMeetingsLiveData().observeForever(new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                assertEquals(2, meetings.size());
            }
        });
    }
}