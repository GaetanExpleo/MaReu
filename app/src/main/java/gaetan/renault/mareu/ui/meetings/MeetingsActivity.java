package gaetan.renault.mareu.ui.meetings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import gaetan.renault.mareu.Repository.MeetingRepository;
import gaetan.renault.mareu.databinding.ActivityListMeetingBinding;
import gaetan.renault.mareu.ui.create.CreateMeetingActivity;
import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.R;
import gaetan.renault.mareu.ViewModelFactory;

public class MeetingsActivity extends AppCompatActivity implements OnMeetingDeleteListener{

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

        mMeetingViewModel = new ViewModelProvider(this).get(MeetingViewModel.class);

        recyclerViewRoom = binding.listMeetingRoomRecyclerview;
        RoomFilterAdapter roomFilterAdapter = new RoomFilterAdapter();
        recyclerViewRoom.setAdapter(roomFilterAdapter);

        recyclerViewHour = binding.listMeetingHourRecyclerview;

        mMeetingViewModel.getMeetings().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                meetingsAdapter.submitList(meetings);
//                meetingsAdapter.notifyDataSetChanged();
            }
        });
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
        switch (itemId){
            case R.id.menu_filter_room:
                if (recyclerViewRoom.getVisibility() == View.GONE) {
                    recyclerViewRoom.setVisibility(View.VISIBLE);
                } else {
                    recyclerViewRoom.setVisibility(View.GONE);
                }
                return true;
            case R.id.menu_filter_hour:
                if (recyclerViewHour.getVisibility() == View.GONE){
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