import java.awt.Image;

import javax.swing.ImageIcon;

public class Obstacle {
    private int x, y;
    private Image image;

    public Obstacle(int startX, int startY) {
        x = startX;
        y = startY;
        loadImage();
    }

    private void loadImage() {
        ImageIcon ii = new ImageIcon("path_to_your_obstacle_image_50x50.png");
        image = ii.getImage();
    }

    public void move() {
        y += 5; // Move obstacle down
        if (y > 500) { // Reset when it goes off-screen
            y = 0;
            x = (int) (Math.random() * 300); // Random new position
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

}
    

