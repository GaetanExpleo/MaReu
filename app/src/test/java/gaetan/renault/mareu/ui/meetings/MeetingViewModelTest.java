package gaetan.renault.mareu.ui.meetings;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.Repository.RoomRepository;
import gaetan.renault.mareu.utils.utilsForTest;

@RunWith(MockitoJUnitRunner.class)
public class MeetingViewModelTest extends TestCase {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private MeetingRepository mMeetingRepository = Mockito.mock(MeetingRepository.class);
    private RoomRepository mRoomRepository = Mockito.mock((RoomRepository.class));
    private List<Room> mRoomsList = RoomRepository.getInstance().getRooms();

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>();
    private final Calendar c1 = Calendar.getInstance();
    private final long startMeeting = c1.getTimeInMillis();
    private final long endMeeting = c1.getTimeInMillis() + utilsForTest.durationToLong(1,0);

    @Before
    public void setUp(){
        Mockito.doReturn(meetingsLiveData).when(mMeetingRepository).getMeetingsLiveData();
    }

    @Test
    public void add_meeting(){
        //Given
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(new Meeting(0,"Réunion A", utilsForTest.MEETING_PARTICIPANTS,
                startMeeting, endMeeting, mRoomsList.get(0)));

        meetingsLiveData.setValue(meetings);

        //When
        MeetingViewModel viewModel = new MeetingViewModel(mMeetingRepository);
        viewModel.getMeetingsLiveData().observeForever(meetings1 -> {
            //Then
            assertEquals(1, meetings1.size());
        });
    }

    @Test
    public void delete_meeting(){
        //Given
        List<Meeting> meetings = utilsForTest.TEST_MEETINGS;
        int meetingsSize = meetings.size();

        meetingsLiveData.setValue(meetings);

        MeetingViewModel viewModel = new MeetingViewModel(mMeetingRepository);
        viewModel.onDeleteMeetingClicked(0);
        viewModel.getMeetingsLiveData().observeForever(new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings1) {
                assertEquals(meetingsSize-1,meetings1.size());
            }
        });

    }

    @Test
    public void meeting_room_filtered(){
        List<Meeting> meetings = utilsForTest.TEST_MEETINGS;

        MeetingViewModel viewModel = new MeetingViewModel(mMeetingRepository);
        viewModel.onRoomFilterSelected(0);

        viewModel.getMeetingsLiveData().observeForever(new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                assertEquals(2, meetings.size());
            }
        });
    }
}