public class Hero {
    public int x;
    public int y;

    public Hero(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public void moveUp() {
        y--;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }
}
