import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Car {
    private int x, y;
    private int dx; // speed change on the x-axis
    private int speed;
    private Image image;

    public Car() {
        loadImage();
        x = 200; // Starting position
        y = 300;
        speed = 40; // Set a speed for movement
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon("path_to_your_car_image_50x50.png");
        image = ii.getImage();
    }

    public void move() {
        x += dx; // Move car based on keyboard input
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -3;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 3;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0; // Stop moving when keys are released
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    // Add the increaseSpeed method
    public void increaseSpeed(int amount) {
        speed += amount;
    }

    Image getImage9() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}