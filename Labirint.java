import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Labirint extends JFrame {
    final String TITLE_OF_GAME = "Labirint";
    final int LOCATION = 200;
    final int WH = 20;
    final int SIZE_CELL = 20;
    final int[][] cell = {{0,0,0,1,0,0,0,1,0,0,0,0,1,0,0},
                          {1,1,0,1,0,1,0,1,0,1,1,0,0,0,1},
                          {0,0,0,0,0,1,0,1,0,1,0,0,1,0,1},
                          {0,1,1,1,1,1,0,1,0,1,1,1,1,0,1},
                          {0,0,0,0,1,0,0,1,0,0,0,0,1,0,1},
                          {0,1,0,1,1,1,1,1,0,1,1,0,1,0,1},
                          {0,1,0,0,0,0,0,0,0,0,1,0,0,0,0},
                          {1,1,0,1,1,0,1,1,1,1,1,0,1,1,1},
                          {0,0,0,0,1,0,0,0,0,0,1,1,1,0,0},
                          {1,0,0,0,1,1,1,1,1,0,0,0,1,0,1},
                          {1,0,1,0,0,0,1,0,1,1,1,0,0,0,0},
                          {1,0,1,1,1,1,1,0,0,0,1,1,1,1,0},
                          {0,0,0,0,0,0,0,0,1,0,0,0,0,1,0},
                          {0,1,0,1,1,1,1,0,1,1,1,1,0,1,0},
                          {0,1,0,0,0,0,1,0,0,0,0,1,0,1,0},};

    final int[][] cellWin = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                           {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                           {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                           {0,1,0,0,0,1,0,0,1,0,0,1,0,1,0},
                           {0,0,1,0,1,0,0,1,0,1,0,1,0,1,0},
                           {0,0,0,1,0,0,0,1,0,1,0,1,0,1,0},
                           {0,0,0,1,0,0,0,1,0,1,0,1,0,1,0},
                           {0,0,0,1,0,0,0,0,1,0,0,0,1,0,0},
                           {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                           {0,0,1,0,0,0,1,0,1,0,1,0,0,1,0},
                           {0,0,1,0,1,0,1,0,1,0,1,1,0,1,0},
                           {0,0,1,0,1,0,1,0,1,0,1,0,1,1,0},
                           {0,0,0,1,0,1,0,0,1,0,1,0,0,1,0},
                           {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                           {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};


    public static void main(String[] args) {
        new Labirint();
    }
    Labirint() {
        setTitle(TITLE_OF_GAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(LOCATION, LOCATION);
        setResizable(false);
        add(new Paint());
        pack();
        add(new Paint());
        setVisible(true);
    }
    class Paint extends JPanel {
        private int xDelta = 0;
        private int yDelta = 0;
        private Timer repaintTimerX;
        private Timer repaintTimerY;
        private int xPos = 1;
        private int yPos = 1;

        public Paint() {
            InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
            ActionMap am = getActionMap();
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "pressed.left");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "released.left");
            am.put("pressed.left", new MoveActionX(-2, true));
            am.put("released.left", new MoveActionX(0, false));

            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "pressed.right");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "released.right");
            am.put("pressed.right", new MoveActionX(2, true));
            am.put("released.right", new MoveActionX(0, false));

            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "pressed.down");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "released.down");
            am.put("pressed.down", new MoveActionY(2, true));
            am.put("released.down", new MoveActionY(0, false));

            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "pressed.up");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "released.up");
            am.put("pressed.up", new MoveActionY(-2, true));
            am.put("released.up", new MoveActionY(0, false));
            repaintTimerX = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    xPos += xDelta;
                    if (xPos < 0) {
                        xPos = 0;
                    } else if (xPos + WH > getWidth()) {
                        xPos = getWidth() - WH;
                    }
                    if (xPos > 40 && yPos < 40 && xPos < 50) xPos = 40;
                    if (xPos < 40 && yPos > 0 && yPos < 40) xPos = 40;
                    if (xPos < 80 && yPos < 40 && xPos > 70) xPos = 80;
                    if (xPos > 80 && yPos > 0 && yPos < 60 && xPos < 90) xPos = 80;
                    if (xPos > 120 && yPos < 100 && xPos < 130) xPos = 120;
                    if (xPos < 120 && xPos > 110 && yPos > 0 && yPos < 80) xPos = 120;
                    if (xPos < 100 && xPos > 90 && yPos < 100 && yPos > 70) xPos = 100;
                    if (xPos > 0 && xPos < 10 && yPos > 40 && yPos < 80) xPos = 0;
                    if (xPos > 60 && xPos < 70 && yPos > 70 && yPos < 90) xPos = 60;
                    if (xPos > 0 && xPos < 10 && yPos > 80 && yPos < 140) xPos = 0;
                    if (xPos < 40 && xPos > 30 && yPos > 80 && yPos < 160) xPos = 40;
                    if (xPos > 40 && xPos < 50 && yPos > 80 && yPos < 120) xPos = 40;
                    if (xPos < 160 && xPos > 150 && yPos < 120) xPos = 160;
                    if (xPos > 160 && xPos < 170 && yPos > 0 && yPos < 80) xPos = 160;
                    if (xPos > 220 && xPos < 230 && yPos < 20) xPos = 220;
                    if (xPos > 260 && xPos < 270 && yPos > 0 && yPos < 120) xPos = 260;
                    if (xPos > 220 && xPos < 230 && yPos > 20 && yPos < 120) xPos = 220;
                    if (xPos < 220 && xPos > 210 && yPos > 0 && yPos < 40) xPos = 220;
                    if (xPos < 200 && xPos > 190 && yPos > 20 && yPos < 70) xPos = 200;
                    if (xPos < 260 && xPos > 250 && yPos < 20) xPos = 260;
                    if (xPos < 260 && xPos > 250 && yPos > 20 && yPos < 120) xPos = 260;
                    if (xPos < 220 && xPos > 210 && yPos > 80 && yPos < 150) xPos = 220;
                    if (xPos > 220 && xPos < 230 && yPos > 120 && yPos < 150) xPos = 220;
                    if (xPos > 180 && xPos < 190 && yPos > 110 && yPos < 130) xPos = 180;
                    if (xPos > 160 && xPos < 170 && yPos > 80 && yPos < 120) xPos = 160;
                    if (xPos > 40 && xPos < 50 && yPos > 120 && yPos < 160) xPos = 40;
                    if (xPos < 100 && xPos > 90 && yPos > 120 && yPos < 170) xPos = 100;
                    if (xPos > 100 && xPos < 110 && yPos > 120 && yPos < 160) xPos = 100;
                    if (xPos > 60 && xPos < 70 && yPos > 150 && yPos < 200) xPos = 60;
                    if (xPos < 280 && xPos > 270 && yPos > 200) xPos = 280;
                    if (xPos > 180 && xPos < 190 && yPos > 150 && yPos < 180) xPos = 180;
                    if (xPos > 220 && xPos < 230 && yPos > 170 && yPos < 200) xPos = 220;
                    if (xPos < 180 && xPos > 170 && yPos > 170 && yPos < 200) xPos = 180;
                    if (xPos < 220 && xPos > 210 && yPos > 180 && yPos < 210) xPos = 220;
                    if (xPos < 260 && xPos > 250 && yPos > 150 && yPos < 200) xPos = 260;
                    if (xPos > 260 && yPos > 160 && yPos < 200) xPos = 260;
                    if (xPos < 20 && yPos > 160 && yPos < 240) xPos = 20;
                    if (xPos > 20 && xPos < 30 && yPos > 180 && yPos < 240) xPos = 20;
                    if (xPos < 60 && xPos > 50 && yPos > 180 && yPos < 230) xPos = 60;
                    if (xPos > 100 && xPos < 110 && yPos > 190 && yPos < 220) xPos = 100;
                    if (xPos > 0 && xPos < 10 && yPos > 240) xPos = 0;
                    if (xPos < 40 && xPos > 30 && yPos > 240) xPos = 40;
                    if (xPos > 40 && xPos < 50 && yPos > 240 && yPos < 280) xPos = 40;
                    if (xPos > 100 && xPos < 110 && yPos > 270) xPos = 100;
                    if (xPos < 140 && xPos > 130 && yPos > 190 && yPos < 240) xPos = 140;
                    if (xPos < 140 && xPos > 130 && yPos > 240) xPos = 140;
                    if (xPos > 140 && xPos < 150 && yPos > 190 && yPos < 220) xPos = 140;
                    if (xPos > 140 && xPos < 150 && yPos > 220 && yPos < 280) xPos = 140;
                    if (xPos > 180 && xPos < 190 && yPos > 210 && yPos < 240) xPos = 180;
                    if (xPos < 180 && xPos > 170 && yPos > 220 && yPos < 270) xPos = 180;
                    if (xPos > 200 && xPos < 210 && yPos > 270) xPos = 200;
                    if (xPos < 240 && xPos > 230 && yPos > 240) xPos = 240;
                    if (xPos > 240 && xPos < 250 && yPos > 230) xPos = 240;
                    repaint();
                }
            });
            repaintTimerY = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    yPos += yDelta;
                    if (yPos < 0) {
                        yPos = 0;
                    } else if (yPos + WH > getHeight()) {
                        yPos = getHeight() - WH;
                    }
                    if (yPos > 0 && xPos < 40 && yPos < 30) yPos = 0;
                    if (yPos > 40 && 0 < xPos && xPos < 110 && yPos < 50) yPos = 40;
                    if (yPos < 40 && xPos < 40 && yPos > 20) yPos = 40;
                    if (yPos < 40 && xPos > 40 && xPos < 80) yPos = 40;
                    if (yPos > 0 && yPos < 10 && xPos > 80 && xPos < 120) yPos = 0;
                    if (yPos > 80 && yPos < 90 && xPos > 40 && xPos < 130) yPos = 80;
                    if (yPos < 80 && yPos > 70 && xPos > 90 && xPos < 120) yPos = 80;
                    if (yPos < 80 && yPos > 70 && xPos > 0 && xPos < 80) yPos = 80;
                    if (yPos > 120 && yPos < 130 && xPos < 10) yPos = 120;
                    if (yPos > 80 && yPos < 90 && xPos > 0 && xPos < 40) yPos = 80;
                    if (yPos < 120 && yPos > 110 && xPos > 40 && xPos < 160) yPos = 120;
                    if (yPos > 120 && yPos < 130 && xPos > 40 && xPos < 100) yPos = 120;
                    if (yPos > 0 && yPos < 10 && xPos > 160 && xPos < 220) yPos = 0;
                    if (yPos < 80 && yPos > 70 && xPos > 160 && xPos < 230) yPos = 80;
                    if (yPos < 20 && xPos > 220 && xPos < 260) yPos = 20;
                    if (yPos > 40 && yPos < 50 && xPos > 190 && xPos < 240) yPos = 40;
                    if (yPos < 40 && yPos > 30 && xPos > 190 && xPos < 220) yPos = 40;
                    if (yPos > 20 && yPos < 30 && xPos > 220 && xPos < 260) yPos = 20;
                    if (yPos > 0 && yPos < 10 && xPos > 260) yPos = 0;
                    if (yPos < 120 && yPos > 110 && xPos > 220 && xPos < 260) yPos = 120;
                    if (yPos < 120 && yPos > 110 && xPos > 260 && xPos < 290) yPos = 120;
                    if (yPos > 120 && yPos < 130 && xPos > 220 && xPos < 290) yPos = 120;
                    if (yPos > 80 && yPos < 90 && xPos > 160 && xPos < 220) yPos = 80;
                    if (yPos > 120 && yPos < 130 && xPos > 100 && xPos < 190) yPos = 120;
                    if (yPos > 140 && yPos < 150 && xPos > 210 && xPos < 230) yPos = 140;
                    if (yPos < 120 && yPos > 110 && xPos > 160 && xPos < 190) yPos = 120;
                    if (yPos < 160 && yPos > 150 && xPos < 40) yPos = 160;
                    if (yPos < 160 && yPos > 150 && xPos > 40 && xPos < 70) yPos = 160;
                    if (yPos < 160 && yPos > 150 && xPos > 100 && xPos < 190) yPos = 160;
                    if (yPos < 160 && yPos > 150 && xPos > 220) yPos = 160;
                    if (yPos > 160 && yPos < 170 && xPos > 70 && xPos < 180) yPos = 160;
                    if (yPos > 180 && yPos < 190 && xPos > 170 && xPos < 220) yPos = 180;
                    if (yPos > 200 && yPos < 210 && xPos > 210 && xPos < 280) yPos = 200;
                    if (yPos < 180 && yPos > 170 && xPos > 180 && xPos < 230) yPos = 180;
                    if (yPos < 200 && yPos > 190 && xPos > 220 && xPos < 260) yPos = 200;
                    if (yPos > 160 && yPos < 170 && xPos > 260) yPos = 160;
                    if (yPos < 200 && yPos > 190 && xPos > 260) yPos = 200;
                    if (yPos > 160 && yPos < 170 && xPos < 20) yPos = 160;
                    if (yPos < 240 && yPos > 230 && xPos < 20) yPos = 240;
                    if (yPos > 180 && yPos < 190 && xPos > 20 && xPos < 60) yPos = 180;
                    if (yPos > 200 && yPos < 210 && xPos > 50 && xPos < 110) yPos = 200;
                    if (yPos < 200 && yPos > 190 && xPos > 60 && xPos < 160) yPos = 200;
                    if (yPos > 240 && yPos < 250 && xPos > 0 && xPos < 40) yPos = 240;
                    if (yPos < 240 && yPos > 230 && xPos > 20 && xPos < 140) yPos = 240;
                    if (yPos > 240 && yPos < 250 && xPos > 40 && xPos < 140) yPos = 240;
                    if (yPos < 280 && yPos > 270 && xPos > 40 && xPos < 130) yPos = 280;
                    if (yPos < 220 && yPos > 210 && xPos > 140 && xPos < 190) yPos = 220;
                    if (yPos > 220 && yPos < 230 && xPos > 140 && xPos < 180) yPos = 220;
                    if (yPos < 280 && yPos > 270 && xPos > 140 && xPos < 210) yPos = 280;
                    if (yPos > 240 && yPos < 250 && xPos > 170 && xPos < 240) yPos = 240;
                    if (yPos < 240 && yPos > 230 && xPos > 180 && xPos < 250) yPos = 240;
                    if (yPos > 275 && yPos < 280 && xPos > 230 && xPos < 250) {
                        yPos = 280;
                        JLabel label1 = new JLabel("Test");
                    }
                    repaint();
                }
            });
            repaintTimerX.setInitialDelay(0);
            repaintTimerX.setRepeats(true);
            repaintTimerX.setCoalesce(true);
            repaintTimerY.setInitialDelay(0);
            repaintTimerY.setRepeats(true);
            repaintTimerY.setCoalesce(true);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300, 300);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            draw1(g);
        }

        private void draw1(Graphics g) {
            Graphics2D paint1 = (Graphics2D) g;
            for (int x = 0; x < 300; x += SIZE_CELL) {
                paint1.setPaint(Color.black);
                paint1.drawLine(x, 0, x, 300);
            }
            for (int y = 0; y < 300; y += SIZE_CELL) {
                paint1.setPaint(Color.black);
                paint1.drawLine(0, y, 300, y);
            }
            Graphics2D paint2 = (Graphics2D) g;
            paint2.setPaint(Color.black);
            paint2.fillRoundRect(xPos, yPos, WH, WH, 0, 0);

            Graphics2D paint3 = (Graphics2D) g;
            paint3.setPaint(Color.green);
            paint3.fillRect(241, 281, 19, 19);

            Graphics2D paint4 = (Graphics2D) g;
            paint4.setPaint(Color.red);
            for (int i = 0; i < cell.length; i++) {
                for (int j = 0; j < cell[i].length; j++) {
                    if (cell[i][j] == 1) {
                        paint4.fillRect((j * 20) + 1, (i * 20) + 1, 19, 19);
                    }
                }
            }
            if (yPos == 280 && xPos == 240) {
            paint4.setPaint(Color.white);
            paint4.fillRect(0, 0, 300, 300);
            paint4.setPaint(Color.red);
            for (int i = 0; i < cell.length; i++) {
                for (int j = 0; j < cell[i].length; j++) {
                    if (cellWin[i][j] == 1) {
                        paint4.fillRect((j * 20) + 1, (i * 20) + 1, 19, 19);
                    }
                }
            }
        }
    }
        public class MoveActionX extends AbstractAction {

            private int direction;
            private boolean keyDown;

            public MoveActionX(int direction, boolean down) {
                this.direction = direction;
                keyDown = down;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                xDelta = direction;
                if (keyDown) {
                    if (!repaintTimerX.isRunning()) {
                        repaintTimerX.start();
                    }
                } else {
                    repaintTimerX.stop();

                    }
                }
            }
        public class MoveActionY extends AbstractAction {

            private int direction;
            private boolean keyDown;

            public MoveActionY(int direction, boolean down) {
                this.direction = direction;
                keyDown = down;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                yDelta = direction;
                if (keyDown) {
                    if (!repaintTimerY.isRunning()) {
                        repaintTimerY.start();
                    }
                } else {
                    repaintTimerY.stop();

                }
            }
          }
       }
    }