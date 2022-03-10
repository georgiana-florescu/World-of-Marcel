import javax.swing.*;
import java.awt.*;

public class FinishPanel extends JPanel {
    JLabel background5;
    JLabel finishText;
    JLabel experience, level, coins, enemies;
    JLabel story;

    public FinishPanel(String s, int lvl, int xp, int c, int eNum) {
        Font font = new Font("Perpetua", Font.PLAIN, 40);
        ImageIcon img = new ImageIcon("resources/final.png");
        background5 = new JLabel(img);
        story = new JLabel(s);
        background5.setBounds(0, 0, 1200, 672);
        finishText = new JLabel("CONGRATULATIONS!", SwingConstants.CENTER);
        experience = new JLabel("XP " + xp);
        level = new JLabel("LEVEL " + lvl);
        coins = new JLabel("COINS " + c);
        enemies = new JLabel("ENEMIES " + eNum);
        finishText.setBounds(300, 100, 600, 50);
        finishText.setFont(new Font("Perpetua", Font.PLAIN, 50));
        story.setBounds(500, 250, 800, 40);
        story.setFont(new Font("Perpetua", Font.PLAIN, 25));
        level.setBounds(700, 350, 200, 30);
        experience.setBounds(950, 350, 200, 30);
        coins.setBounds(700, 400, 200, 30);
        enemies.setBounds(950, 400, 200, 30);
        finishText.setForeground(Color.WHITE);
        story.setForeground(Color.WHITE);
        experience.setForeground(Color.WHITE);
        level.setForeground(Color.WHITE);
        coins.setForeground(Color.WHITE);
        enemies.setForeground(Color.WHITE);
        level.setFont(font);
        experience.setFont(font);
        coins.setFont(font);
        enemies.setFont(font);
        add(background5);
        background5.add(finishText);
        background5.add(story);
        background5.add(level);
        background5.add(experience);
        background5.add(coins);
        background5.add(enemies);
    }
}
