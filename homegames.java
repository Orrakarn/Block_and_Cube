package projectgame;

import java.applet.Applet;
import java.awt.*;
import javax.swing.*;

public class homegames extends JPanel {
    //กำหนดรูปพื้นหลังและรูปปุ่ม
    private ImageIcon feild = new ImageIcon(this.getClass().getResource("home.png"));
    private ImageIcon starts = new ImageIcon(this.getClass().getResource("start.png"));
    public JButton BStart = new JButton(starts);
    //ตำแหน่งปุ่ม
    homegames() {
        setLayout(null);
        BStart.setBounds(70, 310, 170, 70);
        add(BStart);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(feild.getImage(), 0, 0, 12 * 26 + 10, 26 * 23 + 25, this);
    }
}
