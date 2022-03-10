import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Shop implements CellElement {
    List<Potion> potionList;

    public Shop() {
        potionList = new LinkedList<>();
        Random random = new Random();
        int potion_num = random.nextInt(2, 4);
        potionList.add(new HealthPotion());
        potionList.add(new ManaPotion());
        for (int j = 0; j < potion_num - 2; j++) {
            potionList.add(random.nextBoolean() ? new HealthPotion() : new ManaPotion());
        }
    }

    public Potion selectPotion(int index) {
        return potionList.remove(index);
    }

    @Override
    public char toCharacter() {
        return 'S';
    }
}
