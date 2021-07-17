package gaetan.renault.mareu.ui.simplelist;

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

public class SimpleMeetingAdapter extends ListAdapter<Meeting, SimpleMeetingAdapter.SimpleMeetingViewHolder> {

    private final DeleteMeetingListener mDeleteMeetingListener;


    private static final DiffUtil.ItemCallback<Meeting> DIFF_CALLBACK = new DiffUtil.ItemCallback<Meeting>() {
        @Override
        public boolean areItemsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem.equals(newItem);
        }
    };

    protected SimpleMeetingAdapter(@NonNull DeleteMeetingListener deleteMeetingListener) {
        super(DIFF_CALLBACK);
        mDeleteMeetingListener = deleteMeetingListener;
    }

    @NonNull
    @Override
    public SimpleMeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MeetingItemBinding itemBinding = MeetingItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SimpleMeetingViewHolder(itemBinding, mDeleteMeetingListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleMeetingAdapter.SimpleMeetingViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    static class SimpleMeetingViewHolder extends RecyclerView.ViewHolder {

        protected final ImageView mMeetingImageView;
        protected final TextView mTitleMeeting;
        protected final TextView mParticipantsMeeting;
        protected final ImageButton mDeleteButton;
        private final DeleteMeetingListener mDeleteMeetingListener;
        protected int[] color;

        public SimpleMeetingViewHolder(@NonNull MeetingItemBinding itemBinding, DeleteMeetingListener deleteMeetingListener) {
            super(itemBinding.getRoot());
            mDeleteMeetingListener = deleteMeetingListener;
            color = itemBinding.getRoot().getResources().getIntArray(R.array.color_rooms);
            mMeetingImageView = itemBinding.meetingItemImageview;
            mTitleMeeting = itemBinding.meetingItemTitleTextview;
            mParticipantsMeeting = itemBinding.meetingItemParticipantsTextview;
            mDeleteButton = itemBinding.meetingItemDeleteButton;
        }

        public void bindTo(Meeting item) {
            mMeetingImageView.setColorFilter(color[item.getRoom().getId()]);
            mTitleMeeting.setText(item.getTopic());
            mParticipantsMeeting.setText(item.getParticipants().toString().replaceAll("\\[|\\]", ""));
            mDeleteButton.setOnClickListener(v -> mDeleteMeetingListener.onDeleteMeeting(item.getId()));
        }
    }

    public interface DeleteMeetingListener {
        void onDeleteMeeting(int meetingId);
    }
}
