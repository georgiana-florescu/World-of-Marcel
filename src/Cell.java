public class Cell implements CellElement{
    int x;
    int y;

    enum Type { EMPTY, ENEMY, SHOP, FINISH}
    Type type;
    CellElement element;
    boolean visited;

    public Cell(int x, int y, Cell.Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        visited = false;
    }

    public void setType(Cell.Type type) {
        this.type = type;
    }

    public void setElement(CellElement element) {
        this.element = element;
    }

    public void setVisited() {
        this.visited = true;
    }

    @Override
    public char toCharacter() {
        if (element == null) {
            if (type == Type.EMPTY) {
                return 'N';
            } else {
                return 'F';
            }
        }
        return element.toCharacter();
    }
}
