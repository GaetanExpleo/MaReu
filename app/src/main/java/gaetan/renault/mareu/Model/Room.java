package gaetan.renault.mareu.Model;

public class Room {

    private final int id;
    private final String name;
    private final int capacity;

    public Room(int id, String roomName, int roomCapacity) {
        this.id = id;
        this.name = roomName;
        this.capacity = roomCapacity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
