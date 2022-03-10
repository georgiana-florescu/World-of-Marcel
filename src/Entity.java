import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class Entity {
    List<Spell> abilities;
    int current_life;
    int max_life = 100;
    int current_mana;
    int max_mana = 100;
    boolean fire = false;
    boolean ice = false;
    boolean earth = false;

    public void lifeRecovery(int recovery_value) {
        if (current_life + recovery_value <= max_life) {
            current_life += recovery_value;
        } else {
            current_life = max_life;
        }
    }

    public void manaRecovery(int recovery_value) {
        if (current_mana + recovery_value <= max_mana) {
            current_mana += recovery_value;
        } else {
            current_mana = max_mana;
        }
    }

    public void useAbility(Spell spell, Entity entity) {
        if (current_mana >= spell.cost) {
            entity.accept(spell);
            abilities.remove(spell);
            current_mana -= spell.cost;
        }
    }

    public LinkedList<Spell> addRandomSpells() {
        LinkedList<Spell> res = new LinkedList<>();
        Random random = new Random();
        int var1, var2;
        var1 = random.nextInt(2, 5);
        for (int i = 0; i < var1; i++) {
            var2 = random.nextInt(1, 4);
            if (var2 == 1) {
                res.add(new Fire());
            } else if (var2 == 2) {
                res.add(new Earth());
            } else {
                res.add(new Ice());
            }
        }
        return res;
    }

    public abstract void accept(Spell spell);

    public abstract void receiveDamage(int damage);

    public abstract int getDamage();
}
