import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Floor {
    private char[][] map;
    private List<Room> rooms;
    private Random random;
    private Room exitRoom;

    public Floor(int width, int height) {
        this.map = new char[height][width];
        this.rooms = new ArrayList<>();
        this.random = new Random();
    }

    public void generateMap() {
        // Generuj pokoje
        int numRooms = random.nextInt(10) + 10; // Zwiększamy liczbę pokojów
        for (int i = 0; i < numRooms; i++) {
            int width = random.nextInt(6) + 4;
            int height = random.nextInt(6) + 4;
            int x = random.nextInt(map[0].length - width - 2) + 1;
            int y = random.nextInt(map.length - height - 2) + 1;
            Room room = new Room(x, y, width, height);
            if (isRoomValid(room)) {
                rooms.add(room);
                for (int r = room.y; r < room.y + room.height; r++) {
                    for (int c = room.x; c < room.x + room.width; c++) {
                        map[r][c] = '.';
                    }
                }
            }
        }

        // Połącz pokoje korytarzami
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room currentRoom = rooms.get(i);
            Room nextRoom = rooms.get(i + 1);
            int startX = currentRoom.x + currentRoom.width / 2;
            int startY = currentRoom.y + currentRoom.height / 2;
            int endX = nextRoom.x + nextRoom.width / 2;
            int endY = nextRoom.y + nextRoom.height / 2;
            createHorizontalTunnel(startX, endX, startY);
            createVerticalTunnel(startY, endY, endX);
        }

        // Dodaj więcej losowych połączeń między pokojami
        for (int i = 0; i < rooms.size() / 2; i++) {
            Room room1 = rooms.get(random.nextInt(rooms.size()));
            Room room2 = rooms.get(random.nextInt(rooms.size()));
            connectRooms(room1, room2);
        }

        // Wybierz losowy pokój jako wyjście
        exitRoom = rooms.get(random.nextInt(rooms.size()));
        map[exitRoom.y + exitRoom.height / 2][exitRoom.x + exitRoom.width / 2] = 'E';
    }

    private void createHorizontalTunnel(int startX, int endX, int y) {
        int minX = Math.min(startX, endX);
        int maxX = Math.max(startX, endX);
        for (int x = minX; x <= maxX; x++) {
            map[y][x] = '.';
        }
    }

    private void createVerticalTunnel(int startY, int endY, int x) {
        int minY = Math.min(startY, endY);
        int maxY = Math.max(startY, endY);
        for (int y = minY; y <= maxY; y++) {
            map[y][x] = '.';
        }
    }

    private boolean isRoomValid(Room room) {
        for (int i = room.y - 1; i < room.y + room.height + 1; i++) {
            for (int j = room.x - 1; j < room.x + room.width + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[0].length) {
                    if (map[i][j] == '.') {
                        return false; // Pokój nachodzi na inny pokój
                    }
                }
            }
        }
        return true;
    }

    private void connectRooms(Room room1, Room room2) {
        int startX = room1.x + room1.width / 2;
        int startY = room1.y + room1.height / 2;
        int endX = room2.x + room2.width / 2;
        int endY = room2.y + room2.height / 2;
        createHorizontalTunnel(startX, endX, startY);
        createVerticalTunnel(startY, endY, endX);
    }

    public void printMap() {
        for (char[] row : map) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Floor floor = new Floor(50, 20); // Rozmiar mapy
        floor.generateMap();
        floor.printMap();
    }
}