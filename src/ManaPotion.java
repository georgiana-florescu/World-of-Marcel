import java.util.Random;

public class ManaPotion implements Potion {
    private int price;
    private int potion_weight;
    private int recovery_value;

    public ManaPotion() {
        Random my_rand = new Random();
        potion_weight = 3 * my_rand.nextInt(1, 4);
        recovery_value = potion_weight * 10;
        price = potion_weight * 50 - (potion_weight - 3) / 3 * 50;
    }

    @Override
    public void usePotion() {
        Game game = Game.getInstance();
        Character character = game.grid.my_character;
        character.current_mana += getRecoveryValue();
        if (character.current_mana > 100) {
            character.current_mana = 100;
        }
        character.inventory.removePotion(this);
    }

    @Override
    public int getPrice() { return price;}

    @Override
    public int getRecoveryValue() {
        return recovery_value;
    }

    @Override
    public int getPotionWeight() {
        return potion_weight;
    }
}
