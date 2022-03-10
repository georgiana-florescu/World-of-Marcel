import java.util.*;

public class Test {
    public static void main(String[] args) {
        String input;
        Scanner scanner = new Scanner(System.in);
        try {
            Game game = Game.getInstance();
            game.startTest();
            game.grid.showMap();

            game.pressPKey();
            game.grid.goEast();
            game.grid.showStoryAndOptions(true);
            game.grid.showMap();

            game.pressPKey();
            game.grid.goEast();
            game.grid.showStoryAndOptions(true);
            game.grid.showMap();

            game.pressPKey();
            game.grid.goEast();
            game.grid.showStoryAndOptions(true);
            game.grid.showMap();

            game.pressPKey();
            game.grid.goEast();
            game.grid.showStoryAndOptions(true);
            game.grid.showMap();

            game.pressPKey();
            game.grid.goSouth();
            game.grid.showStoryAndOptions(true);
            game.grid.showMap();

            game.pressPKey();
            game.grid.goSouth();
            game.grid.showStoryAndOptions(true);
            game.grid.showMap();

            game.pressPKey();
            game.grid.goSouth();
            game.grid.showStoryAndOptions(true);
            game.grid.showMap();

            game.pressPKey();
            game.grid.goSouth();
            game.grid.showStoryAndOptions(true);
            game.grid.showMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
