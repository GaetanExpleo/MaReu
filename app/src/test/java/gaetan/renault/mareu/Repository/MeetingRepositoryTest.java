package gaetan.renault.mareu.Repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.ui.meetings.LiveDataTestUtils;
import gaetan.renault.mareu.utilsForTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MeetingRepositoryTest {

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingRepository mMeetingRepository;
    private final List<Room> mRooms = RoomRepository.getInstance().getRooms();

    @Before
    public void setUp() {
        mMeetingRepository = MeetingRepository.getInstance();
    }

    @After
    public void tearDown(){
        mMeetingRepository.resetLiveDataForTest();
    }

    @Test
    public void add_meeting() throws InterruptedException {
        //Given
        mMeetingRepository.addMeeting(utilsForTest.TEST_MEETING1.getTopic(),
                utilsForTest.TEST_MEETING1.getStartMeeting(),
                utilsForTest.TEST_MEETING1.getEndMeeting(),
                utilsForTest.TEST_MEETING1.getParticipants(),
                utilsForTest.TEST_MEETING1.getRoom());
        mMeetingRepository.addMeeting(utilsForTest.TEST_MEETING2.getTopic(),
                utilsForTest.TEST_MEETING2.getStartMeeting(),
                utilsForTest.TEST_MEETING2.getEndMeeting(),
                utilsForTest.TEST_MEETING2.getParticipants(),
                utilsForTest.TEST_MEETING2.getRoom());

        List<Meeting> result = LiveDataTestUtils.getOrAwaitValue(mMeetingRepository.getMeetingsLiveData());
        assertEquals(2, result.size());
        assertTrue(result.get(0).getTopic() == utilsForTest.TEST_MEETING1.getTopic()
        && result.get(1).getTopic() == utilsForTest.TEST_MEETING2.getTopic());
    }

    @Test
    public void delete_meeting() throws InterruptedException {
        //Given
        mMeetingRepository.addMeeting(utilsForTest.TEST_MEETING3.getTopic(),
                utilsForTest.TEST_MEETING3.getStartMeeting(),
                utilsForTest.TEST_MEETING3.getEndMeeting(),
                utilsForTest.TEST_MEETING3.getParticipants(),
                utilsForTest.TEST_MEETING3.getRoom());
        mMeetingRepository.addMeeting(utilsForTest.TEST_MEETING5.getTopic(),
                utilsForTest.TEST_MEETING5.getStartMeeting(),
                utilsForTest.TEST_MEETING5.getEndMeeting(),
                utilsForTest.TEST_MEETING5.getParticipants(),
                utilsForTest.TEST_MEETING5.getRoom());

        mMeetingRepository.deleteMeeting(0);
        List<Meeting> result = LiveDataTestUtils.getOrAwaitValue(mMeetingRepository.getMeetingsLiveData());
        assertEquals(1, result.size());
        assertTrue(result.get(0).getTopic() == utilsForTest.TEST_MEETING5.getTopic());
    }
}