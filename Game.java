package projectgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements ActionListener {
    //ลิงค์หน้าต่างๆที่เกี่ยวข้อง
    JPanel a = new JPanel();
    static win win = new win();
    static gameover over = new gameover();
    static homegames home = new homegames();
    private static final long serialVersionUID = -8715353373678321308L;
    private final Point[][][] Tetraminos = {
        // I-Piece
        {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
        },
        // J-Piece
        {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0)}
        },
        // L-Piece
        {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0)}
        },
        // O-Piece
        {
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}
        },
        // S-Piece
        {
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)}
        },
        // T-Piece
        {
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)}
        },
        // Z-Piece
        {
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)},
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)}
        }
    };
    //สี
   private final Color[] tetraminoColors = {
        Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
    };

    private Point pieceOrigin;
    private int currentPiece;
    private int rotation;
    private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    long score;
    private Color[][] well;

    // สร้างขอบรอบและเริ่มหล่นตัวแรก
    // ตำแหน่งและสุ่มลงตัวใหม่
    public void newPiece() {
        pieceOrigin = new Point(5, 2);
        rotation = 0;
        if (nextPieces.isEmpty()) {
            Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
            Collections.shuffle(nextPieces);
        }
        currentPiece = nextPieces.get(0);
        nextPieces.remove(0);
    }

    // เช็คการชนกันของตัวต่อ
    private boolean collidesAt(int x, int y, int rotation) {
        for (Point p : Tetraminos[currentPiece][rotation]) {
            if (well[p.x + x][p.y + y] != Color.BLACK) {
                return true;
            }
        }
        return false;
    }

    //หมุนตามทวน
    public void rotate(int i) {
        int newRotation = (rotation + i) % 4;
        if (newRotation < 0) {
            newRotation = 3;
        }
        if (!collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) {
            rotation = newRotation;
        }
        repaint();
    }

    // ขยับซ้ายขวา
    public void move(int i) {
        if (!collidesAt(pieceOrigin.x + i, pieceOrigin.y, rotation)) {
            pieceOrigin.x += i;
        }
        repaint();
    }

    // เมื่อแถวหายให้เลื่อนลงมาชนกัน
    public void dropDown() {
        if (!collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotation)) {
            pieceOrigin.y += 1;
        } else {
            fixToWell();
        }
        repaint();
    }

    // ตรวจจับการชน
    public void fixToWell() {
        for (Point p : Tetraminos[currentPiece][rotation]) {
            well[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = tetraminoColors[currentPiece];

        }
        clearRows();
        newPiece();
    }
    //ลบแถวเมื่อเต็ม
    public void deleteRow(int row) {
        for (int j = row - 1; j > 0; j--) {
            for (int i = 1; i < 11; i++) {
                well[i][j + 1] = well[i][j];
            }
        }
    }

    // ลบแถวและบวกคะแนนตามเคส
    public void clearRows() {
        boolean gap;
        int numClears = 0;

        for (int j = 21; j > 0; j--) {
            gap = false;
            for (int i = 1; i < 11; i++) {
                if (well[i][j] == Color.BLACK) {
                    gap = true;
                    break;
                }
            }
            if (!gap) {
                deleteRow(j);
                j += 1;
                numClears += 1;
            }
        }

        switch (numClears) {
            case 1:
                score += 50;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
        }

    }

    // เช็คแพ้
    private void drawPiece(Graphics g) {
        g.setColor(tetraminoColors[currentPiece]);
        for (Point p : Tetraminos[currentPiece][rotation]) {
            if ((p.y + pieceOrigin.y) * 26 == 78 && nextPieces.isEmpty()) {
                System.out.println("4");
                this.setLayout(null);
                this.add(over);
                over.setBounds(0, 0, 12 * 26 + 10, 26 * 23 + 25);
            }

            g.fillRect((p.x + pieceOrigin.x) * 26,
                    (p.y + pieceOrigin.y) * 26,
                    25, 25);

        }
    }

    Game() {
        //สีขอบและพื้นหลัง
        well = new Color[12][24];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                if (i == 0 || i == 11 || j == 22) {
                    well[i][j] = Color.GRAY;
                } else {
                    well[i][j] = Color.BLACK;
                }
            }
        }
        newPiece();
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            //การเคลื่อนที่
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        rotate(-1);
                        break;
                    case KeyEvent.VK_DOWN:
                        rotate(+1);
                        break;
                    case KeyEvent.VK_LEFT:
                        move(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        move(+1);
                        break;
                    case KeyEvent.VK_SPACE:
                        dropDown();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        //ให้ตัวต่อลงช้าทุกวิ
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        dropDown();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        //วาดพื้นหลัง
        g.fillRect(0, 0, 26 * 12, 26 * 23);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                g.setColor(well[i][j]);
                g.fillRect(26 * i, 26 * j, 25, 25);
            }
        }
        //เช็คชนะ
        if (this.score == 100) {
            this.setLayout(null);
            this.add(win);
            win.setBounds(0, 0, 12 * 26 + 10, 26 * 23 + 25);
        }

        // คะแนน
        g.setColor(Color.WHITE);
        g.drawString("" + score, 19 * 12, 25);

        //สุ่มวาดตัวต่อไป
        drawPiece(g);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
