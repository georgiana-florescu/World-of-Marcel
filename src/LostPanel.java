import javax.swing.*;
import java.awt.*;

public class LostPanel extends JPanel {
    JLabel background7;
    JLabel text;
    public LostPanel() {
        ImageIcon img = new ImageIcon("resources/lost_game.jpg");
        background7 = new JLabel(img);
        text = new JLabel("YOU LOST!");
        background7.setBounds(0, 0, 1200, 672);
        text.setForeground(Color.WHITE);
        text.setBounds(370, 280, 500, 80);
        text.setFont(new Font("Perpetua", Font.PLAIN, 70));
        text.setHorizontalAlignment(SwingConstants.CENTER);
        add(background7);
        background7.add(text);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1200, 672);
        frame.add(new LostPanel());
        frame.show();
    }
}
