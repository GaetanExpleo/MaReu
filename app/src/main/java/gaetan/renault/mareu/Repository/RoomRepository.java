package gaetan.renault.mareu.Repository;

import java.util.ArrayList;
import java.util.List;

import gaetan.renault.mareu.Model.Room;
import gaetan.renault.mareu.R;

public class RoomRepository {

    private List<Room> mRooms;

    public RoomRepository() {
        mRooms = new ArrayList<>();

        mRooms.add(new Room(0, "Salle 1", 5));
        mRooms.add(new Room(1, "Salle 2", 6));
        mRooms.add(new Room(2, "Salle 3", 10));
        mRooms.add(new Room(3, "Salle 4", 3));
        mRooms.add(new Room(4, "Salle 5", 8));
        mRooms.add(new Room(5, "Salle 6", 12));
        mRooms.add(new Room(6, "Salle 7", 5));
        mRooms.add(new Room(7, "Salle 8", 2));
    }

    private static class RoomRepositoryHolder{
        private final static RoomRepository INSTANCE = new RoomRepository();
    }

    public static RoomRepository getInstance() {
        return RoomRepositoryHolder.INSTANCE;
    }

    public List<Room> getRooms() {
        return mRooms;
    }
}
