package gaetan.renault.mareu.Repository;

import java.util.Arrays;
import java.util.List;

import gaetan.renault.mareu.Model.Room;

public class RoomRepository {

    private final List<Room> mRooms;

    public RoomRepository() {
        mRooms = Arrays.asList(
                new Room(1, "Salle 1", 5),
                new Room(2, "Salle 2", 6),
                new Room(3, "Salle 3", 10),
                new Room(4, "Salle 4", 3),
                new Room(5, "Salle 5", 8),
                new Room(6, "Salle 6", 12),
                new Room(7, "Salle 7", 5),
                new Room(8, "Salle 8", 2)
        );
    }

    private static class RoomRepositoryHolder {
        public static final  RoomRepository INSTANCE = new RoomRepository();
    }

    public static RoomRepository getInstance() {
        return RoomRepositoryHolder.INSTANCE;
    }

    public List<Room> getRooms() {
        return mRooms;
    }
}
