import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Floor floor = new Floor(50, 20); // Rozmiar mapy
        floor.generateMap();
        floor.printMap();
    }
}