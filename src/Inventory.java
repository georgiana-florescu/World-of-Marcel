import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Inventory {
    List<Potion> potions_list;
    int current_weight;
    int max_weight;
    int coins;

    public Inventory(int max_weight) {
        Random random = new Random();
        potions_list = new LinkedList<>();
        current_weight = 0;
        this.max_weight = max_weight;
        this.coins = random.nextInt(700, 1000);
    }

    public void addPotion(Potion potion) {
        current_weight += potion.getPotionWeight();
        potions_list.add(potion);
    }

    public boolean removePotion(Potion potion) {
        current_weight -= potion.getPotionWeight();
        return potions_list.remove(potion);
    }

    public int getRemainingWeight() {
        return max_weight - current_weight;
    }
}
