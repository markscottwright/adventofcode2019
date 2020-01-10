package com.markscottwright.adventofcode2019;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.markscottwright.adventofcode2019.intcode.IntcodeComputer;
import com.markscottwright.adventofcode2019.intcode.IntcodeComputer.IntcodeException;
import com.markscottwright.adventofcode2019.intcode.IntcodeOutput;

/**
 * This was entertaining! But ultimately no reason to do this visibly.
 */
public class Day13GameDisplay extends JFrame
        implements IntcodeOutput, Iterator<Long>, ActionListener {

    private static final int TICK_MILLISECONDS = 1;

    /**
     * Inner swing panel responsible for drawing the game screen
     */
    public class GameScreen extends JPanel {
        private static final int BLOCK_WIDTH = 20;
        private static final int BLOCK_HEIGHT = 20;
        private int[][] display = new int[1000][1000];
        private int width = 0;
        private int height = 0;
        private Color[] colors = { Color.BLACK, Color.WHITE, Color.BLUE,
                Color.RED, Color.WHITE };

        public void set(int x, int y, int tileType) {
            this.display[x][y] = tileType;

            // remember the largest value seen to resize the display
            if (tileType != 0) {
                width = Math.max(width, x + 1);
                height = Math.max(height, y + 1);
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    g.setColor(colors[display[x][y]]);
                    if (display[x][y] == 4) {
                        g.setColor(colors[0]);
                        g.fillRect(x * BLOCK_WIDTH, y * BLOCK_HEIGHT,
                                BLOCK_WIDTH, BLOCK_HEIGHT);
                        g.setColor(colors[display[x][y]]);
                        g.fillOval(x * BLOCK_WIDTH, y * BLOCK_HEIGHT,
                                BLOCK_WIDTH / 2, BLOCK_HEIGHT / 2);
                    } else if (display[x][y] == 3) {
                        g.setColor(colors[0]);
                        g.fillRect(x * BLOCK_WIDTH, y * BLOCK_HEIGHT,
                                BLOCK_WIDTH, BLOCK_HEIGHT);
                        g.setColor(colors[display[x][y]]);
                        g.fillRect(x * BLOCK_WIDTH, y * BLOCK_HEIGHT,
                                BLOCK_WIDTH, BLOCK_HEIGHT / 10);
                    } else
                        g.fillRect(x * BLOCK_WIDTH, y * BLOCK_HEIGHT,
                                BLOCK_WIDTH, BLOCK_HEIGHT);
                }
            }
        }

        int getWidthInPixels() {
            return width * BLOCK_WIDTH;
        }

        int getHeightInPixels() {
            return height * BLOCK_HEIGHT;
        }
    }

    public Day13GameDisplay(List<Long> intcodeInstructions) {
        try {
            gameScreen = new GameScreen();
            getContentPane().add(gameScreen);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameLogic = new IntcodeComputer(intcodeInstructions, this, this);
            gameLogic.setPauseWhenInputEmpty(true);
            gameLogic.runUntilInputEmpty();
            gameScreen.setPreferredSize(
                    new Dimension(gameScreen.getWidthInPixels(),
                            gameScreen.getHeightInPixels()));
            pack();
            gameTimer = new Timer(TICK_MILLISECONDS, this);
            gameTimer.setInitialDelay(0);
            gameTimer.start();
        } catch (IntcodeException e) {
        }
    }

    // this state is used to hold intermediate IntcodeComputer output, since it
    // comes in tripples of x,y,tile-id
    enum State {
        WAITING_FOR_X, WAITING_FOR_Y, WAITING_FOR_TILE_ID
    };

    State state = State.WAITING_FOR_X;
    long x = 0;
    long y = 0;

    Long joystickInput = null;
    private GameScreen gameScreen;
    private IntcodeComputer gameLogic;
    int ballX = 0;
    int paddleX = 0;
    private int screenshotNum = 0;
    private ArrayList<BufferedImage> screenshots = new ArrayList<>();
    private Timer gameTimer;

    /**
     * Process output from the gameLogic IntcodeComputer
     */
    @Override
    public void put(long aVal) {
        switch (state) {
        case WAITING_FOR_TILE_ID: {
            if (x >= 0 && y >= 0) {
                if (aVal == 3)
                    paddleX = (int) x;
                else if (aVal == 4)
                    ballX = (int) x;
                gameScreen.set((int) x, (int) y, (int) aVal);
            } else {
                System.out.println("Score = " + aVal);
                setTitle("Score = " + aVal);
            }

            if (aVal == 3)
                paddleX = (int) x;
            else if (aVal == 4)
                ballX = (int) x;

            state = State.WAITING_FOR_X;
            break;
        }
        case WAITING_FOR_X: {
            x = aVal;
            state = State.WAITING_FOR_Y;
            break;
        }
        case WAITING_FOR_Y: {
            y = aVal;
            state = State.WAITING_FOR_TILE_ID;
            break;
        }
        }
    }

    @Override
    public boolean hasNext() {
        return joystickInput != null;
    }

    @Override
    public Long next() {
        Long out = joystickInput;
        joystickInput = null;
        return out;
    }

    /**
     * Our game tick
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (ballX < paddleX)
                joystickInput = -1L;
            else if (ballX > paddleX)
                joystickInput = 1L;
            else
                joystickInput = 0L;
            boolean done = (gameLogic
                    .runUntilInputEmpty() == IntcodeComputer.State.halted);
            gameScreen.paintImmediately(gameScreen.getX(), gameScreen.getY(),
                    gameScreen.getWidth(), gameScreen.getHeight());
            screenshot(screenshotNum++);

            if (done) {
                gameTimer.stop();
                writeAnimatedGif("day13.gif");
            }
        } catch (IntcodeException e1) {
        }
    }

    private void writeAnimatedGif(String string) {
        try (FileImageOutputStream output = new FileImageOutputStream(
                new File("day13.gif"));) {
            GifSequenceWriter writer = new GifSequenceWriter(output,
                    screenshots.get(0).getType(), 1, false);
            int i = 0;
            for (var screenshot : screenshots) {
                System.out.println(i + "/" + screenshots.size());
                writer.writeToSequence(screenshot);
                i++;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void screenshot(int i) {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        paint(img.getGraphics());
        screenshots.add(img);
    }

    public static void main(String[] args) {
        var instructions = IntcodeComputer.parse(Day13.INPUT);
        var instructions2 = new ArrayList<Long>(instructions);
        instructions2.set(0, 2L); // infinite plays
        var display = new Day13GameDisplay(instructions2);
        display.setVisible(true);
    }
}
