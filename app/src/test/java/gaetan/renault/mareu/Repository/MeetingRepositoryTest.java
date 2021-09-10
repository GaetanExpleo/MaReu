package gaetan.renault.mareu.Repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.ui.meetings.LiveDataTestUtils;

import static org.junit.Assert.assertEquals;

public class MeetingRepositoryTest {

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingRepository mMeetingRepository;
    private final List<Room> mRooms = RoomRepository.getInstance().getRooms();

    @Before
    public void setUp() {
        mMeetingRepository = MeetingRepository.getInstance();
    }

    @Test
    public void add_meeting() throws InterruptedException {
        //Given
        mMeetingRepository.addMeeting("Réunion 1", 1631088000000L, 1631091600000L,
                Arrays.asList("gaetan@lamzone.com"), mRooms.get(0));
        mMeetingRepository.addMeeting("Réunion 2", 1631088000000L, 1631091600000L,
                Arrays.asList("gaetan@lamzone.com"), mRooms.get(1));

        List<Meeting> result = LiveDataTestUtils.getOrAwaitValue(mMeetingRepository.getMeetingsLiveData());
        assertEquals(2, result.size());
    }

    @Test
    public void delete_meeting() throws InterruptedException {
        //Given
        mMeetingRepository.addMeeting("Réunion 1", 1631088000000L, 1631091600000L,
                Arrays.asList("gaetan@lamzone.com"), mRooms.get(0));
        mMeetingRepository.addMeeting("Réunion 2", 1631088000000L, 1631091600000L,
                Arrays.asList("gaetan@lamzone.com"), mRooms.get(1));

        mMeetingRepository.deleteMeeting(0);
        List<Meeting> result = LiveDataTestUtils.getOrAwaitValue(mMeetingRepository.getMeetingsLiveData());
        assertEquals(3, result.size());
    }
}