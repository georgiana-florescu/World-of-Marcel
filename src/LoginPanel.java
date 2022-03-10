import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel implements ActionListener {
    JLabel background1;
    JTextField username;
    JTextField error;
    JPasswordField password;
    JButton login_button;
    JButton sign_up_button;
    Game game;

    LoginPanel() {
        username = new JTextField("Username", 20);
        password = new JPasswordField(" ***********", 20);
        password.setEchoChar('*');
        login_button = new JButton("SIGN IN");
        error = new JTextField("The email and password combination is incorrect.");
        sign_up_button = new JButton("SIGN UP");

        ImageIcon img = new ImageIcon("resources/background1.png");
        background1 = new JLabel(img);
        background1.setBounds(0, 0, 1200, 672);

        setSizeAndPosition();
        setFontAndColor();
        error.setVisible(false);

        add(background1);

        background1.add(username);
        background1.add(password);
        background1.add(login_button);
        background1.add(error);
        background1.add(sign_up_button);

        login_button.addActionListener(this);
        username.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                username.setText("");
            }
        });
        password.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                password.setText("");
            }
        });
        sign_up_button.addActionListener(e -> {
            this.setVisible(false);
            MyFrame frame = MyFrame.getInstance();
            frame.signUpPanel = new SignUpPanel();
            frame.signUpPanel.setBounds(0, 0, 1200, 672);
            frame.add(frame.signUpPanel);
        });
    }

    public void setSizeAndPosition() {
        username.setBounds(870, 287, 220, 30);
        password.setBounds(870, 380, 220, 30);
        login_button.setBounds(810, 450, 170, 50);
        sign_up_button.setBounds(1002, 450, 170, 50);
        error.setBounds(810, 500, 334, 100);
    }

    public void setFontAndColor() {
        Font font = new Font("Perpetua", Font.PLAIN, 26);
        username.setFont(font);
        username.setForeground(new Color(99, 99, 114));
        username.setBorder(null);
        username.setOpaque(false);
        password.setFont(font);
        password.setOpaque(false);
        password.setForeground(new Color(99, 99, 114));
        password.setBorder(null);
        login_button.setFont(font);
        login_button.setBackground(new Color(255, 215, 0));
        login_button.setForeground(Color.BLACK);
        sign_up_button.setFont(font);
        sign_up_button.setBackground(new Color(255, 215, 0));
        sign_up_button.setForeground(Color.BLACK);
        error.setFont(new Font("Optima", Font.PLAIN, 15));
        error.setForeground(Color.RED);
        error.setOpaque(false);
        error.setBorder(null);
    }

    public void actionPerformed(ActionEvent e) {
        String email = username.getText();
        String pass = new String(password.getPassword());
        game = Game.getInstance();
        game.my_account = game.findAccount(email, pass);
        if (game.my_account == null) {
            error.setVisible(true);
        } else {
            MyFrame frame = MyFrame.getInstance();
            this.setVisible(false);
            frame.characterPanel = new CharacterPanel();
            frame.characterPanel.setBounds(0, 0, 1200, 672);
            frame.add(frame.characterPanel);
        }
    }
}
