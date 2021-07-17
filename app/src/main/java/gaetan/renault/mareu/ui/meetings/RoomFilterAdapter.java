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

public class RoomFilterAdapter extends RecyclerView.Adapter<RoomFilterAdapter.RoomFilterViewHolder> {

    private final List<Room> mRooms;
    private int[] color;
    private int[] colorAccent;

    public RoomFilterAdapter(List<Room> rooms) {
        mRooms = rooms;
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

        int[] colors = new int[]{
                color[position],
                colorAccent[position]
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);

        holder.mChip.setChipIconTint(colorStateList);
        holder.mChip.setText(mRooms.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    public static class RoomFilterViewHolder extends RecyclerView.ViewHolder {

        public final Chip mChip;

        public RoomFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            mChip = itemView.findViewById(R.id.meeting_room_chip);
        }
    }
}
