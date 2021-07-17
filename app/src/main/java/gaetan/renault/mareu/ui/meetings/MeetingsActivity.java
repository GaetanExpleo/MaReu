package gaetan.renault.mareu.ui.meetings;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import gaetan.renault.mareu.R;
import gaetan.renault.mareu.ViewModelFactory;
import gaetan.renault.mareu.databinding.ActivityListMeetingBinding;
import gaetan.renault.mareu.ui.create.CreateMeetingActivity;

public class MeetingsActivity extends AppCompatActivity implements OnMeetingDeleteListener {

    private ActivityListMeetingBinding binding;

    private RecyclerView recyclerViewRoom;
    private RecyclerView recyclerViewHour;

    private MeetingViewModel mMeetingViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListMeetingBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        mMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingViewModel.class);

        initToolbar();
        initRecyclerViews();
        initFab();
    }

    private void initToolbar() {
        Toolbar toolbar = binding.listMeetingToolbar;
        setSupportActionBar(toolbar);
    }

    private void initRecyclerViews() {
        final RecyclerView recyclerViewMeeting = binding.listMeetingRecyclerview;
        final MeetingAdapter meetingAdapter = new MeetingAdapter(this);
        recyclerViewMeeting.setAdapter(meetingAdapter);

        mMeetingViewModel.getMeetings().observe(this, meetingAdapter::submitList);

        recyclerViewRoom = binding.listMeetingRoomRecyclerview;
        RoomFilterAdapter roomFilterAdapter = new RoomFilterAdapter(mMeetingViewModel.getRooms());
        recyclerViewRoom.setAdapter(roomFilterAdapter);

        recyclerViewHour = binding.listMeetingHourRecyclerview;
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.list_meeting_fab);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(MeetingsActivity.this, CreateMeetingActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_filter_room:
                if (recyclerViewRoom.getVisibility() == View.GONE) {
                    recyclerViewRoom.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewRoom.setVisibility(View.GONE);
                }
                return true;
            case R.id.menu_filter_hour:
                if (recyclerViewHour.getVisibility() == View.GONE) {
                    recyclerViewHour.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewHour.setVisibility(View.GONE);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDeleteMeetingClicked(int meetingId) {
        mMeetingViewModel.onDeleteMeetingClicked(meetingId);
    }
}