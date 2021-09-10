package gaetan.renault.mareu.ui.meetings;

import java.util.Arrays;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Repository.RoomRepository;

public class utilsForTest {

    public static long durationToLong(int hour, int minute) {
        return ((hour * 60) + minute) * 60000;
    }

    private static final String MEETING_TOPIC = "Réunion";
    private static final List<String> MEETING_PARTICIPANTS = Arrays.asList("participant1@lamzone.com", "paticipant2@lamzone.com", "participant3@lamzone.com");
    private static final long START_MEETING_16h = 1627653600000L; //16h
    private static final long START_MEETING_10h = 1631088000000L; //10h
    private static final long END_MEETING_16h30 = 1627655400000L; //16h30
    private static final long END_MEETING_11h = 1631091600000L; //11h
    private static final RoomRepository sRoomRepository = RoomRepository.getInstance();

    public static final Meeting TEST_MEETING1 = new Meeting(0, MEETING_TOPIC + "1", MEETING_PARTICIPANTS, START_MEETING_16h , END_MEETING_16h30 , sRoomRepository.getRooms().get(0));
    public static final Meeting TEST_MEETING2 = new Meeting(1, MEETING_TOPIC + "2", MEETING_PARTICIPANTS, START_MEETING_16h, END_MEETING_16h30, sRoomRepository.getRooms().get(0));
    public static final Meeting TEST_MEETING3 = new Meeting(2, MEETING_TOPIC + "3", MEETING_PARTICIPANTS, START_MEETING_10h, END_MEETING_11h, sRoomRepository.getRooms().get(1));
    public static final Meeting TEST_MEETING4 = new Meeting(3, MEETING_TOPIC + "4", MEETING_PARTICIPANTS, START_MEETING_16h, END_MEETING_16h30, sRoomRepository.getRooms().get(1));
    public static final Meeting TEST_MEETING5 = new Meeting(4, MEETING_TOPIC + "5", MEETING_PARTICIPANTS, START_MEETING_10h, END_MEETING_11h, sRoomRepository.getRooms().get(2));

    public static final List<Meeting> TEST_MEETINGS = Arrays.asList(TEST_MEETING1, TEST_MEETING2, TEST_MEETING3, TEST_MEETING4, TEST_MEETING5);

}
