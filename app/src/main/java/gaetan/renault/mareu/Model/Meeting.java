package gaetan.renault.mareu.Model;

import androidx.annotation.Nullable;

import java.util.List;

public class Meeting {

    private final int id;
    private final String mTopic;
    private final List<String> mParticipants;
    private final long mStartMeeting;
    private final long mEndMeeting;
    private final Room mRoom;

    public Meeting(int id, String topic, List<String> participants, long startMeeting, long endMeeting, Room room) {
        this.id = id;
        mStartMeeting = startMeeting;
        mEndMeeting = endMeeting;
        mRoom = room;
        mTopic = topic;
        mParticipants = participants;
    }

    public int getId() {
        return id;
    }

    public Long getStartMeeting() {
        return mStartMeeting;
    }

    public long getEndMeeting() {return mEndMeeting;}

    public String getTopic() {
        return mTopic;
    }

    public List<String> getParticipants() {
        return mParticipants;
    }

    public Room getRoom() {
        return mRoom;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Meeting meeting = (Meeting) obj;

        if (getStartMeeting() != meeting.getStartMeeting()) return false;
        if (!getTopic().equals(meeting.getTopic())) return false;
        if (!getParticipants().equals(meeting.getParticipants())) return false;
        return getRoom().equals(meeting.getRoom());
    }
}
