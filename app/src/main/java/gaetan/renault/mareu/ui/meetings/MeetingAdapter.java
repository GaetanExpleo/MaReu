package gaetan.renault.mareu.ui.meetings;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import gaetan.renault.mareu.Model.Meeting;
import gaetan.renault.mareu.R;
import gaetan.renault.mareu.databinding.MeetingItemBinding;

public class MeetingAdapter extends ListAdapter<Meeting, MeetingAdapter.MeetingViewHolder> {

    private final OnMeetingDeleteListener mListener;

    private int[] color;

    protected MeetingAdapter(@NonNull OnMeetingDeleteListener listener) {
        super(DIFF_CALLBACK);
        mListener = listener;
    }

    private static final DiffUtil.ItemCallback<Meeting> DIFF_CALLBACK = new DiffUtil.ItemCallback<Meeting>() {
        @Override
        public boolean areItemsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem.getTopic().contentEquals(newItem.getTopic()) &&
                    oldItem.getRoom().getId() == newItem.getRoom().getId();
        }
    };

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        color = parent.getContext().getResources().getIntArray(R.array.color_rooms);
        MeetingItemBinding itemBinding = MeetingItemBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        return new MeetingViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.MeetingViewHolder holder, int position) {
        Meeting currentMeeting = getItem(position);
        holder.mTitleMeeting.setText(currentMeeting.getTopic());
        holder.mParticipantsMeeting.setText(currentMeeting.getParticipants().toString().replaceAll("\\[|\\]", ""));
        holder.mMeetingImageView.setColorFilter(color[currentMeeting.getRoom().getId()]);
        holder.mDeleteButton.setOnClickListener(v -> mListener.onDeleteMeetingClicked(currentMeeting.getId()));

    }

    protected static class MeetingViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mMeetingImageView;
        public final TextView mTitleMeeting;
        public final TextView mParticipantsMeeting;
        public final ImageButton mDeleteButton;

        public MeetingViewHolder(@NonNull MeetingItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mMeetingImageView = itemBinding.meetingItemImageview;
            mTitleMeeting = itemBinding.meetingItemTitleTextview;
            mParticipantsMeeting = itemBinding.meetingItemParticipantsTextview;
            mDeleteButton = itemBinding.meetingItemDeleteButton;
        }
    }
}
