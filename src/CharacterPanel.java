import javax.swing.*;
import java.awt.*;

public class CharacterPanel extends JPanel {
    JLabel background2;
    JLabel title;
    JButton next_button;
    JButton ok_button;
    JTextArea character;
    int index = 0;
    Game game;

    CharacterPanel() {
        next_button = new JButton("Next");
        ok_button = new JButton("Ok");
        character = new JTextArea();
        title = new JLabel("CHOOSE YOUR CHARACTER");
        game = Game.getInstance();

        ImageIcon img = new ImageIcon("resources/fundal2.png");
        background2 = new JLabel(img);
        background2.setBounds(0, 0, 1200, 672);

        setSizeAndPosition();
        setFontAndColor();

        add(background2);
        setCharacterText();

        background2.add(next_button);
        background2.add(ok_button);
        background2.add(character);
        background2.add(title);

        next_button.addActionListener(e -> {
            if (index == game.my_account.characters.size()) {
                index = 0;
            }
            setCharacterText();
        });

        ok_button.addActionListener(e -> {
            MyFrame frame = MyFrame.getInstance();
            frame.characterPanel.setVisible(false);
            frame.game = new GamePanel();
            frame.game.setBounds(0, 0,1200, 672);
            frame.add(frame.game);
        });
    }

    public void setSizeAndPosition() {
        title.setBounds(40, 100, 550, 50);
        next_button.setBounds(300, 550, 160, 50);
        ok_button.setBounds(40, 550, 160, 50);
        character.setBounds(40, 200, 400, 300);
    }

    public void setFontAndColor() {
        Font font = new Font("Perpetua", Font.PLAIN, 26);
        title.setFont(new Font("Perpetua", Font.PLAIN, 40));
        title.setForeground(new Color(220, 220, 220));
        next_button.setFont(font);
        next_button.setBackground(new Color(255, 215, 0));
        next_button.setForeground(Color.BLACK);
        ok_button.setFont(font);
        ok_button.setBackground(new Color(255, 215, 0));
        ok_button.setForeground(Color.BLACK);
        character.setBorder(null);
        character.setOpaque(false);
        character.setForeground(new Color(220, 220, 220));
        character.setFont(new Font("PERPETUA TITLING MT", Font.PLAIN, 28));
    }

    public void setCharacterText() {
        String s = "";
        Character c = game.my_account.characters.get(index);
        s += c.getClass().getSimpleName() +  "\n\n";
        s += c.name + "\n\n";
        s += "LEVEL " + c.level + "\n\n";
        s += "EXPERIENCE " + c.experience;
        character.setText(s);
        game.grid.my_character = game.my_account.characters.get(index);
        index++;
    }
}
