import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        int Interface;

        System.out.println("Choose an option:");
        System.out.println("1. Command Line Interface");
        System.out.println("2. Graphical User Interface");

        Scanner scanner = new Scanner(System.in);
        Interface = scanner.nextInt();

        Game game = Game.getInstance();

        try {
            game.start(Interface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
