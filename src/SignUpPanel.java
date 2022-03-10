import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.TreeSet;

public class SignUpPanel extends JPanel {
    JTextField username, email;
    JPasswordField password;
    JLabel chooseCharacter, title, emailText;
    JLabel usernameText, passwordText, nameText, favGText;
    JTextField name, favGames;
    JButton add;
    JButton submit, addGame;
    JComboBox jComboBox;
    JLabel country;
    JComboBox countryBox;
    JLabel background;
    JLabel error;
    Account my_Account;
    Game game;

    public SignUpPanel() {
        my_Account = new Account();
        game = Game.getInstance();
        TreeSet<String> favGamesSet = new TreeSet<>();
        CharacterFactory characterFactory = new CharacterFactory();
        ImageIcon img = new ImageIcon("resources/create_account.jpg");
        background = new JLabel(img);
        background.setBounds(0, 0, 1200, 672);
        title = new JLabel("CREATE YOUR ACCOUNT");
        chooseCharacter = new JLabel("CHARACTER ");
        name = new JTextField();
        username = new JTextField();
        password = new JPasswordField();
        add = new JButton("ADD");
        favGames = new JTextField();
        favGText = new JLabel("FAVORITE GAMES");
        submit = new JButton("SUBMIT");
        usernameText = new JLabel("NAME");
        passwordText = new JLabel("PASSWORD");
        nameText = new JLabel("CHARACTER NAME");
        String[] characters = {"Warrior", "Rogue", "Mage"};
        jComboBox = new JComboBox(characters);
        error = new JLabel();
        error.setVisible(false);
        addGame = new JButton("OK");
        email = new JTextField();
        emailText = new JLabel("EMAIL");
        country = new JLabel("COUNTRY");
        String[] countryCodes = Locale.getISOCountries();
        String[] countryNames = new String[countryCodes.length];
        int i = 0;
        for (String c : countryCodes) {
            Locale locale = new Locale("", c);
            countryNames[i] = locale.getDisplayCountry();
            i++;
        }
        countryBox = new JComboBox(countryNames);
        setSizeAndPosition();
        setFontAndColor();

        add(background);
        background.add(title);
        background.add(chooseCharacter);
        background.add(name);
        background.add(username);
        background.add(password);
        background.add(add);
        background.add(jComboBox);
        background.add(submit);
        background.add(usernameText);
        background.add(passwordText);
        background.add(nameText);
        background.add(error);
        background.add(favGames);
        background.add(favGText);
        background.add(addGame);
        background.add(email);
        background.add(emailText);
        background.add(country);
        background.add(countryBox);

        add.addActionListener(e -> {
            error.setVisible(false);
            if (name.getText().isEmpty()) {
                error.setVisible(true);
            } else {
                my_Account.addCharacter(characterFactory.getCharacter(characters[jComboBox.getSelectedIndex()],name.getText(),  1, 0));
                name.setText(null);
            }
        });

        submit.addActionListener(e -> {
            if (username.getText().isEmpty() || email.getText().isEmpty() || password.getPassword().toString().isEmpty()) {
                error.setVisible(false);
                error.setText("Incomplete field");
                error.setVisible(true);
            } else if (my_Account.characters.size() == 0) {
                error.setVisible(false);
                error.setText("You have not added any characters");
                error.setVisible(true);
            } else {
                my_Account.setInformation(username.getText(), email.getText(), password.getPassword().toString(),
                        countryNames[countryBox.getSelectedIndex()], favGamesSet);
                game.addAccount(my_Account);
                game.my_account = my_Account;
                setVisible(false);
                MyFrame frame = MyFrame.getInstance();
                frame.characterPanel = new CharacterPanel();
                frame.characterPanel.setBounds(0, 0, 1200, 672);
                frame.add(frame.characterPanel);
            }
        });

        addGame.addActionListener(e -> {
            if (!favGames.getText().isEmpty()) {
                favGamesSet.add(favGames.getText());
                favGames.setText("");
            }
        });
    }

    public void setSizeAndPosition() {
        title.setBounds(640, 25, 500, 50);
        username.setBounds(700, 110, 300, 35);
        password.setBounds(700, 245, 300, 35);
        chooseCharacter.setBounds(700, 355, 300, 30);
        jComboBox.setBounds(700, 385, 140, 35);
        name.setBounds(700, 455, 300, 35);
        add.setBounds(700, 517, 140, 35);
        submit.setBounds(860, 517, 140, 35);
        usernameText.setBounds(700, 80, 300, 35);
        passwordText.setBounds(700, 215, 300, 35);
        nameText.setBounds(700, 425, 300, 30);
        error.setBounds(740, 580, 300, 35);
        favGames.setBounds(700,315, 300, 35);
        favGText.setBounds(700, 285, 200, 30);
        addGame.setBounds(1010, 315, 60, 35);
        email.setBounds(700, 180, 300, 35);
        emailText.setBounds(700, 150, 300, 35);
        country.setBounds(860, 355, 300, 30);
        countryBox.setBounds(860, 385, 140, 35);
    }

    public void setFontAndColor() {
        setFontAndColorComponent(title, 38, Color.BLACK);
        setFontAndColorComponent(username, 22, Color.BLACK);
        setFontAndColorComponent(password, 22, Color.BLACK);
        setFontAndColorComponent(chooseCharacter, 14, Color.BLACK);
        setFontAndColorComponent(jComboBox, 22, Color.BLACK);
        setFontAndColorComponent(add, 22, Color.BLACK);
        setFontAndColorComponent(submit, 22, Color.BLACK);
        setFontAndColorComponent(name, 22, Color.BLACK);
        setFontAndColorComponent(usernameText, 14, Color.BLACK);
        setFontAndColorComponent(passwordText, 14, Color.BLACK);
        setFontAndColorComponent(nameText, 14, Color.BLACK);
        setFontAndColorComponent(error, 20, Color.RED);
        setFontAndColorComponent(favGames, 22, Color.BLACK);
        setFontAndColorComponent(favGText, 14, Color.BLACK);
        setFontAndColorComponent(addGame, 15, Color.BLACK);
        setFontAndColorComponent(email, 22, Color.BLACK);
        setFontAndColorComponent(emailText, 14, Color.BLACK);
        setFontAndColorComponent(country, 14, Color.BLACK);
        setFontAndColorComponent(countryBox, 22, Color.BLACK);
    }

    public void setFontAndColorComponent(JComponent jComponent, int size, Color color) {
        Font font = new Font("Perpetua", Font.PLAIN, size);
        jComponent.setFont(font);
        jComponent.setForeground(color);
    }
}
