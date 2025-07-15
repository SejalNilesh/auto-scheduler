public class Room {
    public String id;
    public int capacity;

    public Room(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return id + " (cap=" + capacity + ")";
    }
}
