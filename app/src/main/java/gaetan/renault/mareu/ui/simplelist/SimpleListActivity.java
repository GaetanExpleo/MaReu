package gaetan.renault.mareu.ui.simplelist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.R;
import gaetan.renault.mareu.Repository.RoomRepository;

public class SimpleListActivity extends AppCompatActivity implements SimpleMeetingAdapter.DeleteMeetingListener {

    private List<Meeting> mMeetings = new ArrayList<>();
    private int id = 0;
    private SimpleMeetingAdapter adapter;
    private final RoomRepository mRoomRepository = RoomRepository.getInstance();
    private final List<String> participants = Collections.singletonList("blabla@gnagna.bof");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        final RecyclerView recyclerView = findViewById(R.id.simple_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new SimpleMeetingAdapter(this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.simple_fab);
        fab.setOnClickListener(v -> {
            String topic = "Topic" + (id + 1);
            long time = Calendar.getInstance().getTimeInMillis();
            Room room = mRoomRepository.getRooms().get(id + 1);
            mMeetings.add(new Meeting(++id, topic, participants, time, room));
            adapter.submitList(new ArrayList<>(mMeetings));
        });
    }

    @Override
    public void onDeleteMeeting(int meetingId) {
        int i = 0;
        boolean found = false;
        while (i < mMeetings.size()) {
            if (mMeetings.get(i).getId() == meetingId) {
                mMeetings.remove(i);
                found = true;
            }
            i++;
        }
        if (found) adapter.submitList(new ArrayList<>(mMeetings));
    }
}