import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Grid extends ArrayList<ArrayList<Cell>> {
    int length;
    int width;
    Cell current_cell;
    Character my_character;
    private static Grid grid = null;
    Game game;

    private Grid(int length, int width) {
        this.length = length;
        this.width = width;
    }

    static Grid generateMap(int length, int width) {
        if (grid == null) {
            grid = new Grid(length, width);
        }
        return grid;
    }

    public void createMap() {
        Random random = new Random();
        int x_var, y_var, shops, enemies;
        for (int i = 0; i < length; i++) {
            ArrayList<Cell> list = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                Cell cell = new Cell(i, j, Cell.Type.EMPTY);
                list.add(cell);
            }
            add(list);
        }
        shops = random.nextInt(3, 7);
        enemies = random.nextInt(4, 9);
        for (int i = 0; i < shops; i++) {
            x_var = random.nextInt(0, length);
            y_var = random.nextInt(0, width);
            if (getCell(x_var, y_var).type == Cell.Type.EMPTY && x_var != 0 && y_var != 0) {
                getCell(x_var, y_var).setType(Cell.Type.SHOP);
                getCell(x_var, y_var).element = new Shop();
            } else {
                i--;
            }
        }
        for (int i = 0; i < enemies; i++) {
            x_var = random.nextInt(0, length);
            y_var = random.nextInt(0, width);
            if (getCell(x_var, y_var).type == Cell.Type.EMPTY && x_var != 0 && y_var != 0) {
                getCell(x_var, y_var).setType(Cell.Type.ENEMY);
                getCell(x_var, y_var).element = new Enemy();
            } else {
                i--;
            }
        }
        while (true) {
            x_var = random.nextInt(length - 2, length);
            y_var = random.nextInt(0, width);
            if (getCell(x_var, y_var).type == Cell.Type.EMPTY) {
                getCell(x_var, y_var).setType(Cell.Type.FINISH);
                break;
            }
        }
    }

    public void createTestMap() {
        for (int i = 0; i < 5; i++) {
            ArrayList<Cell> arrayList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                arrayList.add(new Cell(i, j, Cell.Type.EMPTY));
            }
            add(arrayList);
        }
        getCell(0, 3).setType(Cell.Type.SHOP);
        getCell(0, 3).setElement(new Shop());
        getCell(1, 3).setType(Cell.Type.SHOP);
        getCell(1, 3).setElement(new Shop());
        getCell(2, 0).setType(Cell.Type.SHOP);
        getCell(2, 0).setElement(new Shop());
        getCell(3, 4).setType(Cell.Type.ENEMY);
        getCell(3, 4).setElement(new Enemy());
        getCell(4, 4).setType(Cell.Type.FINISH);
    }

    public void showMap() {
        for(int i = 0; i < this.length; i++) {
            String s = "";
            for (int j = 0; j < this.width; j++) {
                Cell cell = getCell(i, j);
                if (my_character.x == i && my_character.y == j) {
                    s += "P ";
                    continue;
                }
                if (cell.visited) {
                    s += cell.toCharacter() + " ";
                } else {
                    s += "? ";
                }
            }
            System.out.println(s);
        }
        System.out.println();
    }

    public Cell getCell(int x, int y) {return (this.get(x)).get(y);}

    public void listDirections() {
        int answer;
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                grid.showMap();
                System.out.println("Choose an option:");
                System.out.println("1. NORTH");
                System.out.println("2. SOUTH");
                System.out.println("3. EAST");
                System.out.println("4. WEST");
                answer = scanner.nextInt();
                System.out.println();
                if (answer == 1) {
                    grid.goNorth();
                    grid.showStoryAndOptions(false);
                } else if (answer == 2) {
                    grid.goSouth();
                    grid.showStoryAndOptions(false);
                } else if (answer == 3) {
                    grid.goEast();
                    grid.showStoryAndOptions(false);
                } else if (answer == 4) {
                    grid.goWest();
                    grid.showStoryAndOptions(false);
                } else {
                    throw new InvalidCommandException();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCharacter(Character character) {
        my_character = character;
        my_character.setPosition(0, 0);
        current_cell = getCell(0, 0);
        current_cell.setVisited();
    }

    public void goSouth() {
        if (my_character.x < length - 1) {
            my_character.setPosition(my_character.x + 1, my_character.y);
            current_cell = getCell(my_character.x, my_character.y);
        }
    }

    public void goNorth() {
        if (my_character.x > 0) {
            my_character.setPosition(my_character.x - 1, my_character.y);
            current_cell = getCell(my_character.x, my_character.y);
        }
    }

    public void goEast() {
        if (my_character.y < width - 1) {
            my_character.setPosition(my_character.x, my_character.y + 1);
            current_cell = getCell(my_character.x, my_character.y);
        }
    }

    public void goWest() {
        if (my_character.y > 0) {
            my_character.setPosition(my_character.x, my_character.y - 1);
            current_cell = getCell(my_character.x, my_character.y);
        }
    }

    public void showStoryAndOptions(boolean test) throws InvalidCommandException{
        Random random = new Random();
        Game game = Game.getInstance();
        int coins;
        System.out.println(game.cellStory());
        if (!grid.current_cell.visited && grid.current_cell.type == Cell.Type.EMPTY) {
            int chance = random.nextInt(1, 11);
            if (chance > 8) {
                coins = random.nextInt(20, 30);
                System.out.println("You found " + coins + " coins!");
                grid.my_character.addCoins(coins);
            }
        }
        if (!test) {
            game.showOptions();
        } else {
            game.showOptionsTest();
        }
        current_cell.setVisited();
        if (grid.current_cell.type == Cell.Type.FINISH) {
            grid.showMap();
            System.out.println("FINISH");
            System.exit(0);
        }
    }
}
