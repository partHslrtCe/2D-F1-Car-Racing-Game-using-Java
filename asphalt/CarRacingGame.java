import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class CarRacingGame extends JFrame {     //Every function here is for game panel
    public CarRacingGame() {
        setTitle("F1 Racing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CarRacingGame::new);
    }
}

// GamePanel class that handles the game logic and rendering
class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private Car playerCar;
    private Obstacle[] obstacles;
    private int hitpoints = 50;
    private int lap = 1;
    private int speedIncreaseInterval = 10; // 5 seconds
    private int speedIncreaseAmount = 80; // Amount to increase speed
    private Image roadImage;
    private Image carImage;
    private Image obstacleImage;
    private int backgroundY = 100;

    public GamePanel() {
        setFocusable(true);
        setPreferredSize(new Dimension(400, 500));
        playerCar = new Car();

        obstacles = new Obstacle[1]; // Limit to 3 obstacles
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Obstacle(getRandomLane(), 0);
        }

        // Load images
        try {
            roadImage = ImageIO.read(new File("path_to_your_road_image.png"));
            carImage = ImageIO.read(new File("path_to_your_car_image_50x50.png")); 
            obstacleImage = ImageIO.read(new File("path_to_your_obstacle_image_50x50.png")); 
                                                                                            
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(10, this);
        timer.start();

        // Increase speed every 25 seconds
        new Timer(speedIncreaseInterval, e->playerCar.increaseSpeed(speedIncreaseAmount)).start();

        // Key listener for car movement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playerCar.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                playerCar.keyReleased(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawObjects(g);
        drawScore(g);
    }

    private void drawBackground(Graphics g) {
        backgroundY += 10;
        if (backgroundY > getHeight()) {
            backgroundY = 0;
        }

        g.drawImage(roadImage, 0, backgroundY - getHeight(), null);
        g.drawImage(roadImage, 0, backgroundY, null);
    }

    private void drawObjects(Graphics g) {
        g.drawImage(carImage, playerCar.getX(), playerCar.getY(), this);

        for (Obstacle obstacle : obstacles) {
            g.drawImage(obstacleImage, obstacle.getX(), obstacle.getY(), this);
        }

        g.setColor(Color.WHITE);
        g.drawString("Hitpoints: " + hitpoints, 10, 20);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("LAP: " +lap, 300, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateObjects();
        checkCollisions();
        repaint();
    }

    private void updateObjects() {
        playerCar.move();

        for (Obstacle obstacle : obstacles) {
            obstacle.move();
            if (obstacle.getY() > getHeight()) {
                obstacle.reset(getRandomLane()); // Reset obstacle if it goes off-screen
                if(playerCar.getY() > obstacle.getY()){
                    lap=lap++; // Increase lap for successfully avoiding an obstacle
                }
            }
        }
    }

    private void checkCollisions() {
        Rectangle playerBounds = new Rectangle(playerCar.getX(), playerCar.getY(), 50, 50);

        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleBounds = new Rectangle(obstacle.getX(), obstacle.getY(), 50, 50);

            if (playerBounds.intersects(obstacleBounds)) {
                hitpoints--; // Reduce hitpoints on collision
                if (hitpoints <=0 ) {
                    System.out.println("Game Over");
                    timer.stop();
                }
            }
        }
    }

    private int getRandomLane() {
        return (new Random().nextInt(2) * 150) + 100; // Randomly choose between two lanes
    }
}

//car class that represents the player's car
class Car {
    private int x = 350; // Initial position of the car
    private int y = 400; // Initial position of the car
    private int speed = 50;

    public void move() {
        // Implement car movement based on key input
    }

    public void increaseSpeed(int amount) {
        speed += amount; // Increase car speed
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && x > 300) { // Limit left movement to first lane
            x -= speed; // Move left
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && x < 300) { // Limit right movement to second lane
            x = speed; // Move right
        }
    }

    public void keyReleased(KeyEvent e) {
        // Handle key releases if necessary
    }

    public int getX() {
        return x; // Get current X position
    }

    public int getY() {
        return y; // Get current Y position
    }
}

// Obstacle class that represents the obstacles on the road
class Obstacle {
    private int x;
    private int y;
    private int speed = 1
    ;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += speed; // Move obstacle down the screen
    }

    public void reset(int newX) {
        y = 0; // Reset Y position
        x = newX; // Reset to new lane
    }

    public int getX() {
        return x; // Get current X position
    }

    public int getY() {
        return y; // Get current Y position
    }
}
