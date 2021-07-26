package gaetan.renault.mareu.utils;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.Repository.RoomRepository;

public final class utility {

    private static MeetingRepository sMeetingRepository = MeetingRepository.getInstance();
    private static RoomRepository sRoomRepository = RoomRepository.getInstance();

    public static String formatOneInTwoNumber(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return "" + number;
    }

    public static void generateFalseMeeting(){
        //Todo : refaire la base de temps
        Calendar c = Calendar.getInstance();
        c.set(2021,6,22,18,00,00);
        long startMeeting = c.getTimeInMillis();
        sMeetingRepository.addMeeting("Réunion 1",startMeeting, startMeeting + 600000, Collections.singletonList("gaetan@renault.com"),sRoomRepository.getRooms().get(0));
        sMeetingRepository.addMeeting("Réunion 2",startMeeting + (600000 * 3), startMeeting+ (600000 * 4), Collections.singletonList("gaetan@renault.com"),sRoomRepository.getRooms().get(1));
        sMeetingRepository.addMeeting("Réunion 3",startMeeting, startMeeting, Collections.singletonList("gaetan@renault.com"),sRoomRepository.getRooms().get(2));
        sMeetingRepository.addMeeting("Réunion 4",startMeeting, startMeeting, Collections.singletonList("gaetan@renault.com"),sRoomRepository.getRooms().get(3));
        sMeetingRepository.addMeeting("Réunion 5",startMeeting, startMeeting, Collections.singletonList("gaetan@renault.com"),sRoomRepository.getRooms().get(4));
    }

    public static String formatDate(Calendar calendar, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String formattedDate = dateFormat.format(calendar.getTime());
        return formattedDate;
    }
}