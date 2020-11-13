package projectgame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static projectgame.Game.win;
import static projectgame.PlayGames.state1;
import javax.swing.*;

public class win extends JPanel implements ActionListener {
    //กำหนดรูปและปุ่ม
    Game state1 = new Game();
    private ImageIcon feild = new ImageIcon(this.getClass().getResource("win.png"));
    private ImageIcon starts = new ImageIcon(this.getClass().getResource("restart.png"));
    private ImageIcon exitover = new ImageIcon(this.getClass().getResource("exit.png"));
    public JButton BStart = new JButton(starts);
    public JButton BExitover = new JButton(exitover);
    //ขนาดปุ่มและตำแหน่ง
    win() {
        this.setLayout(null);
        BExitover.setBounds(70, 350, 160, 70);
        add(BExitover);
        add(BStart);
        BStart.addActionListener(this);
        BExitover.addActionListener(this);
        BStart.setBounds(70, 240, 160, 70);
    }
    //eventเมื่อกดปุ่มต่างๆ
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BStart) {
            this.remove(BStart);
            this.remove(BExitover);
            this.add(state1);
            this.setLayout(null);
            state1.setBounds(0, 0, 12 * 26 + 10, 26 * 23 + 25);
            state1.requestFocusInWindow();
        } else if (e.getSource() == BExitover) {
            System.exit(0);
        }
        this.validate();
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(feild.getImage(), 0, 0, 12 * 26 + 10, 26 * 23 + 25, this);
    }
}
