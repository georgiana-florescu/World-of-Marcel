import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FightPanel extends JPanel {
    JLabel background4;
    JLabel cLife, cMana, eLife, eMana, title;
    JButton attackButton, potionButton, abilityButton, useButton;
    DefaultListModel<String> myPotions, myAbilities;
    JScrollPane jScrollPane1, jScrollPane2;
    JLabel error;
    JList list1, list2;
    Game game;
    Character character;
    Enemy enemy;
    MyFrame frame;

    public FightPanel() {
        Random random = new Random();
        frame = MyFrame.getInstance();
        game = Game.getInstance();
        character = game.grid.my_character;
        enemy = (Enemy) game.grid.current_cell.element;
        cLife = new JLabel(" LIFE " + game.grid.my_character.current_life);
        cMana = new JLabel( " MANA " + game.grid.my_character.current_mana);
        eLife = new JLabel(" LIFE " + enemy.current_life);
        eMana = new JLabel(" MANA " + enemy.current_mana);
        title = new JLabel("FIGHT");
        myPotions = new DefaultListModel<>();
        myAbilities = new DefaultListModel<>();
        ImageIcon img = new ImageIcon("resources/background3.jpg");
        background4 = new JLabel(img);
        background4.setBounds(0, 0, 1200, 672);
        attackButton = new JButton();
        potionButton = new JButton();
        abilityButton = new JButton();
        error = new JLabel();
        useButton = new JButton("USE");
        useButton.setVisible(false);

        setDefaultListPotions(character);
        setDefaultListAbilities(character);

        list1 = new JList(myPotions);
        jScrollPane1 = new JScrollPane(list1);
        jScrollPane1.setVisible(false);

        list2 = new JList(myAbilities);
        jScrollPane2 = new JScrollPane(list2);
        jScrollPane2.setVisible(false);

        setSizeAndPosition();
        setFontAndColor();

        add(background4);
        background4.add(title);
        background4.add(cLife);
        background4.add(cMana);
        background4.add(eLife);
        background4.add(eMana);
        background4.add(attackButton);
        background4.add(potionButton);
        background4.add(abilityButton);
        background4.add(jScrollPane1);
        background4.add(jScrollPane2);
        background4.add(useButton);

        attackButton.addActionListener(e -> {
            enemy.receiveDamage(character.getDamage());
            eLife.setText(" LIFE " + enemy.current_life);
            if (enemy.current_life == 0) {
                character.addCoins(random.nextInt(400, 600));
                character.addXP();
                game.defeatedEnemies++;
                setVisible(false);
                frame.game.setVisible(true);
            }
            game.enemyAttack(character, enemy);
            if (character.current_life == 0) {
                stopGame();
            }
            eMana.setText(" MANA " + enemy.current_mana);
            cLife.setText(" LIFE " + character.current_life);
        });

        potionButton.addActionListener(e -> {
            jScrollPane2.setVisible(false);
            useButton.setVisible(false);
            if (character.inventory.potions_list.isEmpty()) {

            } else {
                jScrollPane1.setVisible(true);
                useButton.setVisible(true);
            }
        });

        abilityButton.addActionListener(e -> {
            jScrollPane1.setVisible(false);
            useButton.setVisible(false);
            if (character.abilities.isEmpty()) {

            } else {
                jScrollPane2.setVisible(true);
                useButton.setVisible(true);
            }
        });

        useButton.addActionListener(e -> {
            if (!list2.isSelectionEmpty()) {
                character.useAbility(character.abilities.get(list2.getSelectedIndex()), enemy);
                myAbilities.remove(list2.getSelectedIndex());
                eLife.setText(" LIFE " + enemy.current_life);
                cMana.setText(" MANA " + game.grid.my_character.current_mana);
                jScrollPane2.setVisible(false);
                useButton.setVisible(false);
                if (enemy.current_life == 0) {
                    character.addCoins(random.nextInt(400, 600));
                    game.defeatedEnemies++;
                    setVisible(false);
                    frame.game.setVisible(true);
                }
                game.enemyAttack(character, enemy);
                if (character.current_life==0) {
                    stopGame();
                }
                eMana.setText(" MANA " + enemy.current_mana);
                cLife.setText(" LIFE " + character.current_life);
            } else if (!list1.isSelectionEmpty()) {
                character.inventory.potions_list.get(list1.getSelectedIndex()).usePotion();
                myPotions.remove(list1.getSelectedIndex());
                cMana.setText(" MANA " + game.grid.my_character.current_mana);
                cLife.setText(" LIFE " + character.current_life);
                jScrollPane1.setVisible(false);
                useButton.setVisible(false);
                if (enemy.current_life == 0) {
                    character.addCoins(random.nextInt(400, 600));
                    game.defeatedEnemies++;
                    setVisible(false);
                    frame.game.setVisible(true);
                }
                game.enemyAttack(character, enemy);
                cLife.setText(" LIFE " + character.current_life);
                eMana.setText(" MANA " + enemy.current_mana);
            }
        });
    }

    public void setSizeAndPosition() {
        cLife.setBounds(60, 100, 160, 30);
        cMana.setBounds(60, 150, 160, 30);
        eLife.setBounds(1000, 100, 160, 30);
        eMana.setBounds(1000, 150, 160, 30);
        title.setBounds(520, 20, 200, 60);
        attackButton.setBounds(10, 200, 64, 64);
        potionButton.setBounds(90, 200, 64, 64);
        abilityButton.setBounds(170, 200, 64, 64);
        jScrollPane1.setBounds(30, 300, 200, 200);
        jScrollPane2.setBounds(30, 300, 200, 200);
        attackButton.setIcon(new ImageIcon("resources/attack.png"));
        potionButton.setIcon(new ImageIcon("resources/potion.png"));
        abilityButton.setIcon(new ImageIcon("resources/ability.jpg"));
        error.setBounds(60, 500, 200, 40);
        useButton.setBounds(85, 510, 100, 47);
    }

    public void setFontAndColor() {
        setComponentFontAndColor(cLife, 26);
        setComponentFontAndColor(cMana, 26);
        setComponentFontAndColor(eLife, 26);
        setComponentFontAndColor(eMana, 26);
        setComponentFontAndColor(title, 50);
        setComponentFontAndColor(list1, 24);
        setComponentFontAndColor(list2, 24);
        setComponentFontAndColor(useButton, 26);
        list1.setSelectionBackground(Color.WHITE);
        list1.setCellRenderer(new TransparentCellRenderer());
        list1.setOpaque(false);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        list2.setCellRenderer(new TransparentCellRenderer());
        list2.setSelectionBackground(Color.WHITE);
        list2.setOpaque(false);
        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
    }

    public void setComponentFontAndColor(JComponent component, int size) {
        Color color = new Color(211, 32, 17);
        component.setBackground(color);
        component.setFont(new Font("Perpetua", Font.PLAIN, size));
        component.setForeground(Color.WHITE);
    }

    public void setDefaultListPotions(Character character) {
        for (Potion p : character.inventory.potions_list) {
            myPotions.addElement(p.getClass().getSimpleName() + " " + p.getPotionWeight()
                    + " " + p.getPrice());
        }
    }

    public void stopGame() {
        frame.fightPanel.setVisible(false);
        frame.lostPanel = new LostPanel();
        frame.lostPanel.setBounds(0, 0, 1200, 672);
        frame.add(frame.lostPanel);
        frame.lostPanel.setVisible(true);
    }

    public void setDefaultListAbilities(Character character) {
        for (Spell s : character.abilities) {
            myAbilities.addElement(s.getClass().getSimpleName() + " " + s.damage);
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1200, 672);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new FightPanel());
        frame.show();
    }
}
