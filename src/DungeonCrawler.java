import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DungeonCrawler extends JFrame implements KeyListener {
    private Floor floor;
    private int floorWidth;
    private int floorHeight;

    public DungeonCrawler(int initialWidth, int initialHeight) {
        this.floorWidth = initialWidth;
        this.floorHeight = initialHeight;
        this.floor = new Floor(floorWidth, floorHeight);
        this.floor.generateMap();

        this.setTitle("Dungeon Crawler");
        this.setSize(1015, 435);
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
        floorWidth += 10;
        floorHeight += 5;
        floor = new Floor(floorWidth, floorHeight);
        floor.generateMap();
        this.getContentPane().removeAll(); // Remove all components
        this.add(new MapPanel(floor)); // Add new MapPanel
        this.revalidate(); // Revalidate the frame to refresh components
        this.repaint(); // Repaint the frame
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
