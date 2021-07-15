package gaetan.renault.mareu.ui.meetings;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
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

public class MeetingsAdapter extends ListAdapter<Meeting, MeetingsAdapter.MeetingViewHolder> {

    private final OnMeetingDeleteListener mListener;

    private int[] color;

    public MeetingsAdapter(@NonNull OnMeetingDeleteListener listener) {
        super(DIFF_CALLBACK);
        mListener = listener;
    }

    private static final DiffUtil.ItemCallback<Meeting> DIFF_CALLBACK = new DiffUtil.ItemCallback<Meeting>() {
        @Override
        public boolean areItemsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            //TODO : to complete
            return oldItem.getTopic().equals(newItem.getTopic()) &&
                    oldItem.getParticipants().equals(newItem.getParticipants()) &&
                    oldItem.getRoom().getId() == newItem.getRoom().getId();
        }
    };

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        color = parent.getContext().getResources().getIntArray(R.array.color_rooms);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_item,parent,false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingsAdapter.MeetingViewHolder holder, int position) {
        Meeting currentMeeting = getItem(position);
        holder.mTitleMeeting.setText(currentMeeting.getTopic());
        holder.mParticipantsMeeting.setText(currentMeeting.getParticipants().toString().replaceAll("\\[|\\]",""));
        holder.mMeetingImageView.setColorFilter(color[currentMeeting.getRoom().getId()]);
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteMeetingClicked(currentMeeting.getId());
            }
        });

    }

    public class MeetingViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mMeetingImageView;
        public final TextView mTitleMeeting;
        public final TextView mParticipantsMeeting;
        public final ImageButton mDeleteButton;

        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            mMeetingImageView = itemView.findViewById(R.id.meeting_item_imageview);
            mTitleMeeting = itemView.findViewById(R.id.meeting_item_title_textview);
            mParticipantsMeeting = itemView.findViewById(R.id.meeting_item_participants_textview);
            mDeleteButton = itemView.findViewById(R.id.meeting_item_delete_button);
        }
    }
}
