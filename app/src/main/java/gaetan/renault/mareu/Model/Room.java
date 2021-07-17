package gaetan.renault.mareu.Model;

import androidx.annotation.NonNull;

public class Room {

    private final int id;

    @NonNull
    private final String name;

    private final int capacity;

    public Room(int id, @NonNull String roomName, int roomCapacity) {
        this.id = id;
        this.name = roomName;
        this.capacity = roomCapacity;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
