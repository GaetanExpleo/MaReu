package gaetan.renault.mareu.ui.meetings;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.R;
import gaetan.renault.mareu.Repository.RoomRepository;

public class RoomFilterAdapter extends RecyclerView.Adapter<RoomFilterAdapter.RoomFilterViewHolder> {

    private final List<Room> mRoom = RoomRepository.getInstance().getRooms();
    private int[] color;
    private int[] colorAccent;
    private final RoomSelectedListener mRoomSelectedListener;

    public RoomFilterAdapter(RoomSelectedListener roomSelectedListener) {
        mRoomSelectedListener = roomSelectedListener;
    }

    @NonNull
    @Override
    public RoomFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        color = parent.getContext().getResources().getIntArray(R.array.color_rooms);
        colorAccent = parent.getContext().getResources().getIntArray(R.array.color_accent_rooms);
        return new RoomFilterViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.meeting_room_chips, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomFilterAdapter.RoomFilterViewHolder holder, int position) {

        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colorsBackground = new int[]{
                0,
                colorAccent[position]
        };

        int[] colorsIcon = new int[]{
                color[position],
                color[position]
        };

        ColorStateList colorStateListBackground = new ColorStateList(states, colorsBackground);
        ColorStateList colorStateListIcon = new ColorStateList(states, colorsIcon);

        holder.mChip.setChipIconTint(colorStateListIcon);
        holder.mChip.setChipBackgroundColor(colorStateListBackground);
        holder.mChip.setText(mRoom.get(position).getName());

        holder.mChip.setOnCheckedChangeListener((buttonView, isChecked) -> mRoomSelectedListener.onRoomSelected(position));
    }

    @Override
    public int getItemCount() {
        return mRoom.size();
    }

    public class RoomFilterViewHolder extends RecyclerView.ViewHolder {

        public final Chip mChip;

        public RoomFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            mChip = itemView.findViewById(R.id.meeting_room_chip);
        }
    }

    public interface RoomSelectedListener {
        void onRoomSelected(int roomId);
    }
}