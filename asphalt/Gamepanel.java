import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.Exception;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gamepanel extends JPanel implements ActionListener {
    private Timer timer;
    private Car playerCar;
    private Obstacle[] obstacles;
    private int hitpoints = 3;

    public Gamepanel() {
        setFocusable(true);
        setPreferredSize(new Dimension(400, 500));
        playerCar = new Car();
        obstacles = new Obstacle[5]; // 5 random obstacles

        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Obstacle((int) (Math.random() * 300), (int) (Math.random() * 300));
        }

        timer = new Timer(10, this);
        timer.start();

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
        drawObjects(g);
    }

    private void drawObjects(Graphics g) {
        g.drawImage(playerCar.getImage(), playerCar.getX(), playerCar.getY(), this);

        for (Obstacle obstacle : obstacles) {
            g.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(), this);
        }

        // Draw hitpoints
        g.setColor(Color.BLACK);
        g.drawString("Hitpoints: " + hitpoints, 10, 20);
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
        }
    }

    private void checkCollisions() {
        Rectangle playerBounds = new Rectangle(playerCar.getX(), playerCar.getY(), 50, 50);

        for (Obstacle obstacle : obstacles) {
            Rectangle obstacleBounds = new Rectangle(obstacle.getX(), obstacle.getY(), 50, 50);

            if (playerBounds.intersects(obstacleBounds)) {
                hitpoints--; // Reduce hitpoints on collision
                if (hitpoints <= 0) {
                    Graphics g;
                    // End game if hitpoints reach 0
                    g.setColor(Color.WHITE);
                    g.drawString("GAME OVER " +hitpoints ,100, 200);
                    timer.stop();
                }
            }
        }
    }
}
