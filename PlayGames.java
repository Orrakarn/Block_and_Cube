package projectgame;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class PlayGames extends JFrame implements ActionListener {
    //กำหนดตัวแปรต่างๆ ลิงค์หน้าและขนาดของframe
    public static homegames homegames1 = new homegames();
    public static Game state1 = new Game();
    static JFrame f = new PlayGames();
    public static win win = new win();
    public static gameover over = new gameover();
    //ให้ไปหน้าhomeเป็นหน้าแรก
    PlayGames() {
        this.add(homegames1);
        homegames1.BStart.addActionListener(this);
    }
    //eventเมื่อกดstartให้ไปหน้าgame
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homegames1.BStart) {
            this.setLocationRelativeTo(null);
            this.remove(homegames1);
            this.add(state1);
            state1.requestFocusInWindow();
        }
        this.validate();
        this.repaint();
    }
    //หัวข้อและขนาดframe
    public static void main(String[] args) {
        f.setTitle("Block and Cube");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(12 * 26 + 10, 26 * 23 + 25);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

}
