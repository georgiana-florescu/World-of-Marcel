public abstract class Spell {
    int damage;
    int cost;

    public abstract void visit(Entity entity);
}
