import java.util.Random;

public class Mage extends Character {
    public Mage(String name, int level, int experience) {
        this.name = name;
        this.experience = experience;
        this.level = level;
        this.abilities = addRandomSpells();
        Random random = new Random();
        charisma = 100 + (level - 1) * 50 + random.nextInt(level, level * 10 + 1);
        dexterity = 50 + (level - 1) * 50 + random.nextInt(level, level * 10 + 1);
        strength = 25 + (level - 1) * 50 + random.nextInt(level, level * 10 + 1);
        inventory = new Inventory(18);
        current_life = 100;
        current_mana = 100;
        ice = true;
    }

    @Override
    public void receiveDamage(int damage) {
        if ((dexterity + strength - 75 - (level - 1) * 50) / level >= 14) {
            damage /= 2;
        }
        current_life -= damage;
        if (current_life < 0) {
            current_life = 0;
        }
    }

    @Override
    public int getDamage() {
        if ((charisma - 100 - (level - 1) * 50) / level >= 7) {
            return 24;
        }
        return 12;
    }

    @Override
    public void accept(Spell spell) {
        spell.visit(this);
    }
}
