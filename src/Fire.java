public class Fire extends Spell {
    public Fire() {
        damage = 16;
        cost = 20;
    }

    @Override
    public void visit(Entity entity) {
        if(entity.fire) {
            return;
        }
        entity.receiveDamage(damage);
    }
}
