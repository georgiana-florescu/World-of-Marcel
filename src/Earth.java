public class Earth extends Spell {
    public Earth() {
        damage = 10;
        cost = 25;
    }
    @Override
    public void visit(Entity entity) {
        if(entity.earth == true) {
            return;
        }
        entity.receiveDamage(damage);
    }
}
