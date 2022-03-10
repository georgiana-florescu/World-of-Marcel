public class Ice extends Spell{
    public Ice() {
        damage = 24;
        cost = 30;
    }

    @Override
    public void visit(Entity entity) {
        if(entity.ice) {
            return;
        }
        entity.receiveDamage(damage);
    }
}
