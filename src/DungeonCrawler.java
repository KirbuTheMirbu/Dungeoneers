import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DungeonCrawler extends JFrame implements KeyListener {
    private Floor floor;
    private int floorWidth;
    private int floorHeight;
    private int numRooms;
    private final int MAX_WIDTH = 95;
    private final int MAX_HEIGHT = 50;

    public DungeonCrawler(int initialWidth, int initialHeight) {
        this.floorWidth = initialWidth;
        this.floorHeight = initialHeight;
        this.numRooms = 10;
        this.floor = new Floor(floorWidth, floorHeight);
        this.floor.generateMap(numRooms);

        this.setTitle("Dungeon Crawler");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);

        MapPanel mapPanel = new MapPanel(floor);
        this.add(mapPanel);
        this.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                floor.moveHero(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                floor.moveHero(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                floor.moveHero(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                floor.moveHero(1, 0);
                break;
        }
        repaint();
        if (floor.isExitReached()) {
            JOptionPane.showMessageDialog(this, "You found the exit! Moving to the next floor!");
            nextFloor();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    private void nextFloor() {
        if (floorWidth < MAX_WIDTH) {
            floorWidth += 5;
        }
        if (floorHeight < MAX_HEIGHT) {
            floorHeight += 2;
        }
        numRooms += 2; // Zwiększ liczbę pokoi
        floor = new Floor(floorWidth, floorHeight);
        floor.generateMap(numRooms);
        this.getContentPane().removeAll(); // Usuń wszystkie komponenty
        this.add(new MapPanel(floor)); // Dodaj nowy MapPanel
        this.revalidate(); // Przebuduj ramkę, aby odświeżyć komponenty
        this.repaint(); // Odśwież ramkę
    }

    public static void main(String[] args) {
        new DungeonCrawler(50, 20);
    }
}

class MapPanel extends JPanel {
    private Floor floor;

    public MapPanel(Floor floor) {
        this.floor = floor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        char[][] map = floor.getMap();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '#') {
                    g.setColor(Color.BLACK);
                } else if (map[y][x] == '.') {
                    g.setColor(Color.WHITE);
                } else if (map[y][x] == 'H') {
                    g.setColor(Color.BLUE);
                } else if (map[y][x] == 'E') {
                    g.setColor(Color.RED);
                }
                g.fillRect(x * 20, y * 20, 20, 20);
            }
        }
    }
}
