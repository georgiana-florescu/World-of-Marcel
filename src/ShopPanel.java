import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopPanel extends JPanel {
    JLabel background6;
    JList list;
    DefaultListModel<String> potionList;
    JLabel shopTitle, inventoryTitle;
    JLabel coinsAndWeight;
    JButton exitButton, addButton;
    JScrollPane jScrollPane;
    JLabel error;
    JTextArea myPotions;
    MyFrame frame;
    Game game;

    ShopPanel(String story) {
        frame = MyFrame.getInstance();
        game = Game.getInstance();
        Shop shop = (Shop) game.grid.current_cell.element;
        Character my_char = game.grid.my_character;
        ImageIcon img = new ImageIcon("resources/background6.jpg");
        background6 = new JLabel(img);
        background6.setBounds(0, 0, 1200, 672);
        shopTitle = new JLabel("POTIONS");
        inventoryTitle = new JLabel("INVENTORY");
        potionList = new DefaultListModel<>();
        exitButton = new JButton("EXIT");
        addButton = new JButton("ADD");
        coinsAndWeight = new JLabel("COINS " + my_char.inventory.coins + " WEIGHT " + my_char.inventory.current_weight
                        + "/" + my_char.inventory.max_weight);
        myPotions = new JTextArea("");
        error = new JLabel();
        error.setVisible(false);

        setPotions(my_char, shop);

        list = new JList(potionList);
        jScrollPane = new JScrollPane(myPotions);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);
        list.setSelectionBackground(new Color(210, 180, 140));
        list.setCellRenderer(new TransparentCellRenderer());
        list.setOpaque(false);

        setSizeAndPosition();
        setFontAndColor();

        add(background6);
        background6.add(list);
        background6.add(shopTitle);
        background6.add(inventoryTitle);
        background6.add(addButton);
        background6.add(exitButton);
        background6.add(coinsAndWeight);
        background6.add(jScrollPane);
        background6.add(error);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.isSelectionEmpty()) {
                    if (potionList.isEmpty()) {
                        error.setText("No potion left!");
                    } else {
                        error.setText("No potion selected!");
                    }
                    error.setVisible(true);
                } else {
                    error.setVisible(false);
                    if (my_char.buyPotion(shop.potionList.get(list.getSelectedIndex()))) {
                        Potion p = shop.potionList.get(list.getSelectedIndex());
                        if (my_char.inventory.potions_list.size() == 1) {
                            myPotions.setText(my_char.inventory.potions_list.size() + ". " + p.getClass().getSimpleName() + " " + p.getPotionWeight()
                                    + " " + p.getPrice() + "\n");
                        } else {
                            myPotions.append(my_char.inventory.potions_list.size() + ". " + p.getClass().getSimpleName() + " " + p.getPotionWeight()
                                    + " " + p.getPrice() + "\n");
                        }
                        shop.selectPotion(list.getSelectedIndex());
                        potionList.remove(list.getSelectedIndex());
                        coinsAndWeight.setText("COINS " + my_char.inventory.coins + " WEIGHT " + my_char.inventory.current_weight
                                + "/" + my_char.inventory.max_weight);
                    } else {
                        error.setText("You do not have enough coins or space to buy this potion");
                        error.setVisible(true);
                    }
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                frame.game.setVisible(true);
            }
        });
    }

    public void setSizeAndPosition() {
        list.setBounds(240, 300, 210, 100);
        shopTitle.setBounds(280, 220, 160, 30);
        inventoryTitle.setBounds(740, 220, 160, 30);
        addButton.setBounds(240, 440, 80, 30);
        exitButton.setBounds(380, 440, 80, 30);
        coinsAndWeight.setBounds(700, 300, 300, 30);
        jScrollPane.setBounds(700, 350, 260, 150);
        error.setBounds(240, 480, 200, 30);
    }

    public void setFontAndColor() {
        setFontColorComponent(30, shopTitle, Color.BLACK);
        setFontColorComponent(30, inventoryTitle, Color.BLACK);
        setFontColorComponent(24, list, Color.BLACK);
        setFontColorComponent(24, exitButton, new Color(210, 180, 140));
        setFontColorComponent(24, addButton, new Color(210, 180, 140));
        setFontColorComponent(24, coinsAndWeight, Color.BLACK);
        setFontColorComponent(24, myPotions, Color.WHITE);
        setFontColorComponent(24, error, Color.BLACK);
        myPotions.setOpaque(false);
    }

    public void setFontColorComponent(int size, JComponent jcomponent, Color color) {
        jcomponent.setFont(new Font("Perpetua", Font.PLAIN, size));
        jcomponent.setBackground(color);
    }

    public void setPotions(Character my_char, Shop shop) {
        int i = 1;
        for (Potion p : shop.potionList) {
            potionList.addElement(p.getClass().getSimpleName() + " " + p.getPotionWeight()
                    + " " + p.getPrice());
            i++;
        }
        if (my_char.inventory.potions_list.isEmpty()) {
            myPotions.setText("You have no potions!");
        } else {
            i = 1;
            for (Potion p : my_char.inventory.potions_list) {
                myPotions.append(i + ". " + p.getClass().getSimpleName() + " " + p.getPotionWeight()
                        + " " + p.getPrice() + "\n");
                i++;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1200, 672);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new ShopPanel("Oh boy, are we going to try something dangerous now?"));
        frame.show();
    }
}