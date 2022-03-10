import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel{
    JLabel background3;
    JButton[][] cells;
    JLabel story;
    JLabel life, mana, experience, level, coins, dexterity, charisma, strength;
    Game game;
    Character character;
    ImageIcon shopIcon, enemyIcon, finishIcon, characterIcon, emptyIcon, unvisited;
    MyFrame frame;

    public GamePanel() {
        game = Game.getInstance();
        frame = MyFrame.getInstance();
        game.grid.setCharacter(game.grid.my_character);
        character = game.grid.my_character;
        life = new JLabel("    LIFE " + game.grid.my_character.current_life);
        mana = new JLabel("    MANA " + game.grid.my_character.current_mana);
        experience = new JLabel("    EXPERIENCE " + game.grid.my_character.experience);
        level = new JLabel("    LEVEL " + game.grid.my_character.level);
        coins = new JLabel("    COINS " + game.grid.my_character.inventory.coins);
        charisma = new JLabel("    CHARISMA " + game.grid.my_character.charisma);
        dexterity = new JLabel("    DEXTERITY " + game.grid.my_character.dexterity);
        strength = new JLabel("    STRENGTH " + game.grid.my_character.strength);
        cells = new JButton[game.grid.length][game.grid.width];
        shopIcon = new ImageIcon("resources/shop.jpg");
        enemyIcon = new ImageIcon("resources/enemy.png");
        finishIcon = new ImageIcon("resources/finish.png");
        characterIcon = new ImageIcon("resources/character.jpg");
        emptyIcon = new ImageIcon("resources/empty.png");
        unvisited = new ImageIcon("resources/unvisited.png");
        story = new JLabel();

        for (int i = 0; i < game.grid.length; i++) {
            for (int j = 0; j < game.grid.width; j++) {
                cells[i][j] = new JButton();
            }
        }

        ImageIcon img = new ImageIcon("resources/game_background.jpg");
        background3 = new JLabel(img);
        background3.setBounds(0, 0, 1200, 672);

        setSizeAndPosition();
        setFontAndColor();
        add(background3);

        background3.add(life);
        background3.add(mana);
        background3.add(experience);
        background3.add(level);
        background3.add(coins);
        background3.add(charisma);
        background3.add(dexterity);
        background3.add(strength);
        background3.add(story);

        for (int i = 0; i < game.grid.length; i++) {
            for (int j = 0; j < game.grid.width; j++) {
                if (i == 0 && j == 0) {
                    cells[i][j].setIcon(characterIcon);
                } else {
                    cells[i][j].setIcon(unvisited);
                }
                background3.add(cells[i][j]);
            }
        }

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "goNorth");
        getActionMap().put("goNorth", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCellIcon();
                game.grid.goNorth();
                story.setText(game.cellStory());
                story.setHorizontalAlignment(SwingConstants.CENTER);
                showOptions();
                cells[character.x][character.y].setIcon(characterIcon);
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "goSouth");
        getActionMap().put("goSouth", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCellIcon();
                game.grid.goSouth();
                story.setText(game.cellStory());
                story.setHorizontalAlignment(SwingConstants.CENTER);
                showOptions();
                cells[character.x][character.y].setIcon(characterIcon);
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "goWest");
        getActionMap().put("goWest", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCellIcon();
                game.grid.goWest();
                story.setText(game.cellStory());
                story.setHorizontalAlignment(SwingConstants.CENTER);
                showOptions();
                cells[character.x][character.y].setIcon(characterIcon);
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "goEast");
        getActionMap().put("goEast", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCellIcon();
                game.grid.goEast();
                story.setText(game.cellStory());
                story.setHorizontalAlignment(SwingConstants.CENTER);
                showOptions();
                cells[character.x][character.y].setIcon(characterIcon);
            }
        });
    }

    public void setSizeAndPosition() {
        int x = 200, y = 100;
        for (int i = 0; i < game.grid.length; i++) {
            for (int j = 0; j < game.grid.width; j++) {
                cells[i][j].setBounds(x + j*80, y + i * 80, 60, 60);
                cells[i][j].setBackground(Color.WHITE);
            }
        }
        life.setBounds(1010, 100, 160, 30);
        mana.setBounds(1010, 160, 160, 30);
        experience.setBounds(1010, 220, 160, 30);
        level.setBounds(1010, 280, 160, 30);
        coins.setBounds(1010, 340, 160, 30);
        strength.setBounds(1010, 400, 160, 30);
        charisma.setBounds(1010, 460, 160, 30);
        dexterity.setBounds(1010, 520, 150, 30);
        story.setBounds(200, 20, 800, 30);
    }

    public void setFontAndColor() {
        Color color = new Color(255, 197, 15);
        setFontAndColorComponent(life, color, 18);
        setFontAndColorComponent(mana, color, 18);
        setFontAndColorComponent(experience, color, 18);
        setFontAndColorComponent(level, color, 18);
        setFontAndColorComponent(coins, color, 18);
        setFontAndColorComponent(charisma, color, 18);
        setFontAndColorComponent(strength, color, 18);
        setFontAndColorComponent(dexterity, color, 18);
        setFontAndColorComponent(story, color, 20);
    }

    public void setFontAndColorComponent(JComponent component, Color color, int size) {
        Font font = new Font("Perpetua", Font.PLAIN, size);
        component.setFont(font);
        component.setForeground(color);
        component.setOpaque(false);
    }

    public void setCellIcon() {
        if (game.grid.current_cell.type == Cell.Type.SHOP) {
            cells[character.x][character.y].setIcon(shopIcon);
        } else if (game.grid.current_cell.type == Cell.Type.ENEMY) {
            cells[character.x][character.y].setIcon(enemyIcon);
        } else if (game.grid.current_cell.type == Cell.Type.FINISH) {
            cells[character.x][character.y].setIcon(finishIcon);
        } else if (game.grid.current_cell.type == Cell.Type.EMPTY) {
            cells[character.x][character.y].setIcon(emptyIcon);
        }
    }

    public void showOptions() {
        Random random = new Random();
        if (game.grid.current_cell.type == Cell.Type.EMPTY && !game.grid.current_cell.visited &&
                random.nextInt(1, 11) > 8) {
            character.addCoins(random.nextInt(20, 30));
        }
        if (game.grid.current_cell.type == Cell.Type.ENEMY && !game.grid.current_cell.visited) {
            setVisible(false);
            frame.fightPanel = new FightPanel();
            frame.fightPanel.setBounds(0, 0, 1200, 672);
            frame.add(frame.fightPanel);
        }
        if (game.grid.current_cell.type == Cell.Type.FINISH) {
            setVisible(false);
            frame.finishPanel = new FinishPanel(game.cellStory(), character.level, character.experience,
                    character.inventory.coins, game.defeatedEnemies);
            frame.finishPanel.setBounds(0,0,1200,672);
            frame.add(frame.finishPanel);
        }
        if (game.grid.current_cell.type == Cell.Type.SHOP) {
            setVisible(false);
            frame.shopPanel = new ShopPanel(game.cellStory());
            frame.shopPanel.setBounds(0, 0, 1200, 672);
            frame.add(frame.shopPanel);
        }
        coins.setText("    COINS " + character.inventory.coins);
        level.setText("    LEVEL " + character.level);
        experience.setText("    EXPERIENCE " + character.experience);
        game.grid.current_cell.setVisited();
    }
}
