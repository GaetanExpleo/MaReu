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
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        Calendar c4 = Calendar.getInstance();
        Calendar c5 = Calendar.getInstance();
        c1.set(2021,6,22,18,00,00);
        c2.set(2021,6,22,15,00,00);
        c3.set(2021,6,22,10,00,00);
        c4.set(2021,6,22,9,00,00);
        c5.set(2021,6,22,10,30,00);
        sMeetingRepository.addMeeting("Réunion 1",c1.getTimeInMillis(), c1.getTimeInMillis(), Collections.singletonList("gaetan@renault.com"),sRoomRepository.getRooms().get(0));
        sMeetingRepository.addMeeting("Réunion 2",c2.getTimeInMillis(), c2.getTimeInMillis(), Collections.singletonList("gaetan@renault.com"),sRoomRepository.getRooms().get(1));
        sMeetingRepository.addMeeting("Réunion 3",c3.getTimeInMillis(), c3.getTimeInMillis(), Collections.singletonList("gaetan@renault.com"),sRoomRepository.getRooms().get(2));
        sMeetingRepository.addMeeting("Réunion 4",c4.getTimeInMillis(), c4.getTimeInMillis(), Collections.singletonList("gaetan@renault.com"),sRoomRepository.getRooms().get(3));
        sMeetingRepository.addMeeting("Réunion 5",c5.getTimeInMillis(), c5.getTimeInMillis(), Collections.singletonList("gaetan@renault.com"),sRoomRepository.getRooms().get(4));
    }

    public static String formatDate(Calendar calendar, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String formattedDate = dateFormat.format(calendar.getTime());
        return formattedDate;
    }

    public static String formatDate(long time, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String formattedDate = dateFormat.format(time);
        return formattedDate;
    }

}