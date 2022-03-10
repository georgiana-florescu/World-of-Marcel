import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Game {
    Account my_account;
    List<Account> accounts;
    Map <Cell.Type, List<String>> stories_map;
    Grid grid;
    int defeatedEnemies;
    private static Game single_instance = null;

    private Game() {
        accounts = new LinkedList<>();
        stories_map = new HashMap<>();
        defeatedEnemies = 0;
    }

    public static Game getInstance() {
        if(single_instance == null) {
            single_instance = new Game();
        }
        return single_instance;
    }

    public void start(int Interface) throws InvalidCommandException{
        grid = Grid.generateMap(6, 10);
        grid.createMap();
        run(Interface);
    }

    public void startTest() {
        grid = Grid.generateMap(5, 5);
        grid.createTestMap();
        runTest();
    }

    public void run(int Interface) throws InvalidCommandException{
        Scanner scan = new Scanner(System.in);
        readAccounts("accounts.json");
        readStories("stories.json");
        Account my_account;
        if (Interface == 1) {
            do {
                System.out.println("Email:");
                String email = scan.nextLine();
                System.out.println("Password:");
                String password = scan.nextLine();
                my_account = findAccount(email, password);
                if (my_account == null) {
                    System.out.println("The email and password combination is incorrect");
                }
            } while (my_account == null);

            int i = 1;
            for (Character character : my_account.characters) {
                System.out.println(i + ": " +  character.toString());
                i++;
            }

            System.out.println("Choose a character:");
            i = scan.nextInt();
            if (i > my_account.characters.size()) {
                throw new InvalidCommandException();
            }
            grid.setCharacter(my_account.characters.get(i - 1));
            System.out.println("Your character: " + my_account.characters.get(i-1).toString());
            System.out.println("STRENGTH " + grid.my_character.strength);
            System.out.println("DEXTERITY " + grid.my_character.dexterity);
            System.out.println("CHARISMA " + grid.my_character.charisma + "\n");
            grid.listDirections();
        } else {
            MyFrame frame = MyFrame.getInstance();
        }
    }

    public void runTest() {
        readAccounts("accounts.json");
        readStories("stories.json");
        Account my_account;
        System.out.println("Email: ");
        String email = "marcel@yahoo.com";
        System.out.println(email);
        System.out.println("Password: ");
        String password = "6K7GUxjsAc";
        System.out.println(password);
        my_account = findAccount(email, password);
        System.out.println("\nMy characters:\n");
        int i = 1;
        for (Character character : my_account.characters) {
            System.out.println(i + ": " + character.toString());
            i++;
        }
        System.out.println("\nChoose a character:");
        i = 2;
        System.out.println("Your character is: " + my_account.characters.get(i-1).toString() + "\n");
        grid.setCharacter(my_account.characters.get(i - 1));
    }

    public void readAccounts(String path) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            JSONArray jsonArray = (JSONArray) jsonObject.get("accounts");

            for (Object o : jsonArray) {
                Account account = new Account();
                JSONObject json_account = (JSONObject) o;
                JSONObject json_credential = (JSONObject) json_account.get("credentials");
                String email = (String) json_credential.get("email");
                String password = (String) json_credential.get("password");
                String name = (String) json_account.get("name");
                String country = (String) json_account.get("country");
                JSONArray json_favorite_games = (JSONArray) json_account.get("favorite_games");
                TreeSet<String> favorite_games = new TreeSet<>();

                for (Object o1 : json_favorite_games) {
                    favorite_games.add((String)o1);
                }
                account.setInformation(name, email, password, country, favorite_games);
                int maps_completed =  Integer.parseInt((String) json_account.get("maps_completed"));
                account.setMapsCompleted(maps_completed);
                JSONArray json_characters = (JSONArray) json_account.get("characters");
                CharacterFactory characterFactory = new CharacterFactory();
                for (Object o2 : json_characters) {
                    JSONObject character = (JSONObject) o2;
                    String ch_name = (String) character.get("name");
                    String ch_profession = (String) character.get("profession");
                    int ch_level = Integer.parseInt((String) character.get("level"));
                    int ch_experience = Integer.parseInt((String) character.get("experience"));
                    account.addCharacter(characterFactory.getCharacter(ch_profession, ch_name, ch_level, ch_experience));
                }
                addAccount(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readStories(String path) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            JSONArray jsonArray = (JSONArray) jsonObject.get("stories");

            for (Object o : jsonArray) {
                JSONObject json_story = (JSONObject) o;
                String type = (String) json_story.get("type");
                String value = (String) json_story.get("value");
                addStory(type, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStory(String str_type, String story) {
        if (str_type.equalsIgnoreCase("ENEMY")) {
            if (stories_map.containsKey(Cell.Type.ENEMY)) {
                (stories_map.get(Cell.Type.ENEMY)).add(story);
            } else {
                List<String> list = new LinkedList<>();
                list.add(story);
                stories_map.put(Cell.Type.ENEMY, list);
            }
        } else if (str_type.equalsIgnoreCase("SHOP")) {
            if (stories_map.containsKey(Cell.Type.SHOP)) {
                (stories_map.get(Cell.Type.SHOP)).add(story);
            } else {
                List<String> list = new LinkedList<>();
                list.add(story);
                stories_map.put(Cell.Type.SHOP, list);
            }
        } else if (str_type.equalsIgnoreCase("EMPTY")) {
            if (stories_map.containsKey(Cell.Type.EMPTY)) {
                (stories_map.get(Cell.Type.EMPTY)).add(story);
            } else {
                List<String> list = new LinkedList<>();
                list.add(story);
                stories_map.put(Cell.Type.EMPTY, list);
            }
        } else {
            if (stories_map.containsKey(Cell.Type.FINISH)) {
                (stories_map.get(Cell.Type.FINISH)).add(story);
            } else {
                List<String> list = new LinkedList<>();
                list.add(story);
                stories_map.put(Cell.Type.FINISH, list);
            }
        }
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void showOptions() throws InvalidCommandException{
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int ans, i;
        if (grid.current_cell.type == Cell.Type.SHOP) {
            do {
                System.out.println("SHOP");
                System.out.println("Choose a potion:");
                i = 1;
                for (Potion potion : ((Shop) grid.current_cell.element).potionList) {
                    System.out.println(i + ". " + potion.getClass().getSimpleName() + " " + potion.getPotionWeight()
                            + " " + potion.getPrice());
                    i++;
                }
                System.out.println(i + ". Exit");
                grid.my_character.showInventory();
                ans = scanner.nextInt();
                System.out.println();
                if (ans > i) {
                    throw new InvalidCommandException();
                }
                if (ans != i) {
                    if (grid.my_character.buyPotion(((Shop) grid.current_cell.element).potionList.get(ans - 1))) {
                        ((Shop) grid.current_cell.element).selectPotion(ans - 1);
                    } else {
                        System.out.println("You do not have enough coins or space to buy this potion");
                    }
                }
            } while (ans != i);
        } else if (grid.current_cell.type == Cell.Type.ENEMY && !grid.current_cell.visited){
            System.out.println("ENEMY FOUND");
            Character character = grid.my_character;
            Enemy enemy = (Enemy) grid.current_cell.element;
            System.out.println("YOU LIFE " + character.current_life + " MANA " + character.current_mana +
                    " | ENEMY LIFE " + enemy.current_life + " MANA " + enemy.current_mana + "\n");
            while (character.current_life != 0 && enemy.current_life != 0) {
                System.out.println("Choose an option:");
                System.out.println("1. Attack");
                if (!grid.my_character.inventory.potions_list.isEmpty()) {
                    System.out.println("2. Use Potion");
                }
                if (!character.abilities.isEmpty()) {
                    System.out.println("3. Use Ability");
                }
                ans = scanner.nextInt();
                fightEnemy(ans, character, enemy);
            }
            if (character.current_life == 0) {
                System.out.println("YOU LOST THE GAME! :(\n");
                System.exit(0);
            } else {
                System.out.println("THE ENEMY HAS BEEN DEFEATED! :)\n");
                character.addCoins(random.nextInt(400, 600));
                character.addXP();
                defeatedEnemies++;
            }
        }
    }

    public void showOptionsTest() throws InvalidCommandException {
        int j;
        if (grid.current_cell.type == Cell.Type.SHOP) {
            for (int i = 0; i < 2; i++) {
                System.out.println("SHOP");
                System.out.println("Choose a potion:");
                System.out.println("1. Exit");
                j = 2;
                for (Potion potion : ((Shop) grid.current_cell.element).potionList) {
                    System.out.println(j + ". " + potion.getClass().getSimpleName() + " " + potion.getPotionWeight()
                            + " " + potion.getPrice());
                    j++;
                }
                grid.my_character.showInventory();
                System.out.println();
                if (grid.my_character.buyPotion(((Shop) grid.current_cell.element).potionList.get(0))) {
                    ((Shop) grid.current_cell.element).selectPotion(0);
                } else {
                    System.out.println("You do not have enough coins or space to buy this potion");
                }
                pressPKey();
            }
        } else if (grid.current_cell.type == Cell.Type.ENEMY) {
            // folosim toate abilitatile
            Character character = grid.my_character;
            Enemy enemy = (Enemy) grid.current_cell.element;
            while (!character.abilities.isEmpty()) {
                System.out.println("Choose an option:");
                System.out.println("1. Attack");
                if (!character.inventory.potions_list.isEmpty()) {
                    System.out.println("2. Use Potion");
                }
                if (!character.abilities.isEmpty()) {
                    System.out.println("3. Use ability");
                }
                System.out.println();
                System.out.println("|||||||||||||||||||| My turn ||||||||||||||||||||");
                System.out.println("Using " + character.abilities.get(0).getClass().getSimpleName());
                System.out.println("Enemy FIRE - " + enemy.fire + " ICE - " + enemy.ice + " EARTH - " + enemy.earth);
                character.useAbility(character.abilities.get(0), enemy);
                printLifeAndMana(character, enemy);
                System.out.println("|||||||||||||||||| Enemy's turn ||||||||||||||||||");
                enemyAttack(character, enemy);
                printLifeAndMana(character, enemy);
                pressPKey();
            }

            //folosim toate potiunile
            while (!character.inventory.potions_list.isEmpty()) {
                System.out.println("Choose an option:");
                System.out.println("1. Attack");
                if (!character.inventory.potions_list.isEmpty()) {
                    System.out.println("2. Use Potion");
                }
                if (!character.abilities.isEmpty()) {
                    System.out.println("3. Use ability");
                }
                System.out.println("|||||||||||||||||||| My turn ||||||||||||||||||||");
                character.showInventory();
                System.out.println("Before " + character.inventory.potions_list.get(0).getClass().getSimpleName() +
                        " LIFE " + character.current_life + " MANA " + character.current_mana);
                character.inventory.potions_list.get(0).usePotion();
                System.out.println("After " + " LIFE " + character.current_life + " MANA " + character.current_mana);
                System.out.println("|||||||||||||||||| Enemy's turn ||||||||||||||||||");
                enemyAttack(character, enemy);
                printLifeAndMana(character, enemy);
                pressPKey();
            }

            // restul de atacuri
            while (character.current_life != 0 && enemy.current_life != 0) {
                System.out.println("My turn");
                enemy.receiveDamage(character.getDamage());
                printLifeAndMana(character, enemy);
                if (enemy.current_life == 0) {
                    System.out.println("You won! :)");
                    break;
                }
                System.out.println("Enemy's turn");
                enemyAttack(character, enemy);
                printLifeAndMana(character, enemy);
                if (character.current_life == 0) {
                    System.out.println("You lost! :( Maybe next time...");
                    System.exit(0);
                }
            }
        }
    }


    public void fightEnemy(int ans, Character character, Enemy enemy) {
        Scanner scanner = new Scanner(System.in);
        if (ans == 1) {
            enemy.receiveDamage(character.getDamage());
        } else if (ans == 3) {
            System.out.println("My abilities:");
            int j = 1;
            for (Spell spell : character.abilities) {
                System.out.println(j + " " + spell.getClass().getSimpleName() + " " + spell.damage);
                j++;
            }
            ans = scanner.nextInt();
            System.out.println();
            character.useAbility(character.abilities.get(ans - 1), enemy);
        } else if (ans == 2) {
            grid.my_character.showInventory();
            ans = scanner.nextInt();
            System.out.println("\nBefore potion: LIFE " + character.current_life + " MANA " + character.current_mana);
            character.inventory.potions_list.get(ans - 1).usePotion();
            System.out.println("After potion: LIFE " + character.current_life + " MANA " + character.current_mana + "\n");
        }
        System.out.println("|||||||||||||||||||| My turn ||||||||||||||||||||");
        System.out.println("YOU LIFE " + character.current_life + " MANA " + character.current_mana +
                 " | Enemy LIFE " + enemy.current_life + " MANA " + enemy.current_mana + "\n");
        if (enemy.current_life != 0) {
            System.out.println("|||||||||||||||||| Enemy's turn ||||||||||||||||||");
            enemyAttack(character, enemy);
            System.out.println("YOU LIFE " + character.current_life + " MANA " + character.current_mana +
                    " | ENEMY LIFE " + enemy.current_life + " MANA " + enemy.current_mana + "\n");
        } else {
            return;
        }
    }

    public void enemyAttack(Character character, Enemy enemy) {
        Random random = new Random();
        int ans = random.nextInt(1, 5);
        if (ans == 1) {
            if (!enemy.abilities.isEmpty()) {
                enemy.useAbility(enemy.getSpell(), character);
            } else {
                character.receiveDamage(enemy.getDamage());
            }
        } else {
            character.receiveDamage(enemy.getDamage());
        }
    }

    public Account findAccount(String email, String password) {
        for (Account acc : accounts) {
            if (acc.verifyEmailAndPassword(email, password)) {
                return acc;
            }
        }
        return null;
    }

    public String cellStory() {
        Random random = new Random();
        int size;
        if (grid.current_cell.type == Cell.Type.EMPTY) {
            size = stories_map.get(Cell.Type.EMPTY).size();
            return stories_map.get(Cell.Type.EMPTY).get(random.nextInt(0, size));
        } else if (grid.current_cell.type == Cell.Type.SHOP) {
            size = stories_map.get(Cell.Type.SHOP).size();
            return stories_map.get(Cell.Type.SHOP).get(random.nextInt(0, size));
        } else if (grid.current_cell.type == Cell.Type.ENEMY) {
            size = stories_map.get(Cell.Type.ENEMY).size();
            return stories_map.get(Cell.Type.ENEMY).get(random.nextInt(0, size));
        } else {
            size = stories_map.get(Cell.Type.FINISH).size();
            return stories_map.get(Cell.Type.FINISH).get(random.nextInt(0, size));
        }
    }

    public void printLifeAndMana(Character character, Enemy enemy) {
        System.out.println("Me LIFE " + character.current_life + " MANA " + character.current_mana +
                " | Enemy LIFE " + enemy.current_life + " MANA " + enemy.current_mana + "\n");
    }

    public void pressPKey() throws InvalidCommandException {
        System.out.println("Press P to see the next move:");
        Scanner scanner = new Scanner(System.in);
        String input;
        input = scanner.nextLine();
        System.out.println();
        if (!input.equalsIgnoreCase("P")) {
            throw new InvalidCommandException();
        }
    }
}
