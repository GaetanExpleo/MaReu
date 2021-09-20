package gaetan.renault.mareu.ui.meetings;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import gaetan.renault.mareu.R;
import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.ViewModelFactory;
import gaetan.renault.mareu.databinding.ActivityListMeetingBinding;
import gaetan.renault.mareu.ui.create.CreateMeetingActivity;
import gaetan.renault.mareu.utils.utility;

public class MeetingsActivity extends AppCompatActivity implements
        MeetingsAdapter.DeleteMeetingListener,
        RoomFilterAdapter.RoomSelectedListener,
        HourFilterAdapter.HourSelectedListener {

    private ActivityListMeetingBinding binding;

    private RecyclerView recyclerViewRoom;
    private RecyclerView recyclerViewHour;
    private RecyclerView recyclerViewMeeting;

    private MeetingRepository mMeetingRepository = MeetingRepository.getInstance();
    private MeetingViewModel mMeetingViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListMeetingBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        initToolbar();
        initRecyclerViews();
        initFab();
    }

    private void initToolbar() {
        Toolbar toolbar = binding.listMeetingToolbar;
        setSupportActionBar(toolbar);
    }

    private void initRecyclerViews() {
        recyclerViewMeeting = binding.listMeetingRecyclerview;
        MeetingsAdapter meetingsAdapter = new MeetingsAdapter(this);
        recyclerViewMeeting.setAdapter(meetingsAdapter);

        mMeetingViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingViewModel.class);

        recyclerViewRoom = binding.listMeetingRoomRecyclerview;
        RoomFilterAdapter roomFilterAdapter = new RoomFilterAdapter(this);
        recyclerViewRoom.setAdapter(roomFilterAdapter);

        recyclerViewHour = binding.listMeetingHourRecyclerview;
        HourFilterAdapter hourFilterAdapter = new HourFilterAdapter(this);
        recyclerViewHour.setAdapter(hourFilterAdapter);

        mMeetingViewModel.getMeetingsLiveData().observe(this, meetings -> meetingsAdapter.submitList(meetings));
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.list_meeting_fab);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(MeetingsActivity.this, CreateMeetingActivity.class));
        });
        fab.setOnLongClickListener(v -> {
            utility.generateFalseMeeting();
            return true;
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
    public void onRoomSelected(int roomId) {
        mMeetingViewModel.onRoomFilterSelected(roomId);
    }

    @Override
    public void onDeleteMeting(int meetingId) {
        mMeetingViewModel.onDeleteMeetingClicked(meetingId);
    }

    @Override
    public void onHourSelected(int hourSelected) {
        mMeetingViewModel.onHourFilterSelected(hourSelected);
    }
}