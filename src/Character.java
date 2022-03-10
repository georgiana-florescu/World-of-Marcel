import java.util.Random;

public abstract class Character extends Entity {
    String name;
    int x, y;
    Inventory inventory;
    int experience;
    int level;
    int strength;
    int charisma;
    int dexterity;

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean buyPotion(Potion potion) {
        if (inventory.coins < potion.getPrice()) {
            return false;
        }
        if (inventory.getRemainingWeight() < potion.getPotionWeight()) {
            return false;
        }
        inventory.coins -= potion.getPrice();
        inventory.addPotion(potion);
        return true;
    }

    public void showInventory() {
        System.out.println();
        System.out.println("|||||||||||||||||||| Inventory ||||||||||||||||||||");
        System.out.println("Coins: " + this.inventory.coins);
        System.out.println("Current weight/Weight: " + inventory.current_weight + "/" + inventory.max_weight);
        System.out.println("My potions:");
        int j = 1;
        if (!inventory.potions_list.isEmpty()) {
            for (Potion potion : inventory.potions_list) {
                System.out.println(j + ". " + potion.getClass().getSimpleName() + " " + potion.getPotionWeight() + " " +
                        potion.getRecoveryValue());
                j++;
            }
        } else {
            System.out.println("You don't have potions!");
        }
    }

    public void addCoins(int coins) {
        inventory.coins += coins;
    }

    public void addXP() {
        Random random = new Random();
        experience += random.nextInt(10, 21);
        if (experience >= 100) {
            level ++;
            experience = 0;
        }
    }

    public String toString() {
        return getClass().getSimpleName() + " " + name + " Level " + level + " Experience " + experience;
    }
}
