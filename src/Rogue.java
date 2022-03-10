import java.util.Random;

public class Rogue extends Character {
    public Rogue(String name, int level, int experience) {
        this.name = name;
        this.experience = experience;
        this.level = level;
        this.abilities = addRandomSpells();
        Random random = new Random();
        dexterity = 100 + (level - 1) * 50 + random.nextInt(level, level * 10 + 1);
        strength = 50 + (level - 1) * 50 + random.nextInt(level, level * 10 + 1);
        charisma = 25 + (level - 1) * 50 + random.nextInt(level, level * 10 + 1);
        inventory = new Inventory(24);
        current_life = 100;
        current_mana = 100;
        earth = true;
    }

    @Override
    public void receiveDamage(int damage) {
        if ((strength + charisma - 75 - (level - 1) * 50) / level >= 14) {
            damage /= 2;
        }
        current_life -= damage;
        if (current_life < 0) {
            current_life = 0;
        }
    }

    @Override
    public int getDamage() {
        if ((dexterity - 100 - (level - 1) * 50) / level >= 7) {
            return 20;
        }
        return 10;
    }

    @Override
    public void accept(Spell spell) {
        spell.visit(this);
    }
}
