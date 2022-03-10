public class CharacterFactory {
    public Character getCharacter(String characterType, String name, int level, int experience) {
        if (characterType == null) {
            return null;
        }
        if (characterType.equalsIgnoreCase("Warrior")) {
            return new Warrior(name, level, experience);
        }
        if (characterType.equalsIgnoreCase("Rogue")) {
            return new Rogue(name, level, experience);
        }
        if (characterType.equalsIgnoreCase("Mage")) {
            return new Mage(name, level, experience);
        }
        return null;
    }
}
