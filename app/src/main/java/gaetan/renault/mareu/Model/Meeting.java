package gaetan.renault.mareu.Model;

import androidx.annotation.NonNull;

import java.util.List;

public class Meeting {

    private final int id;

    @NonNull
    private final String topic;

    @NonNull
    private final List<String> participants;

    private final long time;

    @NonNull
    private final Room room;

    public Meeting(int id, @NonNull String topic, @NonNull List<String> participants, long time, @NonNull Room room) {
        this.id = id;
        this.topic = topic;
        this.participants = participants;
        this.time = time;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTopic() {
        return topic;
    }

    @NonNull
    public List<String> getParticipants() {
        return participants;
    }

    public long getTime() {
        return time;
    }

    @NonNull
    public Room getRoom() {
        return room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meeting meeting = (Meeting) o;

        if (getTime() != meeting.getTime()) return false;
        if (!getTopic().equals(meeting.getTopic())) return false;
        if (!getParticipants().equals(meeting.getParticipants())) return false;
        return getRoom().equals(meeting.getRoom());
    }
}
