import java.util.Random;

public class Enemy extends Entity implements CellElement{
    public Enemy() {
        Random random = new Random();
        int var;
        current_life = random.nextInt(40, 80);
        current_mana = random.nextInt(50, 100);
        abilities = addRandomSpells();
        var = random.nextInt(1, 4);
        if (var == 1) {
            fire = true;
        } else if (var == 2) {
            earth = true;
        } else {
            ice = true;
        }
    }

    @Override
    public char toCharacter() {
        return 'E';
    }

    @Override
    public void receiveDamage(int damage) {
        Random random = new Random();
        boolean chance = random.nextBoolean();
        if (chance) {
            damage /= 2;
        }
        current_life -= damage;
        if (current_life < 0) {
            current_life = 0;
        }
    }

    @Override
    public int getDamage() {
        Random random = new Random();
        boolean chance = random.nextBoolean();
        if (chance) {
            return 14;
        }
        return 7;
    }

    @Override
    public void accept(Spell spell) {
        spell.visit(this);
    }

    public Spell getSpell() {
        Random random = new Random();
        int p_num = random.nextInt(0, abilities.size());
        return abilities.remove(p_num);
    }
}
