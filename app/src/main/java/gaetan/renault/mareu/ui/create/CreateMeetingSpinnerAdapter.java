package gaetan.renault.mareu.ui.create;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.R;
import gaetan.renault.mareu.Repository.RoomRepository;

public class CreateMeetingSpinnerAdapter extends BaseAdapter {

    private final List<Room> mRooms;

    public CreateMeetingSpinnerAdapter(List<Room> rooms) {
        mRooms = rooms;
    }

    @Override
    public int getCount() {
        return mRooms.size();
    }

    @Override
    public Object getItem(int position) {
        return mRooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mRooms.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Room currentRoom = (Room) getItem(position);
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_room_item,parent,false);
        ImageView icon = row.findViewById(R.id.meeting_room_item_imageview);
        TextView name = row.findViewById(R.id.meeting_room_item_name_tv);
        TextView capacity = row.findViewById(R.id.meeting_room_item_capacity_tv);

        int[] colors = parent.getContext().getResources().getIntArray(R.array.color_rooms);
        icon.setColorFilter(colors[currentRoom.getId()]);
        name.setText(currentRoom.getName());
        capacity.setText("Capacity: " + currentRoom.getCapacity());

        return row;
    }
}
