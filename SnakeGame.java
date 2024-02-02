import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends JFrame implements ActionListener, KeyListener {
    private static final int TILE_SIZE = 20;
    private static final int BOARD_SIZE = 20;
    private static final int DELAY = 150;

    private LinkedList<Point> snake;
    private Point food;
    private char direction;
    private Timer timer;

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        snake = new LinkedList<>();
        snake.add(new Point(BOARD_SIZE / 2, BOARD_SIZE / 2));
        generateFood();

        direction = 'R'; // Start moving right
        timer = new Timer(DELAY, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
    }

    public void generateFood() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(BOARD_SIZE);
            y = rand.nextInt(BOARD_SIZE);
        } while (snake.contains(new Point(x, y)));

        food = new Point(x, y);
    }

    public void move() {
        Point head = snake.getFirst();
        Point newHead;

        switch (direction) {
            case 'U':
                newHead = new Point(head.x, (head.y - 1 + BOARD_SIZE) % BOARD_SIZE);
                break;
            case 'D':
                newHead = new Point(head.x, (head.y + 1) % BOARD_SIZE);
                break;
            case 'L':
                newHead = new Point((head.x - 1 + BOARD_SIZE) % BOARD_SIZE, head.y);
                break;
            case 'R':
                newHead = new Point((head.x + 1) % BOARD_SIZE, head.y);
                break;
            default:
                return;
        }

        if (newHead.equals(food)) {
            snake.addFirst(newHead);
            generateFood();
        } else {
            snake.addFirst(newHead);
            snake.removeLast();
        }

        checkCollision();
        repaint();
    }

    public void checkCollision() {
        Point head = snake.getFirst();

        // Check collision with walls or itself
        if (snake.size() > 1 && snake.subList(1, snake.size()).contains(head)) {
            gameOver();
        }

        // Check collision with walls
        if (head.x < 0 || head.x >= BOARD_SIZE || head.y < 0 || head.y >= BOARD_SIZE) {
            gameOver();
        }
    }

    public void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw the snake
        for (Point point : snake) {
            g.setColor(Color.GREEN);
            g.fillRect(point.x * TILE_SIZE, point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw the food
        g.setColor(Color.RED);
        g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
            case KeyEvent.VK_LEFT:
                if (direction != 'R') {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') {
                    direction = 'R';
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SnakeGame().setVisible(true));
    }
}
