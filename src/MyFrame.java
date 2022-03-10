import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    Container container;
    JPanel loginPanel;
    JPanel characterPanel;
    JPanel game;
    JPanel fightPanel;
    JPanel finishPanel;
    JPanel shopPanel;
    JPanel lostPanel;
    JPanel signUpPanel;
    static MyFrame myFrame = null;

    private MyFrame() {
        super ("World of Marcel");
        container = getContentPane();
        container.setLayout(null);
        setSize(1200, 672);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon("resources/icon.png");
        setIconImage(imageIcon.getImage());

        loginPanel = new LoginPanel();

        loginPanel.setBounds(0, 0, 1200, 672);
        add(loginPanel);
        this.setVisible(true);
        show();
    }

    static public MyFrame getInstance() {
        if (myFrame == null) {
            myFrame = new MyFrame();
        }
        return myFrame;
    }

    public static void main(String[] args) {
        MyFrame myFrame = getInstance();
    }
}
