package gaetan.renault.mareu.Model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Meeting {

    private final int id;
    private final String mTopic;
    private final List<String> mParticipants;
    private final long mTime;
    private final Room mRoom;

    public Meeting(int id, String topic, List<String> participants, long time, Room room) {
        this.id = id;
        this.mTime = time;
        this.mRoom = room;
        this.mTopic = topic;
        this.mParticipants = participants;
    }

    public int getId() {
        return id;
    }

    public Long getTime() {
        return mTime;
    }

    public String getTopic() {
        return mTopic;
    }

    public List<String> getParticipants() {
        return mParticipants;
    }

    public Room getRoom() {
        return mRoom;
    }
}
