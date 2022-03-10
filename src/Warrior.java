import java.util.Random;

public class Warrior extends Character {
    public Warrior(String name, int level, int experience) {
        Random random = new Random();
        this.name = name;
        this.experience = experience;
        this.level = level;
        this.abilities = addRandomSpells();
        strength = 100 + (level - 1) * 50 + random.nextInt(level, level * 10 + 1);
        charisma = 50 + (level - 1) * 50 + random.nextInt(level, level * 10 + 1);
        dexterity = 25 + (level - 1) * 50 + random.nextInt(level, level * 10 + 1);
        inventory = new Inventory(30);
        current_life = 100;
        current_mana = 100;
        fire = true;
    }

    @Override
    public void receiveDamage(int damage) {
        if ((charisma + dexterity - 75 - (level - 1) * 50) / level >= 14) {
            damage /= 2;
        }
        current_life -= damage;
        if (current_life < 0) {
            current_life = 0;
        }
    }

    @Override
    public int getDamage() {
        if ((strength - 100 - (level - 1) * 50) / level >= 7) {
            return 16;
        }
        return 8;
    }

    @Override
    public void accept(Spell spell) {
        spell.visit(this);
    }
}
