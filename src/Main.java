import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame implements KeyListener {
    private Floor floor;
    private int floorWidth;
    private int floorHeight;
    private int numRooms;
    private final int MAX_WIDTH = 100;
    private final int MAX_HEIGHT = 50;

    public Main(int initialWidth, int initialHeight) {
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
        floor.generateMap(numRooms); // Upewnij się, że przekazujesz numRooms
        this.getContentPane().removeAll(); // Usuń wszystkie komponenty
        this.add(new MapPanel(floor)); // Dodaj nowy MapPanel
        this.revalidate(); // Przebuduj ramkę, aby odświeżyć komponenty
        this.repaint(); // Odśwież ramkę
    }

    public static void main(String[] args) {
        new Main(50, 20);
    }
}



